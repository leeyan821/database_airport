package com.spring.service;

import java.util.List;

import org.springframework.stereotype.Service;
import com.spring.vo.AirportVO;
import com.spring.vo.scheduleVO;

@Service
public interface AirportService {
	public List<AirportVO> getAllList();
	public List<scheduleVO> search_Max();
	public List<scheduleVO> search_Min();
}
