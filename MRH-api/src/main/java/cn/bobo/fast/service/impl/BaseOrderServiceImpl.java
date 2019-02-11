package cn.bobo.fast.service.impl;

import cn.bobo.fast.bean.OrderProduct;
import cn.bobo.fast.common.utils.PageUtils;
import cn.bobo.fast.common.Query;
import cn.bobo.fast.common.utils.R;
import cn.bobo.fast.dao.BaseOrderDao;
import cn.bobo.fast.entity.BaseExchange;
import cn.bobo.fast.entity.BaseOrder;
import cn.bobo.fast.service.BaseOrderService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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
        Page<BaseOrder> page = this.selectPage(
                new Query<BaseOrder>(params).getPage(),
                new EntityWrapper<BaseOrder>()
        );
        int totalCount = this.selectCount(new EntityWrapper<BaseOrder>());
        page.setTotal(totalCount);
        return new PageUtils(page);
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
    public R deleteOrder(String[] orderNos) {
        int count = baseOrderDao.deleteOrder(orderNos);
        if(count > 0){
            return R.ok().put("msg","删除成功");
        }else {
            return R.error().put("msg","操作失败");
        }
    }


    public List<BaseOrder> getListByOrderNo(String orderNo) {
        return baseOrderDao.getListByOrderNo(orderNo);
    }

    public List<BaseOrder> getProList(Map<String,Object> map){

      return   baseOrderDao.selectByMap(map);
    }
}

