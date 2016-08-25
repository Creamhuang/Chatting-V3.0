package simulateqq;
/*
 * ��ServerFrame���У���Ҫ��ʾĿǰ��½���û���������������������֪ͨ�ͻ��ˣ�����ͨ��LoginUser����ʵ�ֵġ�
 * ������һ����ʱ���������࣬��ʱ��ȡ�����û�����Ϣ*/
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.TimerTask;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;

public class LoginUser extends TimerTask{
	private DefaultListModel listModel = null;
	private JList userList = null;
	private JLabel userNum = null;
	private Hashtable userTable = new Hashtable();//���ÿһ���û��Ļ�����Ϣ
	private int num = 0;//��������
	private Connection con = null;
	
	public LoginUser(DefaultListModel listModel, JList userList, JLabel userNum,
			Hashtable userTable, Connection con){
		this.listModel = this.listModel;
		this.userList = userList;
		this.userNum = userNum;
	    this.userTable = userTable;
	    this.con = con;
	}
	
	//��ʱ���ķ���
	public void run(){
		num = 0;
		userTable.clear();
		listModel.clear();
		getUser();//��ѯ���ݿ⣬�Ի����������
		getUserInfo();//�����б�ķ���
		userList.setCellRenderer(new FriendLabel());
		//��ʾ��������
		userNum.setText(new Integer(num).toString());
	}
	
	//��ȡ�����û������洢���ڴ���
	public void getUser(){
		String sql  = "SELECT * FROM USER_INFO WHERE STATUS = 1";
		int qqnum = 0;
		try{
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				++num;
				//�����洢������Ϣ����
				UserInfoBean user = new UserInfoBean();
				qqnum = rs.getInt(1);
				//�����ѵ���Ϣ�洢��������
				user.setQqnum(qqnum);
				user.setName(rs.getString(2));
				user.setPassword(rs.getString(3));
				user.setStatus(rs.getInt(4));
				user.setIp(rs.getString(5));
				user.setInfo(rs.getString(6));
				user.setPic(rs.getString(7));
				user.setSex(rs.getString(8));
				user.setEmail(rs.getString(9));
				user.setPlace(rs.getString(10));
				user.setBirthday(rs.getString(11));
				//������ź�����Ϣ��������ϣ����
				userTable.put(qqnum, user);
			}
		}catch(SQLException ex){
			ex.printStackTrace();
		}
	}
	
	//��ú��ѵ���Ϣ���Դ����б�
	private void getUserInfo(){
		Enumeration it = userTable.elements();
		String name = "";
		int currentQQNUM = 0;
		String pic = "";
		String friendInfo = "";
		int status = 0;
		while(it.hasMoreElements()){
			UserInfoBean user  = (UserInfoBean) it.nextElement();
			name = user.getName();
			currentQQNUM = user.getQqnum();
			pic = user.getPic();
			status = user.getStatus();
			friendInfo = status + name + "[" + currentQQNUM + "]" + "*" + pic;
			listModel.addElement(friendInfo);
		}
	}
}
