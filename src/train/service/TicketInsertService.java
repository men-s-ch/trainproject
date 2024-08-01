package train.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import train.dao.TicketDAO;

public class TicketInsertService implements Train {
	

	@Override
	public void execute() {
		TicketDAO ticketDAOcheck = new TicketDAO();
		Scanner scan = new Scanner(System.in);

		System.out.print("열차 번호 입력 : ");
		String train_id = scan.nextLine();
		System.out.print("열차 이름 입력 : ");
		String train_name = scan.nextLine();
		System.out.print("열차 출발지 입력 : ");
		String departure_station = scan.nextLine();
		System.out.print("열차 도착지 입력 : ");
		String arrival_station_id = scan.nextLine();
		String departure_time = null;
		while (true) {
			System.out.print("열차 출발 시간 (yyyy-MM-dd HH:mm:ss) : ");
			departure_time = scan.nextLine();
			
			if (ticketDAOcheck.dateCheck(departure_time)) {
				break;
			} else {
				System.out.println("""
						입력 형식에 맞춰서 입력해주세요
						예 ex)2024-00-00 13:00:00 """);
			}

		}
		System.out.print("열차 좌석 설정 : ");
		String max_seats = scan.nextLine();
		System.out.print("성인 금액 입력 : ");
		int ticket_adult = scan.nextInt();
		System.out.print("어린이 금액 입력 : ");
		int ticket_child = scan.nextInt();

		TicketDAO ticketDAO = TicketDAO.getInstance();

		String ticket = ticketDAO.insertTicket(train_id, train_name, departure_station, arrival_station_id, departure_time, max_seats);
		String ticket_prices = ticketDAO.insertTicketPrices(train_id, ticket_adult, ticket_child);
		
		if(ticket== null || ticket_prices == null) {
			System.out.println("열차등록 실패했습니다.");
		}else {
			System.out.println("열차 정보가 등록 완료되었습니다.");
		}
		
	}

}
