package cn.bobo.fast.modules.base.controller;

import cn.bobo.fast.common.utils.PageUtils;
import cn.bobo.fast.common.utils.R;
import cn.bobo.fast.modules.base.bean.ExOrderProduct;
import cn.bobo.fast.modules.base.bean.PayExchange;
import cn.bobo.fast.modules.base.entity.BaseExchange;
import cn.bobo.fast.modules.base.service.BaseExchangeService;
import cn.bobo.fast.modules.base.utils.CommonReturnUtil;
import cn.bobo.fast.modules.base.utils.ReturnData;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Api(tags="基础信息/表头")
@RestController
@RequestMapping("/baseExchange")
@ApiIgnore
public class BaseExchangeController {
    @Autowired
    private BaseExchangeService baseExchangeService;

    @ApiOperation(value = "根据系统序号查询明细")
    @PostMapping("/getExchangeListByGuid/{guid}")
    @ApiImplicitParams({
            @ApiImplicitParam(name="guid",value = "系统序号",paramType ="path",dataType = "string")
    })
    public ReturnData getExchangeList(@PathVariable("guid") String guid){
        System.out.println("接收的参数是"+guid);
        List<BaseExchange> exchangeList = baseExchangeService.getExchangeList(guid);
        return CommonReturnUtil.success(exchangeList);
    }

    @ApiOperation(value = "添加基础信息")
    @PostMapping("/addExchange")
    public ReturnData addExchange(@RequestBody BaseExchange exchange){
        if(baseExchangeService.insert(exchange)){
           return CommonReturnUtil.success();
        }else{
           return CommonReturnUtil.error("添加失败");
        }
    }

    @ApiOperation(value = "根据系统序号查询订单商品信息")
    @PostMapping("/getExProductListByGuid/{guid}")
    @ApiImplicitParams({
            @ApiImplicitParam(name="guid",value = "系统序号",paramType ="path",dataType = "string")
    })
    public ReturnData getProductInfoByGuid(@PathVariable String guid){
        List<ExOrderProduct> exProductList = baseExchangeService.getProductInfoByGuid(guid);
        return CommonReturnUtil.success(exProductList);
    }

    @ApiOperation(value = "根据系统序号删除信息")
    @PostMapping("/deleteExchange/{guids}")
    @ApiImplicitParams({
            @ApiImplicitParam(name="guids",value = "系统序号",paramType ="path",
                    allowMultiple=true,dataType = "array")
    })
    public ReturnData deleteExchange(@PathVariable String[] guids){
        for(int i = 0;i < guids.length;i++){
            System.out.println("接收参数是"+guids[i]);
        }
       if(baseExchangeService.deleteExchange(guids)){
           return CommonReturnUtil.success();
       }else{
           return CommonReturnUtil.error("删除失败");
       }
    }

    @ApiOperation(value = "列表")
    @GetMapping("/queryPage")
    @ApiImplicitParams({
            @ApiImplicitParam(name="currPage",value = "当前页",paramType ="query",dataType = "int",required = true),
            @ApiImplicitParam(name="pageSize",value = "页面大小",paramType ="query",dataType = "int",required = true),
            @ApiImplicitParam(name="guid",value = "系统序号",paramType ="query",dataType = "string")
    })
    public ReturnData queryPage(@RequestParam Integer currPage,
                       @RequestParam Integer pageSize,
                       @RequestParam(value = "guid",required = false)String guid){
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("page",currPage.toString());
        params.put("limit",pageSize.toString());
        params.put("guid",guid);
        PageUtils page = baseExchangeService.queryPage(params);
       return CommonReturnUtil.success(page);
    }
}
