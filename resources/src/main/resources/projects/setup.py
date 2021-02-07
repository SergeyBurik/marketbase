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

os.system("cd /home")
os.system("git clone https://github.com/harikvpy/deploy-django.git")

# setup server
os.system("sh /home/deploy-django/install_os_prereq.sh 3")
os.system(f"sh /home/deploy-django/deploy_django_project.sh {order['projectName']} {order['domain']} 3")

# download project files
r = requests.get(f"http://resources.marketbase.com/order/{orderId}/project")
open(f'project_{orderId}.zip', 'wb').write(r.content)

# unpack file
with zipfile.ZipFile(f'project_{orderId}.zip', 'r') as zip_ref:
    zip_ref.extractall(f"/home/webapps/{order['projectName']}/{order['projectName']}")

# start server
os.system(f"sh /home/webapps/{order['projectName']}/gunicorn_start.sh")


