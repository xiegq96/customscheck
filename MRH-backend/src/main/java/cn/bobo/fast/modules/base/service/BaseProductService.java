package cn.bobo.fast.modules.base.service;

import cn.bobo.fast.common.utils.PageUtils;
import cn.bobo.fast.common.utils.R;
import cn.bobo.fast.modules.base.entity.BaseProduct;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;


public interface BaseProductService extends IService<BaseProduct>{
    R addProduct(BaseProduct baseProduct);
    List<BaseProduct> getProductList(String orderNo);
    boolean deleteProduct(String[] productIds);
    PageUtils queryPage(Map<String, Object> params);

}
