package com.spring.service;

import java.sql.Date;
import java.util.List;
import org.springframework.stereotype.Repository;

import com.spring.vo.scheduleVO;
import com.spring.dao.ScheduleDAO;
import com.spring.dao.ScheduleDAOImpl;

@Repository
public class ScheduleServiceImpl implements ScheduleService {
	private ScheduleDAO dao;
	private static ScheduleServiceImpl service1;
	
	private ScheduleServiceImpl() {
		dao = ScheduleDAOImpl.get_Instance();
	}
	public static ScheduleServiceImpl getInstance(){
		if(service1==null) service1 = new ScheduleServiceImpl();
		return service1;
	}
	
	@Override
	public List<scheduleVO> getSchedule() {
		return dao.getSchedule();
	}
	@Override
	public List<scheduleVO> search(String airline1, String airline2,String date1, String price) {
		return dao.search(airline1, airline2, date1, price);
	}
	@Override
	public List<scheduleVO> join() {
		return dao.join();
	}
	@Override
	public int count(String airline1, String airline2, String date1, String price) {
		return dao.count(airline1, airline2, date1, price);
	}
	@Override
	public int avg(String airline1, String airline2, String date1, String price) {
		return dao.avg(airline1, airline2, date1, price);
	}
	@Override
	public List<scheduleVO> priceAsc (String airline1, String airline2, String date1, String date2, String price, String val){
		return dao.priceAsc(airline1, airline2, date1, date2, price, val);
	}
	@Override
	public List<scheduleVO> priceDesc (String airline1, String airline2, String date1, String date2, String price, String val){
		return dao.priceDesc(airline1, airline2, date1, date2, price, val);
	}
	@Override
	   public List<scheduleVO> groupBy() {
	      return dao.groupBy();
	   }
	@Override
	public List<scheduleVO> time(String airline1, String airline2, String date1, String price,
			String val) {
		return dao.time(airline1, airline2, date1, price, val);
	}
	@Override
	public List<scheduleVO> time_now(String airline1, String airline2, String date1, String price) {
		return dao.time_now(airline1, airline2, date1, price);
	}

}
