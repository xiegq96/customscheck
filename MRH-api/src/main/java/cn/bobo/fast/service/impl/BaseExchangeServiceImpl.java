package cn.bobo.fast.service.impl;

import cn.bobo.fast.bean.ExOrderProduct;
import cn.bobo.fast.common.Query;
import cn.bobo.fast.common.utils.PageUtils;
import cn.bobo.fast.common.utils.R;
import cn.bobo.fast.dao.BaseExchangeDao;
import cn.bobo.fast.entity.BaseExchange;
import cn.bobo.fast.service.BaseExchangeService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/1/3 0003.
 */
@Service("baseExchangeService")
public class BaseExchangeServiceImpl extends ServiceImpl<BaseExchangeDao,BaseExchange> implements BaseExchangeService {
    @Autowired
    private BaseExchangeDao baseExchangeDao;
    @Override
    public List<BaseExchange> getExchangeList(String guid) {
        return baseExchangeDao.getExchangeList(guid);
    }

    @Override
    @Transactional
    public R addExchange(BaseExchange baseExchange) {
        int count = 0;
        try {
           count = baseExchangeDao.addExchange(baseExchange);
        }catch (Exception e){
            e.printStackTrace();
        }
        if(count > 0){
            return R.ok().put("msg","添加成功");
        }else {
            return R.error().put("msg","操作失败");
        }
    }

    @Override
    public List<ExOrderProduct> getProductInfoByGuid(String guid) {
        return baseExchangeDao.getProductInfoByGuid(guid);
    }

    @Override
    public R deleteExchange(String[] guids) {
        int count = baseExchangeDao.deleteExchange(guids);
        System.out.println("传入参数是："+guids);
        if(count > 0){
            return R.ok().put("msg","删除成功");
        }else{
            return R.error().put("msg","操作失败");
        }
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<BaseExchange> page = this.selectPage(
                new Query<BaseExchange>(params).getPage(),
                new EntityWrapper<BaseExchange>()
        );
       int totalCount = this.selectCount(new EntityWrapper<BaseExchange>());
       page.setTotal(totalCount);
        return new PageUtils(page);
    }

}
