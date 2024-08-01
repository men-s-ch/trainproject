package service;

import bean.UsersDTO;
import dao.UsersDAO;

import java.util.Scanner;

public class TrainSignUp implements Train{
    @Override
    public void execute() {
        Scanner scan = new Scanner(System.in);
        UsersDAO usersDAO = UsersDAO.getInstance();
        System.out.println();
        System.out.print("이름 입력 : ");
        String userName = scan.next();
        String phone = null;
        while (true) {
            System.out.print("핸드폰번호를 입력하세요(010-xxxx-xxxx) : ");
            phone = scan.next();
            boolean exist = usersDAO.existPhone(phone);
            if (exist) {
                System.out.println("이미 등록된 회원입니다.");
            } else break;
        }
        String user_id = null;
        while (true) {
            System.out.print("아이디를 입력하세요 : ");
            user_id = scan.next();
            boolean exist = usersDAO.existUser_Id(user_id);
            if (exist) {
                System.out.println("사용중인 아이디 입니다.");
            } else {
                System.out.println("사용 가능한 아이디입니다.");
                break;
            }
        }

        System.out.print("비밀번호 입력 : ");
        String password = scan.next();
        System.out.println();

        UsersDTO usersDTO = new UsersDTO();
        usersDTO.setUserName(userName);
        usersDTO.setPhone(phone);
        usersDTO.setUser_id(user_id);
        usersDTO.setPassword(password);



        int su = usersDAO.userSignUp(usersDTO);
        System.out.println(su+" 명의 회원가입이 완료되었습니다.");
    }
}
