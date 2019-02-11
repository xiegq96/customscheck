package cn.bobo.fast.modules.base.dao;


import cn.bobo.fast.modules.base.bean.OrderProduct;
import cn.bobo.fast.modules.base.entity.BaseExchange;
import cn.bobo.fast.modules.base.entity.BaseOrder;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Administrator on 2019/1/2 0002.
 */
public interface BaseOrderDao extends BaseMapper<BaseOrder>{
   /**
    *  根据订单编号查询详情
    * @param orderNo
    * @return
    */
   BaseOrder getListByOrderNo(@Param("orderNo") String orderNo);

   /**
    * 添加订单
    * @param baseOrder
    * @return
    */
   int addOrder(BaseOrder baseOrder);

   /**
    *  根据订单编号查询商品
    * @param orderNo
    * @return
    */
   List<OrderProduct> getOrderProduct(@Param("orderNo") String orderNo);

   /**
    *  根据订单编号删除
    * @param orderNos
    * @return
    */
   int deleteOrder(@Param("orderNos") String[] orderNos);

   /**
    *  根据订单号查询表头信息
    * @param orderNo
    * @return
    */
   BaseExchange getExchangeByOrderNo(@Param("orderNo") String orderNo);

}
