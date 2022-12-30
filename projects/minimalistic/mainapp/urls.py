from django.urls import path
from mainapp import views

app_name = "mainapp"

urlpatterns = [
	path("", views.index, name='main'),
	path("leave_comment/", views.leave_comment, name='leave_comment'),
	path("active/", views.active, name='active'),
]
