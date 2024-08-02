package train.service;

import java.util.Scanner;

import train.bean.TicketDTO;
import train.dao.TicketDAO;

public class TicketBuyService implements Train, users.service.Train { //* 누구꺼 가져오는거지?
	@Override
	public void execute() {
		Scanner scanner = new Scanner(System.in);

		// 열차 정보 조회
		TicketDAO ticketDAO = new TicketDAO();
		ticketDAO.selectTicket();

		TicketDTO ticketDTO;
		String train_Id;
		
		while(true) {
			System.out.print("예매할 열차 번호를 입력하세요 : ");
			train_Id = scanner.nextLine();			
			
			ticketDTO = ticketDAO.getTicketTrain_Id(train_Id); // 열차 정보 조회
			
			if(ticketDTO != null) {
				break;
			}else {				
				System.out.println("등록된 열차 정보가 없습니다 다시 입력하세요.");
			}
			
		}
		// 사용자 입력

		System.out.print("성인 티켓 수를 입력하세요: ");
		int ticket_adult = scanner.nextInt();

		System.out.print("어린이 티켓 수를 입력하세요: ");
		int ticket_child = scanner.nextInt();

		String result = ticketDAO.ticketBuyService(train_Id, ticket_adult, ticket_child);
		
		System.out.println(result);

	}
}
