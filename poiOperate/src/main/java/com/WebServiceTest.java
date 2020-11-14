package com;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import javax.xml.namespace.QName;
/**
 * @author mohuangNPC
 * @version 1.0
 * @date 2020/11/10 20:52
 */
public class WebServiceTest {
    public static void main(String[] args) {
        try {
            //定义字符串，描述要访问的服务器地址 [改为自己需要访问的Web Service服务路径]
            String url = "http://127.0.0.1:8188/service/resourcereceive/UserSynchronize";
            //创建一个 Web Service的服务
            Service sv = new Service();
            //创建一个调用
            Call call = (Call) sv.createCall();
            //指定服务的来源
            call.setTargetEndpointAddress(url);
            //指明调用的具体方法名
            call.setOperationName(new QName(url, "getValue"));
            //执行远端的调用
            call.invoke();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
