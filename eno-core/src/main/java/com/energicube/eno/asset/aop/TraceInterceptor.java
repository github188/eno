package com.energicube.eno.asset.aop;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.aop.interceptor.CustomizableTraceInterceptor;

public class TraceInterceptor extends CustomizableTraceInterceptor {

    private static final long serialVersionUID = 2123416332511354414L;

    protected static Log logger = LogFactory.getLog("aop");

    @Override
    protected void writeToLog(Log logger, String message, Throwable ex) {
        if (ex != null) {
            logger.debug(message, ex);
        } else {
            logger.debug(message);
        }
    }

    @Override
    protected boolean isInterceptorEnabled(MethodInvocation invocation,
                                           Log logger) {
        return true;
    }

}
