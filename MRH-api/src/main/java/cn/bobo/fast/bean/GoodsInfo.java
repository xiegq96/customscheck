package cn.bobo.fast.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel(value = "goodsInfo",description = "商品")
public class GoodsInfo {
  @ApiModelProperty(value = "商品名称",name = "gname")
  private String gname;
  @ApiModelProperty(value = "商品购买链接",name = "itemLink")
  private String itemLink;


}
