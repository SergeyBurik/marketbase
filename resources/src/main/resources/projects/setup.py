import os
import pip
from sys import argv
import zipfile

try:
    import requests
except ImportError:
    install('requests')
    import requests

def log(type: str, message: str):
    requests.post(
        f"{resourcesHost}/deploy/{orderId}/debug", json={
            "type": type, "message": message
        }
    )

try:

    script, orderId, resourcesHost, projectName, domainName = argv

    log("INFO", "Deploying app...")

    def install(package):
        pip.main(['install', package])


    PROJECT_PATH = f"/webapps/{projectName}_project"

    os.system("cd /home")
    os.system("git clone https://github.com/harikvpy/deploy-django.git")

    log("DEBUG", "Cloned deploy-django repository")

    # setup server
    os.system("sh /home/deploy-django/install_os_prereq.sh 3")
    os.system(f"sh /home/deploy-django/deploy_django_project.sh {projectName} {domainName} 3")

    log("INFO", "Server config has been created")

    # prepare env
    os.system(f"chmod +x {PROJECT_PATH}/prepare_env.sh")
    os.system(f"{PROJECT_PATH}/prepare_env.sh")

    log("DEBUG", "Environment has been prepared")


    # download project files
    r = requests.get(f"http://resources.marketbase.com/order/{orderId}/project")
    open(f'{PROJECT_PATH}/project_{orderId}.zip', 'wb').write(r.content)

    log("INFO", "Project files have been downloaded from server")

    # unpack file
    with zipfile.ZipFile(f'{PROJECT_PATH}/project_{orderId}.zip', 'r') as zip_ref:
        # replace existing files with project dir
        os.system(f"rm {PROJECT_PATH}/{projectName}/*")
        zip_ref.extractall(f"{PROJECT_PATH}/{projectName}")

    log("DEBUG", "Project files have been unzipped")

    # install requirements
    os.system(f"source .{PROJECT_PATH}/bin/activate")
    os.system(f"pip install -r {PROJECT_PATH}/{projectName}/requirements.txt")

    log("DEBUG", "Requirements have been installed")

    # start server
    os.system(f"setsid .{PROJECT_PATH}/gunicorn_start.sh >/dev/null 2>&1 < /dev/null &")

    log("INFO", "Server started")

    # confirm deployment
    requests.post(f"{resourcesHost}/deploy/{orderId}/confirm")
except Exception as err:
    log("ERROR", err)

