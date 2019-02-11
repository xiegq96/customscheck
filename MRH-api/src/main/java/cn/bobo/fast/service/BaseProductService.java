package cn.bobo.fast.service;

import cn.bobo.fast.common.utils.PageUtils;
import cn.bobo.fast.common.utils.R;
import cn.bobo.fast.entity.BaseProduct;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * Created by ${Zhangjh} on 2019/1/4 0004.
 */
public interface BaseProductService extends IService<BaseProduct>{
    R addProduct(BaseProduct baseProduct);
    List<BaseProduct> getProductList(String orderNo);
    R deleteProduct(String[] productIds);
    PageUtils queryPage(Map<String,Object> params);

}
