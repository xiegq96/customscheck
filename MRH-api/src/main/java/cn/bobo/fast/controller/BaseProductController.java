package cn.bobo.fast.controller;

import cn.bobo.fast.common.Query;
import cn.bobo.fast.common.utils.PageUtils;
import cn.bobo.fast.common.utils.R;
import cn.bobo.fast.entity.BaseProduct;
import cn.bobo.fast.service.BaseProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ${Zhangjh} on 2019/1/4 0004.
 */
@Api(tags="商品接口")
@RestController
@RequestMapping("/baseProduct")
public class BaseProductController {
    @Autowired
    private BaseProductService baseProductService;

    @ApiOperation(value = "根据商品ID查询详细信息")
    @PostMapping("/getProductInfoById")
    @ApiImplicitParams({
            @ApiImplicitParam(name="productId",value = "商品编号",paramType ="query",dataType = "string")
    })
    public R getProductList(@RequestParam String productId) {
        List<BaseProduct> getProductList = baseProductService.getProductList(productId);
        return R.ok().put("list", getProductList);
    }

    @ApiOperation(value = "添加商品")
    @PostMapping("/addProduct")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderNo",value = "订单编号",required = true,paramType = "query",dataType = "string"),
            @ApiImplicitParam(name = "gname",value = "商品名称",required = true,paramType = "query",dataType = "string"),
            @ApiImplicitParam(name = "itemLink",value = "商品购买链接",required = true,paramType = "query",dataType = "string")
    })
    public R addProduct(@RequestParam String orderNo,
                        @RequestParam String gname,
                        @RequestParam String itemLink){
        BaseProduct baseProduct = new BaseProduct();
        baseProduct.setOrderNo(orderNo);
        baseProduct.setGname(gname);
        baseProduct.setItemLink(itemLink);
        return baseProductService.addProduct(baseProduct);
    }

    @ApiOperation(value = "根据订单编号删除商品信息")
    @PostMapping("/deleteProduct")
    @ApiImplicitParams({
            @ApiImplicitParam(name="orderNos",value = "订单编号",paramType ="query",
                    allowMultiple=true,dataType = "string")
    })
    public R deleteProduct(@RequestParam String [] orderNos){
        return baseProductService.deleteProduct(orderNos);
    }

    @ApiOperation(value = "商品列表")
    @PostMapping("/queryPage")
    @ApiImplicitParams({
            @ApiImplicitParam(name="currPage",value = "当前页",paramType ="query",dataType = "int"),
            @ApiImplicitParam(name="pageSize",value = "页面大小",paramType ="query",dataType = "int")
    })
    public R queryPage(@RequestParam Integer currPage,
                       @RequestParam Integer pageSize){
        Map<String,Object> params = new HashMap<>();
        params.put("page",currPage.toString());
        params.put("limit",pageSize.toString());
        PageUtils page = baseProductService.queryPage(params);
        System.out.println("*****************"+params);
        return R.ok().put("page",page);
    }
}

