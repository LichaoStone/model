package com.xuncai.parking.common.frame.dataSources;

import com.xuncai.parking.common.util.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 多数据源处理
 * @ClassName: DataSourceAspect
 * @author
 * @date 2020-02-12 18:40
 */
@Aspect
@Component
@Order(1)
//@EnableAsync
public class DataSourceAspect {
	//private static final Logger log = LoggerFactory.getLogger(DataSourceAspect.class);
	
	@Pointcut("@annotation(com.xuncai.parking.common.frame.dataSources.DataSource)")
    public void dsPointCut()
    {

    }
	
	@Around("dsPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable
    {
        MethodSignature signature = (MethodSignature) point.getSignature();

        Method method = signature.getMethod();

        DataSource dataSource = method.getAnnotation(DataSource.class);

        if (null!=dataSource)
        {
            DataSourceContextHolder.setDataSource(dataSource.value().name());
        }

        try
        {
            return point.proceed();
        }
        finally
        {
            // 销毁数据源 在执行方法之后
        	DataSourceContextHolder.clearDataSource();
        }
    }
	
	 /**
     * 获取需要切换的数据源
     */
    public DataSource getDataSource(ProceedingJoinPoint point)
    {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Class<? extends Object> targetClass = point.getTarget().getClass();
        DataSource targetDataSource = targetClass.getAnnotation(DataSource.class);
        if (!StringUtils.isEmpty(targetDataSource))
        {
            return targetDataSource;
        }
        else
        {
            Method method = signature.getMethod();
            DataSource dataSource = method.getAnnotation(DataSource.class);
            return dataSource;
        }
    }
}
