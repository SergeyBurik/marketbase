from django.apps import AppConfig
from django.conf import settings

class MainAppConfig(AppConfig):
    name = 'mainapp'

    def ready(self):
        from mainapp.models import TextObject

        # settings setup
        settings.TEMPLATES[0]["OPTIONS"]["context_processors"] \
            .extend(
                ['mainapp.context_processors.texts',
                 'mainapp.context_processors.plugins']
            )

        # check DB default values
        required_values = [
            "plugins", "com_advantages", "com_delivery",
            "com_address", "com_name", "main_video",
            "main_photo", "com_logo", "com_type",
            "com_phone_number", "com_address",
            "com_description"
        ]

        for value in required_values:
            if value == "plugins":
                # register plugins to db
                plugins = ""
                for module in open("applications.txt", "r").readlines():
                    plugins += module.split(";")[0] + ", "

                if TextObject.objects.filter(key="plugins").count() == 0:
                    TextObject.objects.create(key="plugins", value=plugins)
                else:
                    TextObject.objects.filter(key="plugins").update(value=plugins)

            if TextObject.objects.filter(key=value).count() == 0:
                print(f"Missing required value: {value}")
                TextObject.objects.create(key=value, value="")

                print(f"Default value '{value}' was created")
