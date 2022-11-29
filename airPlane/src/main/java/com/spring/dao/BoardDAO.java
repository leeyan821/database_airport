package com.spring.dao;

import java.util.List;

import com.spring.vo.BoardVO;

public interface BoardDAO {
	public List list() throws Exception;
	public List<BoardVO> getAllList();
}
