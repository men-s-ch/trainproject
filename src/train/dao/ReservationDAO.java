package train.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import train.bean.ReservationDTO;
import users.bean.UsersDTO;
import users.dao.UsersDAO;

public class ReservationDAO {

	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String username = "c##train";
	private String password = "1234";

	private Connection con;
	private PreparedStatement pstmt;
	private ResultSet rs;

	private static ReservationDAO instance;

	public ReservationDAO() {
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

	public static ReservationDAO getInstance() {

		if (instance == null) {
			synchronized (TicketDAO.class) {
				instance = new ReservationDAO();
			}
		}
		return instance;
	}

	public void ticketBuySelect() {

		String sql = "select * from reservations where user_id = ? ";
		UsersDAO usersDAO = UsersDAO.getInstance();
		UsersDTO loginPeople = usersDAO.getLoginPeople();
		String login_id = loginPeople.getUser_Id();
		try {
			getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, login_id);
			rs = pstmt.executeQuery();

			boolean hasRecords = false;

			while (rs.next()) {
				hasRecords = true;

				ReservationDTO reservationDTO = new ReservationDTO();

				reservationDTO.setRes_id(rs.getString("res_id"));
				reservationDTO.setUser_id(rs.getString("user_id"));
				reservationDTO.setTrain_id(rs.getString("train_id"));
				reservationDTO.setRes_adult(rs.getInt("res_adult"));
				reservationDTO.setRes_child(rs.getInt("res_child"));
				reservationDTO.setRes_total(rs.getInt("res_total"));
				reservationDTO.setRes_time(rs.getTimestamp("res_time"));
				reservationDTO.setRes_status(rs.getString("res_status"));

				String check = " ";
				if ("CONFIRMED".equals(reservationDTO.getRes_status())) {
					check = "확정";
				} else if ("CANCELLED".equals(reservationDTO.getRes_status())) {
					check = "취소";
				}

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String resTime = sdf.format(reservationDTO.getRes_time());

				System.out.println(
						"----------------------------------------------------------------------------------------------------------------------------------------------------");
				System.out.print("예약번호 : " + reservationDTO.getRes_id() + " / ");
				System.out.print("사용자 ID : " + reservationDTO.getUser_id() + " / ");
				System.out.print("열차번호 : " + reservationDTO.getTrain_id() + " / ");
				System.out.print("성인 티켓 : " + reservationDTO.getRes_adult() + "개 / ");
				System.out.print("어린이 티켓 : " + reservationDTO.getRes_child() + "개 / ");
				System.out.print("총 금액 : " + reservationDTO.getRes_total() + "원 / ");
				System.out.print("예약일자 : " + reservationDTO.getRes_time() + " / ");
				System.out.println("예약상태 : " + check);
				System.out.println(
						"----------------------------------------------------------------------------------------------------------------------------------------------------");

			}
			if (!hasRecords) {
				System.out.println("예매하신 열차 정보가 없습니다.");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
				e.printStackTrace(); // 자원 해제 시 예외 처리
			}
		}

	}

}
