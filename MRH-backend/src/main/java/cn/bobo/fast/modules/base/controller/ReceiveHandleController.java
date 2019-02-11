package cn.bobo.fast.modules.base.controller;

import cn.bobo.fast.common.annotation.CustomsLog;
import cn.bobo.fast.common.annotation.SysLog;
import cn.bobo.fast.common.utils.R;
import cn.bobo.fast.modules.base.bean.PayExchange;
import cn.bobo.fast.modules.base.service.PayExchangeInfoService;
import cn.bobo.fast.modules.base.utils.CommonReturnUtil;
import cn.bobo.fast.modules.base.utils.ReturnData;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/receive")
@Api(tags="处理海关数据接口")
public class ReceiveHandleController {
    @Autowired
    private PayExchangeInfoService payExchangeInfoService;

    @CustomsLog("商家平台")
    @PostMapping("/addAllInfo")
    @ApiOperation(value = "处理海关数据接口")
    public ReturnData addAllInfo(
            @ApiParam(name="表头、订单、商品信息",value="传入json格式",required=true)
            @RequestBody PayExchange payExchange) {
      if(payExchangeInfoService.addAllInfo(JSON.toJSONString(payExchange))){
          return CommonReturnUtil.success();
      }else{
         return CommonReturnUtil.error("添加失败");
      }
    }
}



