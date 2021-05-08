"""teacher_prototype_first URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/3.1/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  path('', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  path('', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.urls import include, path
    2. Add a URL to urlpatterns:  path('blog/', include('blog.urls'))
"""
from django.conf.urls import url
from django.conf.urls.static import static
from django.contrib import admin
from django.urls import path, include
from des import urls as des_urls
from minimalistic import settings


urlpatterns = [
	path('admin/', admin.site.urls),
	path('django-des/', include(des_urls)),
] + static(settings.MEDIA_URL, document_root=settings.MEDIA_ROOT)


# append apps' urls
for module in open("applications.txt", 'r').readlines():
	# app type: storeapp;store;store/
	m = module.replace('\n', '')
	m = m.split(";")

	urlpatterns.append(
		path(m[2] if m[2] != "/" else "", include(f"{m[0]}.urls", namespace=m[1])),
	)

