package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ArticleDao {
	private String driver = "com.mysql.cj.jdbc.Driver";
	
	private String url = "jdbc:mysql://localhost:3306/t1?serverTimezone=UTC";
	
	private String user = "sbsst";
	
	private String pass = "sbs123414";
	
	public ArrayList<Article> getArticles()
	{
		ArrayList<Article> articles = new ArrayList();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			
			conn = DriverManager.getConnection(url,user,pass);
		
			String listSQL = "SELECT * FROM article";
			pstmt = conn.prepareStatement(listSQL);
			
			rs = pstmt.executeQuery();
			
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
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try {
				if(rs != null)
				{
					rs.close();
				}
				if(pstmt != null)
				{
					pstmt.close();
				}
				if(conn != null)
				{
					conn.close();
				}
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
		}
		return articles;
	}
	
	public void addArticle(String title, String body)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try
		{
			Class.forName(driver);
			conn = DriverManager.getConnection(url,user,pass);
			
			String addSQL = "INSERT INTO article SET title = ?, `body` = ?, nickname = '홍길동', hit = 10";
			pstmt = conn.prepareStatement(addSQL);
			
			pstmt.setString(1, title);
			pstmt.setString(2, body);
			pstmt.executeUpdate();
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		finally
		{
			try {
				if(rs != null)
				{
					rs.close();
				}
				if(pstmt != null)
				{
					pstmt.close();
				}
				if(conn != null)
				{
					conn.close();
				}
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public void readArticle(int id)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String readSQL = "SELECT * FROM article WHERE id = ?";
		
		try
		{
			Class.forName(driver);
			conn = DriverManager.getConnection(url,user,pass);
			
			pstmt = conn.prepareStatement(readSQL);
			
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			
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
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		finally {
			try {
				if(rs != null)
				{
					rs.close();
				}
				if(pstmt != null)
				{
					pstmt.close();
				}
				if(conn != null)
				{
					conn.close();
				}
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
		}
		
	}
	
	public void updateArticle(int id, String title, String body)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String updateSQL = "UPDATE article SET title = ?, `body` = ? where id = ?";
		
		try
		{
			Class.forName(driver);
			conn = DriverManager.getConnection(url,user,pass);
			
			pstmt = conn.prepareStatement(updateSQL);
			
			pstmt.setString(1, title);
			pstmt.setString(2, body);
			pstmt.setInt(3, id);
			pstmt.executeUpdate();
			
			
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		finally {
			try {
				if(pstmt != null)
				{
					pstmt.close();
				}
				if(conn != null)
				{
					conn.close();
				}
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
		}
		
	}
	
	public void deleteArticle(int id)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String deleteSQL = "DELETE FROM article WHERE id = ?";
		
		try
		{
			Class.forName(driver);
			conn = DriverManager.getConnection(url,user,pass);
			
			pstmt = conn.prepareStatement(deleteSQL);
			
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
			
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		finally {
			try {
				if(pstmt != null)
				{
					pstmt.close();
				}
				if(conn != null)
				{
					conn.close();
				}
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
		}
	}
	
}
