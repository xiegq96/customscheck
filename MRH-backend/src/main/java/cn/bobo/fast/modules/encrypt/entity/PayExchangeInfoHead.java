package cn.bobo.fast.modules.encrypt.entity;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by liuyu on 2018/12/20 0020.
 */
public class PayExchangeInfoHead {
    @JSONField(ordinal=1)
    private String guid;
    @JSONField(ordinal=2)
    private String initalRequest;
    @JSONField(ordinal=3)
    private String initalResponse;
    @JSONField(ordinal=4)
    private String ebpCode;
    @JSONField(ordinal=5)
    private String payCode;
    @JSONField(ordinal=6)
    private String payTransactionId;
    @JSONField(ordinal=7)
    private int totalAmount;
    @JSONField(ordinal=8)
    private String currency;
    @JSONField(ordinal=9)//verDept为一位
    private String verDept;
    @JSONField(ordinal=10)
    private String payType;
    @JSONField(ordinal=11)
    private String tradingTime;
    @JSONField(ordinal=12)
    private String note;

    public void setGuid(String guid){
        this.guid = guid;
    }
    public String getGuid(){
        return this.guid;
    }
    public void setInitalRequest(String initalRequest){
        this.initalRequest = initalRequest;
    }
    public String getInitalRequest(){
        return this.initalRequest;
    }
    public void setInitalResponse(String initalResponse){
        this.initalResponse = initalResponse;
    }
    public String getInitalResponse(){
        return this.initalResponse;
    }
    public void setEbpCode(String ebpCode){
        this.ebpCode = ebpCode;
    }
    public String getEbpCode(){
        return this.ebpCode;
    }
    public void setPayCode(String payCode){
        this.payCode = payCode;
    }
    public String getPayCode(){
        return this.payCode;
    }
    public void setPayTransactionId(String payTransactionId){
        this.payTransactionId = payTransactionId;
    }
    public String getPayTransactionId(){
        return this.payTransactionId;
    }
    public void setTotalAmount(int totalAmount){
        this.totalAmount = totalAmount;
    }
    public int getTotalAmount(){
        return this.totalAmount;
    }
    public void setCurrency(String currency){
        this.currency = currency;
    }
    public String getCurrency(){
        return this.currency;
    }
    public void setVerDept(String verDept){
        this.verDept = verDept;
    }
    public String getVerDept(){
        return this.verDept;
    }
    public void setPayType(String payType){
        this.payType = payType;
    }
    public String getPayType(){
        return this.payType;
    }

    public String getTradingTime() {
        return tradingTime;
    }

    public void setTradingTime(String tradingTime) {
        this.tradingTime = tradingTime;
    }

    public void setNote(String note){
        this.note = note;
    }
    public String getNote(){
        return this.note;
    }
}
