package cn.bobo.fast.modules.base.controller;


import cn.bobo.fast.common.annotation.CustomsLog;
import cn.bobo.fast.common.annotation.SysLog;
import cn.bobo.fast.common.utils.PageUtils;
import cn.bobo.fast.common.utils.R;
import cn.bobo.fast.modules.base.bean.OrderProduct;
import cn.bobo.fast.modules.base.entity.BaseExchange;
import cn.bobo.fast.modules.base.entity.BaseOrder;
import cn.bobo.fast.modules.base.entity.BaseProduct;
import cn.bobo.fast.modules.base.service.BaseOrderService;
import cn.bobo.fast.modules.base.service.BaseProductService;
import cn.bobo.fast.modules.base.utils.CommonReturnUtil;
import cn.bobo.fast.modules.base.utils.ReturnData;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by  on 2019/1/2 0002.
 */

@Api(tags="订单接口")
@RestController
@RequestMapping("/baseOrder")
public class BaseOrderController {
    @Autowired
    private BaseOrderService baseOrderService;
    @Autowired
    private BaseProductService baseProductService;

    /**
     * @param currPage
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "订单列表")
    @GetMapping("/queryPage")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "currPage", value = "当前页", paramType = "query", dataType = "int", required = true),
            @ApiImplicitParam(name = "pageSize", value = "页面大小", paramType = "query", dataType = "int", required = true),
            @ApiImplicitParam(name = "orderNo", value = "订单编号", paramType = "query", dataType = "string")
    })
    public ReturnData queryPage(@RequestParam Integer currPage,
                                @RequestParam Integer pageSize,
                                @RequestParam(value = "orderNo", required = false) String orderNo) {
        System.out.println("前台传入的参数:当前页" + currPage);
        System.out.println("前台传入的参数:页码" + pageSize);
        System.out.println("前台传入的参数:订单编号" + orderNo);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("page", currPage.toString());
        params.put("limit", pageSize.toString());
        params.put("orderNo", orderNo);
        PageUtils page = baseOrderService.queryPage(params);
        return CommonReturnUtil.success(page);
    }

    /**
     * 订单添加
     *
     * @param baseOrder
     * @return
     */
    @ApiOperation(value = "添加订单")
    @PostMapping("/addOrder")
   // @CustomsLog("添加订单2")
    @SysLog("添加订单1")
    public ReturnData addOrder(
            @ApiParam(name = "订单", value = "传入json格式", required = true)
            @RequestBody BaseOrder baseOrder) {
        boolean con = baseOrderService.insert(baseOrder);
        if (con) {
            return CommonReturnUtil.success();
        } else {
            return CommonReturnUtil.error("添加失败");
        }
    }

    /**
     * 根据订单编号查询详情
     *
     * @param orderNo
     * @return
     */
    @ApiOperation(value = "根据订单编号查询")
    @PostMapping("/getListByOrderNo/{orderNo}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderNo", value = "订单编号", required = true, paramType = "path", dataType = "string")
    })
    public ReturnData getListByOrderNo(@PathVariable String orderNo) {
        BaseOrder list = baseOrderService.getListByOrderNo(orderNo);
        if (list != null) {
            return CommonReturnUtil.success(list);
        } else {
            return CommonReturnUtil.error("没有该订单");
        }
    }

    @ApiOperation(value = "根据订单编号查询商品明细")
    @PostMapping("/selectProductByOrderNo/{orderNo}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderNo", value = "订单编号", paramType = "path", dataType = "string")
    })

    public ReturnData getOrderProduct(@PathVariable String orderNo) {
        List<OrderProduct> list = baseOrderService.getOrderProduct(orderNo);

        return CommonReturnUtil.success(list);
    }

    @ApiOperation(value = "根据订单编号删除信息")
    @PostMapping("/deleteOrder/{orderNos}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderNos", value = "订单编号", paramType = "path",
                    allowMultiple = true, dataType = "array")
    })
    public ReturnData deleteOrder(@PathVariable String[] orderNos) {
        //先判断订单下是否有商品,若有商品，先删除对应的商品信息，再删除订单信息
        List<String> arr = new ArrayList<String>();
        for (int i = 0; i < orderNos.length; i++) {
            System.out.println("传入参数是" + orderNos[i]);
            //如果某个订单下有商品，将该订单添加进list
            List<BaseProduct> list = baseProductService.getProductList(orderNos[i]);
            if (list != null) {
                System.out.println("存入的参数：" + orderNos[i]);
                arr.add(orderNos[i]);
            }
        }
        //
        if (arr.size() > 0) {
            System.out.println("arr的长度" + arr.size());
            // 将list 集合转换为数组
            baseProductService.deleteProduct(arr.toArray(new String[arr.size()]));
        }
        if (baseOrderService.deleteOrder(orderNos)) {
           return CommonReturnUtil.success();
        } else {
           return CommonReturnUtil.error();
        }
    }

    @ApiOperation(value = "根据订单编号查询表头信息")
    @PostMapping("/getExchangeByOrderNo")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderNo", value = "订单编号", paramType = "query", dataType = "string")
    })

    public ReturnData getExchangeByOrderNo(@RequestParam String orderNo) {
        BaseExchange exchange = baseOrderService.getExchangeByOrderNo(orderNo);

        return CommonReturnUtil.success(exchange);
    }

}



