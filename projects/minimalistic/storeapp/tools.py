from django.forms import model_to_dict

from storeapp.models import Product, ProductPhoto


def add_images_to_product(product: Product):
	prod = model_to_dict(product)
	prod['images'] = [f.image.url for f in ProductPhoto.objects.filter(product=product)]
	return prod