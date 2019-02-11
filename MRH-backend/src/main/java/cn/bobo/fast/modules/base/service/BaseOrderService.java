package cn.bobo.fast.modules.base.service;


import cn.bobo.fast.common.utils.PageUtils;
import cn.bobo.fast.common.utils.R;
import cn.bobo.fast.modules.base.bean.OrderProduct;
import cn.bobo.fast.modules.base.entity.BaseExchange;
import cn.bobo.fast.modules.base.entity.BaseOrder;
import com.baomidou.mybatisplus.service.IService;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/1/2 0002.
 */
public interface BaseOrderService extends IService<BaseOrder> {

     BaseOrder getListByOrderNo(String orderNo);
     public R addOrder(BaseOrder baseOrder);
     public boolean deleteOrder(String[] orderNos);
     public List<OrderProduct> getOrderProduct(String orderNo);
    PageUtils queryPage(Map<String, Object> params);
    public List<BaseOrder> selectByMap(Map<String, Object> map);
    BaseExchange getExchangeByOrderNo(String orderNo);
}
