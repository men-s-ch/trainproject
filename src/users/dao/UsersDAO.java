package users.dao;

import users.bean.UsersDTO;

import java.sql.*;
import java.util.Map;

public class UsersDAO {
    private String driver = "oracle.jdbc.driver.OracleDriver";
    private String url = "jdbc:oracle:thin:@localhost:1521:xe";
    private String username = "c##java";
    private String password = "1234";

    private Connection con;
    private PreparedStatement pstmt;
    private ResultSet rs;
    private static UsersDAO instance = new UsersDAO();//싱글톤 잡기술
    
    private UsersDTO loginPeople;

    //싱글톤
    public static UsersDAO getInstance() {
        return instance;
    }
    //DB driver
    public UsersDAO() {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    //커넥션
    public void getConnection() {

        try {
            con = DriverManager.getConnection(url,username,password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //회원가입
    public int userSignUp(UsersDTO usersDTO) {
        int su = 0;
        getConnection();
        String sql = "insert into users values(?,?,?,?,0)";

            try {
            pstmt = con.prepareStatement(sql);

            pstmt.setString(1, usersDTO.getUserName());
            pstmt.setString(2, usersDTO.getPhone());
            pstmt.setString(3, usersDTO.getUser_Id());
            pstmt.setString(4, usersDTO.getPassword());

            su = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return su;
    }
    //아이디 중복
    public boolean existUser_Id(String user_Id) {
        boolean exist = false;

        getConnection();
        String sql = "select *  from users where user_Id=?";

        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1,user_Id);

            rs = pstmt.executeQuery();

            if(rs.next()) exist = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return exist;
    }
    //가입된 전화번호
    public boolean existPhone(String formattedPhone) {
        boolean exist = false;


        try{
            getConnection();
            String sql = "SELECT * FROM users WHERE phone = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1,formattedPhone);

            rs = pstmt.executeQuery();

            if(rs.next()) exist = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return exist;
    }
    //아이디 비밀번호 찾기
    public void findInfo(String username , String fomattedPhone) {

        String user_Id = null;
        String password= null;
        getConnection();
        String sql = "select USER_ID,PASSWORD from users where username = ? and phone = ?";

        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1,username);
            pstmt.setString(2,fomattedPhone);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                user_Id = rs.getString("USER_ID");
                password = rs.getString("PASSWORD");
            }
            if(user_Id == null || password == null){
                System.out.println("입력된 이름과 전화번호의 정보가 일치하지 않습니다.");
                System.out.println("초기화면으로 돌아갑니다.");
            }
            else {
                System.out.println(username + " 님의 아이디 : " + user_Id);
                System.out.println(username + " 님의 비밀번호 : " + password);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally{
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    //로그인
    public UsersDTO signIn(String user_Id,String password) {
        UsersDTO usersDTO = null;

        getConnection();
        String sql = "select * from users where user_id = ? and password = ?";
        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1,user_Id);
            pstmt.setString(2,password);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                usersDTO = new UsersDTO();
                usersDTO.setUserName(rs.getString("username"));
                usersDTO.setIs_admin(rs.getInt("is_admin"));

                
                // 로그인 사용자 정보 저장
                usersDTO.setUser_Id(rs.getString("user_id"));
                loginPeople = usersDTO;
                
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return usersDTO;
    }
    
    public UsersDTO getLoginPeople() {
    	return loginPeople;
    }

    
    //update
    public UsersDTO getUsers(String user_Id) {
        UsersDTO usersDTO = null;

        getConnection();

        String sql = "select * from users where user_id = ?";
        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1,user_Id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                usersDTO = new UsersDTO();
                usersDTO.setUserName(rs.getString("username"));
                usersDTO.setPhone(rs.getString("phone"));
                usersDTO.setUser_Id(rs.getString("user_id"));
                usersDTO.setPassword(rs.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return usersDTO;
    }
    public int userUpdate(Map<String, String> map) {
        int su = 0;

        getConnection();

        String sql = "update users set  password= ?, phone= ?  where  user_id = ?";

        try {
            pstmt = con.prepareStatement(sql);

            pstmt.setString(1, map.get("password"));
            pstmt.setString(2, map.get("phone"));
            pstmt.setString(3, map.get("user_id"));

            su = pstmt.executeUpdate();

        } catch (SQLException e) {
//        	수정 내역
//            e.printStackTrace();
        	System.out.println("잘못입력했습니다.");
        }finally {
            try {
                if(pstmt != null) pstmt.close();
                if(con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return su;
    }

}

