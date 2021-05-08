from django.contrib import admin

# Register your models here.
from mainapp.models import TextObject, Review

admin.site.register(TextObject)
admin.site.register(Review)
