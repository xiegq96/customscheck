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
@RequestMapping("/business")
@Api(tags="电商平台")
public class BusinessController {
    @Autowired
    private PayExchangeInfoService payExchangeInfoService;

    @CustomsLog("电商平台数据上传")
    @PostMapping("/addAllInfo")
    @ApiOperation(value = "电商平台数据上传接口")
    @ApiParam(name="payExchange",value="传入json格式",required=true)
    public ReturnData addAllInfo(
            @RequestBody PayExchange payExchange) {
      if(payExchangeInfoService.addAllInfo(JSON.toJSONString(payExchange))){
          return CommonReturnUtil.success();
      }else{
         return CommonReturnUtil.error("添加失败");
      }
    }
}



