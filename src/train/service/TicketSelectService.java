package train.service;

import java.util.Scanner;

import train.dao.TicketDAO;

public class TicketSelectService implements Train {

	@Override
	public void execute() {

		// TicketDAO 인스턴스 생성 및 데이터 조회
		TicketDAO ticketDAO = TicketDAO.getInstance();
		ticketDAO.selectTicket(); // 해당 열차 정보를 조회하고 출력
		
	}

}
