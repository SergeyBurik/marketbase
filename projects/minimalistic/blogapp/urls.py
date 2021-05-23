from django.urls import path
from blogapp import views

app_name = "blogapp"

urlpatterns = [
	path("", views.main, name="main"),
	path("post/<int:id>/", views.post, name="post"),
	path("posts/", views.posts, name="posts")
]
