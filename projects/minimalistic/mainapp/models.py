from django.db import models


# Create your models here.
class TextObject(models.Model):
	key = models.CharField(max_length=50, verbose_name="Id текстовой записи", unique=True)
	value = models.TextField(verbose_name="Значение текстовой записи")

	def __str__(self):
		return self.key


class MediaObject(models.Model):
	key = models.CharField(max_length=50, verbose_name="Media key")
	file = models.FileField(verbose_name="File")

	def __str__(self):
		return self.key


class Review(models.Model):
	name = models.CharField(max_length=50, verbose_name="Имя комментатора")
	comment = models.TextField(verbose_name="Отзыв")

	def __str__(self):
		return self.name
