package cn.bobo.fast.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
@Data
public class BaseExchange implements Serializable{
  private static final long serialVersionUID = 1L;
  private String guid;
  private String initalRequest;
  private String initalResponse;
  private String ebpCode;
  private String payCode;
  private String payTransactionId;
  private Integer totalAmount;
  private String currency;
  private String verDept;
  private String payType;
  private String tradingTime;
  private String note;
}
