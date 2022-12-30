from django.db import models


# Create your models here.

class Category(models.Model):
	name = models.CharField(max_length=50, verbose_name="Имя")

	def __str__(self):
		return self.name


class Product(models.Model):
	name = models.CharField(max_length=100, verbose_name="Имя продукта")
	price = models.DecimalField(decimal_places=2, max_digits=9, verbose_name="Цена")
	description = models.TextField(verbose_name='Описание')
	is_unlimited = models.BooleanField(verbose_name="Товар не лимитирован в кол-ве", default=False)
	video = models.FileField(verbose_name="Видео", null=True, blank=True)
	quantity = models.PositiveIntegerField(verbose_name="Количество", default=0)
	file_source = models.FileField(verbose_name="Файл к продукту", blank=True, null=True)
	category = models.ForeignKey(Category, on_delete=models.CASCADE, verbose_name="Категория")

	def __str__(self):
		return f'{self.name} {"(" + str(self.quantity) + "шт)" if not self.is_unlimited else ""}'


class ProductPhoto(models.Model):
	product = models.ForeignKey(Product, on_delete=models.CASCADE, verbose_name="Продукт")
	image = models.FileField(verbose_name="Фото")


class Order(models.Model):
	product = models.ForeignKey(Product, on_delete=models.CASCADE, verbose_name="Продукт")
	quantity = models.PositiveIntegerField(verbose_name="Кол-во", null=True, blank=True, default=1)
	pickup = models.BooleanField(verbose_name="Самовывоз", null=True, blank=True, default=False)
	address = models.CharField(max_length=200, verbose_name="Адрес доставки", blank=True, null=True)
	payment_token = models.CharField(verbose_name="Платежный токен", blank=True, null=True, max_length=150)
	email = models.CharField(verbose_name="Email", max_length=50)
	payed = models.BooleanField(verbose_name="Оплачен", default=False)

	def __str__(self):
		address = ''

		if self.pickup:
			address = "-> Самовывоз"
		elif self.address:
			address = "-> " + self.address

		return f"{self.product.name} * {self.quantity}  {address}"
