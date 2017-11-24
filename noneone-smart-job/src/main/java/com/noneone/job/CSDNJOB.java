package com.noneone.job;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.noneone.webmagic.inter.ProcessorEXE;
import com.noneone.webmagic.processor.CSDNRepoPageProcessor;

public class CSDNJOB implements Job {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
      JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
      String userName = jobDataMap.getString("userName");
      int pageTotal = Integer.parseInt(jobDataMap.getString("pageTotal"));
      String authorId =  jobDataMap.getString("authorId");
      int userId = 0;
      if (authorId!=null) {
    	  userId = Integer.parseInt(authorId);
	}
      System.out.println(userName+"=="+pageTotal+"=="+userId);
      // 从用户博客首页开始抓，开启5个线程，启动爬虫
//      ProcessorEXE exe = new CSDNRepoPageProcessor();
//      exe.executeProcessor(userName, pageTotal,userId);
	}

}
