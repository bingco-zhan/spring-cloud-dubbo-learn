package bcz.dubbo.cloud.utils;

import org.springframework.transaction.interceptor.TransactionAspectSupport;

public class IBatisKit {

    public static void rollback() {
        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
    }
}
