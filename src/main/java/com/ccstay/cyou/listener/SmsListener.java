//package com.ccstay.cyou.listener;
//
//import com.aliyuncs.exceptions.ClientException;
//
//import com.ccstay.cyou.util.SmsUtil;
//import org.springframework.amqp.rabbit.annotation.RabbitHandler;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import java.util.Map;
//
//@Component
//@RabbitListener(queues = "sms.checkcode")
//public class SmsListener {
//    @Autowired
//    private SmsUtil smsUtil;
//
//    @Value("${aliyun.sms.template_code}")
//    private  String template_code;
//    //你的accessKeySecret
//    @Value("${aliyun.sms.sign_name}")
//    private  String sign_name ;
//    @RabbitHandler
//    public void executeSms(Map<String,String> map){
//        System.err.println(map.get("mobile")+"   "+map.get("checkcode"));
//        try {
//            smsUtil.sendSms(map.get("mobile"), sign_name, template_code, "{\"code\":" + map.get("checkcode") + "}");
//        }catch (ClientException e){
//            e.printStackTrace();
//        }
//    }
//}
