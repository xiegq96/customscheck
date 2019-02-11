package cn.bobo.fast.modules.base.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


@Data
public class PayExchange {
    @ApiModelProperty(value = "表头信息",name = "payExchangeInfoHead")
    private ExOrderProduct payExchangeInfoHead;
    @ApiModelProperty(value = "订单商品信息",name = "payExchangeInfoLists")
    private List<OrderProduct> payExchangeInfoLists;

}
