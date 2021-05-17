from django.http import JsonResponse
from django.shortcuts import render, redirect, get_object_or_404

# Create your views here.
from django.views.decorators.csrf import csrf_exempt

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

@csrf_exempt
def active(request):
	if request.method == "POST":
		TextObject.objects.filter(key="active")\
			.update(value=True if request.POST.get("active") == "true" else False)

	return JsonResponse({"code": 200})

