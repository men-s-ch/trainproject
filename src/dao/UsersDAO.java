package dao;

import bean.UsersDTO;

import java.sql.*;

public class UsersDAO {
    private String driver = "oracle.jdbc.driver.OracleDriver";
    private String url = "jdbc:oracle:thin:@localhost:1521:xe";
    private String username = "SCOTT";
    private String password = "tiger";

    private Connection con;
    private PreparedStatement pstmt;
    private ResultSet rs;
    private static UsersDAO instance = new UsersDAO();//싱글톤 잡기술

    //싱글톤
    public static UsersDAO getInstance() {
        return instance;
    }
    //==============DB연결==============================
    public UsersDAO() {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }//드라이버 로딩
    public void getConnection() {

        try {
            con = DriverManager.getConnection(url,username,password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }//커넥션
    public int userSignUp(UsersDTO usersDTO) {
        int su = 0;
        getConnection();
        String sql = "insert into users values(?,?,?,?,0)";

            try {
            pstmt = con.prepareStatement(sql);

            pstmt.setString(1, usersDTO.getUserName());
            pstmt.setString(2, usersDTO.getPhone());
            pstmt.setString(3, usersDTO.getUser_id());
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

    public boolean existUser_Id(String user_Id) {
        boolean exist = false;

        getConnection();
        String sql = "select *  from users where user_id=?";

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

    public boolean existPhone(String phone) {
        boolean exist = false;

        getConnection();
        String sql = "select *  from users where phone=?";

        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1,phone);

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
    

}
