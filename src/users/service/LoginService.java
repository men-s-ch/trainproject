package users.service;

import java.util.Scanner;

public class LoginService {
    Scanner scan = new Scanner(System.in);
    int num;
    Train train = null;
    public void userlogin() {
        while (true) {
            System.out.println("****************************************");
            System.out.println("****************************************");
            System.out.println("               (1)열차 조회");
            System.out.println();
            System.out.println("               (2)열차 예매");
            System.out.println();
            System.out.println("               (3)예매 취소");
            System.out.println();
            System.out.println("               (4)회원 정보 수정");
            System.out.println();
            System.out.println("               (5)이전 메뉴로 이동");
            System.out.println("****************************************");
            System.out.println("****************************************");
            System.out.print("번호를 입력해주세요 :");
            num = scan.nextInt();
                if (num == 5) {
                    return;
                } else if (num == 1) {

                } else if (num == 2) {

                } else if (num == 3) {

                } else if (num == 4) {
                    train = new UserUpdate();
                }else {
                    System.out.println("잘못된 번호를 입력하셨습니다.");
                    continue;
                }
                    train.execute();

        }

    }

    public void adminlogin() {
        while (true) {
            System.out.println("****************************************");
            System.out.println("****************************************");
            System.out.println("              (1)열차 등록");
            System.out.println();
            System.out.println("              (2)예매 취소 승인");
            System.out.println();
            System.out.println("              (3)이전 메뉴로 이동");
            System.out.println("****************************************");
            System.out.println("****************************************");
            System.out.print("번호를 입력해주세요 :");
            num = scan.nextInt();
            if (num == 3) {
                return;
            } else if (num == 1) {

            } else if (num == 2) {

            }else {
                System.out.println("잘못된 번호를 입력하셨습니다.");
                continue;
            }
            train.execute();

        }

    }
}
