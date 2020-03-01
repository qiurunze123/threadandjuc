//package com.geekq.highimporttry;
//
//import com.alibaba.fastjson.JSON;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSenderImpl;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import javax.mail.internet.MimeMessage;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class SpringbootJmsApplicationTests {
//
//    @Test
//    public void contextLoads() {
//    }
//
//
//
//    /**
//     * 发送包含简单文本的邮件
//     */
//    @Test
//    public void sendTxtMail() {
//
//    }
//
//    /**
//     * 发送包含HTML文本的邮件
//     *
//     * @throws Exception
//     */
//    @Test
//    public void sendHtmlMail() throws Exception {
//        MimeMessage mimeMessage = mailSender.createMimeMessage();
//        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
//        mimeMessageHelper.setTo("3341386488@qq.com");
//        mimeMessageHelper.setFrom("QiuRunZe_key@163.com");
//        mimeMessageHelper.setSubject("Spring Boot Mail 邮件测试【HTML】");
//
//        StringBuilder sb = new StringBuilder();
//        sb.append("<html><head></head>");
//        sb.append("<body><h1>spring 邮件测试</h1><p>hello!this is spring mail test。</p></body>");
//        sb.append("</html>");
//
//        // 启用html
//        mimeMessageHelper.setText(sb.toString(), true);
//        // 发送邮件
//        mailSender.send(mimeMessage);
//
//        System.out.println("邮件已发送");
//
//    }
//
//}
