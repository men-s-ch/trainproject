package trian.main;

import java.util.Scanner;

import train.service.TicketBuySelect;
import train.service.TicketBuyService;
import train.service.TicketInsertService;
import train.service.TicketSelectService;
import train.service.Train;

public class TicketMain {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);

		Train train = null;

		int sel = 0;

		while (true) {
			System.out.println("""
					===== 관리자 페이지 =====
					  1. 열차 정보 등록
					  2. 등록된 열차 조회
					  3. 열차 취소 승인
					  4. 열차 예매
					  5. 이전 메뉴로 이동
					=====================
					  입력 :
						""");
			sel = scan.nextInt();

			if (sel == 1) {
				train = new TicketInsertService();
				train.execute();
			} else if (sel == 2) {
				train = new TicketSelectService();
				train.execute();
			} else if (sel == 3) {
//				train = new TicketBuy();
			} else if (sel == 4) {
				int num = 0;
				while (true) {
					System.out.println("""
							===== 사용자 페이지 =====
							  1. 열차 예매
							  2. 열차 예매 내역 조회
							  3. 이전 메뉴로 이동
							=====================
							  입력 :
								""");
					num = scan.nextInt();
					if (num == 1) {
						train = new TicketBuyService();
						train.execute();
					} else if (num == 2) {
						train = new TicketBuySelect();
						train.execute();
					} else if (num == 3) {
						break;
					} else {

					}

				}

			} else if (sel == 5) {
				break;
			} else {
				System.out.println("1 ~ 4번 메뉴에서 선택해주세요");
				continue;
			}

		}

	}

}
