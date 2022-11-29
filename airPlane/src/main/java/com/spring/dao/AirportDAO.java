package com.spring.dao;

import java.util.List;
import com.spring.vo.AirportVO;
import com.spring.vo.scheduleVO;

public interface AirportDAO {
	public List<AirportVO> getAllList();
	public List<scheduleVO> search_Max();
	public List<scheduleVO> search_Min();
}
