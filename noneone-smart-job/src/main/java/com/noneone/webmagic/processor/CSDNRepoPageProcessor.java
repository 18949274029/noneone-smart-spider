package com.noneone.webmagic.processor;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import com.alibaba.fastjson.JSON;
import com.noneone.DTO.Post;
import com.noneone.util.AESUtil;
import com.noneone.util.Http4ClientUtil;
import com.noneone.webmagic.inter.ProcessorEXE;

/**
 * CSDN博客爬虫
 * 
 * @describe 可以爬取指定用户的csdn博客所有文章，并保存到数据库中。
 * @author leisure
 */
public class CSDNRepoPageProcessor   implements PageProcessor,ProcessorEXE{
    private static String noneoneblogUrl = "https://www.noneone.cn/blog/spider/spiderSubmit";
//	private static String noneoneblogUrl = "http://test.noneone.cn/noneone-test/spider/spiderSubmit";
    private static String SPIDER_USERNAME = "";// 设置csdn用户名
    private static int SPIDER_PAGETOTAL = 0;//抓取量
    private static int size = 0; // 共抓取到的文章数量
    private static Spider spider;
    private static int readSize = 0;//读取总篇数
    private static int AUTHORID=15;
//    CSDNRepoPageProcessor(){}
//    CSDNRepoPageProcessor(String userName,int pageTotal){
//    	this.username = userName;
//    	this.pageTotal = pageTotal;
//    	
//    }
    // 抓取网站的相关配置，包括：编码、抓取间隔、重试次数等
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);
    @Override
    public Site getSite() {
        return site;
    }

    @Override
    // process是定制爬虫逻辑的核心接口，在这里编写抽取逻辑
    public void process(Page page) {
        // 列表页
        if (!page.getUrl().regex("http://blog\\.csdn\\.net/" + SPIDER_USERNAME + "/article/details/\\d+").match()) {
            // 添加所有文章页
//            page.addTargetRequests(page.getHtml().xpath("//div[@id='article_list']").links()// 限定文章列表获取区域
//                    .regex("/" + SPIDER_USERNAME + "/article/details/\\d+")
//                    .replace("/" + SPIDER_USERNAME + "/", "http://blog.csdn.net/" + SPIDER_USERNAME + "/")// 巧用替换给把相对url转换成绝对url
//                    .all());
//            // 添加其他列表页
//            page.addTargetRequests(page.getHtml().xpath("//div[@id='papelist']").links()// 限定其他列表页获取区域
//                    .regex("/" + SPIDER_USERNAME + "/article/list/\\d+")
//                    .replace("/" + SPIDER_USERNAME + "/", "http://blog.csdn.net/" + SPIDER_USERNAME + "/")// 巧用替换给把相对url转换成绝对url
//                    .all());
            page.addTargetRequests(page.getHtml().xpath("//div[@class='content clearfix']").links()// 限定文章列表获取区域
                    .regex("/" + SPIDER_USERNAME + "/article/details/\\d+")
                    .replace("/" + SPIDER_USERNAME + "/", "http://blog.csdn.net/" + SPIDER_USERNAME + "/")// 巧用替换给把相对url转换成绝对url
                    .all());
            // 文章页
        } else {
            
            // 用CsdnBlog类来存抓取到的数据，方便存入数据库
            Post post = new Post();
            post.setContent(
                    deleteCSDNImage(page.getHtml().xpath("//div[@class='markdown_views']").get(),false));
            if (StringUtils.isEmpty(post.getContent())) {
            	 post.setContent(
                         deleteCSDNImage(page.getHtml().xpath("//div[@class='article_content csdn-tracking-statistics']").get(),false));
			}
            post.setAuthorId(AUTHORID);
            post.setEditor("spider-csdn");
            post.setGroup(1);
            post.setStatus(0);
            post.setTags("IT博文,IT,编程");
            post.setTitle(page.getHtml().xpath("//h1[@class='csdn_top']/allText()").get());
           String data = JSON.toJSONString(post);
		try {
			if (post.getTitle()==null||post.getContent()==null) {
				return;
			}
//			System.out.println(JSON.toJSONString(post));
			 AESUtil aes = new AESUtil("lifeifei11111111");
			 data = aes.Encrypt(data);
            String responseJson = Http4ClientUtil.postForm(noneoneblogUrl, "post="+data );
            System.out.println("发布==>"+responseJson);
        
            System.out.println(JSON.toJSONString(post));
            if ("ok".equals(responseJson)) {
            	size++;// 文章数量加1
            	readSize++;
            	if (size>=SPIDER_PAGETOTAL-1) {
            		spider.stop();
				} 
            	
			}else{
				readSize++;
			}
            if (readSize>10000) {//读一百篇就不加载了
            	spider.stop();
			}
//            Thread.sleep(1000000000);
		} catch (Exception e) {
			e.printStackTrace();
		
        }
        }
    }

     public static String deleteCSDNImage(String content,boolean flag){
    	 if (flag==false) {
			return content;
		}
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
    	 ProcessorEXE exe = new CSDNRepoPageProcessor();
       exe.executeProcessor("foamflower",100,15);
    }
	@Override
	public void executeProcessor(String userName,int pageTotal,int authorIds) {
		  this.SPIDER_USERNAME = userName;
		  this.SPIDER_PAGETOTAL = pageTotal;
		  if (authorIds!=0) {
			  this.AUTHORID = authorIds;
		    }
		  long startTime, endTime;
	        System.out.println("【爬虫开始】请耐心等待一大波数据到你碗里来...");
	        startTime = System.currentTimeMillis();
	        // 从用户博客首页开始抓，开启5个线程，启动爬虫
	        spider=  Spider.create(new CSDNRepoPageProcessor()).addUrl("http://blog.csdn.net/"+SPIDER_USERNAME ).thread(2);
	        spider.run();
	        endTime = System.currentTimeMillis();
	        System.out.println("【爬虫结束】共抓取" + size + "篇文章，耗时约" + ((endTime - startTime) / 1000) + "秒，已保存到数据库，请查收！");
	}
}