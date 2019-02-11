package cn.bobo.fast.modules.base.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "baseExchange",description = "基础信息")
public class BaseExchange implements Serializable{
  private static final long serialVersionUID = 1L;
  @ApiModelProperty(value = "系统序号",name = "guid")
  private String guid;
  @ApiModelProperty(value = "原始请求",name = "initalRequest")
  private String initalRequest;
  @ApiModelProperty(value = "原始响应",name = "initalResponse")
  private String initalResponse;
  @ApiModelProperty(value = "电商平台代码",name = "ebpCode")
  private String ebpCode;
  @ApiModelProperty(value = "支付企业代码",name = "payCode")
  private String payCode;
  @ApiModelProperty(value = "交易流水号",name = "payTransactionId")
  private String payTransactionId;
  @ApiModelProperty(value = "交易金额",name = "totalAmount")
  private Integer totalAmount;
  @ApiModelProperty(value = "币制",name = "currency")
  private String currency;
  @ApiModelProperty(value = "验核机构",name = "verDept")
  private String verDept;
  @ApiModelProperty(value = "支付类型",name = "payType")
  private String payType;
  @ApiModelProperty(value = "交易成功时间",name = "tradingTime")
  private String tradingTime;
  @ApiModelProperty(value = "备注",name = "note")
  private String note;
}
