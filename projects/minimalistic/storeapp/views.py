from django.contrib import messages
from django.core.mail import send_mail
from django.http import JsonResponse, HttpResponseRedirect
from django.shortcuts import render, get_object_or_404, redirect
from django.urls import reverse
from django.views.decorators.csrf import csrf_exempt
from yandex_checkout import Payment, Configuration
import uuid
# Create your views here.
from mainapp.models import TextObject
from storeapp.models import Product, ProductPhoto, Category, Order
from mainapp import tools
from minimalistic import settings

Configuration.configure('707095', 'test_ZlfSv-xgCIBSKohaHNcifWgXrkaO3CTfEtt7sbj5FWk')


def products(request):
	p = []

	for product_ in Product.objects.all():
		p.append(tools.add_images_to_product(product_))

	context = {'products': p}

	if request.GET.get('api', False):
		for i in range(len(p)):
			p[i]['video'] = p[i]['video'].url if p[i]['video'] else ""
			p[i]['file_source'] = p[i]['file_source'].url if p[i]['file_source'] else ""
		return JsonResponse(p, safe=False)

	return render(request, 'storeapp/products.html', context)


def category(request, id):
	p = []
	for product_ in Product.objects.filter(category__id=id):
		p.append(tools.add_images_to_product(product_))

	context = {"products": p, 'category': get_object_or_404(Category, id=id)}
	return render(request, 'storeapp/category.html', context)


def product(request, id):
	product_ = get_object_or_404(Product, id=id)

	similar_prods = []
	for prod in Product.objects.filter(category=product_.category):
		similar_prods.append(tools.add_images_to_product(prod))

	similar_prods = similar_prods[:4]
	product_ = tools.add_images_to_product(product_)

	d = get_object_or_404(TextObject, key="com_delivery").value == "true"

	return render(request, 'storeapp/product.html',
	              {
		              "product": product_, "similar": similar_prods, 'host': settings.HOST,
		              "delivery": d
	              }
	              )


@csrf_exempt
def checkout(request, id):
	quantity = int(request.GET.get("quantity", 1))

	product_ = get_object_or_404(Product, id=id)

	# decrease quantity if needed
	if not product_.is_unlimited:
		# if there are enough product items in stock
		if not quantity >= 0:
			messages.error(request, "Невалидное количество")
			return HttpResponseRedirect(request.META.get('HTTP_REFERER', '/'))

		if product_.quantity < product_.quantity - quantity:
			messages.error(request, "Товара не хватает")
			return HttpResponseRedirect(request.META.get('HTTP_REFERER', '/'))

	payment = Payment.create({
		"amount": {
			"value": str(product_.price * quantity),
			"currency": "RUB"
		},
		"confirmation": {
			"type": "embedded"
		},
		"capture": True,
		"description": f"Заказ '{product_.name}'"
	}, str(uuid.uuid4()))

	# creating Order
	Order.objects.create(product=product_, email=request.POST.get("email"),
	                     payment_token=payment.confirmation.confirmation_token,
	                     quantity=quantity, pickup=True if 'pickup' in request.POST else False,
	                     address=request.POST.get("address"))

	# return confirmation url
	return JsonResponse({"token": payment.confirmation.confirmation_token})


def complete_checkout(request, token):
	order = get_object_or_404(Order, payment_token=token)

	# completing checkout after payment
	if not order.payed:
		order.payed = True

		if not order.product.is_unlimited:
			# if there are enough product items in stock
			if not order.quantity >= 0:
				messages.error(request, "Невалидное количество")
				return HttpResponseRedirect(reverse("store:product", kwargs={"id": order.product.id}))

			if order.product.quantity < order.product.quantity - order.quantity:
				messages.error(request, "Товара не хватает")
				return HttpResponseRedirect(reverse("store:product", kwargs={"id": order.product.id}))

			order.product.quantity -= order.quantity
			order.product.save()

		order.save()

		# get store address
		adr = get_object_or_404(TextObject, key="com_address").value

		message = \
			f"""Ваш заказ был создан.\nID: {order.id}\nНаименование: {order.product.name}\nКатегория: {order.product.category.name}\nКоличество: {order.quantity}\nАдрес доставки: {order.address if order.address else "Самовывоз"}\nАдрес магазина:{adr}\nИтого: {order.quantity * order.product.price}руб.\n\n"""

		if order.product.file_source:
			message += f"Вы получаете доступ к ресурсу файла продукта по ссылке: {settings.HOST}{order.product.file_source.url}"

		# sending confirmation email
		send_mail(
			f"Заказ в '{order.product.name}'", message, None,
			[order.email], fail_silently=False
		)

	return render(request, 'storeapp/complete_checkout.html',
	              {'product': order.product, 'quantity': order.quantity})
