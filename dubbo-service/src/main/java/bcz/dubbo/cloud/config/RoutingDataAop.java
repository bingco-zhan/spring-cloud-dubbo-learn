package bcz.dubbo.cloud.config;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(100)
public class RoutingDataAop {

    @Pointcut("@annotation(bcz.dubbo.cloud.config.Master) && execution(public * bcz.dubbo.cloud.service.*Impl..*(..))")
    public void masterPointcut() {

    }

    @Pointcut("!@annotation(bcz.dubbo.cloud.config.Master) && execution(public * bcz.dubbo.cloud.service.*Impl..*(..))")
    public void slavePointcut() {

    }

    @Before("slavePointcut()")
    public void read() {
        DBHolder.RELOAD("SLAVE");
    }

    @Before("masterPointcut()")
    public void write() {
        DBHolder.RELOAD("MASTER");
    }
}
