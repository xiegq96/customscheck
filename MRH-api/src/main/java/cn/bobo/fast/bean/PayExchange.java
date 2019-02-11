package cn.bobo.fast.bean;

import lombok.Data;

import java.util.List;

/**
 * Created by ${Zhangjh} on 2019/1/17 0017.
 */
@Data
public class PayExchange {
    private ExOrderProduct payExchangeInfoHead;
    private List<OrderProduct> payExchangeInfoLists;

}
