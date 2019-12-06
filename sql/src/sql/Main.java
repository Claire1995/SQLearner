package sql;
import java.io.*;
import java.io.FileReader;
import java.sql.*;
import java.util.*;
import java.util.Scanner;


public class Main {
	private static Connection con;
	private static PreparedStatement pstmt;
	static Scanner sc = new Scanner(System.in);
	
	static String id;
	static String password;

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		
		// 디비 연결
		connectToDb();
		
		// 로그인 화면
		System.out.println("아이디와 비밀번호를 입력해주세요.");
		id = sc.next(); password = sc.next();
		
		//유저 테이블이 없으면 만들고 있으면 불러와서 삽입한다
		userTable(id, password);
		printUser();
		System.out.println();
		
		//취향 수집
		System.out.println(id+"님이 마음에 드시는 글을 골라주세요.");
		ArrayList<String> preferences = getAligenment();
		//책 추천 
		System.out.println();
		for(int i=0; i<preferences.size(); i++) {
			printRecBook(preferences.get(i));
		}
		
		
		
		
	}
	
	public static void connectToDb() {
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/SQLearner", 
					"root", "answjd1403");
			
//			java.sql.Statement st = null;
//			ResultSet result = null;
//			st = con.createStatement();
//			result = st.executeQuery("select genre from book_description");
//			
//			while(result.next()) {
//				String str = result.getNString(1);
//				System.out.println(str);
//			}
		}catch(SQLException e)
		{
			System.out.println("SQLException: "+e.getMessage());
			System.out.println("SQLState: "+e.getSQLState());
		}
	
	}
	
	public static void userTable(String id, String password) throws SQLException {
		try {
		
			java.sql.Statement making = null;
			making = con.createStatement();
			making.execute("create table if not exists user(u_id int not null auto_increment primary key, id char(20), password char(20))");
			String str = "insert into user(id, password) values('"+id+"', "+"'"+password+"')";
			making.executeUpdate(str);

		}catch(Exception e) {
			System.out.println("userTable err : " + e);
			
			
		}
		
	}
	
	public static void dropUser() {
		try {
			
			java.sql.Statement making = null;
			making = con.createStatement();
			making.execute("drop table if exists user");

		}catch(Exception e) {
			System.out.println("userTable err : " + e);
			
			
		}
	}
	
	public static void printUser() throws SQLException {
		java.sql.Statement st = null;
//		
		ResultSet result = null;
		
		st = con.createStatement();
		result = st.executeQuery("select * from user");
		
		while(result.next()) {
			String str = result.getString(1);
			System.out.println( result.getString(1)+" "+ result.getString(2)+" "+ result.getString(3));
		}
	}
	
	public static void printRecBook(String genre) throws SQLException {
		//두권씩 프린트하기

		java.sql.Statement st = null;
//		
		ResultSet result = null;
		
		st = con.createStatement();
		result = st.executeQuery("select * from book_description where genre='"+genre+"'");
		
		int size = 0;
		String temp;
		
		while(size!=2 && result.next()) {
			temp = "";
			String b_id = result.getString(1);
			
			// 책 제목 가져오기
			java.sql.Statement stt = null;
			ResultSet resultt = null;
			stt = con.createStatement();
			resultt = stt.executeQuery("select title, author from book where book.b_id="+b_id);
			while(resultt.next()) {
				System.out.println("책 제목: "+ resultt.getString(1));
				System.out.println("저자: "+ resultt.getString(2));
				System.out.println("발행년도: "+ result.getString(5));
				System.out.println("장르: " + result.getString(3));
				System.out.println("출판사: " + result.getString(4));
				System.out.println("책 번호: "+ result.getString(2));
			}
			
			System.out.println("-------------------------------------------------------------------------");
			
			
			size++;
		}
	}
	
	public static ArrayList<String> getAligenment()
	   {
	      try
	      {
	         File file = new File("C:/Users/songv/Desktop/web-workspace/SQLearner/alignment.txt");
	         FileReader file_reader = new FileReader(file);
	         int cur = 0;
	         while((cur = file_reader.read()) != -1) {
	            System.out.print((char)cur);
	         }
	         file_reader.close();
	      }catch (FileNotFoundException e)
	      {	
	         e.getStackTrace();
	      }catch(IOException e)
	      {
	         e.getStackTrace();
	      }
	      
	      int num;
	      int []number = new int[5];
	      ArrayList<String> genreList = new ArrayList<String>();

	      for(int i = 0; i < number.length; i++)
	      {
	         System.out.println("맘에 드는 글귀 5개 입력 ("+(i+1)+") ");
	         num = sc.nextInt();
	         number[i] = num;
	         
	         switch(number[i])
	         {
	         case 1:
	            genreList.add("총류");
	            break;
	         case 2:
	            genreList.add("철학");
	            break;
	         case 3:
	            genreList.add("종교");
	            break;
	         case 4:
	            genreList.add("사회과학");
	            break;
	         case 5:
	            genreList.add("어학");
	            break;
	         case 6:
	            genreList.add("자연과학");
	            break;
	         case 7:
	            genreList.add("응용과학");
	            break;
	         case 8:
	            genreList.add("예술");
	            break;
	         case 9:
	            genreList.add("문학");
	            break;
	         case 10:
	            genreList.add("역사");
	            break;
	            
	         }
	      }
	      
	      return genreList;
	      
	   }

	
}
