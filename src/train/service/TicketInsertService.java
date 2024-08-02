package train.service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import train.bean.TicketDTO;
import train.dao.TicketDAO;

public class TicketInsertService implements Train {

	@Override
	public void execute() {
		TicketDAO ticketDAOcheck = new TicketDAO();

		TicketDAO ticketDAO = TicketDAO.getInstance();

		Scanner scan = new Scanner(System.in);

		System.out.println(
				"============================================================현재 등록된 열차 정보==================================================================");
		ticketDAO.selectTicket(); // 해당 열차 정보를 조회하고 출력
		System.out.print("등록 할 열차 번호 입력 : ");
		String train_id = scan.nextLine();
		System.out.print("등록 할 열차 이름 입력 : ");
		String train_name = scan.nextLine();
		System.out.print("등록 할 열차 출발지 입력 : ");
		String departure_station = scan.nextLine();
		System.out.print("등록 할 열차 도착지 입력 : ");
		String arrival_station_id = scan.nextLine();

		// 날짜형식 등록 및 입력받았는지 검증
		String departure_time_str;
		Timestamp departure_time = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 현재 날짜로부터 일주일 후 등록 가능하게 하는 변수
		Calendar calendar = Calendar.getInstance(); // 추상클래스이기 때문에 메서드 호출로 사용
		calendar.add(Calendar.DAY_OF_YEAR, 3); // 현재 날짜에 3일 추가
		Date three = calendar.getTime(); // 3일 후 날짜 객체로 얻기
		Timestamp threeTime = new Timestamp(three.getTime()); // Date 객체 Timestamp 객체로 변환

		while (true) {
			System.out.print("열차 출발 시간 (yyyy-MM-dd HH:mm:ss) : ");
			departure_time_str = scan.nextLine();

			// 날짜 형식으로 입력 받았는지 검증
			if (ticketDAOcheck.dateCheck(departure_time_str)) {
				try {
					// 입력 받은 문자열 Date 객체로 변환
					Date parsedDate = dateFormat.parse(departure_time_str);
					// Date 객체 Timestamp 객체로 변환
					departure_time = new Timestamp(parsedDate.getTime());

					// 출발 시간이 3일 이상인지 확인
					if (departure_time.before(threeTime)) {
						System.out.println("열차 등록은 현재로부터 3일 이상이어야 가능합니다.");
					} else {
						break;
					}

				} catch (ParseException e) {
					System.out.println("출발 시간 형식 오류: " + e.getMessage());
				}
			} else {
				System.out.println("""
						입력 형식에 맞춰서 입력해주세요
						ex) 2024-10-15 13:00:00 """);
			}
		}
		System.out.print("등록 할 열차 좌석 설정 : ");
		int max_seats = scan.nextInt();
		System.out.print("등록 할 성인 금액 입력 : ");
		int type_adult = scan.nextInt();
		System.out.print("등록 할 어린이 금액 입력 : ");
		int type_kid = scan.nextInt();

		TicketDTO ticketDTO = new TicketDTO();
		ticketDTO.setTrain_id(train_id);
		ticketDTO.setTrain_name(train_name);
		ticketDTO.setDepature_station(departure_station);
		ticketDTO.setArrival_station(arrival_station_id);
		ticketDTO.setDeparture_time(departure_time);
		ticketDTO.setMax_seat(max_seats);
		ticketDTO.setType_adult(type_adult);
		ticketDTO.setType_kid(type_kid);

		String result = ticketDAO.insertTicket(ticketDTO);

		System.out.println(result);

	}

}
