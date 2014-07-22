package edu.zju.bme.training.service;

import org.springframework.stereotype.Service;

import edu.zju.bme.training.dao.UserDao;
import javax.annotation.Resource;

@Service("demoService")
public class DemoServiceImp implements DemoService{
	
	@Resource(name="userDao")
	private UserDao userDao;
	
	@Override
	public String sayHello(){
		return "Hello World";
	}

}
