from django.forms import model_to_dict

from storeapp.models import Category


def categories(request):
	return {"categories": [model_to_dict(c) for c in Category.objects.all()]}

