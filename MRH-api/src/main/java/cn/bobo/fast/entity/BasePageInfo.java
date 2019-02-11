package cn.bobo.fast.entity;

import java.util.List;

/**
 * Created by ${Zhangjh} on 2019/1/10 0010.
 */
public class BasePageInfo {
    private BaseExchange baseExchange;
    private List<BaseOrder> list;

    public BaseExchange getBaseExchange() {
        return baseExchange;
    }

    public void setBaseExchange(BaseExchange baseExchange) {
        this.baseExchange = baseExchange;
    }

    public List<BaseOrder> getList() {
        return list;
    }

    public void setList(List<BaseOrder> list) {
        this.list = list;
    }
}
