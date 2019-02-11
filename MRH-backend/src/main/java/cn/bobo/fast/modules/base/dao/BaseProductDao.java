package cn.bobo.fast.modules.base.dao;

import cn.bobo.fast.modules.base.entity.BaseProduct;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface BaseProductDao extends BaseMapper<BaseProduct>{
     /**
      *  添加商品
      * @param baseProduct
      * @return
      */
     int addProduct(BaseProduct baseProduct);

     /**
      *  根据订单号查询商品详情
      * @param orderNo
      * @return
      */
     List<BaseProduct> getProductListById(@Param("orderNo") String orderNo);

     /**
      * 删除订单号对应的商品信息
      * @param orderNos
      * @return
      */
     int deleteProduct(@Param("orderNos") String[] orderNos);
}
