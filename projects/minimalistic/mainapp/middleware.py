from mainapp.models import TextObject


class SiteIsActiveMiddleware:
	def __init__(self, get_response):
		self.get_response = get_response

	def __call__(self, request):
		response = self.get_response(request)

		if request.path != "/active/":
			if TextObject.objects.get(key="active").value != "True":
				raise Exception("Site is not active")

		return response

