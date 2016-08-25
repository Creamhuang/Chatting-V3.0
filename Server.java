package simulateqq;
/*�����û���������Ĺ����࣬Ҳ��һ���߳���*/
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.print.DocFlavor.INPUT_STREAM;

public class Server implements Runnable{
	private Socket socket = null;
	private DataInputStream in = null;
	private DataOutputStream out = null;
	public static DBConnection DBcon = null;
	private Connection con = null;
	private Boolean flag = true;//���Ʒ������̵߳�������ֹͣ
	
	public Server(Socket socket){
		this.socket = socket;
		try{
			//����������
			in = new DataInputStream(socket.getInputStream());
			//���������
			out = new DataOutputStream(socket.getOutputStream());
			//�������ݿ����Ӷ���
			con = DBcon.makeConnection();
		}catch(IOException ex){
			ex.printStackTrace();
		}
	}
	
	//���̵߳�������
	public void run(){
		try{
			//ѭ����ȡ�ͻ��˷�������������������������Ӧ�ķ���������
			while(flag){
				String str = in.readUTF();
				if(str.equals("end")){
					break;
				}else if(str.equals("registerNewUser")){
					registerNewUser();
				}else if(str.equals("login")){
					login();
				}else if(str.equals("queryUser")){
					int qqnum = Integer.parseInt(in.readUTF());
					queryUser(qqnum);
				}else if(str.equals("addFriend")){
					addFriend();
				}else if(str.equals("updateOwnInfo")){
					updateOwnInfo();
				}else if(str.equals("logout")){
					logout();
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//�˷������ڴ������û�ע��
	public void registerNewUser(){
		//���û���Ϣ�������ݿ��е�sql���
		String sql = "INSERT INTO USER_INFO (QQNUM, NAME, PASSWORD,INFO, PIC, SEX, EMAIL, PLACE, BIRTHDAY)"+
				 "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
		//��ȡ�Ѿ�ע���QQ��
		String sql2 = "SELECTE QQNUM FROM QQNUM WHERE ID = 1";
		Statement stmt = null;
		ResultSet rs = null;
		try{
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql2);
			rs.next();
			int qqnum = rs.getInt("QQNUM");
			PreparedStatement pre1 = con.prepareCall(sql);
			qqnum += 1;
			
			//�����ȡ�ͻ��˷���������Ϣ
			String name = in.readUTF();
			String password = in.readUTF();
			String info = in.readUTF();
			String pic = in.readUTF();
			String sex = in.readUTF();
			String email = in.readUTF();
			String place = in.readUTF();
			String birthday = in.readUTF();
			pre1.clearParameters();
			pre1.setInt(1, qqnum);
			pre1.setString(2, name);
			pre1.setString(3, password);
			pre1.setString(4, info);
			pre1.setString(5, pic);
			pre1.setString(6, sex);
			pre1.setString(7, email);
			pre1.setString(8, place);
			pre1.setString(9, birthday);
			System.out.println(sql);
			pre1.executeUpdate();

			//����QQ�ŵ�sql���
			String sql3 = "UPDATE QQNUM SET QQNUM = ? WHERE ID = 1";
			PreparedStatement pre2 = con.prepareCall(sql3);
			pre2.clearParameters();
			pre2.executeUpdate();
			out.writeUTF("registerOver");
			
			//���û�����ע���QQ��
			out.writeInt(qqnum);
			rs.close();
			stmt.close();
		}catch(Exception ex){
			try{
				out.writeUTF("registerFail");
			}catch(IOException ex1){
				ex1.printStackTrace();
			}
			ex.printStackTrace();
		}
	}
	
	//�˷��������û���½
	public void login(){
		Statement stmt1 = null;
		Statement stmt2 = null;
		ResultSet rs = null;
		try{
			int qqnum = Integer.parseInt(in.readUTF());
			String password = in.readUTF();
			int port = Integer.parseInt(in.readUTF());
			String sql1 = "SELECT * FROM USER_INFO WHERE QQNUM = " + qqnum +
					"AND PASSWORD = '" + password + "'";
			stmt1 = con.createStatement();
			rs = stmt1.executeQuery(sql1);
			
			//�����½�ɹ�����ִ�����²���
			if(rs.next()){
				String sql2 = "UPDATE USER_INFO SET STATUS = 1, IP = '" +
						socket.getInetAddress().getHostAddress() + 
						"' PORT  = " + port + "WHERE QQNUM = " + qqnum;
				System.out.println(sql2);
				System.out.println(port + " " + socket.getLocalPort());
				stmt2 = con.createStatement();
				stmt2.executeUpdate(sql2);
				out.writeUTF("sendUserInfo");
				queryUser(qqnum);
				out.writeUTF("loginSuccess");
				//�������ĺ�����Ϣ����ʾ�ں����б���
				queryFriend(qqnum);
				stmt2.close();
			}
			else{
				out.writeUTF("loginFail");
			}
			rs.close();
			stmt1.close();
		}catch(Exception ex){
			try{
				out.writeUTF("loginFail");
			}catch(IOException ex1){
				ex1.printStackTrace();
			}
			ex.printStackTrace();
		}
	}
	
	//�˷������ڲ��Һ���
	public void queryFriend(int qqnum){
		Statement stmt1 = null;
		Statement stmt2 = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		Vector friendNum = new Vector();//���ڴ洢���ѵ�QQ����
		try{
			//��������QQ�����sql���
			String sql1 = "SELECT FRIEND.FRIEND FROM USER_INFO, FRIEND WHERE "+
			"USER_INFO.QQNUM = " + qqnum +" AND USER_INFO.QQNUM = FRIEND.QQNUM";
			stmt1 = con.createStatement();
			rs1 = stmt1.executeQuery(sql1);
			//�����ѵ�QQ�������δ���������
			while(rs1.next()){
				friendNum.addElement(rs1.getInt(1));
			}
			rs1.close();
			stmt1.close();
			
			//����ȡ�����ѵĺ��룬����������Ϣ
			for(int i = 0; i < friendNum.size(); i++){
				int num = (int) friendNum.elementAt(i);
				String sql2  = "SELECT * FROM USER_INFO WHERE QQNUM = " + num;
				stmt2 = con.createStatement();
				rs2 = stmt2.executeQuery(sql2);
				rs2.next();
				
				//��ͻ��˷��ͺ�����Ϣ
				out.writeUTF(new Integer(rs2.getInt(1)).toString());
				out.writeUTF(rs2.getString(2));
				out.writeUTF(rs2.getString(3));
				out.writeUTF(new Integer(rs2.getInt(4)).toString());
				out.writeUTF(rs2.getString(5));
				out.writeUTF(rs2.getString(6));
				out.writeUTF(rs2.getString(7));
				out.writeUTF(rs2.getString(8));
				out.writeUTF(rs2.getString(9));
				out.writeUTF(rs2.getString(10));
				out.writeUTF(rs2.getString(11));
				out.writeUTF(new Integer(rs2.getInt(12)).toString());
				rs2.close();
				stmt2.close();
			}
			out.writeUTF("queryFriendOver");
		}catch(Exception ex){
			try{
				out.writeUTF("queryFriendFail");
			}catch(IOException ex2){
				ex2.printStackTrace();
			}
			ex.printStackTrace();
		}
	}
	
	//�˷������ڲ����û�
	public void queryUser(int qqnum){
		Statement stmt = null;
		ResultSet rs = null;
		try{
			String sql = "SELECT * FROM USER_INFO WHERE QQNUM = " +qqnum;
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				out.writeUTF(new Integer(rs.getInt(1)).toString());
				out.writeUTF(rs.getString(2));
				out.writeUTF(rs.getString(3));
				out.writeUTF(new Integer(rs.getInt(4)).toString());
				out.writeUTF(rs.getString(5));
				out.writeUTF(rs.getString(6));
				out.writeUTF(rs.getString(7));
				out.writeUTF(rs.getString(8));
				out.writeUTF(rs.getString(9));
				out.writeUTF(rs.getString(10));
				out.writeUTF(rs.getString(11));
				out.writeUTF(new Integer(rs.getInt(12)).toString());
				rs.close();
				stmt.close();
			}else{
				out.writeUTF("noUser");
			}
		}catch(Exception ex){
			try{
				out.writeUTF("queryUserFail");
			}catch(IOException ex2){
				ex2.printStackTrace();
			}
			ex.printStackTrace();
		}finally{
			try{
				rs.close();
				stmt.close();
			}catch(SQLException ex2){
				ex2.printStackTrace();
			}
		}
	}
	
	//�˷�������ɾ������
	public void addFriend(){
		try{
			String sql ="INSERT INTO FRIEND(QQNUM, FRIEND) VALUES(?, ?)";
			PreparedStatement pre = con.prepareCall(sql);
			pre.clearParameters();
			pre.setInt(1, Integer.parseInt(in.readUTF()));
			pre.setInt(2, Integer.parseInt(in.readUTF()));
			pre.execute();
			out.writeUTF("addFriendOver");
		}catch(Exception ex){
			try{
				out.writeUTF("addFriendFail");
			}catch(IOException ex1){
				ex1.printStackTrace();
			}
			ex.printStackTrace();
		}
	}
	
	//�˷�������ɾ������
	public void deleteFriend(){
		try{
			String sql ="DELETE FROM FRIEND WHERE QQNUM = ? AND FRIEND = ?";
			PreparedStatement pre = con.prepareCall(sql);
			pre.clearParameters();
			pre.setInt(1, Integer.parseInt(in.readUTF()));
			pre.setInt(2, Integer.parseInt(in.readUTF()));
			pre.execute();
			out.writeUTF("deleteFriendOver");
		}catch(Exception ex){
			try{
				out.writeUTF("deleteFriendFail");
			}catch(IOException ex1){
				ex1.printStackTrace();
			}
			ex.printStackTrace();
		}
	}
	
	//�˷��������û������Լ�����Ϣ
	public void updateOwnInfo(){
		try{
			String sql = "UPDATE USER_INFO SET NAME = ?, PASSWORD = ?, " +
					"INFO = ?, PIC = ?, SEX = ?, EMAIL = ?, PLACE = ?, BIRTHDAY = ?, " +
					"WHERE QQNUM = ?";
			int qqnum = Integer.parseInt(in.readUTF());
			PreparedStatement pre = con.prepareCall(sql);
			pre.clearParameters();
			pre.setString(1, in.readUTF());
			pre.setString(2, in.readUTF());
			pre.setString(3, in.readUTF());
			pre.setString(4, in.readUTF());
			pre.setString(5, in.readUTF());
			pre.setString(6, in.readUTF());
			pre.setString(7, in.readUTF());
			pre.setString(8, in.readUTF());
			pre.setInt(9, qqnum);
			pre.execute();
			out.writeUTF("updateOver");
		}catch(Exception ex){
			try{
				out.writeUTF("updateFail");
			}catch(IOException ex1){
				ex1.printStackTrace();
			}
			ex.printStackTrace();
		}
	}
	
	//�˷������ڴ����û�����
	public void logout(){
		try{
			String sql = "UPDATE USER_INFO SET STATUS = 0, IP = 'NULL', PORT = 0,"
					+ "WHERE QQNUM = ? ";
			PreparedStatement pre = con.prepareStatement(sql);
			pre.clearParameters();
			pre.setInt(1, Integer.parseInt(in.readUTF()));
			pre.execute();
			out.writeUTF("logou");
		}catch(Exception ex){
			try{
				out.writeUTF("logoutFail");
			}catch(IOException ex1){
				ex1.printStackTrace();
			}
			ex.printStackTrace();
		}
	}
}
