package cn.xhp.feign;

import cn.xhp.pojo.TbDescription;
import feign.hystrix.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

//@FeignClient("service-b")： service-b是eureka服务里面service-b项目的名称，加入此注解，能直接连接service-b项目接口
// 两个坑：1. @GetMapping不支持   2. @PathVariable得设置value
@FeignClient(name = "service-b", path = "/",fallbackFactory = BFeignFallbackFactory.class)
public interface BFeign {
	
    //负载均衡测试
    @RequestMapping("ribbon")
    public String ribbon();

    //分布式事务测试
    @RequestMapping("txlcn")
    public TbDescription txlcn(@RequestBody Integer userId);
}

/**
 * BFeign熔断降级
 */
@Component
class BFeignFallbackFactory implements FallbackFactory<BFeign> {

    @Override
    public BFeign create(Throwable cause) {
        //打印异常
        System.err.println(cause.getMessage());

        //容错处理
        return new BFeign() {

            @Override
            public String ribbon() {
                return "调用service-b失败";
            }

            @Override
            public TbDescription txlcn(Integer userId) {
                return new TbDescription();
            }
        };
    }
}
