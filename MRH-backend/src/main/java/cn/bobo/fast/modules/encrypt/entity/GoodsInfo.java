package cn.bobo.fast.modules.encrypt.entity;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by liuyu on 2018/12/20 0020.
 */
public class GoodsInfo {
    @JSONField(ordinal=1)
    private String gname;
    @JSONField(ordinal=2)
    private String itemLink;

    public void setGname(String gname){
        this.gname = gname;
    }
    public String getGname(){
        return this.gname;
    }
    public void setItemLink(String itemLink){
        this.itemLink = itemLink;
    }
    public String getItemLink(){
        return this.itemLink;
    }
}
