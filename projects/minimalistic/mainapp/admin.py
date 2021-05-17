from django.contrib import admin

# Register your models here.
from mainapp.models import TextObject, Review, MediaObject

admin.site.register(TextObject)
admin.site.register(MediaObject)
admin.site.register(Review)
