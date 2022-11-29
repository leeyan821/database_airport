package com.spring.service;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.spring.vo.BoardVO;
import com.spring.dao.BoardDAO;
import com.spring.dao.BoardDAOImpl;

@Repository
public class BoardServiceImpl implements BoardService {
	private BoardDAO dao;
	private static BoardServiceImpl service;
	
	private BoardServiceImpl() {
		dao = BoardDAOImpl.getInstance();
	}
	public static BoardServiceImpl getInstance(){
		if(service==null) service = new BoardServiceImpl();
		return service;
	}
	
	@Override
	public List<BoardVO> getAllList() {
		return dao.getAllList();
	}

}
