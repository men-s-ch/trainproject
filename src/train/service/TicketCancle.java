package train.service;

import java.security.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Scanner;

import train.bean.ReservationDTO;
import train.bean.TicketDTO;
import train.dao.ReservationDAO;
import train.dao.TicketDAO;
import users.service.Train;

public class TicketCancle implements Train{

	@Override
	public void execute() {
		Scanner scan = new Scanner(System.in);

		
		System.out.println("예약번호\t 유저ID\t 열차번호\t 출발일\t 예매취소일\t 티켓구분\t 상태");
		// 예매 내역 보여주기
		TicketDAO ticketDAO = new TicketDAO();
		ReservationDAO reservationDAO = new ReservationDAO();
		reservationDAO.ticketBuySelect();
		//------------------------------------
	    System.out.print("취소 하고 싶은 표를 선택하세요(예약번호) :");
	    String cancleTicket = scan.next();
	    System.out.println();
	    System.out.println(">> 예매 취소 하시겠습니까??? [1]네 / [2] 아니요");
	    int num = scan.nextInt();

	    if(num==1){
	    	
	    	// 예매한 티켓의 중에 취소할 표의 정보(예매코드)로 가져옴
	    	ReservationDTO reservationDTO = ticketDAO.selectCancleTicket(cancleTicket);
	    	
	    	if(reservationDTO.getRes_status().equals("CANCELLED")) {
	    		System.out.println("이미 취소된 표입니다. 취소할 수 없습니다.");
	    	}else {
	    		String trainid = reservationDTO.getTrain_id();
	    		TicketDTO ticketDTO = ticketDAO.getTicketTrain_Id(trainid);
	    		
	    		
	    		// 선택한 표가 출발일자를 지났는지 확인
	    		// timestamp를 loacalDatetime 변경 (gpt)
	    		LocalDateTime ticketTime = ticketDTO.getDeparture_time().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
	    		if(sysTime().isBefore(ticketTime)) { 
	    			// 현재 시간은 티켓(열차출발) 시간보다 이전입니다. >> 취소가능
	    			// 상태를 취소대기로 바꾼다. 
	    			ticketDAO.statusChage(cancleTicket);
	    			System.out.println("예매를 취소하였습니다.");
	    			
	    		} else {
	    			// 취소 불가능
	    			System.out.println("열차가 이미 출발 하였습니다. 취소 불가능합니다.");
	    		}	    		
	    	}
	    	
	    } else {
	    	return;
	    } 
	   
		
	}
	
	
	// 현재 시스템 시간을 가져오는 메소드
	public LocalDateTime sysTime() {
		LocalDateTime systime = LocalDateTime.now();
		
		return systime;
	}
	
}
