package jdbc;

import java.sql.SQLException;
import java.util.ArrayList;
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
				ArrayList<Article> articles = articleDao.getArticles();
				
				for(int i = 0; i < articles.size(); i++)
				{
					System.out.println("번호 : " + articles.get(i).getId());
					System.out.println("제목 : " + articles.get(i).getTitle());
					System.out.println("내용 : " + articles.get(i).getBody());
					System.out.println("날짜 : " + articles.get(i).getRegDate());
					System.out.println("작성자 : " + articles.get(i).getNickname());
					System.out.println("조회수 : " + articles.get(i).getHit());
				}
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
			
			else if(cmd.equals("update"))
			{
				System.out.print("수정하고 싶은 게시물 번호를 입력해 주세요 : ");
				int id = Integer.parseInt(sc.nextLine());
				Article article = articleDao.readArticle(id);
				
				if(article != null)
				{		
					System.out.print("제목 : ");
					String title = sc.nextLine();
					
					System.out.print("내용 : ");
					String body = sc.nextLine();
					
					articleDao.updateArticle(id, title, body);
				}
				else
				{
					System.out.println("해당 게시물이 존재하지 않습니다.");
				}
			}
			
			else if(cmd.equals("delete"))
			{
				System.out.print("지우고 싶은 게시물 번호를 입력해 주세요 :");
				int id = Integer.parseInt(sc.nextLine());
				
				Article article = articleDao.readArticle(id);
				
				if(article != null)
				{
					articleDao.deleteArticle(id);
				}
				else
				{
					System.out.println("해당 게시물이 존재하지 않습니다.");
				}
			}
		}

	}

	
	
}
