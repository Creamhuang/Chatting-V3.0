package simulateqq;
/*
 * DBConnection�������������ݿ�͹ر�����*/
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
	private String driver = null;//��������
	private String url = null;//ODBC����Դ
	private String username = null;
	private String password = null;
	private Connection con = null;
	
	public DBConnection(){
		Properties p = new Properties();
		try{
			//��ȡ�ļ��ķָ���
			String file_separator = System.getProperty("file.separator");
			//System.out.println(file_separator);
			FileInputStream in = new FileInputStream("property" + file_separator + 
					"dbProperties.txt");
			p.load(in);//���������ж�ȡ�����б�
			driver = p.getProperty("jdbc.driver");
			url = p.getProperty("jdbc.url");
			username  = p.getProperty("username");
			password = p.getProperty("password");
			System.out.println(driver + " " + url + " " + password);
			in.close();
		}catch(FileNotFoundException ex){
			ex.printStackTrace();
		}catch(IOException ex){
			ex.printStackTrace();
		}
	}
	
	public DBConnection(String driver, String url, String username, String password){
		this.driver = driver;
		this.url = url;
		this.username = username; 
		this.password = password;
	}

	//�������ݿ�����
	public Connection makeConnection(){
		con = null;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/myqq",
					"root","123");
		}catch(SQLException sqle){
			sqle.printStackTrace();
		}catch(ClassNotFoundException ex){
			ex.printStackTrace();
		}
		return con;
	}
	
	//�ر����ݿ�����
	public void closeConnection(){
		try{
			con.close();
		}catch(SQLException sqle){
			sqle.printStackTrace();
		}
	}
}
