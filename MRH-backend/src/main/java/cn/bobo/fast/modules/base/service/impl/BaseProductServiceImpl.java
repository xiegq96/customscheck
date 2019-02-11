package cn.bobo.fast.modules.base.service.impl;

import cn.bobo.fast.common.utils.PageUtils;
import cn.bobo.fast.common.utils.Query;
import cn.bobo.fast.common.utils.R;
import cn.bobo.fast.modules.base.dao.BaseProductDao;
import cn.bobo.fast.modules.base.entity.BaseProduct;
import cn.bobo.fast.modules.base.service.BaseProductService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


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


    public boolean deleteProduct(String[] orderNos) {
        int count = baseProductDao.deleteProduct(orderNos);
        if(count > 0){
           return true;
        }else {
            return false;
        }
    }
    public   PageUtils queryPage(Map<String,Object> params){
        String orderNo = (String) params.get("orderNo");
        Page<BaseProduct> page = new Page<BaseProduct>();
        page = this.selectPage(new Query<BaseProduct>(params).getPage(),
                new EntityWrapper<BaseProduct>()
                .like(StringUtils.isNotBlank(orderNo),"order_no",orderNo));
         return new PageUtils(page);
    }


}
