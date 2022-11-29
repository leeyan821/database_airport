/*package com.spring.web;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import java.util.List;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import com.spring.service.AirportService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/test.do")
public class BoardController {
	
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	@Inject
	private AirportService serv;
	
	@RequestMapping(value="/test.do",method = RequestMethod.GET)
	public String allList(HttpServletRequest request, Model model) {
		
		List s2 = serv.join();
		model.addAttribute("list2",s2);
		
		return "/board/allList";
	}
	
}*/