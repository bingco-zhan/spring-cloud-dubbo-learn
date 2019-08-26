package bcz.dubbo.cloud.control;

import bcz.dubbo.cloud.api.IService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class Controller {

    @Reference(version = "1.0.0", group = "dubbo")
    private IService service;

    @GetMapping("echo")
    public String echo(@RequestParam("msg") String var1) {
        return service.echo("api: " + var1);
    }
}
