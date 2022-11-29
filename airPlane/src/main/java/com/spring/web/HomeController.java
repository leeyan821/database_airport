package com.spring.web;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.spring.service.ScheduleService;
import com.spring.service.AirportService;
/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		return "home";
	}
	
	@Inject
	private ScheduleService ser;
	
	@RequestMapping(value="/move.do",method = RequestMethod.GET)
	public String move(HttpServletRequest request, Model model) {
		String val = request.getParameter("val");
		String airline1;
		String airline2;
		String date1;
		String date2;
		String price;
		List s1 = null;
		int re1 = 0;
		int re2 = 0;
		if(val == null) {
			airline1 = request.getParameter("s1");
			airline2 = request.getParameter("s2");
			date1 = request.getParameter("s3");
			date2 = request.getParameter("s4");
			price = request.getParameter("s5");
		
			s1 = ser.search(airline1,airline2,date1,price);
			re1 = ser.count(airline1, airline2, date1, price);
			re2 = ser.avg(airline1, airline2, date1, price);
		}
		else {
			airline1 = request.getParameter("test1");
			airline2 = request.getParameter("test2");
			date1 = request.getParameter("test3");
			date2 = request.getParameter("test4");
			price = request.getParameter("test5");
			
			String id = "up";
			String id2 = "down";
			String id3 = "sun";
			String id4 = "time";
			if(val.equals(id)) {
				System.out.println("if(up)");
				s1 = ser.priceAsc(airline1,airline2,date1,date2,price,val);
				re1 = ser.count(airline1, airline2, date1, price);
				re2 = ser.avg(airline1, airline2, date1, price);
			}
			else if(val.equals(id2)) {
				System.out.println("else(down)");
				s1 = ser.priceDesc(airline1,airline2,date1,date2,price,val);
				re1 = ser.count(airline1, airline2, date1, price);
				re2 = ser.avg(airline1, airline2, date1, price);
			}
			else if(val.equals(id4)) {
				System.out.println("now time");
				s1 = ser.time_now(airline1, airline2, date1, price);
				re1 = ser.count(airline1, airline2, date1, price);
				re2 = ser.avg(airline1, airline2, date1, price);
			}
			else {
				if(val.equals(id3)) {
					System.out.println("sun");
					String re = "< '12:00:00'";
					s1 = ser.time(airline1, airline2, date1, price, re);
					re1 = ser.count(airline1, airline2, date1, price);
					re2 = ser.avg(airline1, airline2, date1, price);
				}
				else {
					System.out.println("moon");
					String re = ">= '12:00:00'";
					s1 = ser.time(airline1,airline2,date1,price,re);
					re1 = ser.count(airline1, airline2, date1, price);
					re2 = ser.avg(airline1, airline2, date1, price);
				}
			}
		}				
		
		model.addAttribute("list2",s1);
		model.addAttribute("s1", airline1);
		model.addAttribute("s2", airline2);
		model.addAttribute("s3", date1);
		model.addAttribute("s4", date2);
		model.addAttribute("s5", price);
		model.addAttribute("count", re1);
		model.addAttribute("avg", re2);
		
		return "/board/list";
	}
	
	@Inject
	private AirportService service;
	
	@RequestMapping(value="/board/allList",method = RequestMethod.GET)
	public String allList(HttpServletRequest request, Model model) {
		List s1 = service.search_Max();
		List s3 = service.search_Min();
		List s2 = ser.join();
		List s4 = ser.groupBy();
	    
		model.addAttribute("list4",s4);

		model.addAttribute("list1",s1);
		model.addAttribute("list3",s3);
		model.addAttribute("list2",s2);
		
		return "/board/allList";
	}
	
}
