package cn.xhp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.xhp.feign.BFeign;
import cn.xhp.feign.CFeign;
import cn.xhp.service.TestService;

@RestController
public class TestController {

    @Autowired
    private TestService testService;

    @Autowired
    private BFeign bFeign;

    @Autowired
    private CFeign cFeign;

    @RequestMapping("ribbon")
    public String ribbon(){
        return bFeign.ribbon();
    }

    @RequestMapping("feign")
    public String feign(){
        return cFeign.feign();
    }

    @RequestMapping("txlcn")
    public String txlcn(String exFlag){
        return testService.txlcn(exFlag);
    }
}
