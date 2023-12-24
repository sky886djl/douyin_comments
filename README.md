# douyin_comments
Java爬虫爬取抖音视频评论

1. 入参
   {
      "url:"",//视频链接url,为了标记是哪个视频的评论，具体内容无所谓，能标记视频内容即可，如https://www.douyin.com/discover?modal_id=731602246463771xxx
      "commentUrl":"",//评论url，F12找到comment/list? 如https://www.douyin.com/aweme/v1/web/comment/list/?device_platform=webapp&aid=6383...
       "pageNo":1//页号，默认从第一页开始,每页20个
   }
2. VideoService中替换你的userAgent和Cookie
3. 爬完后sleep的目的，是为了避免反爬虫监控发现，发现后cookie就不能用了，只能换一个号
4. 数据库可根据VideoInfo和CommentInfo反推，videoInfo的requestObj没用到，本想存储请求信息

本次爬取抖音视频评论信息，仅为了朋友的毕业论文目的，不作商业用途，欢迎进一步交流QQ群：923073081
