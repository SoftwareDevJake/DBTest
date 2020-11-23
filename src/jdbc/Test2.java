package jdbc;

public class Test2 {
	
	public static void main(String[] args)
	{
		String sql = "update * from article where id = ? and title = ?";
		
		if(sql.startsWith("select") || sql.startsWith("update"))
		{
			int index = sql.indexOf(" where ");
			
			sql = sql.substring(index + 7);
			
			String[] stringBits = sql.split("and");
			
			for(int i = 0; i < stringBits.length; i++)
			{
				String rst = stringBits[i].replace(" ", "");
				System.out.println(rst);
			}
		}
		
		else
		{
			System.out.println("2");
		}
		
	}
	
}
