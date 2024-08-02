package users.service;

import java.util.Scanner;

public class TrainService {
    Scanner scan = new Scanner(System.in);
    int num;
    Train train = null;
    public void menu() {
        while (true) {
            System.out.println();
            System.out.println("****************************************");
            System.out.println("****************************************");
            System.out.println("*** K J P TRAIN을 이용해 주셔서 갑사합니다. ***");
            System.out.println("****************************************");
            System.out.println("****************************************");
            System.out.println("              1. 회원가입                ");
            System.out.println();
            System.out.println("              2. 로그인                  ");
            System.out.println();
            System.out.println("              3. 나가기                ");
            System.out.println("****************************************");
            System.out.println("****************************************");
            System.out.print("번호를 입력해주세요 : ");
            num = scan.nextInt();
                if (num == 3){
                    System.out.println();
                    System.out.println("이용해주셔서 감사합니다.");
                    break;
                }
                else if (num == 1){
                    train = new UserSignUp();
                }
                else if (num == 2){
                    train = new UserSignIn();
                }
                else{
                    System.out.println("잘못 입력하셨습니다. 올바른 번호를 입력 부탁드립니다.");
                    continue;
                }
                    train.execute();
        }
    }
}
