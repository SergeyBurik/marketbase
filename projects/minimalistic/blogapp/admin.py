from django.contrib import admin

# Register your models here.
from blogapp.models import Post, PostItem, PostItemMedia

admin.site.register(Post)
admin.site.register(PostItem)
admin.site.register(PostItemMedia)