package cn.bobo.fast.modules.base.service.impl;

import cn.bobo.fast.common.utils.PageUtils;
import cn.bobo.fast.common.utils.Query;
import cn.bobo.fast.common.utils.R;
import cn.bobo.fast.modules.base.bean.OrderProduct;
import cn.bobo.fast.modules.base.dao.BaseOrderDao;
import cn.bobo.fast.modules.base.entity.BaseExchange;
import cn.bobo.fast.modules.base.entity.BaseOrder;
import cn.bobo.fast.modules.base.service.BaseOrderService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/1/2 0002.
 */
@Service("BaseOrderService")
public class BaseOrderServiceImpl extends ServiceImpl<BaseOrderDao,BaseOrder> implements BaseOrderService {
    @Autowired
    private BaseOrderDao baseOrderDao;
    public PageUtils queryPage(Map<String, Object> params) {
        String orderNo = (String) params.get("orderNo");
        System.out.println("传入的参数："+orderNo);
        Page<BaseOrder> page = this.selectPage(
                new Query<BaseOrder>(params).getPage(),
                new EntityWrapper<BaseOrder>()
                        .like(StringUtils.isNotBlank(orderNo),"order_no",orderNo)
        );
        System.out.println("结果集："+page.getTotal());
        return new PageUtils(page);
    }

    @Override
    public BaseExchange getExchangeByOrderNo(String orderNo) {
        return baseOrderDao.getExchangeByOrderNo(orderNo);
    }


    public R addOrder(BaseOrder baseOrder){
        int count = baseOrderDao.insert(baseOrder);
        if(count > 0){
            return R.ok().put("msg","添加成功");
        }else{
            return R.error().put("msg","操作失败");
        }

    }
    public List<OrderProduct> getOrderProduct(String orderNo){

        return baseOrderDao.getOrderProduct(orderNo);
    }

    @Override
    public boolean deleteOrder(String[] orderNos) {
        int count = baseOrderDao.deleteOrder(orderNos);
        if(count > 0){
           return true;
        }else {
          return false;
        }
    }


    public BaseOrder getListByOrderNo(String orderNo) {
        return baseOrderDao.getListByOrderNo(orderNo);
    }

    public List<BaseOrder> getProList(Map<String,Object> map){

      return   baseOrderDao.selectByMap(map);
    }


}

