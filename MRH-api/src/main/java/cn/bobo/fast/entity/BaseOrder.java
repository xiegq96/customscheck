package cn.bobo.fast.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModelProperty;
import jdk.nashorn.internal.objects.annotations.Getter;
import lombok.Data;



import java.io.Serializable;
import java.util.List;

@TableName("base_order")
@Data
public class BaseOrder implements Serializable{
  private static final long serialVersionUID = 1L;
  private String guid;
  private String orderNo;
  private String recpAccount;
  private String recpCode;
  private String recpName;

}
