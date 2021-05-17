from django.forms import model_to_dict
from django.shortcuts import get_object_or_404
from mainapp.models import TextObject, MediaObject


def texts(request):
	return {"text": {entity.key: entity.value for entity in TextObject.objects.all()}}


def plugins(request):
	return {"plugins": get_object_or_404(TextObject, key="plugins").value.split(", ")}


def media(request):
	return {"media": {obj.key: obj.file for obj in MediaObject.objects.all()}}

