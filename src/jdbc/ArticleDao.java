package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ArticleDao {
	private String driver = "com.mysql.cj.jdbc.Driver";
	
	private String url = "jdbc:mysql://localhost:3306/t1?serverTimezone=UTC";
	
	private String user = "sbsst";
	
	private String pass = "sbs123414";
	
	public void getArticles() throws SQLException, ClassNotFoundException
	{
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url,user,pass);
		String listSQL = "SELECT * FROM article";
		PreparedStatement pstmt = conn.prepareStatement(listSQL);
		
		ResultSet rs = pstmt.executeQuery();
		
		while(rs.next())
		{
			String title = rs.getString("title");
			int id = rs.getInt("id");
			String body = rs.getString("body");
			String nickname = rs.getString("nickname");
			int hit = rs.getInt("hit");
			System.out.println(id + " " + title + " " + body + " " + nickname + " " + hit);
		}
	}
	
	public void addArticle(String title, String body) throws ClassNotFoundException, SQLException
	{
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url,user,pass);
		
		String addSQL = "INSERT INTO article SET title = ?, `body` = ?, nickname = '홍길동', hit = 10";
		PreparedStatement pstmt = conn.prepareStatement(addSQL);
		
		pstmt.setString(1, title);
		pstmt.setString(2, body);
		pstmt.executeUpdate();
	}
	
	public void readArticle(int id) throws ClassNotFoundException, SQLException
	{
		
		String readSQL = "SELECT * FROM article WHERE id = ?";
		
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url,user,pass);
		
		PreparedStatement pstmt = conn.prepareStatement(readSQL);
		
		pstmt.setInt(1, id);
		ResultSet rs = pstmt.executeQuery();
		
		if(rs.next())
		{
			String title = rs.getString("title");
			int id2 = rs.getInt("id");
			String body = rs.getString("body");
			String nickname = rs.getString("nickname");
			int hit = rs.getInt("hit");
			System.out.println(id2 + " " + title + " " + body + " " + nickname + " " + hit);
		}
		
		else 
		{
			System.out.println("해당 게시물이 존재하지 않습니다.");
		}
		
	}
	
}
