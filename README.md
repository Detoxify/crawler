# crawler
<b>Java爬虫，使用HttpClient和Jsoup</b>
现在只是一个初步的实现，可以爬取<a>https://movie.douban.com/chart</a>的影片数据，包括影片的名称、评分、类型。
使用HttpClient发起get请求，将response转换成String类型，通过Jsoup进行解析。
当前界面只能爬取排行榜上10个电影的信息。
后续会加入高评分电影的爬取，并准备存入Elasticsearch备以后使用。
