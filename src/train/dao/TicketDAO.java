package train.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TicketDAO {

	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String username = "c##java";
	private String password = "1234";

	private Connection con;
	private PreparedStatement pstmt;
	private ResultSet rs;

	private static TicketDAO instance;

	public TicketDAO() {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void getConnection() {
		try {
			con = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static TicketDAO getInstance() {

		if (instance == null) {
			synchronized (TicketDAO.class) {
				instance = new TicketDAO();
			}
		}
		return instance;
	}

	public String insertTicket(String train_id, String train_name, String departure_station_id, String arrival_station_id, String departure_time,
			String max_seats) {
		String result = "데이터 삽입 실패";
		String sql = "INSERT INTO trains (train_id, train_name, departure_station_id, arrival_station_id, departure_time, max_seats) VALUES (?, ?, ?, ?, ?, ?)";

		try {
			getConnection();

			Timestamp departureTimestamp = null;
			try {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date parsedDate = dateFormat.parse(departure_time);
				departureTimestamp = new Timestamp(parsedDate.getTime());
			} catch (ParseException e) {
				return "출발 시간 형식 오류 입니다 다시 입력하세요";
			}

			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, train_id);
			pstmt.setString(2, train_name);
			pstmt.setString(3, departure_station_id);
			pstmt.setString(4, arrival_station_id);
			pstmt.setTimestamp(5, departureTimestamp);
			pstmt.setInt(6, Integer.parseInt(max_seats));

			int rowsAffected = pstmt.executeUpdate();
			if (rowsAffected > 0) {
				result = "목적지가 등록 되었습니다";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public String insertTicketPrices(String train_id, int ticket_adult, int ticket_child) {
		String result = null;
		String sql = "INSERT INTO ticket_prices (train_id, ticket_adult, ticket_child) VALUES (?, ?, ?)";

		try {
			getConnection();


			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, train_id);
			pstmt.setInt(2, ticket_adult);
			pstmt.setInt(3, ticket_child);
			int rowsAffected = pstmt.executeUpdate();
			if (rowsAffected > 0) {
				result = "가격등록 완료";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	public void selectTicket() {
		String sql = "SELECT * FROM trains";

		try {
			getConnection();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			boolean hasRecords = false;
			while (rs.next()) {
				hasRecords = true;

				String id = rs.getString("train_id");
				String name = rs.getString("train_name");
				String departurestation = rs.getString("departure_station_id");
				String arrivalStation = rs.getString("arrival_station_id");
				Timestamp departureTime = rs.getTimestamp("departure_time");
				int maxSeats = rs.getInt("max_seats");

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String formattedDepartureTime = sdf.format(departureTime);
				System.out.println(
						"---------------------------------------------------------------------------------------------------");
				System.out.print("열차번호 : " + id + " / ");
				System.out.print("열차이름 : " + name + " / ");
				System.out.print("출발역 : " + departurestation + " / ");
				System.out.print("도착역 : " + arrivalStation + " / ");
				System.out.print("출발시간 : " + formattedDepartureTime + " / ");
				System.out.println("남은 좌석 수 : " + maxSeats);
				System.out.println(
						"---------------------------------------------------------------------------------------------------");
			}

			if (!hasRecords) {
				System.out.println("등록된 열차 정보가 없습니다.");
			}
		} catch (SQLException e) {
			e.printStackTrace(); // SQL 예외 처리
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace(); // 자원 해제 시 예외 처리
			}
		}
	}

	public boolean dateCheck(String dateStr) {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			dateFormat.setLenient(false);
			dateFormat.parse(dateStr);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}
	
}
