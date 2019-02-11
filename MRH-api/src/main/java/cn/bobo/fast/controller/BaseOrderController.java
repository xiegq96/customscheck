package cn.bobo.fast.controller;

import cn.bobo.fast.bean.OrderProduct;
import cn.bobo.fast.common.utils.PageUtils;
import cn.bobo.fast.common.utils.R;
import cn.bobo.fast.entity.BaseOrder;
import cn.bobo.fast.entity.BaseProduct;
import cn.bobo.fast.service.BaseOrderService;
import cn.bobo.fast.service.BaseProductService;
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
     *
     * @param pageNo
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "订单列表")
    @PostMapping("/getOrderList")
    @ApiImplicitParams({
            @ApiImplicitParam(name="pageNo",value = "当前页数",required = true,paramType = "query", dataType = "int"),
            @ApiImplicitParam(name="pageSize",value = "页面大小",required = true,paramType = "query", dataType = "int")
    })
    public R queryPage(
                  @NotNull(message = "当前页不能为空")Integer pageNo,
                  @NotNull(message = "页面大小不能为空")Integer pageSize){
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("currPage",pageNo.toString());
        params.put("limit",pageSize.toString());
        PageUtils page = baseOrderService.queryPage(params);


        return R.ok().put("page",page);
    }

    /**
     * 订单添加
     * @param baseOrder
     * @return
     */
    @ApiOperation(value = "添加订单")
    @PostMapping("/addOrder")

    public R addOrder(
            @ApiParam(name="订单对象",value="传入json格式",required=true)
            @RequestBody BaseOrder baseOrder) {
      return baseOrderService.addOrder(baseOrder);
    }

    /**
     * 根据订单编号查询详情
     * @param orderNo
     * @return
     */
    @ApiOperation(value = "根据订单编号查询")
    @PostMapping("/getListByOrderNo")
    @ApiImplicitParams({
            @ApiImplicitParam(name="orderNo",value = "订单编号",required = true,paramType ="query",dataType = "array")
    })
    public R getListByOrderNo(@RequestParam String orderNo){
        List<BaseOrder> list = baseOrderService.getListByOrderNo(orderNo);
        if(list != null){
            return R.ok().put("list",list);
        }else {
            return R.error("没有此订单信息");
        }
    }

    @ApiOperation(value = "根据订单编号查询商品明细")
    @PostMapping("/selectProductByOrderNo")
    @ApiImplicitParams({
            @ApiImplicitParam(name="orderNo",value = "订单编号",paramType ="query",dataType = "array")
    })

    public R getOrderProduct(@RequestParam String orderNo){
        List<OrderProduct> list = baseOrderService.getOrderProduct(orderNo);

        return R.ok().put("OrderInfo",list);
    }

    @ApiOperation(value = "根据订单编号删除信息")
    @PostMapping("/deleteOrder")
    @ApiImplicitParams({
            @ApiImplicitParam(name="orderNos",value = "订单编号",paramType ="query",
                    allowMultiple=true,dataType = "string")
    })
    public R deleteOrder(@RequestParam String[] orderNos){
        //先判断订单下是否有商品,若有商品，先删除对应的商品信息，再删除订单信息
       List<String> arr = new ArrayList<String>();
        for(int i = 0;i < orderNos.length;i++){
            System.out.println("传入参数是"+orderNos[i]);
            //如果某个订单下有商品，将该订单添加进list
            List<BaseProduct> list = baseProductService.getProductList(orderNos[i]);
            if(list.size() > 0){
                System.out.println("存入的参数："+orderNos[i]);
               arr.add(orderNos[i]);
            }
        }
        //
        if(arr.size() > 0){
            System.out.println("arr的长度"+arr.size());
            // 将list 集合转换为数组
            baseProductService.deleteProduct(arr.toArray(new String[arr.size()]));
        }
        return baseOrderService.deleteOrder(orderNos);
    }


}
