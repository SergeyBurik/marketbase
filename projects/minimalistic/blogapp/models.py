from django.db import models


# Create your models here.

class Post(models.Model):
	title = models.CharField(verbose_name="Название стастьи", max_length=150)
	preview = models.FileField(verbose_name="Обложка статьи")
	annotation = models.TextField(verbose_name="Аннотация (краткое описание статьи)")
	date = models.DateTimeField(auto_now_add=True)

	def __str__(self):
		return self.title


class PostItem(models.Model):
	post = models.ForeignKey(Post, verbose_name="Статья", on_delete=models.CASCADE)
	header = models.CharField(verbose_name="Подзаголовок", max_length=200)
	content = models.TextField(verbose_name="Текст абзаца")

	def __str__(self):
		return f"Post #{self.post.id}: {self.header}"


class PostItemMedia(models.Model):
	post_item = models.ForeignKey(PostItem, verbose_name="Абзац", on_delete=models.CASCADE)
	media_type = models.CharField(
		verbose_name="Тип файла", max_length=50,
		choices=(("video", "video"), ("image", "image"))
	)
	file = models.FileField(verbose_name="Файл")

	def __str__(self):
		return self.post_item.header
