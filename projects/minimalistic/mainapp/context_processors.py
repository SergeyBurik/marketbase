from django.shortcuts import get_object_or_404
from mainapp.models import TextObject


def texts(request):
	return {"text": {entity.key: entity.value for entity in TextObject.objects.all()}}


def plugins(request):
	return get_object_or_404(TextObject, key="plugins").value.split(", ")


