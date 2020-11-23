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
	
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	public Article getRow(String sql, String[] params)
	{
		return (getRows(sql,params)).get(0);
	}
	
	public ArrayList<Article> getRows(String sql, String[] params)
	{
		ArrayList<Article> articles = new ArrayList<>();
		
		try {
			conn = getConnection();
			
			pstmt = conn.prepareStatement(sql);
			
			String[] fields = sqlCheck(sql);
			
			if(fields != null)
			{
				for(int i = 0; i < fields.length; i++)
				{
					if(fields.equals("hit") || fields.equals("id"))
					{
						pstmt.setInt(i + 1,  Integer.parseInt(params[i]));
					}
					else
					{
						pstmt.setString(i + 1,  params[i]);
					}
				}
				
			}
	
			rs = pstmt.executeQuery();
			
			while(rs.next())
			{
				String title = rs.getString("title");
				int id = rs.getInt("id");
				String body = rs.getString("body");
				String nickname = rs.getString("nickname");
				int hit = rs.getInt("hit");
				System.out.println(id + " " + title + " " + body + " " + nickname + " " + hit);
				
				Article article = new Article();
				article.setTitle(title);
				article.setBody(body);
				article.setNickname(nickname);
				article.setId(id);
				article.setHit(hit);
				
				articles.add(article);
				
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
			}
			
		return articles;
		
	}
	
	public void setData(String sql, String[] params)
	{
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			
			String[] fields = sqlCheck(sql);
			if(fields != null)
			{
				for(int i = 0; i <fields.length; i++)
				{
					if(fields.equals("hit") || fields.equals("id"))
					{
						pstmt.setInt(i + 1, Integer.parseInt(params[i]));
					}
					else
					{
						pstmt.setString(i + 1, params[i]);
					}
				}
			}
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
		
	}
	
	public String[] sqlCheck(String sql)
	{
		String[] stringBits = null;
		
		if(sql.startsWith("select") || sql.startsWith("update"))
		{
			int index = sql.indexOf(" where ");
			
			if(index != -1)
			{
				sql = sql.substring(index + 7);
				
				stringBits = sql.split("and");
				
				for(int i = 0; i < stringBits.length; i++)
				{
					stringBits[i]  = stringBits[i].replace(" ", "");
				}
				
				for(int i = 0; i < stringBits.length; i++)
				{
					String[] tmp = stringBits[i].split("=");
					stringBits[i] = tmp[0].replace(" ", "");
				}	
			}
		}
		return stringBits;
	}
	
	
	
	
	public ArrayList<Article> getArticles()
	{
		return getRows("select * from article", null);
	}
	
	public ArrayList<Article> getArticlesByTitle(String title)
	{
		String[] params = new String[1];
		params[0] = title;
		
		return getRows("select * from article where title = ?", params);
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
	
	public Article readArticle(int id)
	{
		Article article = new Article();
		
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
				
				article.setTitle(title);
				article.setId(id2);
				article.setBody(body);
				article.setNickname(nickname);
				article.setHit(hit);
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
		return article;
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
	
	public Connection getConnection()
	{
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, pass);			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return conn;
	}
	
	public void close()
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
