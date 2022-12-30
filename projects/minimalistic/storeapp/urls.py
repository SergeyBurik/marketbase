from django.urls import path

from storeapp import views

app_name = "storeapp"

urlpatterns = [
	path('products/', views.products, name="products"),
	path('category/<int:id>/', views.category, name="category"),
	path('product/<int:id>/', views.product, name="product"),

	path('product/<int:id>/checkout/', views.checkout, name="checkout"),

	path('order/<str:token>/complete/', views.complete_checkout, name="complete_checkout"),
]