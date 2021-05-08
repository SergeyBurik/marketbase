from django.apps import AppConfig
from django.urls import path, include


class StoreappConfig(AppConfig):
    name = 'storeapp'

    def ready(self):
        from minimalistic import urls
        from django.conf import settings

        # settings setup
        settings.TEMPLATES[0]["OPTIONS"]["context_processors"] \
            .append('storeapp.context_processors.categories')

        # # urls setup
        # urls.urlpatterns.append(
        #     path("store/", include("storeapp.urls", namespace="store")),
        # )
