package com.prive.ordering.controller;

import com.prive.ordering.common.Result;
import com.prive.ordering.model.PriveOrder;
import com.prive.ordering.service.PriveOrderService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("ping")
@Slf4j
public class PingController {

    private final Logger logger= LoggerFactory.getLogger(PingController.class);

    @Resource
    private PriveOrderService priveOrderService;

    @RequestMapping(value = "/postAnOrder", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Result postAnOrder(@RequestBody PriveOrder request)
    {
        System.out.println(request.toString());
        int appendex = (int)(Math.random()*100000);
        java.util.Date day=new Date();
        SimpleDateFormat sdf= new SimpleDateFormat("yyyyMMddHHmmss");
        String id = sdf.format(day).toString()+String.valueOf(appendex);
        request.setId(id);
        request.setStatus("placed");
        //ask broker

        priveOrderService.save(request);

        System.out.println(sdf.format(day).toString()+String.valueOf(appendex));

        return Result.ok().put("id",id);
    }

    @RequestMapping(value = "/simulateBrokerFeedback", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Result simulateBrokerFeedback(@RequestBody PriveOrder request) {

        String status = request.getStatus().equalsIgnoreCase("accepted")? "fullfield":"rejected";
        return myUpdateByid(request.getId(), status);
    }

    @RequestMapping(value = "/getPriveOrderList", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public Result getPriveOrderList(){
        List<PriveOrder> data = priveOrderService.mygetList();
        return Result.ok().put("data",data);
    }

    @Async
    public Result myUpdateByid(String id, String status)
    {
        try {
            PriveOrder order = priveOrderService.getById(id);
            if(order.getStatus().equalsIgnoreCase("placed"))
            {
                order.setStatus(status);
                priveOrderService.updateById(order);
            }
            else {
                return Result.error().put("error","request has already been processed.");
            }
        }catch(Exception e){
            System.out.println("Ayync err"+e);
        }
        return Result.ok();
    }
}
