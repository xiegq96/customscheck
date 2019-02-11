package cn.bobo.fast.modules.encrypt.controller;

import cn.bobo.fast.modules.encrypt.entity.GoodsInfo;
import cn.bobo.fast.modules.encrypt.entity.PayExchangeInfoLists;
import cn.bobo.fast.modules.encrypt.entity.Root;
import cn.bobo.fast.modules.encrypt.entity.SendArgs;
import cn.bobo.fast.modules.encrypt.utils.CallBack;
import cn.bobo.fast.modules.encrypt.utils.WebSocketClientHandle;
import com.alibaba.fastjson.JSONObject;
import com.https.post.constant.DemoConstant;
import com.https.post.utils.SSLUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.net.ssl.SSLContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/send")
public class SendController {

    public static final String CHARSET = "UTF-8";
    public static final String testPayInfo = "{ \"sessionID\": \"ly6587-9foejf66-25324468\", \t\"payExchangeInfoHead\": { \t\t\"guid\": \"fe2374-8fnejf97-32839218\", \t\t\"initalRequest\": \"https://openapi.alipay.com/gateway.do?timestamp=2013-01-01 08:08:08&method=alipay.trade.pay&app_id=13580&sign_type=RSA2&sign=ERITJKEIJKJHKKKKKKKHJEREEEEEEEEEEE&version=1.0&charset=GBK&biz_content=\\n {\\n\\\"out_trade_no\\\":\\\"20150320010101001\\\",\\n\\\"scene\\\":\\\"bar_code\\\",\\n\\\"auth_code\\\":\\\"28763443825664394\\\",\\n\\\"product_code\\\":\\\"FACE_TO_FACE_PAYMENT\\\",\\n\\\"subject\\\":\\\"Iphone6 16G\\\",\\n\\\"buyer_id\\\":\\\"2088202954065786\\\",\\n\\\"seller_id\\\":\\\"2088102146225135\\\",\\n\\\"total_amount\\\":88.88,\\n\\\"trans_currency\\\":\\\"USD\\\",\\n\\\"settle_currency\\\":\\\"USD\\\",\\n\\\"discountable_amount\\\":8.88,\\n\\\"body\\\":\\\"Iphone6 16G\\\",\\n \\\"goods_detail\\\":[{\\n \\\"goods_id\\\":\\\"apple-01\\\",\\n\\\"goods_name\\\":\\\"ipad\\\",\\n\\\"quantity\\\":1,\\n\\\"price\\\":2000,\\n\\\"goods_category\\\":\\\"34543238\\\",\\n\\\"body\\\":\\\"特价手机\\\",\\n\\\"show_url\\\":\\\"http://www.alipay.com/xxx.jpg\\\"\\n }],\\n\\\"operator_id\\\":\\\"yx_001\\\",\\n\\\"store_id\\\":\\\"NJ_001\\\",\\n\\\"terminal_id\\\":\\\"NJ_T_001\\\",\\n\\\"extend_params\\\":{\\n\\\"sys_service_provider_id\\\":\\\"2088511833207846\\\",\\n\\\"industry_reflux_info\\\":\\\"{\\\\\\\\\\\\\\\"scene_code\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\"metro_tradeorder\\\\\\\\\\\\\\\",\\\\\\\\\\\\\\\"channel\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\"xxxx\\\\\\\\\\\\\\\",\\\\\\\\\\\\\\\"scene_data\\\\\\\\\\\\\\\":{\\\\\\\\\\\\\\\"asset_name\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\"ALIPAY\\\\\\\\\\\\\\\"}}\\\",\\n\\\"card_type\\\":\\\"S0JP0000\\\"\\n },\\n\\\"timeout_express\\\":\\\"90m\\\",\\n\\\"auth_confirm_mode\\\":\\\"COMPLETE：转交易支付完成结束预授权;NOT_COMPLETE：转交易支付完成不结束预授权\\\",\\n\\\"terminal_params\\\":\\\"{\\\\\\\"key\\\\\\\":\\\\\\\"value\\\\\\\"}\\\"\\n }\\n\", \t\t\"initalResponse\": \"{\\n \\\"alipay_trade_pay_response\\\": {\\n \\\"code\\\": \\\"10000\\\",\\n \\\"msg\\\": \\\"Success\\\",\\n \\\"trade_no\\\": \\\"2013112011001004330000121536\\\",\\n \\\"out_trade_no\\\": \\\"6823789339978248\\\",\\n \\\"buyer_logon_id\\\": \\\"159****5620\\\",\\n \\\"total_amount\\\": 120.88,\\n \\\"trans_currency\\\": \\\"USD\\\",\\n \\\"settle_currency\\\": \\\"USD\\\",\\n \\\"settle_amount\\\": \\\"88.88\\\",\\n \\\"pay_currency\\\": \\\"CNY\\\",\\n \\\"pay_amount\\\": \\\"580.04\\\",\\n \\\"settle_trans_rate\\\": \\\"1\\\",\\n \\\"trans_pay_rate\\\": \\\"6.5261\\\",\\n \\\"receipt_amount\\\": \\\"88.88\\\",\\n \\\"buyer_pay_amount\\\": 8.88,\\n \\\"point_amount\\\": 8.12,\\n \\\"invoice_amount\\\": 12.5,\\n \\\"gmt_payment\\\": \\\"2014-11-27 15:45:57\\\",\\n \\\"fund_bill_list\\\": [\\n {\\n \\\"fund_channel\\\": \\\"ALIPAYACCOUNT\\\",\\n \\\"bank_code\\\": \\\"CEB\\\",\\n \\\"amount\\\": 10,\\n \\\"real_amount\\\": 11.21\\n }\\n ],\\n \\\"card_balance\\\": 98.23,\\n \\\"store_name\\\": \\\"证大五道口店\\\",\\n \\\"buyer_user_id\\\": \\\"2088101117955611\\\",\\n \\\"discount_goods_detail\\\": \\\"[{\\\\\\\"goods_id\\\\\\\":\\\\\\\"STANDARD1026181538\\\\\\\",\\\\\\\"goods_name\\\\\\\":\\\\\\\"雪碧\\\\\\\",\\\\\\\"discount_amount\\\\\\\":\\\\\\\"100.00\\\\\\\",\\\\\\\"voucher_id\\\\\\\":\\\\\\\"2015102600073002039000002D5O\\\\\\\"}]\\\",\\n \\\"voucher_detail_list\\\": [\\n {\\n \\\"id\\\": \\\"2015102600073002039000002D5O\\\",\\n \\\"name\\\": \\\"XX超市5折优惠\\\",\\n \\\"type\\\": \\\"ALIPAY_FIX_VOUCHER\\\",\\n \\\"amount\\\": 10,\\n \\\"merchant_contribute\\\": 9,\\n \\\"other_contribute\\\": 1,\\n \\\"memo\\\": \\\"学生专用优惠\\\",\\n \\\"template_id\\\": \\\"20171030000730015359000EMZP0\\\",\\n \\\"purchase_buyer_contribute\\\": 2.01,\\n \\\"purchase_merchant_contribute\\\": 1.03,\\n \\\"purchase_ant_contribute\\\": 0.82\\n }\\n ],\\n \\\"auth_trade_pay_mode\\\": \\\"CREDIT_PREAUTH_PAY\\\",\\n \\\"business_params\\\": \\\"{\\\\\\\"data\\\\\\\":\\\\\\\"123\\\\\\\"}\\\",\\n \\\"buyer_user_type\\\": \\\"PRIVATE\\\",\\n \\\"mdiscount_amount\\\": \\\"88.88\\\",\\n \\\"discount_amount\\\": \\\"88.88\\\"\\n },\\n \\\"sign\\\": \\\"ERITJKEIJKJHKKKKKKKHJEREEEEEEEEEEE\\\"\\n}\", \t\t\"ebpCode\": \"3512830031\", \t\t\"payCode\": \"4403160BUF\", \t\t\"payTransactionId\": \"201808010XTOO180881108351\", \t\t\"totalAmount\": 2000, \t\t\"currency\": \"502\", \t\t\"verDept\": \"1\", \t\t\"payType\": \"1\", \t\t\"tradingTime\": \"2018-07-05 13:33:20\", \t\t\"note\": \"备注\" \t}, \t\"payExchangeInfoLists\": [{ \t\t\t\"orderNo\": \"ord201808030001\", \t\t\t\"goodsInfo\":[                         {\"gname\": \"小米盒子\", \t\t\t\"itemLink\": \"http://click.simba.taobao.com/cc_im?spm=a2e15.8261149.07626516002.1&p=?í??&s=1648065802&k=525&clk1=9f73e9cdcce965d198471b70ed0bd643&e=BmEuJDus6V0PZbqH6AbTJV0AKPHu3YhwVj/tY3U/BPnJRCjFyFQ8GHxmrU6Q+bkjDDPHzLdwU/etpQpyzbOjC5wBu0ordUSSMi7bCqPy47R1nIOYRCll+4+g7T1j2K+vML5LpUxRY23BdDKBL+XYG61TuZ6KTbzphgvsV4Ojz5jpcmo9XCws//tnCtUX8NyE5jc7bsfu1N4M1tX0LAzVL8Q/8Cb3PXX0Lg5CK0SP9A1GPZR1xBhowNfNkjqys1bd5jWRIP4BomXImyoaJy83WdBjdkAzm/0nUGlUh8jQ4hq8tVvOYh5jaAjZNGP6kg4m3CTQUO/p1GO/6+U5DeLwJLMyCLM4UCxLwYmDL9sJHo9BAnbfln/8xq83b9LkfalG081DbhBf6xDt6WwO7bF5pQdW8K5ahpAflCUwHLCjWUurCsD9ob8Lq6NckDlv0x6dd6spMGSy0l3cJNBQ7+nUY0qfAtCS17uKWJHDCg4IHl7OvKn1XZO6wl90By37YYjBRw4q/RI+UHU=\"},                         {\"gname\": \"零食\", \t\t\t\"itemLink\": \"http://click.simba.taobao.com/cc_im?spm=a2e15.8261149.07626516002.1&p=?í??&s=1648065802&k=525&clk1=9f73e9cdcce965d198471b70ed0bd643&e=BmEuJDus6V0PZbqH6AbTJV0AKPHu3YhwVj/tY3U/BPnJRCjFyFQ8GHxmrU6Q+bkjDDPHzLdwU/etpQpyzbOjC5wBu0ordUSSMi7bCqPy47R1nIOYRCll+4+g7T1j2K+vML5LpUxRY23BdDKBL+XYG61TuZ6KTbzphgvsV4Ojz5jpcmo9XCws//tnCtUX8NyE5jc7bsfu1N4M1tX0LAzVL8Q/8Cb3PXX0Lg5CK0SP9A1GPZR1xBhowNfNkjqys1bd5jWRIP4BomXImyoaJy83WdBjdkAzm/0nUGlUh8jQ4hq8tVvOYh5jaAjZNGP6kg4m3CTQUO/p1GO/6+U5DeLwJLMyCLM4UCxLwYmDL9sJHo9BAnbfln/8xq83b9LkfalG081DbhBf6xDt6WwO7bF5pQdW8K5ahpAflCUwHLCjWUurCsD9ob8Lq6NckDlv0x6dd6spMGSy0l3cJNBQ7+nUY0qfAtCS17uKWJHDCg4IHl7OvKn1XZO6wl90By37YYjBRw4q/RI+UHU=\"}                          ], \t\t\t\"recpAccount\": \"6217850100007893905\", \t\t\t\"recpCode\": \"1105910159\", \t\t\t\"recpName\": \"天猫国际有限公司\" \t\t}, \t\t{ \t\t\t\"orderNo\": \"ord201808030002\", \t\t\t\"goodsInfo\":[                         {\"gname\": \"奶粉\", \t\t\t\"itemLink\": \"http://click.simba.taobao.com/cc_im?spm=a2e15.8261149.07626516002.1&p=?í??&s=1648065802&k=525&clk1=9f73e9cdcce965d198471b70ed0bd643&e=BmEuJDus6V0PZbqH6AbTJV0AKPHu3YhwVj/tY3U/BPnJRCjFyFQ8GHxmrU6Q+bkjDDPHzLdwU/etpQpyzbOjC5wBu0ordUSSMi7bCqPy47R1nIOYRCll+4+g7T1j2K+vML5LpUxRY23BdDKBL+XYG61TuZ6KTbzphgvsV4Ojz5jpcmo9XCws//tnCtUX8NyE5jc7bsfu1N4M1tX0LAzVL8Q/8Cb3PXX0Lg5CK0SP9A1GPZR1xBhowNfNkjqys1bd5jWRIP4BomXImyoaJy83WdBjdkAzm/0nUGlUh8jQ4hq8tVvOYh5jaAjZNGP6kg4m3CTQUO/p1GO/6+U5DeLwJLMyCLM4UCxLwYmDL9sJHo9BAnbfln/8xq83b9LkfalG081DbhBf6xDt6WwO7bF5pQdW8K5ahpAflCUwHLCjWUurCsD9ob8Lq6NckDlv0x6dd6spMGSy0l3cJNBQ7+nUY0qfAtCS17uKWJHDCg4IHl7OvKn1XZO6wl90By37YYjBRw4q/RI+UHU=\"},                         {\"gname\": \"拖鞋\", \t\t\t\"itemLink\": \"http://click.simba.taobao.com/cc_im?spm=a2e15.8261149.07626516002.1&p=?í??&s=1648065802&k=525&clk1=9f73e9cdcce965d198471b70ed0bd643&e=BmEuJDus6V0PZbqH6AbTJV0AKPHu3YhwVj/tY3U/BPnJRCjFyFQ8GHxmrU6Q+bkjDDPHzLdwU/etpQpyzbOjC5wBu0ordUSSMi7bCqPy47R1nIOYRCll+4+g7T1j2K+vML5LpUxRY23BdDKBL+XYG61TuZ6KTbzphgvsV4Ojz5jpcmo9XCws//tnCtUX8NyE5jc7bsfu1N4M1tX0LAzVL8Q/8Cb3PXX0Lg5CK0SP9A1GPZR1xBhowNfNkjqys1bd5jWRIP4BomXImyoaJy83WdBjdkAzm/0nUGlUh8jQ4hq8tVvOYh5jaAjZNGP6kg4m3CTQUO/p1GO/6+U5DeLwJLMyCLM4UCxLwYmDL9sJHo9BAnbfln/8xq83b9LkfalG081DbhBf6xDt6WwO7bF5pQdW8K5ahpAflCUwHLCjWUurCsD9ob8Lq6NckDlv0x6dd6spMGSy0l3cJNBQ7+nUY0qfAtCS17uKWJHDCg4IHl7OvKn1XZO6wl90By37YYjBRw4q/RI+UHU=\"}                          ], \t\t\t\"recpAccount\": \"6217850100007893905\", \t\t\t\"recpCode\": \"1105910159\", \t\t\t\"recpName\": \"天猫国际有限公司\" \t\t} \t], \t\"serviceTime\": 1533282603450, \t\"certNo\":\"012c14a9\", \t\"signValue\":\"123\" } ";
    @Autowired
    private WebSocketClientHandle webSocketClientHandle;
    @RequestMapping("/initData")
    public ModelAndView initData() throws Exception {
        String signBeforeData = "";
        try {
            //json串转为对象
            Root root = JSONObject.parseObject(testPayInfo, Root.class);
            //更改对象中的部分内容
            if (root != null) {
                setInitData(root);
            }
            //生成需要加签的数据
            signBeforeData = root.signBeforeData();
            String url = "ws://127.0.0.1:61232";
            final Object[] returnStr = new Object[1];
            //组装数据
            SendArgs sendArgs = new SendArgs();
            sendArgs.setInData(signBeforeData);//需加签数据
            sendArgs.setPasswd("88888888");//ukey的密码
            String content = "{\"_method\":\"cus-sec_SpcSignDataAsPEM\",\"_id\":1,\"args\":"+ JSONObject.toJSON(sendArgs).toString()+"}";
            System.out.println(content);
            final String[] returnSign = {null};
            webSocketClientHandle.send(url,content,
                    new CallBack() {
                        @Override
                        public Object run(Object params) {
                            System.out.println(params);
                            returnSign[0] = params.toString();
                            return null;
                        }
                    },new CallBack() {
                        @Override
                        public Object run(Object params) {
                            Exception e=(Exception) params;
                            e.printStackTrace();
                            return null;
                        }
                    });
            try{
//                webSocketClientHandle.countDownLatch.await();
            }catch (Exception e){
                e.printStackTrace();
            }
            System.out.println(returnSign[0]);
            System.out.println("加签++++++++++++++++++++++");
            postCustom(returnSign[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map model = new HashMap<>();
        model.put("initData", signBeforeData);
        System.out.println("initData完成++++++++++++++++++++++");

        return null;//new ModelAndView("signPage",model);
    }

    private void getSign(String signBeforeData){

    }

    private void setInitData(Root root) {
        root.setSessionID("sd132fb-923sd9dfg-5dseo549");
        root.setCertNo("012c14a9");
        root.getPayExchangeInfoHead().setEbpCode("3512830031");
        root.getPayExchangeInfoHead().setInitalRequest("https://openapi.alipay.com/gateway.do?timestamp=2013-01-0108:08:08&method=alipay.trade.pay&app_id=13580&sign_type=RSA2&sign=ERITJKEI&version=1.0&charset=GBK");
        root.getPayExchangeInfoHead().setInitalResponse("ok");
        root.getPayExchangeInfoHead().setTradingTime("131231231231");
        for(PayExchangeInfoLists info:root.getPayExchangeInfoLists()){
            info.getGoodsInfo().remove(0);
            for(GoodsInfo goodsInfo:info.getGoodsInfo()){
                goodsInfo.setItemLink("http://m.yunjiweidian.com/yunjibuyer/static/vue-buyer/idc/index.html#/detail?itemId=999761&shopId=453");
            }
        }
    }

//    @RequestMapping("/postCustom")
    public String postCustom(String signValue) throws Exception {
        try{
            //签名获取
            JSONObject signValueJson = JSONObject.parseObject(signValue);
            String method = signValueJson.getString("_method");
            if("cus-sec_SpcSignDataAsPEM".equals(method)){
                JSONObject args = signValueJson.getJSONObject("_args");
                String signData = args.getJSONArray("Data").getString(0);
                SSLContext sc = SSLUtils.createSslContext();
                //json串转为对象
                Root root = JSONObject.parseObject(testPayInfo,Root.class);
                //更改对象中的部分内容
                if(root!=null){
                    setInitData(root);
                    root.setSignValue(signData);
                }
                //生成需要加签的数据
                String signBeforeData=root.signBeforeData();
                System.out.println("签名=====================================");
                System.out.println(signData);
                System.out.println("请求数据=====================================");
                JSONObject postJson = new JSONObject();
                String postData = JSONObject.toJSONString(root);
                postJson.put("payExInfoStr", postData);
                System.out.println(postJson.toString());
                String result = postForm(DemoConstant.URL, postJson, "application/json;charset=utf-8");
                System.out.println(result);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return "index";
    }

    /**
     * Accept : application/x-www-form-urlencoded
     * <p>
     * 以post方式提交form数据，返回结果类型由returnType决定
     *
     * @param url        待请求的url
     * @param params     待请求的参数，
     * @param returnType 返回类型，HttpUtil.TYPE_JSON,HttpUtil.TYPE_HTML
     * @return
     */
    public static String postForm(String url, Map<String, Object> params, String returnType) {
        String result = null;
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader("Accept", returnType);
            httpPost.addHeader("Accept-Charset", CHARSET);
            List<NameValuePair> formParams = new ArrayList<NameValuePair>();
            if (params != null && params.size() > 0) {
                for (String key : params.keySet()) {
                    formParams.add(new BasicNameValuePair(key, "" + params.get(key)));
                }
            }
            UrlEncodedFormEntity requestEntity = new UrlEncodedFormEntity(formParams, CHARSET);
            httpPost.setEntity(requestEntity);
            //  url = URLDecoder.decode(url, CHARSET);
            CloseableHttpResponse response = httpClient.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                result = EntityUtils.toString(responseEntity, CHARSET);
            }
            response.close();
            httpClient.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;

    }
}
