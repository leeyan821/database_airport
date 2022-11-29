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

import com.spring.vo.AirportVO;
import com.spring.vo.scheduleVO;

@Repository
public class AirportDAOImpl implements AirportDAO{
	private static AirportDAOImpl dao;
	private AirportDAOImpl() {}
	
	public static AirportDAOImpl getInstance() {
		if(dao==null) dao = new AirportDAOImpl();
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
	public List<AirportVO> getAllList() {
		List<AirportVO> boardList = null;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/airport?allowPublicRetrieval=true&useSSL=false","root","1004");
			
			String sql = "select * from airport_table";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			boardList = new ArrayList<AirportVO>();
			
			while(rs.next()) {
				AirportVO boardVo = new AirportVO();
				boardVo.setShorts(rs.getString("short"));
				boardVo.setFull(rs.getString("full"));
				
				boardList.add(boardVo );
			}
		}catch(SQLException e) {
			boardList = null;
			e.printStackTrace();
		}finally {
			disConnect();
		}
		return boardList;
	}

	@Override
	public List<scheduleVO> search_Max() {
		List<scheduleVO> boardList = null;	
		try {
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/airport?allowPublicRetrieval=true&useSSL=false","root","1004");
			
			String sql = "select (select full from airline as k where k.shorts = A.항공사) as 항공사, (select full from airport_table as b where b.short = A.출발공항) as '출발공항'"
					+ ",(select full from airport_table as c where c.short = A.도착공항) as '도착공항', A.출발시간,A.도착시간, A.운항요일, A.시작일자,"
					+ " A.종료일자, A.가격 from schdule as A where A.가격 IN (select max(가격) from schdule);";		
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			boardList = new ArrayList<scheduleVO>();			
			
			while(rs.next()) {
				scheduleVO scheduleVo = new scheduleVO();
				scheduleVo.setAirline(rs.getString("항공사"));
				scheduleVo.setDeparture(rs.getString("출발공항"));
				scheduleVo.setDes(rs.getString("도착공항"));
				scheduleVo.setDe_time(rs.getString("출발시간"));
				scheduleVo.setDes_time(rs.getString("도착시간"));
				scheduleVo.setDay(rs.getString("운항요일"));
				scheduleVo.setDe_day(rs.getString("시작일자"));
				scheduleVo.setDes_day(rs.getString("종료일자"));
				scheduleVo.setPrice(rs.getString("가격"));
				
				boardList.add(scheduleVo);
			}
		}catch(SQLException e) {
			boardList = null;
			e.printStackTrace();
		}finally {
			disConnect();
		}
		return boardList;
	}

	@Override
	public List<scheduleVO> search_Min() {
		List<scheduleVO> boardList = null;	
		try {
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/airport?allowPublicRetrieval=true&useSSL=false","root","1004");
			
			String sql = "select (select full from airline as k where k.shorts = A.항공사) as 항공사, (select full from airport_table as b where b.short = A.출발공항) as '출발공항'"
					+ ",(select full from airport_table as c where c.short = A.도착공항) as '도착공항', A.출발시간,A.도착시간, A.운항요일, A.시작일자,"
					+ " A.종료일자, A.가격 from schdule as A where A.가격 IN (select min(가격) from schdule);";		
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			boardList = new ArrayList<scheduleVO>();			
			
			while(rs.next()) {
				scheduleVO scheduleVo = new scheduleVO();
				scheduleVo.setAirline(rs.getString("항공사"));
				scheduleVo.setDeparture(rs.getString("출발공항"));
				scheduleVo.setDes(rs.getString("도착공항"));
				scheduleVo.setDe_time(rs.getString("출발시간"));
				scheduleVo.setDes_time(rs.getString("도착시간"));
				scheduleVo.setDay(rs.getString("운항요일"));
				scheduleVo.setDe_day(rs.getString("시작일자"));
				scheduleVo.setDes_day(rs.getString("종료일자"));
				scheduleVo.setPrice(rs.getString("가격"));
				
				boardList.add(scheduleVo);
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
