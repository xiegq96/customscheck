package cn.bobo.fast.dao;

import cn.bobo.fast.bean.ExOrderProduct;
import cn.bobo.fast.entity.BaseExchange;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

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
    List<BaseExchange> getExchangeList(@Param("guid")String guid);

    /**
     *  删除
     * @param guids
     * @return
     */
    int deleteExchange(String[] guids);

    /**
     *  根据系统序号查询商品信息
     * @param guid
     * @return
     */
    List<ExOrderProduct> getProductInfoByGuid(@Param("guid")String guid);
}
