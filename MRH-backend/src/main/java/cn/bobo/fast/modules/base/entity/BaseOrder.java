package cn.bobo.fast.modules.base.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@TableName("base_order")
@Data
@ApiModel(value = "baseOrder",description = "订单")
public class BaseOrder implements Serializable{
  private static final long serialVersionUID = 1L;
  @ApiModelProperty(value = "系统序号",name = "guid")
  private String guid;
  @ApiModelProperty(value = "订单编号",name = "orderNo")
  private String orderNo;
  @ApiModelProperty(value = "收款账号",name = "recpAccount")
  private String recpAccount;
  @ApiModelProperty(value = "收款企业代码",name = "reCode")
  private String recpCode;
  @ApiModelProperty(value = "收款企业名称",name = "recpName")
  private String recpName;

}
