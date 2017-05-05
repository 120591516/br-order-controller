package br.order.test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import br.order.common.utils.SimpleEmail;


public class TestController {
	@Test
	public void testSendMail() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring/spring-mail.xml");
        SimpleEmail mail = (SimpleEmail)context.getBean("simpleMail");
        mail.sendMail("Spring SMTP Mail Subject", "Spring SMTP Mail Text", "747974530@qq.com");
        //mail.sendMail("标题", "内容", "收件人邮箱");
    }
}
