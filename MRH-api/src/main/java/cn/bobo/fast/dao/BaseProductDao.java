package cn.bobo.fast.dao;

import cn.bobo.fast.entity.BaseProduct;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by ${Zhangjh} on 2019/1/4 0004.
 */
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
     int deleteProduct(@Param("orderNos")String[] orderNos);
}
