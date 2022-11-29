package com.spring.dao;

import java.sql.Date;
import java.util.List;

import com.spring.vo.scheduleVO;

public interface ScheduleDAO {
	public List<scheduleVO> getSchedule();
	
	public List<scheduleVO> search(String airline1, String airline2,String date1, String price);
	public List<scheduleVO> join();
	public int count(String airline1, String airline2,String date1, String price);
	public int avg(String airline1, String airline2,String date1, String price);
	
	public List<scheduleVO> priceAsc(String airline1, String airline2, String date1, String date2, String price,String val);
	public List<scheduleVO> priceDesc(String airline1, String airline2, String date1, String date2, String price,String val);
	public List<scheduleVO> groupBy();
	public List<scheduleVO> time(String airline1, String airline2, String date1,String price,String val);
	public List<scheduleVO> time_now(String airline1, String airline2, String date1,String price);
}
