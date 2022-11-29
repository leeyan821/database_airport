package com.spring.dao;

import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Repository;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.sql.Time;
import java.time.LocalDate;

import com.spring.vo.scheduleVO;

@Repository
public class ScheduleDAOImpl implements ScheduleDAO{
	private static ScheduleDAOImpl dao;
	private ScheduleDAOImpl() {}

	public static ScheduleDAOImpl get_Instance() {
		if(dao==null) dao = new ScheduleDAOImpl();
		return dao;
	}
	private Connection conn;
	private PreparedStatement pstmt;
	private Statement stmt;
	private ResultSet rs;
	
	private void disConnect(){
		if(rs!=null) try{ rs.close(); }catch(SQLException e){}
		if(stmt!=null) try{ stmt.close(); }catch(SQLException e){}
		if(pstmt!=null) try{ pstmt.close(); }catch(SQLException e){}
		if(conn!=null) try{ conn.close(); }catch(SQLException e){}
	}
	
	@Override
	public List<scheduleVO> getSchedule() {
		List<scheduleVO> boardList = null;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/airport?allowPublicRetrieval=true&useSSL=false","root","1004");
			
			String sql = "select *from schdule;";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			boardList = new ArrayList<scheduleVO>();
			
			while(rs.next()) {
				scheduleVO scheduleVo = new scheduleVO();
				scheduleVo.setNum(rs.getInt("번호"));
				scheduleVo.setAirline(rs.getString("항공사"));
				scheduleVo.setName(rs.getString("편명"));
				scheduleVo.setDeparture(rs.getString("출발공항"));
				scheduleVo.setDes(rs.getString("도착공항"));
				scheduleVo.setDe_time(rs.getString("출발시간"));
				scheduleVo.setDes_time(rs.getString("도착시간"));
				scheduleVo.setDay(rs.getString("운항요일"));
				scheduleVo.setDe_day(rs.getString("시작일자"));
				scheduleVo.setDes_day(rs.getString("종료일자"));
				scheduleVo.setPart(rs.getString("기종"));
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
	public List<scheduleVO> join() {
		List<scheduleVO> boardList = null;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/airport?allowPublicRetrieval=true&useSSL=false","root","1004");
			
			String sql = "select A.번호, d.full as 항공사, A.편명, b.full as 출발공항, c.full as 도착공항, A.출발시간, A.도착시간, A.운항요일, A.시작일자, A.종료일자, A.기종, A.가격 "
					+ "from schdule as A left join airport_table as b on b.short = A.출발공항 left join airport_table as c on c.short = A.도착공항 "
					+ "left join airline as d on A.항공사 = d.shorts;";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			boardList = new ArrayList<scheduleVO>();
			
			while(rs.next()) {
				scheduleVO scheduleVo = new scheduleVO();
				scheduleVo.setNum(rs.getInt("번호"));
				scheduleVo.setAirline(rs.getString("항공사"));
				scheduleVo.setName(rs.getString("편명"));
				scheduleVo.setDeparture(rs.getString("출발공항"));
				scheduleVo.setDes(rs.getString("도착공항"));
				scheduleVo.setDe_time(rs.getString("출발시간"));
				scheduleVo.setDes_time(rs.getString("도착시간"));
				scheduleVo.setDay(rs.getString("운항요일"));
				scheduleVo.setDe_day(rs.getString("시작일자"));
				scheduleVo.setDes_day(rs.getString("종료일자"));
				scheduleVo.setPart(rs.getString("기종"));
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
	public List<scheduleVO> search(String airline1, String airline2,String date1, String price) {
		List<scheduleVO> boardList = null;	
		try {
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/airport?allowPublicRetrieval=true&useSSL=false","root","1004");
			char[] d= {'월','화','수','목','금','토','일'};
			
			//출발 요일 받기
			String sql2 = "select weekday('"+date1+"') as 'day';";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql2);
			int day = 0;
			if(rs.next()) {
				day = rs.getInt("day");
			}	
			char day2 = d[day];
				
			//한국어 바꾸기
			String sql3 = "select short from airport_table where full LIKE '%"+airline1
					+"%' or short = '" + airline1 + "';";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql3);
			if(rs.next()) {
				airline1 = rs.getString("short");
			}	
			
			String sql4 = "select short from airport_table where full LIKE '%"+airline2
					+"%' or short = '" + airline2 + "';";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql4);
			if(rs.next()) {
				airline2 = rs.getString("short");
			}	
			
			//편도 필터
			String sql = "select A.번호, A.항공사, A.편명, b.full as dep, c.full as des, "
		               + "A.출발시간, A.도착시간, A.운항요일, A.시작일자, A.종료일자, A.기종, A.가격 "
		               + "from schdule as A left join airport_table as b on b.short = A.출발공항 "
		               + "left join airport_table as c on c.short = A.도착공항 where 출발공항 = '"
		               +airline1+"' and 도착공항 = '"+airline2+"' and 시작일자 <= '"+date1+
		               "' and 종료일자 >= '"+date1+"' and 가격 <= "+price+" and 운항요일 LIKE '%"+day2
		               +"%';";
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			boardList = new ArrayList<scheduleVO>();
				
			while(rs.next()) {
				scheduleVO scheduleVo = new scheduleVO();
				scheduleVo.setNum(rs.getInt("번호"));
				scheduleVo.setAirline(rs.getString("항공사"));
				scheduleVo.setName(rs.getString("편명"));
				scheduleVo.setDeparture(rs.getString("dep"));
				scheduleVo.setDes(rs.getString("des"));
				scheduleVo.setDe_time(rs.getString("출발시간"));
				scheduleVo.setDes_time(rs.getString("도착시간"));
				scheduleVo.setDay(rs.getString("운항요일"));
				scheduleVo.setDe_day(rs.getString("시작일자"));
				scheduleVo.setDes_day(rs.getString("종료일자"));
				scheduleVo.setPart(rs.getString("기종"));
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
	public int count(String airline1, String airline2, String date1, String price) {
		int count = 0;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/airport?allowPublicRetrieval=true&useSSL=false","root","1004");
			char[] d= {'월','화','수','목','금','토','일'};
			
			//출발 요일 받기
			String sql2 = "select weekday('"+date1+"') as 'day';";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql2);
			int day = 0;
			if(rs.next()) {
				day = rs.getInt("day");
			}	
			char day2 = d[day];
			
			//한국어 바꾸기
			String sql3 = "select short from airport_table where full LIKE '%"+airline1
					+"%' or short = '" + airline1 + "';";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql3);
			if(rs.next()) {
				airline1 = rs.getString("short");
			}	
			
			String sql4 = "select short from airport_table where full LIKE '%"+airline2
					+"%' or short = '" + airline2 + "';";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql4);
			if(rs.next()) {
				airline2 = rs.getString("short");
			}	
			
			String sql = "select count(*) as num from schdule where 출발공항 = '"+ airline1
					+ "' and 도착공항 = '"+ airline2 + "' and 시작일자 <= '" + date1 + "' and 종료일자 >= '"
					+ date1 + "' and 가격 <= " + price + " and 운항요일 LIKE '%" + day2 + "%';";
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);				
	
			while(rs.next()) {
				count = rs.getInt("num");
			}
		}catch(SQLException e) {
			count = 0;
			e.printStackTrace();
		}finally {
			disConnect();
		}
		return count;
	}

	@Override
	public int avg(String airline1, String airline2, String date1, String price) {
		int avg = 0;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/airport?allowPublicRetrieval=true&useSSL=false","root","1004");
			char[] d= {'월','화','수','목','금','토','일'};
			
			//출발 요일 받기
			String sql2 = "select weekday('"+date1+"') as 'day';";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql2);
			int day = 0;
			if(rs.next()) {
				day = rs.getInt("day");
			}	
			char day2 = d[day];
			
			//한국어 바꾸기
			String sql3 = "select short from airport_table where full LIKE '%"+airline1
					+"%' or short = '" + airline1 + "';";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql3);
			if(rs.next()) {
				airline1 = rs.getString("short");
			}	
			
			String sql4 = "select short from airport_table where full LIKE '%"+airline2
					+"%' or short = '" + airline2 + "';";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql4);
			if(rs.next()) {
				airline2 = rs.getString("short");
			}	
			
			String sql = "select avg(가격) as avg from schdule where 출발공항 = '"+ airline1
					+ "' and 도착공항 = '"+ airline2 + "' and 시작일자 <= '" + date1 + "' and 종료일자 >= '"
					+ date1 + "' and 가격 <= " + price + " and 운항요일 LIKE '%" + day2 + "%';";
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);				
	
			while(rs.next()) {
				avg = rs.getInt("avg");
			}
		}catch(SQLException e) {
			avg = 0;
			e.printStackTrace();
		}finally {
			disConnect();
		}
		return avg;
	}
	@Override
	public List<scheduleVO> priceAsc(String airline1, String airline2,String date1,String date2, String price, String val) {
		List<scheduleVO> boardList = null;	
		try {
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/airport?allowPublicRetrieval=true&useSSL=false","root","1004");
			char[] d= {'월','화','수','목','금','토','일'};
			
			//출발 요일 받기
			String sql2 = "select weekday('"+date1+"') as 'day';";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql2);
			int day = 0;
			if(rs.next()) {
				day = rs.getInt("day");
			}	
			char day2 = d[day];			
			//한국어 바꾸기
			String sql3 = "select short from airport_table where full LIKE '%"+airline1
					+"%' or short = '" + airline1 + "';";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql3);
			if(rs.next()) {
				airline1 = rs.getString("short");
			}	
			
			String sql4 = "select short from airport_table where full LIKE '%"+airline2
					+"%' or short = '" + airline2 + "';";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql4);
			if(rs.next()) {
				airline2 = rs.getString("short");
			}	
			
			//총 필터
			String sql = "select A.번호, A.항공사, A.편명, b.full as dep, c.full as des, "
					+ "A.출발시간, A.도착시간, A.운항요일, A.시작일자, A.종료일자, A.기종, A.가격 "
					+ "from schdule as A left join airport_table as b on b.short = A.출발공항 "
					+ "left join airport_table as c on c.short = A.도착공항 where 출발공항 = '"
					+airline1+"' and 도착공항 = '"+airline2+"' and 시작일자 <= '"+date1+
					"' and 종료일자 >= '"+date1+"' and 가격 <= "+price+" and 운항요일 LIKE '%"+day2
					+"%' order by A.가격 asc;";	
		
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			boardList = new ArrayList<scheduleVO>();		
			
			while(rs.next()) {
				scheduleVO scheduleVo = new scheduleVO();
				scheduleVo.setNum(rs.getInt("번호"));
				scheduleVo.setAirline(rs.getString("항공사"));
				scheduleVo.setName(rs.getString("편명"));
				scheduleVo.setDeparture(rs.getString("dep"));
				scheduleVo.setDes(rs.getString("des"));
				scheduleVo.setDe_time(rs.getString("출발시간"));
				scheduleVo.setDes_time(rs.getString("도착시간"));
				scheduleVo.setDay(rs.getString("운항요일"));
				scheduleVo.setDe_day(rs.getString("시작일자"));
				scheduleVo.setDes_day(rs.getString("종료일자"));
				scheduleVo.setPart(rs.getString("기종"));
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
	public List<scheduleVO> priceDesc(String airline1, String airline2, String date1, String date2, String price, String val) {
		List<scheduleVO> boardList = null;	
		try {
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/airport?allowPublicRetrieval=true&useSSL=false","root","1004");
			char[] d= {'월','화','수','목','금','토','일'};
			
			//출발 요일 받기
			String sql2 = "select weekday('"+date1+"') as 'day';";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql2);
			int day = 0;
			if(rs.next()) {
				day = rs.getInt("day");
			}	
			char day2 = d[day];	
			//한국어 바꾸기
			String sql3 = "select short from airport_table where full LIKE '%"+airline1
					+"%' or short = '" + airline1 + "';";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql3);
			if(rs.next()) {
				airline1 = rs.getString("short");
			}	
			
			String sql4 = "select short from airport_table where full LIKE '%"+airline2
					+"%' or short = '" + airline2 + "';";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql4);
			if(rs.next()) {
				airline2 = rs.getString("short");
			}	
			//총 필터
			String sql = "select A.번호, A.항공사, A.편명, b.full as dep, c.full as des, "
					+ "A.출발시간, A.도착시간, A.운항요일, A.시작일자, A.종료일자, A.기종, A.가격 "
					+ "from schdule as A left join airport_table as b on b.short = A.출발공항 "
					+ "left join airport_table as c on c.short = A.도착공항 where 출발공항 = '"
					+airline1+"' and 도착공항 = '"+airline2+"' and 시작일자 <= '"+date1+
					"' and 종료일자 >= '"+date1+"' and 가격 <= "+price+" and 운항요일 LIKE '%"+day2
					+"%' order by A.가격 desc;";	
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			boardList = new ArrayList<scheduleVO>();			
			
			while(rs.next()) {
				scheduleVO scheduleVo = new scheduleVO();
				scheduleVo.setNum(rs.getInt("번호"));
				scheduleVo.setAirline(rs.getString("항공사"));
				scheduleVo.setName(rs.getString("편명"));
				scheduleVo.setDeparture(rs.getString("dep"));
				scheduleVo.setDes(rs.getString("des"));
				scheduleVo.setDe_time(rs.getString("출발시간"));
				scheduleVo.setDes_time(rs.getString("도착시간"));
				scheduleVo.setDay(rs.getString("운항요일"));
				scheduleVo.setDe_day(rs.getString("시작일자"));
				scheduleVo.setDes_day(rs.getString("종료일자"));
				scheduleVo.setPart(rs.getString("기종"));
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
	   public List<scheduleVO> groupBy() {
	      List<scheduleVO> boardList = null;
	      try {
	         conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/airport?allowPublicRetrieval=true&useSSL=false","root","1004");
	         
	         String sql = "select airline.full as 항공사, count(항공사) as 비행기수 from schdule \r\n"
	               + " left join airline on schdule.항공사 = airline.shorts group by 항공사;";
	         stmt = conn.createStatement();
	         rs = stmt.executeQuery(sql);
	         
	         
	         boardList = new ArrayList<scheduleVO>();
	         while(rs.next()) {
	            scheduleVO scheduleVo = new scheduleVO();
	            scheduleVo.setAirline(rs.getString("항공사"));
	            scheduleVo.setDes(rs.getString("비행기수"));
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
	public List<scheduleVO> time(String airline1, String airline2, String date1, String price,
			String val) {
		List<scheduleVO> boardList = null;	
		try {
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/airport?allowPublicRetrieval=true&useSSL=false","root","1004");
			char[] d= {'월','화','수','목','금','토','일'};
			
			//출발 요일 받기
			String sql2 = "select weekday('"+date1+"') as 'day';";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql2);
			int day = 0;
			if(rs.next()) {
				day = rs.getInt("day");
			}	
			char day2 = d[day];	
			//한국어 바꾸기
			String sql3 = "select short from airport_table where full LIKE '%"+airline1
					+"%' or short = '" + airline1 + "';";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql3);
			if(rs.next()) {
				airline1 = rs.getString("short");
			}	
			
			String sql4 = "select short from airport_table where full LIKE '%"+airline2
					+"%' or short = '" + airline2 + "';";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql4);
			if(rs.next()) {
				airline2 = rs.getString("short");
			}	
			//총 필터
			String sql = "select A.번호, A.항공사, A.편명, b.full as dep, c.full as des, "
					+ "A.출발시간, A.도착시간, A.운항요일, A.시작일자, A.종료일자, A.기종, A.가격 "
					+ "from schdule as A left join airport_table as b on b.short = A.출발공항 "
					+ "left join airport_table as c on c.short = A.도착공항 where A.번호 in ("
					+ "select 번호 from schdule where 출발시간 "+ val + " and 출발공항 = '"
					+ airline1 + "' and 도착공항 = '" + airline2 + "' and 시작일자 <= '"
					+ date1 + "' and 종료일자 >= '" + date1 + "' and 운항요일 LIKE '%" + day2
					+ "%' and 가격 <= "+ price + ");";
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			boardList = new ArrayList<scheduleVO>();			
			
			while(rs.next()) {
				scheduleVO scheduleVo = new scheduleVO();
				scheduleVo.setNum(rs.getInt("번호"));
				scheduleVo.setAirline(rs.getString("항공사"));
				scheduleVo.setName(rs.getString("편명"));
				scheduleVo.setDeparture(rs.getString("dep"));
				scheduleVo.setDes(rs.getString("des"));
				scheduleVo.setDe_time(rs.getString("출발시간"));
				scheduleVo.setDes_time(rs.getString("도착시간"));
				scheduleVo.setDay(rs.getString("운항요일"));
				scheduleVo.setDe_day(rs.getString("시작일자"));
				scheduleVo.setDes_day(rs.getString("종료일자"));
				scheduleVo.setPart(rs.getString("기종"));
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
	public List<scheduleVO> time_now(String airline1, String airline2, String date1, String price) {
		List<scheduleVO> boardList = null;	
		try {
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/airport?allowPublicRetrieval=true&useSSL=false","root","1004");
			char[] d= {'월','화','수','목','금','토','일'};
			
			//출발 요일 받기
			String sql2 = "select weekday('"+date1+"') as 'day';";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql2);
			int day = 0;
			if(rs.next()) {
				day = rs.getInt("day");
			}	
			char day2 = d[day];	
			//한국어 바꾸기
			String sql3 = "select short from airport_table where full LIKE '%"+airline1
					+"%' or short = '" + airline1 + "';";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql3);
			if(rs.next()) {
				airline1 = rs.getString("short");
			}	
			
			String sql4 = "select short from airport_table where full LIKE '%"+airline2
					+"%' or short = '" + airline2 + "';";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql4);
			if(rs.next()) {
				airline2 = rs.getString("short");
			}	
			//총 필터
			String sql = "select A.번호, A.항공사, A.편명, b.full as dep, c.full as des, "
					+ "A.출발시간, A.도착시간, A.운항요일, A.시작일자, A.종료일자, A.기종, A.가격 "
					+ "from schdule as A left join airport_table as b on b.short = A.출발공항 "
					+ "left join airport_table as c on c.short = A.도착공항 where "
					+ "출발공항 = '"+ airline1 + "' and 도착공항 = '" + airline2 + "' and 시작일자 "
					+ "<= '" + date1 + "' and 종료일자 >= '" + date1 + "' and 가격 <= " + price
					+ " and 운항요일 LIKE '%" + day2 + "%' and A.출발시간 >= (select "
					+ "date_add(NOW(), interval 2 hour)) and A.출발시간 <= (select "
					+ "date_add(NOW(), interval 5 hour));";
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			boardList = new ArrayList<scheduleVO>();			
			
			while(rs.next()) {
				scheduleVO scheduleVo = new scheduleVO();
				scheduleVo.setNum(rs.getInt("번호"));
				scheduleVo.setAirline(rs.getString("항공사"));
				scheduleVo.setName(rs.getString("편명"));
				scheduleVo.setDeparture(rs.getString("dep"));
				scheduleVo.setDes(rs.getString("des"));
				scheduleVo.setDe_time(rs.getString("출발시간"));
				scheduleVo.setDes_time(rs.getString("도착시간"));
				scheduleVo.setDay(rs.getString("운항요일"));
				scheduleVo.setDe_day(rs.getString("시작일자"));
				scheduleVo.setDes_day(rs.getString("종료일자"));
				scheduleVo.setPart(rs.getString("기종"));
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
