package com.spring.service;

import java.util.List;

import org.springframework.stereotype.Repository;
import com.spring.vo.AirportVO;
import com.spring.vo.scheduleVO;
import com.spring.dao.AirportDAO;
import com.spring.dao.AirportDAOImpl;

@Repository
public class AirportServiceImpl implements AirportService{

	private AirportDAO dao;
	private static AirportServiceImpl service;
	
	private AirportServiceImpl() {
		dao = AirportDAOImpl.getInstance();
	}
	public static AirportServiceImpl getInstance(){
		if(service==null) service = new AirportServiceImpl();
		return service;
	}
	@Override
	public List<AirportVO> getAllList() {	
		return dao.getAllList();
	}
	@Override
	public List<scheduleVO> search_Max() {
		return dao.search_Max();
	}
	@Override
	public List<scheduleVO> search_Min() {
		// TODO Auto-generated method stub
		return dao.search_Min();
	}

}
