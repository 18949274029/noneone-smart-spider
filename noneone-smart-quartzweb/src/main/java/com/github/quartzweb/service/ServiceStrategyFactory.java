package com.github.quartzweb.service;

import com.github.quartzweb.service.strategy.ServiceStrategy;

/**
 * @author leisure
 */
public interface ServiceStrategyFactory {

    /**
     * 创建策略
     * @param url
     * @return
     */
    public ServiceStrategy createStrategy(String url);

}
