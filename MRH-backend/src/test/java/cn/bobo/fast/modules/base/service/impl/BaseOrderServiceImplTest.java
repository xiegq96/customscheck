package cn.bobo.fast.modules.base.service.impl;

import cn.bobo.fast.modules.base.dao.BaseOrderDao;
import cn.bobo.fast.modules.base.entity.BaseExchange;
import cn.bobo.fast.modules.base.service.BaseOrderService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * Created by ${Zhangjh} on 2019/1/30 0030.
 */
public class BaseOrderServiceImplTest {
    @Autowired
    private BaseOrderService service;
    @Test
    public void getExchangeByOrderNo() throws Exception {

         String orderNo ="ord2018080301A54";
        BaseExchange exchange = service.getExchangeByOrderNo(orderNo);
        System.out.println(exchange);
    }

}