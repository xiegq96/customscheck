package cn.bobo.fast.service;

import cn.bobo.fast.bean.ExOrderProduct;
import cn.bobo.fast.common.utils.PageUtils;
import cn.bobo.fast.common.utils.R;
import cn.bobo.fast.entity.BaseExchange;

import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/1/3 0003.
 */
public interface BaseExchangeService extends IService<BaseExchange > {
    List<BaseExchange> getExchangeList(String guid);
    R addExchange(BaseExchange baseExchange);
    R deleteExchange(String[] guids);
    List<ExOrderProduct> getProductInfoByGuid(String guid);
    PageUtils queryPage(Map<String, Object> params);
}
