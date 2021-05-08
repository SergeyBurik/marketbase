from django.shortcuts import render, redirect, get_object_or_404

# Create your views here.
from mainapp.models import TextObject, Review
from minimalistic.settings import TEMPLATES


def index(request):
	r = Review.objects.all()
	advs = TextObject.objects.get(key="com_advantages").value.split(", ")

	return render(request, 'mainapp/index1.html', {"advantages": advs, "reviews": r})


def leave_comment(request):
	if request.method == "POST":
		Review.objects.create(name=request.POST.get("name"), comment=request.POST.get("comment"))
		return redirect("/")
