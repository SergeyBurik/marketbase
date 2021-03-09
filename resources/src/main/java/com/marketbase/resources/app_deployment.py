try:

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
    client.exec_command("cd /home")

    # download and execute setup.py
    client.exec_command("sudo apt install curl")
    client.exec_command(f"curl -s {host}/order/{orderId}/file/setup.py | python3 -")

    client.close()

except Exception as e:
    requests.get(f"{host}/{orderId}/debug?message={e}")
