package com.noneone.webmagic.processor;

import java.util.List;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import com.alibaba.fastjson.JSON;
import com.noneone.DTO.Post;
import com.noneone.util.AESUtil;
import com.noneone.util.Http4ClientUtil;

/**
 * CSDN博客爬虫
 * 
 * @describe 可以爬取指定用户的csdn博客所有文章，并保存到数据库中。
 * @author leisure
 */
public class CSDNRepoPageProcessor implements PageProcessor {
    private static String noneoneblogUrl = "https://www.noneone.cn/blog/spider/spiderSubmit";
//	private static String noneoneblogUrl = "http://test.noneone.cn/noneone-test/spider/spiderSubmit";
    private static String username = "liang19890820";// 设置csdn用户名
    private static int size = 0;// 共抓取到的文章数量

    // 抓取网站的相关配置，包括：编码、抓取间隔、重试次数等
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);
    static Spider spider = Spider.create(new CSDNRepoPageProcessor()).addUrl("http://blog.csdn.net/"+username ).thread(1);
    @Override
    public Site getSite() {
        return site;
    }

    @Override
    // process是定制爬虫逻辑的核心接口，在这里编写抽取逻辑
    public void process(Page page) {
        // 列表页
        if (!page.getUrl().regex("http://blog\\.csdn\\.net/" + username + "/article/details/\\d+").match()) {
            // 添加所有文章页
            page.addTargetRequests(page.getHtml().xpath("//div[@id='article_list']").links()// 限定文章列表获取区域
                    .regex("/" + username + "/article/details/\\d+")
                    .replace("/" + username + "/", "http://blog.csdn.net/" + username + "/")// 巧用替换给把相对url转换成绝对url
                    .all());
            // 添加其他列表页
            page.addTargetRequests(page.getHtml().xpath("//div[@id='papelist']").links()// 限定其他列表页获取区域
                    .regex("/" + username + "/article/list/\\d+")
                    .replace("/" + username + "/", "http://blog.csdn.net/" + username + "/")// 巧用替换给把相对url转换成绝对url
                    .all());
            // 文章页
        } else {
            
            // 用CsdnBlog类来存抓取到的数据，方便存入数据库
            Post post = new Post();
            System.out.println("1=="+page.getHtml().xpath("//div[@class='markdown_views']").get());
            System.out.println("2=="+deleteCSDNImage(page.getHtml().xpath("//div[@class='markdown_views']").get()));
            // 设置标题
            post.setContent(
                    deleteCSDNImage(page.getHtml().xpath("//div[@class='markdown_views']").get()));
            post.setAuthorId(15);
            post.setEditor("spider-csdn");
            post.setGroup(4);
            post.setStatus(0);
            post.setTags("博客,CSDN,编程");
            post.setTitle(page.getHtml().xpath("//h1[@class='csdn_top']/allText()").get());
           String data = JSON.toJSONString(post);
		try {
			 AESUtil aes = new AESUtil("lifeifei11111111");
			 data = aes.Encrypt(data);
//            System.out.println(data);
//             把对象存入数据库
            String responseJson = Http4ClientUtil.postForm(noneoneblogUrl, "post="+data );
            System.out.println("发布==>"+responseJson);
        
            // 把对象输出控制台
//            System.out.println(JSON.toJSONString(post));
            if ("ok".equals(responseJson)) {
            	size++;// 文章数量加1
            	if (size>=1) {
            		spider.stop();
				}
            	
			}
//            Thread.sleep(1000000000);
		} catch (Exception e) {
			e.printStackTrace();
		
        }
        }
    }

     public static String deleteCSDNImage(String content){
    	 String left =  content.substring(0,content.length()-109); 
    	 String right =  content.substring(content.length()-6); 
    	 return left+right;
     }
     
    // 把list转换为string，用,分割
    public static String listToString(List<String> stringList) {
        if (stringList == null) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        boolean flag = false;
        for (String string : stringList) {
            if (flag) {
                result.append(",");
            } else {
                flag = true;
            }
            result.append(string);
        }
        return result.toString();
    }

    public static void main(String[] args) {
        long startTime, endTime;
        System.out.println("【爬虫开始】请耐心等待一大波数据到你碗里来...");
        startTime = System.currentTimeMillis();
        // 从用户博客首页开始抓，开启5个线程，启动爬虫
        spider.run();
        endTime = System.currentTimeMillis();
        System.out.println("【爬虫结束】共抓取" + size + "篇文章，耗时约" + ((endTime - startTime) / 1000) + "秒，已保存到数据库，请查收！");
    }
}