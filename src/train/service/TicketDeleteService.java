package train.service;

import java.util.Scanner;

import train.bean.TicketDTO;
import train.dao.TicketDAO;

public class TicketDeleteService implements Train, users.service.Train {
	@Override
	public void execute() {
		TicketDAO ticketDAO = TicketDAO.getInstance();
		Scanner scan = new Scanner(System.in);

		// TicketDAO 인스턴스 생성 및 데이터 조회
		ticketDAO.adminSelectTicket(); // 해당 열차 정보를 조회하고 출력
		
		// 삭제할 열차 번호를 입력받습니다.
		System.out.println("삭제할 열차 번호를 입력하세요: ");
		String train_id = scan.nextLine();

		// 입력한 열차 번호에 해당하는 티켓 정보를 조회합니다.
		TicketDTO ticketDTO = ticketDAO.getTicketTrain_Id(train_id);

		if (ticketDTO != null) {
			// 티켓 정보가 존재하면 출력합니다.
			System.out.println("이 열차를 삭제하시겠습니까? (yes/no): ");
			String confirmation = scan.nextLine();

			if ("yes".equalsIgnoreCase(confirmation)) {
				// 삭제 작업을 수행합니다.
				boolean success = ticketDAO.deleteTicket(train_id);
				if (success) {
					System.out.println("열차가 성공적으로 삭제되었습니다.");
				} else {
					System.out.println("열차 삭제에 실패하였습니다.");
				}
			} else {
				System.out.println("삭제가 취소되었습니다.");
			}
		} else {
			System.out.println("입력하신 열차 번호가 존재하지 않습니다.");
		}

	}
}
