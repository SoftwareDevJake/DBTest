package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class DBTest {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		
		ArticleDao articleDao = new ArticleDao();
		Scanner sc = new Scanner(System.in);
		
		while(true)
		{
			System.out.print("명령어 입력 : ");
			String cmd = sc.nextLine();
			
			if(cmd.equals("list"))
			{
				articleDao.getArticles();
			}
			
			else if(cmd.equals("add"))
			{	
				System.out.print("제목 : ");
				String title = sc.nextLine();
				
				System.out.print("내용 : ");
				String body = sc.nextLine();
				
				articleDao.addArticle(title, body);
			}
			
			else if(cmd.equals("read"))
			{
				System.out.print("읽고싶은 게시물 번호를 입력해 주세요 : ");
				int id = Integer.parseInt(sc.nextLine());
				
				articleDao.readArticle(id);
			}
			
		}

	}

	
	
}
