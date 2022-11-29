package com.spring.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.spring.vo.BoardVO;

@Service
public interface BoardService {
	public List<BoardVO> getAllList();
}
