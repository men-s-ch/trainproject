package train.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Random;

import train.bean.ReservationDTO;
import train.bean.TicketDTO;
import users.bean.UsersDTO;
import users.dao.UsersDAO;

public class TicketDAO {

	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String username = "c##train";
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

	public String insertTicket(TicketDTO ticketdto) {
		String result = "데이터 삽입 실패";
		String sql = "INSERT INTO trains (train_id, train_name, depature_station, arrival_station, depature_time, max_seat, type_adult, type_kid) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		try {
			getConnection();

			Timestamp departureTimestamp = ticketdto.getDeparture_time();

			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, ticketdto.getTrain_id());
			pstmt.setString(2, ticketdto.getTrain_name());
			pstmt.setString(3, ticketdto.getDepature_station());
			pstmt.setString(4, ticketdto.getArrival_station());
			pstmt.setTimestamp(5, departureTimestamp);
			pstmt.setInt(6, ticketdto.getMax_seat());
			pstmt.setInt(7, ticketdto.getType_adult());
			pstmt.setInt(8, ticketdto.getType_kid());

			int rowsAffected = pstmt.executeUpdate();
			if (rowsAffected > 0) {
				result = "목적지가 등록되었습니다.";
			}
		} catch (SQLException e) {
			// SQL 상태코드 23000 = 무결성 제약 조건 위반
			if (e.getSQLState().equals("23000")) {
				result = "이미 등록 완료 된 열차 번호입니다. 열차 번호를 확인하고 다시 입력해주세요";
			} else {
				result = "데이터 베이스 오류 발생되었습니다." + e.getMessage();
			}
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

				TicketDTO ticketDTO = new TicketDTO();
				ticketDTO.setTrain_id(rs.getString("train_id"));
				ticketDTO.setTrain_name(rs.getString("train_name"));
				ticketDTO.setDepature_station(rs.getString("depature_station"));
				ticketDTO.setArrival_station(rs.getString("arrival_station"));
				ticketDTO.setDeparture_time(rs.getTimestamp("depature_time"));
				ticketDTO.setMax_seat(rs.getInt("max_seat"));
				ticketDTO.setType_adult(rs.getInt("type_adult"));
				ticketDTO.setType_kid(rs.getInt("type_kid"));

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String startTime = sdf.format(ticketDTO.getDeparture_time());

				System.out.println(
						"--------------------------------------------------------------------------------------------------------------------------------------------");
				System.out.print("열차번호 : " + ticketDTO.getTrain_id() + " / ");
				System.out.print("열차이름 : " + ticketDTO.getTrain_name() + " / ");
				System.out.print("출발역 : " + ticketDTO.getDepature_station() + " / ");
				System.out.print("도착역 : " + ticketDTO.getArrival_station() + " / ");
				System.out.print("출발시간 : " + startTime + " / ");
				System.out.print("남은 좌석 수 : " + ticketDTO.getMax_seat() + " / ");
				System.out.print("성인 : " + ticketDTO.getType_adult() + "원 / ");
				System.out.println("어린이 : " + ticketDTO.getType_kid() + "원 ");
				System.out.println(
						"--------------------------------------------------------------------------------------------------------------------------------------------");
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

	// 날짜 유효성 검사
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

	// 열차 구매 열차 번호 ID 가져오는 메소드
	public TicketDTO getTicketTrain_Id(String Train_id) {  //열차 번호

		TicketDTO ticketDTO = null;

		String sql = "select * from trains where train_id = ? ";

		try {
			getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, Train_id);
			rs = pstmt.executeQuery();

			if (rs.next()) {

				ticketDTO = new TicketDTO();
				ticketDTO.setTrain_id(rs.getString("Train_id"));
				ticketDTO.setTrain_name(rs.getString("Train_name"));
				ticketDTO.setDepature_station(rs.getString("depature_station"));
				ticketDTO.setArrival_station(rs.getString("arrival_station"));
				ticketDTO.setDeparture_time(rs.getTimestamp("depature_time"));
				ticketDTO.setMax_seat(rs.getInt("max_seat"));
				ticketDTO.setType_adult(rs.getInt("type_adult"));
				ticketDTO.setType_kid(rs.getInt("type_kid"));
			} 

		} catch (Exception e) {
			// TODO: handle exception
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

		return ticketDTO;
	}

	public String ticketBuyService(String train_id, int ticket_adult, int ticket_child) {
		String result = "예매 실패";

		// SQL 쿼리들
		String trainSql = "SELECT * FROM trains WHERE train_id = ?";
		String updateSql = "UPDATE trains SET max_seat = ? WHERE train_id = ?";
		String insertReservationSql = "INSERT INTO reservations (res_id, user_id, train_id, res_adult, res_child, res_total, res_time, res_status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		try {
			getConnection();

			// 1. 열차 정보 조회
			pstmt = con.prepareStatement(trainSql);
			pstmt.setString(1, train_id);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				int maxSeat = rs.getInt("max_seat");
				int typeAdult = rs.getInt("type_adult");
				int typeKid = rs.getInt("type_kid");

				// 좌석 수 계산
				int totalTickets = ticket_adult + ticket_child;
				if (totalTickets > maxSeat) {
					return "남은 좌석이 부족합니다.";
				}

				// 2. 예매 처리
				int remainingSeats = maxSeat - totalTickets;

				// 좌석 수 업데이트
				pstmt = con.prepareStatement(updateSql);
				pstmt.setInt(1, remainingSeats);
				pstmt.setString(2, train_id);
				int rowsAffected = pstmt.executeUpdate();

				if (rowsAffected > 0) {

					// 3. 예약 정보 삽입
					String reservationId = randomResId(); // 고유 예약 ID 생성
					Timestamp reservationDate = new Timestamp(System.currentTimeMillis());
					
					// user__name 가져오기
				
					// 로그인 된 사용자 정보 호출
					UsersDAO usersDAO = UsersDAO.getInstance();
					UsersDTO loginPeople = usersDAO.getLoginPeople();
					String login_id = loginPeople.getUser_Id();
					
					pstmt = con.prepareStatement(insertReservationSql);
					pstmt.setString(1, reservationId);
					pstmt.setString(2, login_id); 
					pstmt.setString(3, train_id);
					pstmt.setInt(4, ticket_adult);
					pstmt.setInt(5, ticket_child);
					pstmt.setInt(6, (ticket_adult * typeAdult + ticket_child * typeKid));
					pstmt.setTimestamp(7, reservationDate);
					pstmt.setString(8, "CONFIRMED");
					int reservationRowsAffected = pstmt.executeUpdate();

					if (reservationRowsAffected > 0) {
						// 예매 성공
						result = "예매 성공: 총 금액은 " + (ticket_adult * typeAdult + ticket_child * typeKid) + "원입니다.";
					}
				}
			} else {
				result = "해당 열차 정보가 없습니다.";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
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

	// 예약 ID를 생성하는 메서드
	public String randomResId() {
		Random random = new Random();
		String resId;
		boolean isUnique; // 예약 ID의 유일성 여부

		do {
			// 0부터 999까지의 난수를 생성
			int randomNumber = random.nextInt(1000);

			// 예약 ID 형식으로 변환 (예: RES000123)
			resId = String.format("RES%03d", randomNumber);

			// 데이터베이스에서 예약 ID의 유일성을 확인
			isUnique = checkResId(resId);

		} while (!isUnique); // 유일하지 않으면 반복

		return resId; // 유일한 예약 ID 반환
	}

	// 난수 중복체크
	public boolean checkResId(String res_id) {

		String sql = "select count(*) from reservations where res_id = ? ";

		try {
			getConnection();
			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, res_id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				int cnt = rs.getInt(1); // 존재하면 false
				return cnt == 0; // 생성값 유일하면 true 아니면 false
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// true 반환 시 SQL, dB연결 등 문제가 발생시 잘못된 결과를 반환 할수이으므로 false 처리
		return false;
	}

	
	
	// 티켓 취소
	public void statusChage(String cancleTicket) {

		getConnection();
		
		String sql = "update reservations set res_status='CANCELLED' where res_id= ?";
		
		try {
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, cancleTicket);
			
			pstmt.executeUpdate();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt!=null) pstmt.close();
				if(con!=null) con.close();				
			} catch(SQLException e ) {
				e.printStackTrace();
			}
		}
	}

	
// 취소한 열차표 가져오기
	public ReservationDTO selectCancleTicket(String cancleTicket) {
		getConnection();
		
		ReservationDTO reservationDTO = new ReservationDTO();
		
		String sql = "select * from reservations where res_id = ? ";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, cancleTicket);
			
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				reservationDTO.setRes_id(rs.getString("res_id"));
				reservationDTO.setUser_id(rs.getString("user_id"));
				reservationDTO.setTrain_id(rs.getString("train_id"));
				reservationDTO.setRes_adult(rs.getInt("res_adult"));
				reservationDTO.setRes_child(rs.getInt("res_child"));
				reservationDTO.setRes_total(rs.getInt("res_total"));
				reservationDTO.setRes_time(rs.getTimestamp("res_time"));
				reservationDTO.setRes_status(rs.getString("res_status"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)	rs.close();
				if (pstmt != null) pstmt.close();
				if (con != null) con.close();
			} catch (SQLException e) {
				e.printStackTrace(); 
			}
		}
		
		return reservationDTO;
	}
	
	
	
}
