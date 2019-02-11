package cn.bobo.fast.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by ${Zhangjh} on 2019/1/7 0007.
 */
@Data
@ApiModel(value = "exOrderProduct",description = "基础信息")
public class ExOrderProduct {
    @ApiModelProperty(value = "系统序号",name = "guid")
    private String guid;
    @ApiModelProperty(value = "请求",name = "initalRequest")
    private String initalRequest;
    @ApiModelProperty(value = "响应",name = "initalResponse")
    private String initalResponse;
    @ApiModelProperty(value = "电商代码",name = "ebpCode")
    private String ebpCode;
    @ApiModelProperty(value = "支付代码",name = "payCode")
    private String payCode;
    @ApiModelProperty(value = "流水",name = " payTransactionId")
    private String payTransactionId;
    @ApiModelProperty(value = "金额",name = "totalAmount")
    private Double totalAmount;
    @ApiModelProperty(value = "币制",name = "currency")
    private String currency;
    @ApiModelProperty(value = "核验",name = "verDept")
    private String verDept;
    @ApiModelProperty(value = "支付类型",name = "payType")
    private String payType;
    @ApiModelProperty(value = "时间",name = "tradingTime")
    private String tradingTime;
    @ApiModelProperty(value = "备注",name = "note")
    private String note;



}
