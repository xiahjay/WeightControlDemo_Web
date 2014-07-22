package edu.zju.bme.training.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

import edu.zju.bme.training.service.DemoService;

@Controller
public class HomeController {
	
	@Resource(name="demoService")
	private DemoService demoService;
	
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping(value="/")
	public ModelAndView homePage(){
		this.logger.info("homePage called.");
		Map<String,String> model = new HashMap<String,String>();
		model.put("hello", this.demoService.sayHello());
		return new ModelAndView("index",model);
	}
	
	@RequestMapping(value="/test")
	public ModelAndView testPage(){
		this.logger.info("testPage called.");
		Map<String,String> model = new HashMap<String,String>();
		model.put("hello", "test");
		return new ModelAndView("index",model);
	}
}
