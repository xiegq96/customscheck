package cn.bobo.fast.service;

import cn.bobo.fast.bean.OrderProduct;
import cn.bobo.fast.common.utils.PageUtils;
import cn.bobo.fast.common.utils.R;
import cn.bobo.fast.entity.BaseOrder;

import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/1/2 0002.
 */
public interface BaseOrderService extends IService<BaseOrder> {
    PageUtils queryPage(Map<String, Object> params);
     public List<BaseOrder> getListByOrderNo(String orderNo);
     public R addOrder(BaseOrder baseOrder);
     public R deleteOrder(String[] orderNos);
     public List<OrderProduct> getOrderProduct(String orderNo);
    public List<BaseOrder> selectByMap(Map<String,Object> map);
}
