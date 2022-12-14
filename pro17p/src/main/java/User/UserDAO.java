package User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {
	
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	public UserDAO() {
		try {
			String dbURL = "jdbc:oracle:thin:@127.0.0.1:1521:XE";
			String dbID = "bg";
			String dbPassword = "bg";
			Class.forName("oracle.jdbc.OracleDriver");
			conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}		
	}
	
	
	public int login(String userID, String userPassword) {
		
		String SQL = "SELECT userPassword FROM USER_t WHERE userID=?";
		
		try {
			
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userID);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				if(rs.getString(1).equals(userPassword)) {
					return 1; // 로그인 성공
				}else {
					return 0; // 비밀번호 불 일치
				}
				
			}
			
			return -1; // 아이디 없음
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -2; // 데이터베이스 오류 
	
	}
	
	public int join(User user) {
		
		String SQL = "INSERT INTO USER_t VALUES(?,?,?,?,?)";	//회원가입
		
		try {
			
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, user.getUserID());
			pstmt.setString(2, user.getUserPassword());
			pstmt.setString(3, user.getUserName());
			pstmt.setString(4, user.getUserGender());
			pstmt.setString(5, user.getUserEmail());
	
			return pstmt.executeUpdate();
			
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return -1; // 데이터베이스 오류 

	}
	
}
