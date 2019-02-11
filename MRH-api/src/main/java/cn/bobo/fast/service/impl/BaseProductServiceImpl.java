package cn.bobo.fast.service.impl;

import cn.bobo.fast.common.Query;
import cn.bobo.fast.common.utils.PageUtils;
import cn.bobo.fast.common.utils.R;
import cn.bobo.fast.dao.BaseProductDao;
import cn.bobo.fast.entity.BaseExchange;
import cn.bobo.fast.entity.BaseProduct;
import cn.bobo.fast.service.BaseProductService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by ${Zhangjh} on 2019/1/4 0004.
 */
@Service("baseProductService")
public class BaseProductServiceImpl extends ServiceImpl<BaseProductDao,BaseProduct> implements BaseProductService{
    @Autowired
    private BaseProductDao baseProductDao;

    public R addProduct(BaseProduct baseProduct) {
        int count = baseProductDao.addProduct(baseProduct);
        if(count > 0){
            return R.ok().put("msg","添加成功");
        }else{
            return R.error().put("msg","操作失败");
        }

    }


    public List<BaseProduct> getProductList(String orderNo) {
        return baseProductDao.getProductListById(orderNo);
    }


    public R deleteProduct(String[] orderNos) {
        int count = baseProductDao.deleteProduct(orderNos);
        if(count > 0){
            return R.ok().put("msg","删除成功");
        }else {
            return R.error().put("msg","操作失败");
        }
    }
    public   PageUtils queryPage(Map<String,Object> params){
        Page<BaseProduct> page = new Page<BaseProduct>();

        page = this.selectPage(new Query<BaseProduct>(params).getPage(),
                new EntityWrapper<BaseProduct>());
        int totalCount = this.selectCount(new EntityWrapper<BaseProduct>());
        page.setTotal(totalCount);
      return new PageUtils(page);
    }


}
