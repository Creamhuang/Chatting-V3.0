package simulateqq;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Hashtable;

import javax.sound.midi.Receiver;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import com.borland.jbcl.layout.XYConstraints;
import com.borland.jbcl.layout.XYLayout;

public class ChatFrame 
   extends JFrame implements Runnable, ActionListener, KeyListener{
	XYLayout xYLayout1 = new XYLayout();
	JPanel jPanel1 = new JPanel();
	Border border1 = BorderFactory.createEtchedBorder(Color.white, 
			new Color(165, 163, 151));
	Border border2 = new TitledBorder(border1, "好友信息");
	TitledBorder titledBorder1 = new TitledBorder("");
	JButton sendButton = new JButton();
	JPanel leftPane = new JPanel();
	XYLayout xYLayout4 = new XYLayout();
	JLabel jLabel1 = new JLabel();
	JPanel rightPane = new JPanel();
	BorderLayout borderLayout3 = new BorderLayout();
	JSplitPane splitPane = new JSplitPane();
	JScrollPane showScrollPane = new JScrollPane();
	JTextArea showArea = new JTextArea();
	JLabel showTime = new JLabel();
	JScrollPane sendScrollPane = new JScrollPane();
	JTextArea sendArea = new JTextArea();
	XYLayout xYLayout2 = new XYLayout();
	JLabel jLabel2 = new JLabel();
	JLabel name = new JLabel();
	JLabel jLabel3 = new JLabel();	
	JLabel pic = new JLabel();
	JLabel jLabel4 = new JLabel();
	JLabel sex = new JLabel();
	JLabel jLabel5 = new JLabel();
	JLabel address = new JLabel();
	JLabel jLabel6 = new JLabel();
	JScrollPane showFriendScrollPane = new JScrollPane();
	JTextArea friendInfo = new JTextArea();
	Border border3 = BorderFactory.createLineBorder(new Color(157, 185, 235), 1);
	//声明次此窗体的父窗体
	JFrame owner = null;
	//声明存放好友信息类的哈希表
	private UserInfoBean friend = null;
	private UserInfoBean myInfo = null;
	//声明存放好友信息的哈希表
	Hashtable friendsInfo = null;
	//声明发送信息的数据报套接字
	private DatagramSocket sendSocket = null;
	//声明存放信息的数据包
	private DatagramPacket sendPacket = null;
	//声明接收信息的数据报套接字
	private DatagramSocket receiveSocket = null;
	//声明接收信息的数据包
	private DatagramPacket receivePacket = null;
	//收发数据的端口
	private int myPort = 0;
	//接收数据主机的IP地址
	private String friendIP = null;
	private int friendPort = 0;
	//缓冲数组的大小
	public static final int BUFFER_SIZE = 5120;
	//发送数据的缓冲数组
	private byte outBuf[] = null;
	//获取系统的换行符
	String line_separator = System.getProperty("line_separator");
	//构造方法，对成员变量进行初始化
	public ChatFrame(UserInfoBean myInfo, UserInfoBean friend, JFrame owner, int port,
			DatagramSocket receiveSocket, DatagramPacket receivePacket, Hashtable friends){
		this.myInfo = myInfo;
		this.friend = friend;
		this.owner = owner;
		this.myPort = myPort;
		this.receiveSocket = receiveSocket;
		this.receivePacket = receivePacket;
		this.friendsInfo = friends;
		showArea.append("好友IP： " + friendIP + " 好友端口：" + friendPort + line_separator);
		showArea.append("我的端口：" + myPort + line_separator);
		try{
			jbInit();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		try{
			//创建发送信息的数据报套接字
			sendSocket  = new DatagramSocket();
		}catch(SocketException ex){
			ex.printStackTrace();
		}		
	}
	
	//处理关闭窗口事件
	protected void processWindowEvent(WindowEvent e){
		if(e.getID() == WindowEvent.WINDOW_CLOSING){
			this.dispose();
		}
	}
	
	//设置控件布局
	private void jbInit()  throws Exception{
		border2 = new TitledBorder(BorderFactory.createLineBorder(
				UIManager.getColor("InternalFrame.inactiveTitleGradient"), 1), "好友信息");
		getContentPane().setLayout(xYLayout1);
		xYLayout1.setWidth(557);
		xYLayout1.setHeight(410);
		jPanel1.setBorder(border2);
		jPanel1.setLayout(xYLayout2);
		sendButton.setInputVerifier(null);
		sendButton.setMargin(new Insets(2, 8, 2, 8));
		sendButton.setText("发送");
		sendButton.addActionListener(this);
		leftPane.setLayout(xYLayout4);
		leftPane.setBorder(null);
		jLabel1.setForeground(Color.blue);
		jLabel1.setText("(Alt+Enter)");
		rightPane.setLayout(borderLayout3);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane.setLastDividerLocation(250);
		showTime.setBorder(border3);
		showTime.setText("现在时间");
		jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel2.setText("姓名");
		jLabel3.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel3.setText("头像");
		jLabel4.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel4.setText("性别");
		jLabel5.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel5.setText("籍贯");
		jLabel6.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel6.setText("好友简介");
		this.setResizable(false);
		this.setTitle("与 [" + friend.getName() + "] 聊天中");
		friendInfo.setEditable(false);
		friendInfo.setLineWrap(true);
		
		showArea.setFont(new java.awt.Font("Dialog", Font.PLAIN, 14));
		showArea.setForeground(Color.blue);
		showArea.setEditable(false);
		showArea.setLineWrap(true);
		
		sendArea.addKeyListener(this);
		sendArea.setFont(new java.awt.Font("Dialog", Font.PLAIN, 14));
		sendArea.setForeground(Color.blue);
		splitPane.add(showScrollPane, JSplitPane.TOP);
		splitPane.add(showScrollPane, JSplitPane.BOTTOM);
		sendScrollPane .getViewport().add(sendArea);
		sendScrollPane .getViewport().add(showArea);
		
		//下面用绝对位置为控件定位
		this.getContentPane().add(rightPane, new XYConstraints(0, 6, 368, 365));
		rightPane.add(splitPane, java.awt.BorderLayout.CENTER);
		leftPane.add(sendButton, new XYConstraints(6, 333, 56, -1));
		leftPane.add(jLabel1, new XYConstraints(66, 333, 74, 22));
		this.getContentPane().add(leftPane, new XYConstraints(367, 8, 187, 362));
		jPanel1.add(showFriendScrollPane, new XYConstraints(2, 179, 160, 100));
		jPanel1.add(jLabel2, new XYConstraints(13, 6, 44, 23));
		jPanel1.add(jLabel3, new XYConstraints(12, 43, 45, 25));
		jPanel1.add(jLabel4, new XYConstraints(14, 84, 44, 25));
		jPanel1.add(jLabel5, new XYConstraints(13, 118, 44, 22));
		leftPane.add(jPanel1, new XYConstraints(4, 0, 178, 312));
		showFriendScrollPane.getViewport().add(friendInfo);
		this.getContentPane().add(showTime, new XYConstraints(2, 380, 547, 24));
		jPanel1.add(name, new XYConstraints(63, 6, 99, 23));
		jPanel1.add(pic, new XYConstraints(63, 33, 62, 39));
		jPanel1.add(sex, new XYConstraints(63, 84, 66, 25));
		jPanel1.add(address, new XYConstraints(63, 117, 100, 25));
		jPanel1.add(jLabel6, new XYConstraints(5, 151, 67, 23));
		showFriendInfo();//显示好友信息
		this.pack();
		SetCenter.setFrameCenter(owner, this);
		this.setVisible(true);
		splitPane.setDividerLocation(240);
		//下面在标签中动态显示时间
		java.util.Timer myTimer = new java.util.Timer();
		java.util.TimerTask task = new ShowTimeTask(showTime);
		myTimer.schedule(task, 0, 1000);
	}
	
	//该方法用于在标签中动态显示好友基本信息
	private void showFriendInfo(){
		name.setText(friend.getName());
		pic.setIcon(new ImageIcon(friend.getPic()));
		sex.setText(friend.getSex());
		address.setText(friend.getPlace());
		friendInfo.setText(friend.getInfo());
	}
	
	//监听其他好友发来的消息
	public void run(){
		String receiveInfo = "";
		while(true){
			try {
				receiveSocket.receive(receivePacket);
				receiveInfo = new String(receivePacket.getData(), 0, receivePacket.getLength());
				//获取*出现的位置
				int num_Index  = receiveInfo.indexOf("*");
				//获取/出现的位置
				int name_Index  = receiveInfo.indexOf("/");
				//获取发送方的QQ号
				int f_qqnum = Integer.parseInt(receiveInfo.substring(num_Index+1, name_Index));
				//获取发送方的用户名
				String f_name = receiveInfo.substring(num_Index+1, name_Index);
				//获取发送方的信息
				String f_info = receiveInfo.substring(name_Index);
				//如果好友列表中不包括该用户，就显示接收到陌生人消息对话框
				/*if(!friendInfo.contains(f_qqnum)){
					ReceiveOtherDialog rod = new ReceiveOtherDialog(null, "收到陌生人消息", true, 
							f_qqunm, f_name, f_info);
					SetCenter.setDialogCenter(null, rod);
				}else{*/
					showArea.append(friend.getName() + " : " + line_separator);
					showArea.append("   " + f_info + line_separator);
					showArea.append(line_separator);
				//}
			} catch (IOException e) {
				showArea.append("接收数据出错" + line_separator);
			}
		}
	}
	
	public void sendButton_actionPerformed(){
		//获取我的QQ号
		int qqnum = myInfo.getQqnum();
		//获取我的用户名
		String name = myInfo.getName();
		//获取我要发送的信息
		String initInfo = sendArea.getText().trim();
		//装配我要发送的信息，有3部分组成，包括（我的QQ号，我的用户名和我要发送的信息）
		String sendInfo = qqnum + "*" + name + "/" + initInfo;
		//将我要发送的信息转换成字节数组
		outBuf = sendInfo.getBytes();
		if(initInfo.length() != 0){
			try{
				sendPacket = new DatagramPacket(outBuf, outBuf.length, 
						InetAddress.getByName(friendIP), friendPort);
				sendSocket.send(sendPacket);
				showArea.append(myInfo.getName() + " : " + line_separator);
				showArea.append("   " + initInfo + line_separator);
				showArea.append(line_separator);
				showArea.setText("");
			}catch(UnknownHostException e1){
				showArea.append("对方不在线，无法连接到指定地址" + line_separator);
			}catch(IOException e1){
				showArea.append("发送数据失败" + line_separator);
			}
		}
	}
	
	//响应发送按钮单击事件，调用它的处理方法
	public void actionPerformed(ActionEvent e){
		sendButton_actionPerformed();
	}
	
	//在聊天窗口中输入了ALT+回车
	public void keyTyped(KeyEvent e){
		if(e.isAltDown() && (e.getKeyChar() == '\n'))
			sendButton_actionPerformed();
	}
	
	//下面两个方法都是为了实现接口所必须的
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}

