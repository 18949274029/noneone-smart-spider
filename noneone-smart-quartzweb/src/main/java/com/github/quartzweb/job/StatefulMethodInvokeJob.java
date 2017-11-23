/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.github.quartzweb.job;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.PersistJobDataAfterExecution;

/**
 * @author leisure
 */
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class StatefulMethodInvokeJob extends MethodInvokeJob {

}
