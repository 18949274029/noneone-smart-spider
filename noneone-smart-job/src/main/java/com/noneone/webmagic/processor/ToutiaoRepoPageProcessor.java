package com.noneone.webmagic.processor;

import org.apache.commons.lang3.StringEscapeUtils;

import com.noneone.webmagic.pipeline.ConsolePipeline;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

public class ToutiaoRepoPageProcessor implements PageProcessor {
	private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);

	@Override
	public Site getSite() {
		return site;
	}

	@Override
	public void process(Page page) {
		page.putField("html",StringEscapeUtils.unescapeHtml4(page.getHtml().regex(".*articleInfo:.*").toString()));
		page.putField("links", page.getHtml().links().toString());
		// 部分二：定义如何抽取页面信息，并保存下来
		page.putField("author",
				page.getUrl().regex("https://github\\.com/(\\w+)/.*")
						.toString());
		page.putField(
				"name",
				page.getHtml()
						.xpath("//h1[@class='entry-title public']/strong/a/text()")
						.toString());
		// if (page.getResultItems().get("name") == null) {
		// //skip this page
		// page.setSkip(true);
		// }
		// System.out.println("author=="+page.getUrl().regex("https://github\\.com/(\\w+)/.*").toString());
		// System.out.println("name=="+page.getHtml().xpath("//h1[@class='entry-title public']/strong/a/text()").toString());
		page.putField("readme",
				page.getHtml().xpath("//div[@id='readme']/tidyText()"));

		// 部分三：从页面发现后续的url地址来抓取
		 page.addTargetRequests(page.getHtml().links().regex("(https://github\\.com/[\\w\\-]+/[\\w\\-]+)").all());
	}

	public static void main(String[] args) {

		Spider.create(new ToutiaoRepoPageProcessor())
				.addUrl("https://www.toutiao.com/a6488233206444196366/")
//		        .addUrl("https://github.com/code4craft")
				.addPipeline(new ConsolePipeline())
				// 开启5个线程抓取
				.thread(1)
				// 启动爬虫
				.run();
	}

}
