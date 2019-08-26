package bcz.dubbo.cloud.service;

import bcz.dubbo.cloud.api.IService;
import org.apache.dubbo.config.annotation.Service;

@Service(version = "1.0.0", group = "dubbo")
public class ServiceImpl implements IService {
    @Override
    public String echo(String var1) {
        return "dubbo: " + var1;
    }
}
