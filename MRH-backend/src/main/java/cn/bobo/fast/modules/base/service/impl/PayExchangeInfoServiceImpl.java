package cn.bobo.fast.modules.base.service.impl;

import cn.bobo.fast.common.annotation.SysLog;
import cn.bobo.fast.common.utils.R;
import cn.bobo.fast.modules.base.entity.BaseExchange;
import cn.bobo.fast.modules.base.entity.BaseOrder;
import cn.bobo.fast.modules.base.entity.BaseProduct;
import cn.bobo.fast.modules.base.service.BaseExchangeService;
import cn.bobo.fast.modules.base.service.BaseOrderService;
import cn.bobo.fast.modules.base.service.BaseProductService;
import cn.bobo.fast.modules.base.service.PayExchangeInfoService;
import cn.bobo.fast.modules.base.utils.ReturnData;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service("payExchangeInfoService")
public class PayExchangeInfoServiceImpl implements PayExchangeInfoService {
    @Autowired
    private BaseExchangeService baseExchangeService;
    @Autowired
    private BaseProductService baseProductService;
    @Autowired
    private BaseOrderService baseOrderService;
    @Transactional(rollbackFor = Exception.class)
    public boolean addAllInfo(String jsonStr) {
        System.out.println("*****传入的json数据"+jsonStr);
        //json对象
        try {
            JSONObject jsStr = JSONObject.parseObject(jsonStr);
            JSONObject exchange = jsStr.getJSONObject("payExchangeInfoHead");
            BaseExchange base = exchange.toJavaObject(BaseExchange.class);
            System.out.println("++++" + base.getGuid());
            baseExchangeService.insert(base);
            //Json数组
            JSONArray array = jsStr.getJSONArray("payExchangeInfoLists");
            System.out.println("订单list的长度" + array.size());
            for (int i = 0; i < array.size(); i++) {
                JSONObject object = array.getJSONObject(i);
                BaseOrder or = object.toJavaObject(BaseOrder.class);
                System.out.println("____" + or.getOrderNo());
                System.out.println("*****第" + i + "个订单");
                if (or.getGuid() == null) {
                    or.setGuid(base.getGuid());
                }
                baseOrderService.insert(or);
                List<Map<String, Object>> info = (List<Map<String, Object>>) object.get("goodsInfo");
                // List<BaseProduct> map = new ArrayList<>();
                //map = (List<BaseProduct>) object.get("GoodsInfo");
                System.out.println("订单里商品的长度map:" + info.size());
                for (int j = 0; j < info.size(); j++) {
                    System.out.println("*******第" + i + "订单下的" + "第" + j + "件商品");
                    String gname = (String) info.get(j).get("gname");
                    String itemLink = (String) info.get(j).get("itemLink");
                    BaseProduct baseProduct = new BaseProduct();
                    baseProduct.setOrderNo(or.getOrderNo());
                    baseProduct.setGname(gname);
                    baseProduct.setItemLink(itemLink);
                    baseProductService.insert(baseProduct);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
}

