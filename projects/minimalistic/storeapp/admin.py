from django.contrib import admin

# Register your models here.
from storeapp.models import Product, ProductPhoto, Category, Order


class ProductPhotoInline(admin.TabularInline):
	model = ProductPhoto


class ProductAdmin(admin.ModelAdmin):
	inlines = [ProductPhotoInline]

admin.site.register(Product, ProductAdmin)
admin.site.register(ProductPhoto)
admin.site.register(Category)
admin.site.register(Order)