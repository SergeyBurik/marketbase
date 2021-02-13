import os
import pip
from sys import argv
import json
import zipfile

script, orderId, = argv


def install(package):
    pip.main(['install', package])


try:
    import pexpect
except ImportError:
    install('requests')
    import requests

# get order info
order = json.loads(requests.get(f"http://marketbase.com/orders/{orderId}").content.decode())

PROJECT_PATH = f"/webapps/{order['projectName']}_project"

os.system("cd /home")
os.system("git clone https://github.com/harikvpy/deploy-django.git")

# setup server
os.system("sh /home/deploy-django/install_os_prereq.sh 3")
os.system(f"sh /home/deploy-django/deploy_django_project.sh {order['projectName']} {order['domain']} 3")

# prepare env
os.system(f"chmod +x {PROJECT_PATH}/prepare_env.sh")
os.system(f"{PROJECT_PATH}/prepare_env.sh")

# install requirements
os.system(f"source .{PROJECT_PATH}/bin/activate")
os.system(f"pip install -r {PROJECT_PATH}/{order['projectName']}/requirements.txt")

# download project files
r = requests.get(f"http://resources.marketbase.com/order/{orderId}/project")
open(f'{PROJECT_PATH}/project_{orderId}.zip', 'wb').write(r.content)

# unpack file
with zipfile.ZipFile(f'{PROJECT_PATH}/project_{orderId}.zip', 'r') as zip_ref:
    # replace existing files with project dir
    os.system(f"rm {PROJECT_PATH}/{order['projectName']}/*")
    zip_ref.extractall(f"{PROJECT_PATH}/{order['projectName']}")

# start server
os.system(f"setsid .{PROJECT_PATH}/gunicorn_start.sh >/dev/null 2>&1 < /dev/null &")


