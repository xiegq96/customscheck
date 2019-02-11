package cn.bobo.fast.modules.base.dao;


import cn.bobo.fast.modules.base.bean.ExOrderProduct;
import cn.bobo.fast.modules.base.entity.BaseExchange;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Created by Administrator on 2019/1/2 0002.
 */
public interface BaseExchangeDao extends BaseMapper<BaseExchange>{
    /**
     *  添加
     * @param baseExchange
     * @return
     */
    int addExchange(BaseExchange baseExchange);

    /**
     *  根据系统序号查询
     * @param guid
     * @return
     */
    List<BaseExchange> getExchangeList(@Param("guid") String guid);

    /**
     *  删除
     * @param guids
     * @return
     */
    int deleteExchange(@Param("guids")String[] guids);

    /**
     *  根据系统序号查询商品信息
     * @param guid
     * @return
     */
    List<ExOrderProduct> getProductInfoByGuid(@Param("guid") String guid);
}
