package br.order.controller.init;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;


@Component
public class InitServlet implements ApplicationListener<ContextRefreshedEvent> {


	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (event.getApplicationContext().getParent() == null) {
			System.out.println("加载完成ApplicationListener".concat(event.getApplicationContext().getDisplayName()));	
			
			
		}
		
	}

	
}
