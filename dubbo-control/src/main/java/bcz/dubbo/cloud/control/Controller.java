package bcz.dubbo.cloud.control;

import bcz.dubbo.cloud.api.IService;
import bcz.dubbo.cloud.entity.Order;
import bcz.dubbo.cloud.service.OrderService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class Controller {

    @Reference(
            version = "1.0.0",
            group = "dubbo",
            check = false,
            filter = "sentinel.dubbo.consumer.filter"
    )
    private IService service;

    @Autowired
    private OrderService orderService;

    @GetMapping("echo")
    public String echo(@RequestParam("msg") String var1) {
        return service.echo("api: " + var1);
    }

    @GetMapping("addOdr")
    public String addOdr(Order order) {
        String errorMsg = orderService.addOdr(order);
        return errorMsg == null ? "OK" : errorMsg;
    }
}
