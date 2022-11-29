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
				scheduleVo.setNum(rs.getInt("��ȣ"));
				scheduleVo.setAirline(rs.getString("�װ���"));
				scheduleVo.setName(rs.getString("���"));
				scheduleVo.setDeparture(rs.getString("��߰���"));
				scheduleVo.setDes(rs.getString("��������"));
				scheduleVo.setDe_time(rs.getString("��߽ð�"));
				scheduleVo.setDes_time(rs.getString("�����ð�"));
				scheduleVo.setDay(rs.getString("���׿���"));
				scheduleVo.setDe_day(rs.getString("��������"));
				scheduleVo.setDes_day(rs.getString("��������"));
				scheduleVo.setPart(rs.getString("����"));
				scheduleVo.setPrice(rs.getString("����"));
				
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
			
			String sql = "select A.��ȣ, d.full as �װ���, A.���, b.full as ��߰���, c.full as ��������, A.��߽ð�, A.�����ð�, A.���׿���, A.��������, A.��������, A.����, A.���� "
					+ "from schdule as A left join airport_table as b on b.short = A.��߰��� left join airport_table as c on c.short = A.�������� "
					+ "left join airline as d on A.�װ��� = d.shorts;";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			boardList = new ArrayList<scheduleVO>();
			
			while(rs.next()) {
				scheduleVO scheduleVo = new scheduleVO();
				scheduleVo.setNum(rs.getInt("��ȣ"));
				scheduleVo.setAirline(rs.getString("�װ���"));
				scheduleVo.setName(rs.getString("���"));
				scheduleVo.setDeparture(rs.getString("��߰���"));
				scheduleVo.setDes(rs.getString("��������"));
				scheduleVo.setDe_time(rs.getString("��߽ð�"));
				scheduleVo.setDes_time(rs.getString("�����ð�"));
				scheduleVo.setDay(rs.getString("���׿���"));
				scheduleVo.setDe_day(rs.getString("��������"));
				scheduleVo.setDes_day(rs.getString("��������"));
				scheduleVo.setPart(rs.getString("����"));
				scheduleVo.setPrice(rs.getString("����"));
				
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
			char[] d= {'��','ȭ','��','��','��','��','��'};
			
			//��� ���� �ޱ�
			String sql2 = "select weekday('"+date1+"') as 'day';";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql2);
			int day = 0;
			if(rs.next()) {
				day = rs.getInt("day");
			}	
			char day2 = d[day];
				
			//�ѱ��� �ٲٱ�
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
			
			//�� ����
			String sql = "select A.��ȣ, A.�װ���, A.���, b.full as dep, c.full as des, "
		               + "A.��߽ð�, A.�����ð�, A.���׿���, A.��������, A.��������, A.����, A.���� "
		               + "from schdule as A left join airport_table as b on b.short = A.��߰��� "
		               + "left join airport_table as c on c.short = A.�������� where ��߰��� = '"
		               +airline1+"' and �������� = '"+airline2+"' and �������� <= '"+date1+
		               "' and �������� >= '"+date1+"' and ���� <= "+price+" and ���׿��� LIKE '%"+day2
		               +"%';";
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			boardList = new ArrayList<scheduleVO>();
				
			while(rs.next()) {
				scheduleVO scheduleVo = new scheduleVO();
				scheduleVo.setNum(rs.getInt("��ȣ"));
				scheduleVo.setAirline(rs.getString("�װ���"));
				scheduleVo.setName(rs.getString("���"));
				scheduleVo.setDeparture(rs.getString("dep"));
				scheduleVo.setDes(rs.getString("des"));
				scheduleVo.setDe_time(rs.getString("��߽ð�"));
				scheduleVo.setDes_time(rs.getString("�����ð�"));
				scheduleVo.setDay(rs.getString("���׿���"));
				scheduleVo.setDe_day(rs.getString("��������"));
				scheduleVo.setDes_day(rs.getString("��������"));
				scheduleVo.setPart(rs.getString("����"));
				scheduleVo.setPrice(rs.getString("����"));
				
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
			char[] d= {'��','ȭ','��','��','��','��','��'};
			
			//��� ���� �ޱ�
			String sql2 = "select weekday('"+date1+"') as 'day';";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql2);
			int day = 0;
			if(rs.next()) {
				day = rs.getInt("day");
			}	
			char day2 = d[day];
			
			//�ѱ��� �ٲٱ�
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
			
			String sql = "select count(*) as num from schdule where ��߰��� = '"+ airline1
					+ "' and �������� = '"+ airline2 + "' and �������� <= '" + date1 + "' and �������� >= '"
					+ date1 + "' and ���� <= " + price + " and ���׿��� LIKE '%" + day2 + "%';";
			
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
			char[] d= {'��','ȭ','��','��','��','��','��'};
			
			//��� ���� �ޱ�
			String sql2 = "select weekday('"+date1+"') as 'day';";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql2);
			int day = 0;
			if(rs.next()) {
				day = rs.getInt("day");
			}	
			char day2 = d[day];
			
			//�ѱ��� �ٲٱ�
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
			
			String sql = "select avg(����) as avg from schdule where ��߰��� = '"+ airline1
					+ "' and �������� = '"+ airline2 + "' and �������� <= '" + date1 + "' and �������� >= '"
					+ date1 + "' and ���� <= " + price + " and ���׿��� LIKE '%" + day2 + "%';";
			
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
			char[] d= {'��','ȭ','��','��','��','��','��'};
			
			//��� ���� �ޱ�
			String sql2 = "select weekday('"+date1+"') as 'day';";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql2);
			int day = 0;
			if(rs.next()) {
				day = rs.getInt("day");
			}	
			char day2 = d[day];			
			//�ѱ��� �ٲٱ�
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
			
			//�� ����
			String sql = "select A.��ȣ, A.�װ���, A.���, b.full as dep, c.full as des, "
					+ "A.��߽ð�, A.�����ð�, A.���׿���, A.��������, A.��������, A.����, A.���� "
					+ "from schdule as A left join airport_table as b on b.short = A.��߰��� "
					+ "left join airport_table as c on c.short = A.�������� where ��߰��� = '"
					+airline1+"' and �������� = '"+airline2+"' and �������� <= '"+date1+
					"' and �������� >= '"+date1+"' and ���� <= "+price+" and ���׿��� LIKE '%"+day2
					+"%' order by A.���� asc;";	
		
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			boardList = new ArrayList<scheduleVO>();		
			
			while(rs.next()) {
				scheduleVO scheduleVo = new scheduleVO();
				scheduleVo.setNum(rs.getInt("��ȣ"));
				scheduleVo.setAirline(rs.getString("�װ���"));
				scheduleVo.setName(rs.getString("���"));
				scheduleVo.setDeparture(rs.getString("dep"));
				scheduleVo.setDes(rs.getString("des"));
				scheduleVo.setDe_time(rs.getString("��߽ð�"));
				scheduleVo.setDes_time(rs.getString("�����ð�"));
				scheduleVo.setDay(rs.getString("���׿���"));
				scheduleVo.setDe_day(rs.getString("��������"));
				scheduleVo.setDes_day(rs.getString("��������"));
				scheduleVo.setPart(rs.getString("����"));
				scheduleVo.setPrice(rs.getString("����"));
				
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
			char[] d= {'��','ȭ','��','��','��','��','��'};
			
			//��� ���� �ޱ�
			String sql2 = "select weekday('"+date1+"') as 'day';";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql2);
			int day = 0;
			if(rs.next()) {
				day = rs.getInt("day");
			}	
			char day2 = d[day];	
			//�ѱ��� �ٲٱ�
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
			//�� ����
			String sql = "select A.��ȣ, A.�װ���, A.���, b.full as dep, c.full as des, "
					+ "A.��߽ð�, A.�����ð�, A.���׿���, A.��������, A.��������, A.����, A.���� "
					+ "from schdule as A left join airport_table as b on b.short = A.��߰��� "
					+ "left join airport_table as c on c.short = A.�������� where ��߰��� = '"
					+airline1+"' and �������� = '"+airline2+"' and �������� <= '"+date1+
					"' and �������� >= '"+date1+"' and ���� <= "+price+" and ���׿��� LIKE '%"+day2
					+"%' order by A.���� desc;";	
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			boardList = new ArrayList<scheduleVO>();			
			
			while(rs.next()) {
				scheduleVO scheduleVo = new scheduleVO();
				scheduleVo.setNum(rs.getInt("��ȣ"));
				scheduleVo.setAirline(rs.getString("�װ���"));
				scheduleVo.setName(rs.getString("���"));
				scheduleVo.setDeparture(rs.getString("dep"));
				scheduleVo.setDes(rs.getString("des"));
				scheduleVo.setDe_time(rs.getString("��߽ð�"));
				scheduleVo.setDes_time(rs.getString("�����ð�"));
				scheduleVo.setDay(rs.getString("���׿���"));
				scheduleVo.setDe_day(rs.getString("��������"));
				scheduleVo.setDes_day(rs.getString("��������"));
				scheduleVo.setPart(rs.getString("����"));
				scheduleVo.setPrice(rs.getString("����"));
				
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
	         
	         String sql = "select airline.full as �װ���, count(�װ���) as ������ from schdule \r\n"
	               + " left join airline on schdule.�װ��� = airline.shorts group by �װ���;";
	         stmt = conn.createStatement();
	         rs = stmt.executeQuery(sql);
	         
	         
	         boardList = new ArrayList<scheduleVO>();
	         while(rs.next()) {
	            scheduleVO scheduleVo = new scheduleVO();
	            scheduleVo.setAirline(rs.getString("�װ���"));
	            scheduleVo.setDes(rs.getString("������"));
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
			char[] d= {'��','ȭ','��','��','��','��','��'};
			
			//��� ���� �ޱ�
			String sql2 = "select weekday('"+date1+"') as 'day';";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql2);
			int day = 0;
			if(rs.next()) {
				day = rs.getInt("day");
			}	
			char day2 = d[day];	
			//�ѱ��� �ٲٱ�
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
			//�� ����
			String sql = "select A.��ȣ, A.�װ���, A.���, b.full as dep, c.full as des, "
					+ "A.��߽ð�, A.�����ð�, A.���׿���, A.��������, A.��������, A.����, A.���� "
					+ "from schdule as A left join airport_table as b on b.short = A.��߰��� "
					+ "left join airport_table as c on c.short = A.�������� where A.��ȣ in ("
					+ "select ��ȣ from schdule where ��߽ð� "+ val + " and ��߰��� = '"
					+ airline1 + "' and �������� = '" + airline2 + "' and �������� <= '"
					+ date1 + "' and �������� >= '" + date1 + "' and ���׿��� LIKE '%" + day2
					+ "%' and ���� <= "+ price + ");";
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			boardList = new ArrayList<scheduleVO>();			
			
			while(rs.next()) {
				scheduleVO scheduleVo = new scheduleVO();
				scheduleVo.setNum(rs.getInt("��ȣ"));
				scheduleVo.setAirline(rs.getString("�װ���"));
				scheduleVo.setName(rs.getString("���"));
				scheduleVo.setDeparture(rs.getString("dep"));
				scheduleVo.setDes(rs.getString("des"));
				scheduleVo.setDe_time(rs.getString("��߽ð�"));
				scheduleVo.setDes_time(rs.getString("�����ð�"));
				scheduleVo.setDay(rs.getString("���׿���"));
				scheduleVo.setDe_day(rs.getString("��������"));
				scheduleVo.setDes_day(rs.getString("��������"));
				scheduleVo.setPart(rs.getString("����"));
				scheduleVo.setPrice(rs.getString("����"));
				
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
			char[] d= {'��','ȭ','��','��','��','��','��'};
			
			//��� ���� �ޱ�
			String sql2 = "select weekday('"+date1+"') as 'day';";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql2);
			int day = 0;
			if(rs.next()) {
				day = rs.getInt("day");
			}	
			char day2 = d[day];	
			//�ѱ��� �ٲٱ�
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
			//�� ����
			String sql = "select A.��ȣ, A.�װ���, A.���, b.full as dep, c.full as des, "
					+ "A.��߽ð�, A.�����ð�, A.���׿���, A.��������, A.��������, A.����, A.���� "
					+ "from schdule as A left join airport_table as b on b.short = A.��߰��� "
					+ "left join airport_table as c on c.short = A.�������� where "
					+ "��߰��� = '"+ airline1 + "' and �������� = '" + airline2 + "' and �������� "
					+ "<= '" + date1 + "' and �������� >= '" + date1 + "' and ���� <= " + price
					+ " and ���׿��� LIKE '%" + day2 + "%' and A.��߽ð� >= (select "
					+ "date_add(NOW(), interval 2 hour)) and A.��߽ð� <= (select "
					+ "date_add(NOW(), interval 5 hour));";
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			boardList = new ArrayList<scheduleVO>();			
			
			while(rs.next()) {
				scheduleVO scheduleVo = new scheduleVO();
				scheduleVo.setNum(rs.getInt("��ȣ"));
				scheduleVo.setAirline(rs.getString("�װ���"));
				scheduleVo.setName(rs.getString("���"));
				scheduleVo.setDeparture(rs.getString("dep"));
				scheduleVo.setDes(rs.getString("des"));
				scheduleVo.setDe_time(rs.getString("��߽ð�"));
				scheduleVo.setDes_time(rs.getString("�����ð�"));
				scheduleVo.setDay(rs.getString("���׿���"));
				scheduleVo.setDe_day(rs.getString("��������"));
				scheduleVo.setDes_day(rs.getString("��������"));
				scheduleVo.setPart(rs.getString("����"));
				scheduleVo.setPrice(rs.getString("����"));
				
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
