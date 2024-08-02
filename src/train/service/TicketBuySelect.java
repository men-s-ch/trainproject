package train.service;

import train.dao.ReservationDAO;
import train.dao.TicketDAO;

public class TicketBuySelect implements Train {
	@Override
	public void execute() {
		
		ReservationDAO reservationDAO = ReservationDAO.getInstance();
		
		reservationDAO.ticketBuySelect();

	}
}
