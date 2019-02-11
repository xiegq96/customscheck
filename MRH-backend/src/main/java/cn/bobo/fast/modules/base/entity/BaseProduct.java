package cn.bobo.fast.modules.base.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "baseProduct",description = "商品")
public class BaseProduct {
  @ApiModelProperty(value = "订单编号",name = "orderNo")
  private String orderNo;
  @ApiModelProperty(value = "商品名称",name = "gname")
  private String gname;
  @ApiModelProperty(value = "商品购买链接",name = "itemLink")
  private String itemLink;
  @TableId
  private Long id;
}
