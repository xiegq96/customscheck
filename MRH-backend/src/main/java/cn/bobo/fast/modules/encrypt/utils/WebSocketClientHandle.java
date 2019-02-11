package cn.bobo.fast.modules.encrypt.utils;

import com.alibaba.fastjson.JSONObject;
import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * Created by ken on 2018/12/20.
 */
@Component
public class WebSocketClientHandle {
    public static CountDownLatch countDownLatch = new CountDownLatch(1);
    Map<String,WebSocketClient> clientMap=new HashMap<>();
    public void send(String url, String message, final CallBack onSuccess, final CallBack onError){
        WebSocketClient client = clientMap.get(url);
        if(client!=null&&client.getConnection().isConnecting()
                &&client.getConnection().isOpen()){
            client.send(message);
        }
        else
        {
            try {
                client = new WebSocketClient(new URI(url)) {
                    public void onOpen(ServerHandshake arg0) {
//                        System.out.println("打开链接");
                    }
                    public void onMessage(String arg0) {
                        if(onSuccess!=null){
                            JSONObject signValueJson = JSONObject.parseObject(arg0);
                            String method = signValueJson.getString("_method");
                            if("cus-sec_SpcSignDataAsPEM".equals(method)){
                                countDownLatch.countDown();
                            }
                            onSuccess.run(arg0);
                        }
                    }
                    public void onError(Exception arg0) {
                        if(onError!=null){
                            onError(arg0);
                        }
                    }

                    public void onClose(int arg0, String arg1, boolean arg2) {
//                        System.out.println("链接已关闭");
                    }

                    public void onMessage(ByteBuffer bytes) {
                        try {
                            if(onSuccess!=null){
                                onSuccess.run(new String(bytes.array(),"utf-8"));
                            }
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                };
                client.connect();
                Integer timeOut=3*60*1000;
                Integer step=5;
                Integer count=0;
                //阻塞以等待OPEN
                while(!client.getReadyState().equals(WebSocket.READYSTATE.OPEN)){
                    try {
                        count+=step;
                        Thread.sleep(step);
                        if(count>=timeOut){
                            onError.run(new Exception("WS服务器连接超时！或服务器已经关闭"));
                            break;
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                client.send(message);
                clientMap.put(url,client);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

        }

    }

    public void destroyClient(String url){
        WebSocketClient client = clientMap.get(url);
        if(!client.getConnection().isClosed()){
            client.close();
        }
    }
}
