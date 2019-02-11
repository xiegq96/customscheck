package cn.bobo.fast.service.impl;

import cn.bobo.fast.common.utils.R;
import cn.bobo.fast.entity.BaseExchange;
import cn.bobo.fast.entity.BaseOrder;
import cn.bobo.fast.entity.BaseProduct;
import cn.bobo.fast.service.BaseExchangeService;
import cn.bobo.fast.service.BaseOrderService;
import cn.bobo.fast.service.BaseProductService;
import cn.bobo.fast.service.PayExchangeInfoService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ${Zhangjh} on 2019/1/13 0013.
 */
@Service("payExchangeInfoService")
public class PayExchangeInfoServiceImpl implements PayExchangeInfoService {
    @Autowired
    private BaseExchangeService baseExchangeService;
    @Autowired
    private BaseProductService baseProductService;
    @Autowired
    private BaseOrderService baseOrderService;
    @Transactional(rollbackFor = Exception.class)
    public R addAllInfo(String jsonStr) {
        R r1 = new R();
        R r2 = new R();
        R r3 = new R();
        System.out.println("*****传入的json数据"+jsonStr);
        //json对象
        JSONObject jsStr = JSONObject.parseObject(jsonStr);
        JSONObject exchange = jsStr.getJSONObject("payExchangeInfoHead");
        BaseExchange base = exchange.toJavaObject(BaseExchange.class);
        System.out.println("++++"+base.getGuid());
        try {
            r1 = baseExchangeService.addExchange(base);
        } catch (Exception e) {
            e.printStackTrace();
            return  R.error().put("msg","基础信息添加异常");
        }
        //Json数组
        JSONArray array = jsStr.getJSONArray("payExchangeInfoLists");
        System.out.println("订单list的长度" + array.size());
        for (int i = 0; i < array.size(); i++) {
            JSONObject object = array.getJSONObject(i);
            BaseOrder or = object.toJavaObject(BaseOrder.class);
            System.out.println("____"+or.getOrderNo());
            System.out.println("*****第"+i+"个订单");
            if(or.getGuid() == null){
                or.setGuid(base.getGuid());
            }
            try {
                r2 = baseOrderService.addOrder(or);
            } catch (Exception e) {
                e.printStackTrace();
                return R.error().put("msg","订单信息添加异常");
            }
            List<Map<String,Object>> info = (List<Map<String,Object>>) object.get("goodsInfo");
           // List<BaseProduct> map = new ArrayList<>();
            //map = (List<BaseProduct>) object.get("GoodsInfo");
            System.out.println("订单里商品的长度map:" + info.size());
            for (int j = 0; j < info.size(); j++) {
                System.out.println("*******第"+i+"订单下的"+"第"+j+"件商品");
                String gname = (String) info.get(j).get("gname");
                String itemLink = (String) info.get(j).get("itemLink");
                BaseProduct baseProduct = new BaseProduct();
                baseProduct.setOrderNo(or.getOrderNo());
                baseProduct.setGname(gname);
                baseProduct.setItemLink(itemLink);
                try {
                    r3 = baseProductService.addProduct(baseProduct);
                } catch (Exception e) {
                    e.printStackTrace();
                    return  R.error().put("msg","商品信息添加异常");
                }
            }
        }
       if(r1.equals(r2) && r1.equals(r3)){
        /*    Map<String,Object> map = new HashMap<>();
            map.put("code",2000);
            map.put("msg","添加成功");
            return R.ok(map);*/
            return R.ok().put("msg","添加成功");
       }else{
           return R.error();
       }
    }
}

