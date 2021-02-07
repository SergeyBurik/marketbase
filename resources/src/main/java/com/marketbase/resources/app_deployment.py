from sys import argv
import paramiko
import requests
import time

# parsing args
script, orderId, host, ip, user, password, domain = argv

# connect to ssh
client = paramiko.SSHClient()
client.set_missing_host_key_policy(paramiko.AutoAddPolicy())
client.connect(hostname=ip, username=user, password=password, port=22)

# enter sudo mode
client.exec_command(f"echo {password} && sudo -s")

# create clone deploy script
client.exec_command("cd /home;")

# download and execute setup.py
client.exec_command("sudo apt install curl")
client.exec_command(f"curl -O {host}/order/{orderId}/file/setup.py")
client.exec_command(f"python3 setup.py {orderId}")

client.close()
