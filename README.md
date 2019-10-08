# WeiBoSpider
获取微博评论下的图片和指定博主的微博相册图片
通过分享链接可用获得微博id
如：http：//weibo.com/2574279745/I95aSoCCF
中的  I95aSoCCF 就是这条微博的ID，
如果要获取博主的所以普图片可以不用传入itemid 
spider spider = new spider(Cookie, itemId, uid);
Cookie的获取方法为：
访问weibo.cn 登陆后 点击F12 ——> network ——> weibo.cn，刷新后看到Cookie，复制出来即可。
uid的获取方法：
在想要下载博主的页面，点击分享，复制链接，
如 http://weibo.com/u/5374280642中 5374280642就是uid，
下载完成的图片会保存在D盘weibo / uid + img的路径下
