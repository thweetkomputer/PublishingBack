package com.publishing.util;

import org.junit.Test;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

/**
 * @ClassName:      SendMail
 * @Auther:         Mollen
 * @CreateTime:     2018-10-17  23:56:36
 * @Description:
 *              简单邮件发送封装工具类：
 *                  qq邮箱可用：已经通过测试
 */
public class SendMailUtils {

    /**
     * 1.静态成员变量
     */
    private static final Properties props;                        // 初始化参数
    private static InternetAddress sendMan = null;          // 发件人地址
    private static final String userName = "2320092610@qq.com";      // 发件人的邮箱地址
    private static String password = "pcgfatmsddujebbe";        // 发件人的密码(授权码)

    //网络参数
    static {
        props = new Properties();
        props.put("mail.transport.protocol", "smtp");  // 指定协议
        props.put("mail.smtp.host", "smtp.qq.com");    // 主机 smtp.qq.com
//        props.put("mail.smtp.port", 25);               // 端口
        props.put("mail.smtp.auth", "true");           // 用户密码认证
        //props.put("mail.debug", "true");               // 调试模式

        try {
            //创建地址对象
            sendMan = new InternetAddress(userName);
        } catch (AddressException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 2.静态方法用于发送邮件
     *
     */
    public static void sendMail(String to ,String title,String text) throws AddressException, MessagingException {

        Session session = Session.getInstance(props);    // 创建邮件会话
        MimeMessage msg = new MimeMessage(session);      // 创建邮件对象

        msg.setFrom(sendMan);                            // 设置发件人
        msg.setRecipients(Message.RecipientType.TO,to);  // 设置邮件收件人
        msg.setSubject(title);                           // 标题
        msg.setSentDate(new Date());                     // 发送时间
        msg.setContent(text, "text/html;charset=UTF-8"); // 发送内容

        // 发送
        try{
            Transport trans = session.getTransport();
            trans.connect(userName, password);
            trans.sendMessage(msg, msg.getAllRecipients());
            System.out.println("发送完成...");
            trans.close();
        }catch(Exception e){
            System.out.println("发送失败...");
            e.printStackTrace();
        }
    }

    @Test
    public void  test(){
        try {
            SendMailUtils.sendMail("8916048@qq.com","收到一封新邮件","尊敬的用户您好\n您的验证码是10312");
//            SendMailUtils.sendMail("981507892@qq.com","收到一封新邮件，您的验证码为adfadsfdasf","Hello Mail!");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}