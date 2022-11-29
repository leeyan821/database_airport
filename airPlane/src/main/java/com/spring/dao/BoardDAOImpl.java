package com.spring.dao;

import java.util.List;
import javax.inject.Inject;
import org.springframework.stereotype.Repository;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.spring.vo.BoardVO;

@Repository
public class BoardDAOImpl implements BoardDAO{
	private static BoardDAOImpl dao;
	private BoardDAOImpl() {}
	
	public static BoardDAOImpl getInstance() {
		if(dao==null) dao = new BoardDAOImpl();
		return dao;
	}
	
	private Connection conn;
	private PreparedStatement pstmt;
	private Statement stmt;
	private ResultSet rs;
	
	// 사용한 자원을 반납하는 메서드
	private void disConnect(){
		if(rs!=null) try{ rs.close(); }catch(SQLException e){}
		if(stmt!=null) try{ stmt.close(); }catch(SQLException e){}
		if(pstmt!=null) try{ pstmt.close(); }catch(SQLException e){}
		if(conn!=null) try{ conn.close(); }catch(SQLException e){}
	}
	
	@Override
	public List list() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<BoardVO> getAllList() {
		List<BoardVO> boardList = null;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/airport?allowPublicRetrieval=true&useSSL=false","root","1004");
			
			String sql = "select * from airline";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			boardList = new ArrayList<BoardVO>();
			
			while(rs.next()) {
				BoardVO boardVo = new BoardVO();
				boardVo.setShorts(rs.getString("shorts"));
				boardVo.setFull(rs.getString("full"));
				
				boardList.add(boardVo);
			}
		}catch(SQLException e) {
			boardList = null;
			e.printStackTrace();
		}finally {
			disConnect();
		}
		return boardList;
	}
}
