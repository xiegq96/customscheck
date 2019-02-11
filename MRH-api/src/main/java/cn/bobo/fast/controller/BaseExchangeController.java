package cn.bobo.fast.controller;

import cn.bobo.fast.bean.ExOrderProduct;
import cn.bobo.fast.common.utils.PageUtils;
import cn.bobo.fast.common.utils.R;
import cn.bobo.fast.entity.BaseExchange;
import cn.bobo.fast.service.BaseExchangeService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ${Zhangjh} on 2019/1/4 0004.
 */
@Api(tags="支付接口")
@RestController
@RequestMapping("/baseExchange")
public class BaseExchangeController {
    @Autowired
    private BaseExchangeService baseExchangeService;
    @ApiOperation(value = "根据系统序号查询明细")
    @PostMapping("/getExchangeListByGuid")
    @ApiImplicitParams({
            @ApiImplicitParam(name="guid",value = "系统序号",paramType ="query",dataType = "string")
    })
    public R getExchangeList(@RequestParam String guid){
        List<BaseExchange> exchangeList = baseExchangeService.getExchangeList(guid);
        return R.ok().put("list",exchangeList);
    }

    @ApiOperation(value = "添加支付信息")
    @PostMapping("/addExchange")
    @ApiImplicitParams({
            @ApiImplicitParam(name="guid",value = "系统序号",paramType ="query",required = true,dataType = "string"),
            @ApiImplicitParam(name = "initalRequest",value = "原始请求",required = true,paramType = "query",dataType = "string"),
            @ApiImplicitParam(name = "initalResponse",value = "商品响应",required = true,paramType = "query",dataType = "string"),
            @ApiImplicitParam(name = "ebpCode",value = "电商平台代码",required = true,paramType = "query",dataType = "string"),
            @ApiImplicitParam(name = "payCode",value = "支付企业代码",required = true,paramType = "query",dataType = "string"),
            @ApiImplicitParam(name = "payTransactionId",value = "交易流水号",required = true,paramType = "query",dataType = "string"),
            @ApiImplicitParam(name = "totalAmount",value = "交易金额",required = true,paramType = "query",dataType = "string"),
            @ApiImplicitParam(name = "currency",value = "币制",required = true,paramType = "query",dataType = "string"),
            @ApiImplicitParam(name = "verDept",value = "核验机构",required = true,paramType = "query",dataType = "string"),
            @ApiImplicitParam(name = "payType",value = "支付类型",required = false,paramType = "query",dataType = "string"),
            @ApiImplicitParam(name = "tradingTime",value = "交易成功时间",required = true,paramType = "query",dataType = "string"),
            @ApiImplicitParam(name = "note",value = "备注",paramType = "query",  required =false, dataType = "string")
    })
    public R addExchange(@RequestParam String guid,
                         @RequestParam String initalRequest,
                         @RequestParam String initalResponse,
                         @RequestParam String ebpCode,
                         @RequestParam String payCode,
                         @RequestParam String payTransactionId,
                         @RequestParam Integer totalAmount,
                         @RequestParam String currency,
                         @RequestParam String verDept,
                         @RequestParam String payType,
                         @RequestParam String tradingTime,
                         @RequestParam String note){
        BaseExchange baseExchange = new BaseExchange();
        baseExchange.setGuid(guid);
        baseExchange.setInitalRequest(initalRequest);
        baseExchange.setInitalResponse(initalResponse);
        baseExchange.setEbpCode(ebpCode);
        baseExchange.setPayCode(payCode);
        baseExchange.setPayTransactionId(payTransactionId);
        baseExchange.setTotalAmount(totalAmount);
        baseExchange.setCurrency(currency);
        baseExchange.setVerDept(verDept);
        baseExchange.setPayType(payType);
        baseExchange.setTradingTime(tradingTime);
        baseExchange.setNote(note);
        return baseExchangeService.addExchange(baseExchange);
    }

    @ApiOperation(value = "根据系统序号查询订单商品信息")
    @PostMapping("/getExProductListByGuid")
    public R getProductInfoByGuid(@RequestParam String guid){
        List<ExOrderProduct> exProductList = baseExchangeService.getProductInfoByGuid(guid);
        return R.ok().put("ExheadList",exProductList);
    }

    @ApiOperation(value = "根据系统序号删除信息")
    @PostMapping("/deleteExchange")
    @ApiImplicitParams({
            @ApiImplicitParam(name="guids",value = "系统序号",paramType ="query",
                    allowMultiple=true,dataType = "string")
    })

    public R deleteExchange(@RequestParam String[] guids){
        return baseExchangeService.deleteExchange(guids);
    }

    @ApiOperation(value = "列表")
    @PostMapping("/queryPage")
    @ApiImplicitParams({
            @ApiImplicitParam(name="currPage",value = "当前页",paramType ="query",dataType = "int"),
            @ApiImplicitParam(name="pageSize",value = "页面大小",paramType ="query",dataType = "int")
    })
    public R queryPage(@RequestParam Integer currPage,
                       @RequestParam Integer pageSize){
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("page",currPage.toString());
        params.put("limit",pageSize.toString());
        PageUtils page = baseExchangeService.queryPage(params);
        return R.ok().put("page",page);
    }
}
