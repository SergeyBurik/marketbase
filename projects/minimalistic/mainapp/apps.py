from django.apps import AppConfig
from django.conf import settings


class MainAppConfig(AppConfig):
    name = 'mainapp'

    def ready(self):
        from mainapp.models import TextObject
        from mainapp.models import MediaObject

        # settings setup
        settings.TEMPLATES[0]["OPTIONS"]["context_processors"] \
            .extend(
                ['mainapp.context_processors.texts',
                 'mainapp.context_processors.plugins',
                 'mainapp.context_processors.media']
            )

        # check DB default values
        required_text_values = [
            "active",
            "plugins", "com_advantages", "com_delivery",
            "com_address", "com_name",
            "com_type", "com_phone_number",
            "com_address", "com_description"
        ]

        for value in required_text_values:
            if value == "plugins":
                # register plugins to db
                plugins = ""
                for module in open("applications.txt", "r").readlines():
                    plugins += module.split(";")[0] + ", "

                if TextObject.objects.filter(key="plugins").count() == 0:
                    # set default values
                    TextObject.objects.create(key="plugins", value=plugins)
                else:
                    # update plugins
                    TextObject.objects.filter(key="plugins").update(value=plugins)

            # check if site is active
            if value == "active":
                if TextObject.objects.filter(key="active").count() == 0:
                    TextObject.objects.create(key="active", value="false")

            if TextObject.objects.filter(key=value).count() == 0:
                print(f"Missing required value: {value}")

                # set default value
                TextObject.objects.create(key=value, value="")

                print(f"Default value '{value}' was created")

        required_media_values = ["main_video", "main_photo", "com_logo"]
        for value in required_media_values:
            if MediaObject.objects.filter(key=value).count() == 0:
                print(f"Missing required value: {value}")
                MediaObject.objects.create(key=value)

