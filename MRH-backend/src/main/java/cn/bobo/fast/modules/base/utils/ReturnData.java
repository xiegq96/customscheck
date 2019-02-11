package cn.bobo.fast.modules.base.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "返回的数据对象外层")
public class ReturnData<T> {
    @ApiModelProperty(value = "成功或者失败的编码")
    private Integer code;
    @ApiModelProperty(value = "成功或者失败的提示语")
    private String msg;
    @ApiModelProperty(value = "数据结果")
    private T result;


}
