package simulateqq;
/*
 * 在ServerFrame类中，需要显示目前登陆的用户情况，并将好友上线情况通知客户端，这是通过LoginUser类来实现的。
 * 该类是一个定时器的任务类，定时获取上线用户的信息*/
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
	private Hashtable userTable = new Hashtable();//存放每一个用户的基本信息
	private int num = 0;//上线人数
	private Connection con = null;
	
	public LoginUser(DefaultListModel listModel, JList userList, JLabel userNum,
			Hashtable userTable, Connection con){
		this.listModel = this.listModel;
		this.userList = userList;
		this.userNum = userNum;
	    this.userTable = userTable;
	    this.con = con;
	}
	
	//定时器的方法
	public void run(){
		num = 0;
		userTable.clear();
		listModel.clear();
		getUser();//查询数据库，以获得上线人数
		getUserInfo();//创建列表的方法
		userList.setCellRenderer(new FriendLabel());
		//显示上线人数
		userNum.setText(new Integer(num).toString());
	}
	
	//获取上线用户，并存储到内存中
	public void getUser(){
		String sql  = "SELECT * FROM USER_INFO WHERE STATUS = 1";
		int qqnum = 0;
		try{
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				++num;
				//创建存储好友信息的类
				UserInfoBean user = new UserInfoBean();
				qqnum = rs.getInt(1);
				//将好友的信息存储到该类中
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
				//安静存放好友信息的类放入哈希表中
				userTable.put(qqnum, user);
			}
		}catch(SQLException ex){
			ex.printStackTrace();
		}
	}
	
	//获得好友的信息，以创建列表
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
