package cn.bobo.fast.modules.base.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


@Data
@ApiModel(value = "payExchangeInfoLists",description = "订单商品信息")
public class OrderProduct {

  /*  @ApiModelProperty(value = "系统序号",name = "guid")
    private String guid;*/
    @ApiModelProperty(value = "订单编号",name = "orderNo")
    private String orderNo;
    @ApiModelProperty(value = "收款账号",name = "recpAccount")
    private String recpAccount;
    @ApiModelProperty(value = "收款企业编码",name = "recpCode")
    private String recpCode;
    @ApiModelProperty(value = "收款企业名称",name = "recpName")
    private String recpName;
    @ApiModelProperty(value = "商品数组",name = "proInfo",dataType = "array")
    private List<GoodsInfo> goodsInfo;


}
