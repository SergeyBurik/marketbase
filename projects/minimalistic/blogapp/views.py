from django.forms import model_to_dict
from django.http import JsonResponse
from django.shortcuts import render, get_object_or_404

# Create your views here.
from blogapp.models import Post, PostItem, PostItemMedia


def main(request):
	posts = Post.objects.all().order_by("-date")

	return render(request, "blogapp/main.html", {"last_article": posts[0], "posts": posts[1:]})


def post(request, id):
	p = get_object_or_404(Post, id=id)
	post_items = []

	for item in PostItem.objects.filter(post__id=id):
		t = model_to_dict(item)
		t["media"] = []

		for media in PostItemMedia.objects.filter(post_item__id=item.id):
			t["media"].append(model_to_dict(media))

		post_items.append(t)

	return render(request, 'blogapp/post.html', {"post": p, "post_items": post_items})


def posts(request):
	posts_ = []
	for post in Post.objects.all():
		p = model_to_dict(post)
		p['preview'] = post.preview.url

		posts_.append(p)

	return JsonResponse(posts_, safe=False)
