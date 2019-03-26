package cn.bobo.fast.modules.base.controller;


import cn.bobo.fast.common.annotation.SysLog;
import cn.bobo.fast.common.utils.PageUtils;
import cn.bobo.fast.common.utils.R;
import cn.bobo.fast.modules.base.entity.BaseProduct;
import cn.bobo.fast.modules.base.service.BaseProductService;
import cn.bobo.fast.modules.base.utils.CommonReturnUtil;
import cn.bobo.fast.modules.base.utils.ReturnData;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Api(tags="商品接口")
@RestController
@RequestMapping("/baseProduct")
@ApiIgnore
public class BaseProductController {
    @Autowired
    private BaseProductService baseProductService;

    @ApiOperation(value = "根据订单号查询详细信息")
    @PostMapping("/getProductInfoById/{orderNo}")
    @ApiImplicitParams({
            @ApiImplicitParam(name="orderNo",value = "订单编号",paramType ="path",dataType = "string")
    })
    public ReturnData getProductList(@PathVariable String orderNo) {
        List<BaseProduct> getProductList = baseProductService.getProductList(orderNo);
        return CommonReturnUtil.success(getProductList);
    }

    @ApiOperation(value = "添加商品")
    @PostMapping("/addProduct")
    @SysLog("添加商品")
    public ReturnData addProduct(
            @ApiParam(name="商品信息",value="传入json格式",required=true)
            @RequestBody BaseProduct baseProduct){

        boolean count = baseProductService.insert(baseProduct);
        if(count){
          return CommonReturnUtil.success(baseProduct);
        }else {
           return CommonReturnUtil.error("添加失败");
        }
    }

    @ApiOperation(value = "根据订单编号删除商品信息")
    @PostMapping("/deleteProduct/{orderNos}")
    @ApiImplicitParams({
            @ApiImplicitParam(name="orderNos",value = "订单编号",paramType ="path",
                    allowMultiple=true,dataType = "array")
    })
    public ReturnData deleteProduct(@PathVariable String [] orderNos){
        if(baseProductService.deleteProduct(orderNos)){
           return CommonReturnUtil.success();
        }else{
            return CommonReturnUtil.error("删除失败");
        }
    }

    @ApiOperation(value = "商品列表")
    @GetMapping("/queryPage")
    @ApiImplicitParams({
            @ApiImplicitParam(name="currPage",value = "当前页",paramType ="query",dataType = "int",required = true),
            @ApiImplicitParam(name="pageSize",value = "页面大小",paramType ="query",dataType = "int",required = true),
            @ApiImplicitParam(name="orderNo",value = "订单编号",paramType ="query",dataType = "string")
    })
    public ReturnData queryPage(@RequestParam Integer currPage,
                       @RequestParam Integer pageSize,
                       @RequestParam(value = "orderNo",required = false)String orderNo){
        Map<String,Object> params = new HashMap<>();
        System.out.println("接收订单参数:"+orderNo);
        params.put("page",currPage.toString());
        params.put("limit",pageSize.toString());
        params.put("orderNo",orderNo);
        PageUtils page = baseProductService.queryPage(params);
        System.out.println("*****************"+params);
       return CommonReturnUtil.success(page);
    }

    @ApiOperation(value = "根据商品id删除")
    @PostMapping("/deleteProductByIds")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value = "商品编号",paramType ="query",dataType = "int")
    })
    public ReturnData getProductInfoById(@RequestBody Long [] ids){
        for (int i = 0;i < ids.length;i++){
            System.out.println("接收的参数是:"+ids[i]);
        }
        baseProductService.deleteBatchIds(Arrays.asList(ids));
        return CommonReturnUtil.success();
    }
}

