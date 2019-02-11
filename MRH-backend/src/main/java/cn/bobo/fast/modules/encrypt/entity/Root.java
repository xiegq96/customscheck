package cn.bobo.fast.modules.encrypt.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * Created by liuyu on 2018/12/20 0020.
 */
public class Root {
    @JSONField(ordinal=1)
    private String sessionID;
    @JSONField(ordinal=2)
    private PayExchangeInfoHead payExchangeInfoHead;
    @JSONField(ordinal=3)
    private List<PayExchangeInfoLists> payExchangeInfoLists ;
    @JSONField(ordinal=4)
    private Long serviceTime;
    @JSONField(ordinal=5)
    private String certNo;
    @JSONField(ordinal=6)
    private String signValue;

    //需要加签数据
    public String signBeforeData(){
        String strTemp = "\"sessionID\":\""+this.sessionID+"\"||";
        strTemp += "\"payExchangeInfoHead\":\""+ JSON.toJSONString(this.getPayExchangeInfoHead())+"\"||";
        strTemp += "\"payExchangeInfoLists\":\""+ JSON.toJSONString(this.getPayExchangeInfoLists())+"\"||";
        strTemp += "\"serviceTime\":\""+ this.serviceTime+"\"";

        System.out.println("需要加签数据=====================================");
        System.out.println(strTemp);
        return strTemp;
    }

    public void setSessionID(String sessionID){
        this.sessionID = sessionID;
    }
    public String getSessionID(){
        return this.sessionID;
    }
    public void setPayExchangeInfoHead(PayExchangeInfoHead payExchangeInfoHead){
        this.payExchangeInfoHead = payExchangeInfoHead;
    }
    public PayExchangeInfoHead getPayExchangeInfoHead(){
        return this.payExchangeInfoHead;
    }
    public void setPayExchangeInfoLists(List<PayExchangeInfoLists> payExchangeInfoLists){
        this.payExchangeInfoLists = payExchangeInfoLists;
    }
    public List<PayExchangeInfoLists> getPayExchangeInfoLists(){
        return this.payExchangeInfoLists;
    }

    public Long getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(Long serviceTime) {
        this.serviceTime = serviceTime;
    }

    public void setCertNo(String certNo){
        this.certNo = certNo;
    }
    public String getCertNo(){
        return this.certNo;
    }
    public void setSignValue(String signValue){
        this.signValue = signValue;
    }
    public String getSignValue(){
        return this.signValue;
    }

}
