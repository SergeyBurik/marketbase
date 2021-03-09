import ftplib
from sys import argv
import paramiko
import os


def file_write(conn: paramiko.SSHClient, path: str, content: str, ftype: str = "w") -> None:
	sftp = conn.open_sftp()
	f = sftp.open(path, ftype)
	f.write(content)
	f.close()


# parsing args
script, projectPath, projectName, ip, user, password, domain = argv
print("Parsed args")

# connect to ssh
client = paramiko.SSHClient()
client.set_missing_host_key_policy(paramiko.AutoAddPolicy())
client.connect(hostname=ip, username=user, password=password, port=22)
stdin, stdout, stderr = client.exec_command(f"echo {password} && sudo -s")

data = stdout.read() + stderr.read()
print(data.decode())

print("Established SSH connection")

# create deploy directory
stdin, stdout, stderr = client.exec_command(f'cd /home; mkdir -p deploy/{projectName}; ls deploy -l')
# data = stdout.read() + stderr.read()
# print(data.decode())

print("Created deploy directory")

# uploading file
session = ftplib.FTP(ip)
session.set_debuglevel(2)

session.login(user, password)
# session.sendcmd("USER root")
# session.sendcmd(f"PASS {password}")
print("Established FTP connection")

file = open(projectPath, 'rb')  # file to send

print("Started uploading file")
session.storbinary(f'STOR /home/deploy/{projectName}/{projectName}.zip', file)  # send the file
file.close()  # close file and FTP
print("Uploaded successfully")

session.quit()

# server setup

# create virtual env
client.exec_command(f"cd /home/deploy/{projectName}")
client.exec_command(f"pip3 install gunicorn; sudo apt-get install unzip; sudo apt install nginx; pip3 install venv; python3 -m venv env")

# unzip file
print(f"Unzipping: {os.path.basename(projectPath)}")
client.exec_command(f"unzip {projectName}.zip")

# create venv and install packages
client.exec_command(f"python3 -m venv env; source {projectName}/env/bin/activate")
client.exec_command(f"pip install -r {projectName}/requirements.txt")

# create gunicorn service
file_write(client, f'/etc/systemd/system/{projectName}.service',
f"""
[Unit]
Description=gunicorn daemon
Requires={projectName}.socket
After=network.target
			
			
[Service]
User={user}
Group=www-data
WorkingDirectory=/home/deploy/{projectName}
ExecStart=/home/deploy/{projectName}/env/bin/gunicorn \
    --access-logfile - \
    --workers 3 \
    --bind unix:/home/deploy/{projectName}/{projectName}.sock \
    {projectName}.wsgi:application

[Install]
WantedBy=multi-user.target
""")

# create socket
file_write(client, f'/etc/systemd/system/{projectName}.socket',
f"""
[Unit]
Description=gunicorn socket

[Socket]
ListenStream=/home/deploy/{projectName}/{projectName}.sock

[Install]
WantedBy=sockets.target
""")

# nginx configuration
file_write(client, f'/etc/nginx/sites-available/{projectName}',
"""
server {
	listen 80;
    server_name {0};

    location = /favicon.ico """.format(projectName) + '{ access_log off; log_not_found off; }' + """
    location /static/ {
        root /home/deploy/{0}/;
    }
    
    location /media/ {
        root /home/deploy/{0}/;
    }

    location / {
        include proxy_params;
        proxy_pass http://unix:/home/deploy/{0}/{0}.sock;
    }
}
""".format(projectName))

# link configuration
client.exec_command(
	f"ln -s /etc/nginx/sites-available/{projectName} /etc/nginx/sites-enabled/{projectName}"
)

client.exec_command("service gunicorn restart")
client.exec_command("service nginx restart")

client.exec_command(f"systemctl start {projectName}.socket; systemctl enable {projectName}.socket")

client.close()
