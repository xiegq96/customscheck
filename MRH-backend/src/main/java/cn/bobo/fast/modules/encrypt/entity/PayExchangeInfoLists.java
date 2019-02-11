package cn.bobo.fast.modules.encrypt.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * Created by liuyu on 2018/12/20 0020.
 */
public class PayExchangeInfoLists {
    @JSONField(ordinal=1)
    private String orderNo;
    @JSONField(ordinal=2)
    private List<GoodsInfo> goodsInfo ;
    @JSONField(ordinal=3)
    private String recpAccount;
    @JSONField(ordinal=4)
    private String recpCode;
    @JSONField(ordinal=5)
    private String recpName;

    public void setOrderNo(String orderNo){
        this.orderNo = orderNo;
    }
    public String getOrderNo(){
        return this.orderNo;
    }
    public void setGoodsInfo(List<GoodsInfo> goodsInfo){
        this.goodsInfo = goodsInfo;
    }
    public List<GoodsInfo> getGoodsInfo(){
        return this.goodsInfo;
    }
    public void setRecpAccount(String recpAccount){
        this.recpAccount = recpAccount;
    }
    public String getRecpAccount(){
        return this.recpAccount;
    }
    public void setRecpCode(String recpCode){
        this.recpCode = recpCode;
    }
    public String getRecpCode(){
        return this.recpCode;
    }
    public void setRecpName(String recpName){
        this.recpName = recpName;
    }
    public String getRecpName(){
        return this.recpName;
    }

}
