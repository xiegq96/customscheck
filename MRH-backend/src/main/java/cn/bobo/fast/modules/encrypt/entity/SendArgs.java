package cn.bobo.fast.modules.encrypt.entity;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by liuyu on 2018/12/25 0025.
 */
public class SendArgs {
    @JSONField(ordinal = 1)
    private String inData;
    @JSONField(ordinal = 2)
    private String passwd;

    public String getInData() {
        return inData;
    }

    public void setInData(String inData) {
        this.inData = inData;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }
}
