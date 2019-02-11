package cn.bobo.fast.controller;
import cn.bobo.fast.bean.ExOrderProduct;
import cn.bobo.fast.bean.PayExchange;
import cn.bobo.fast.common.utils.R;
import cn.bobo.fast.service.PayExchangeInfoService;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by ${Zhangjh} on 2019/1/10 0010.
 */
@RestController
@RequestMapping("/receive")
@Api(tags="处理海关数据接口")
public class ReceiveHandleController {
    @Autowired
    private PayExchangeInfoService payExchangeInfoService;

    @PostMapping("/addAllInfo")
    @ApiOperation(value = "处理海关数据接口")
    public R addAllInfo(
            @ApiParam(name="表头、订单、商品信息",value="传入json格式",required=true)
            @RequestBody PayExchange payExchange) {
      return payExchangeInfoService.addAllInfo(JSON.toJSONString(payExchange));
    }
}



