package cn.bobo.fast.modules.base.service.impl;

import cn.bobo.fast.common.utils.PageUtils;
import cn.bobo.fast.common.utils.Query;
import cn.bobo.fast.common.utils.R;
import cn.bobo.fast.modules.base.bean.ExOrderProduct;
import cn.bobo.fast.modules.base.dao.BaseExchangeDao;
import cn.bobo.fast.modules.base.entity.BaseExchange;
import cn.bobo.fast.modules.base.service.BaseExchangeService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
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
    public boolean deleteExchange(String[] guids){
        int count = baseExchangeDao.deleteExchange(guids);
        if(count > 0){
           return true;
        }else{
            return false;
        }
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String guid = (String) params.get("guid");
        Page<BaseExchange> page = this.selectPage(
                new Query<BaseExchange>(params).getPage(),
                new EntityWrapper<BaseExchange>()
                .like(StringUtils.isNotBlank(guid),"guid",guid)
        );

        return new PageUtils(page);
    }

}
