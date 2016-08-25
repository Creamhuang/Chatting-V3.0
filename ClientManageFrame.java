package simulateqq;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;

import com.borland.jbcl.layout.XYConstraints;
import com.borland.jbcl.layout.XYLayout;

public class ClientManageFrame extends JFrame implements Runnable, ActionListener{
	JPanel mainPane = new JPanel();
	JPanel buttonPane = new JPanel();
	JPanel labelPane = new JPanel();
	XYLayout xYLayout2 = new XYLayout();
	JLabel infoLabel = new JLabel();
	JPanel userListPane = new JPanel();
	JButton find = new JButton();
	JButton update = new JButton();
	JButton deleteFriend = new JButton();
	JButton addFriend = new JButton();
	JButton exit = new JButton();
	JScrollPane jScrollPane1 = new JScrollPane();
	BorderLayout borderLayout1 = new BorderLayout();
	DefaultListModel listModel = new DefaultListModel();
	JList userList = new JList(listModel);
	JMenuBar jMenuBar1 = new JMenuBar();
	JMenu jMenu1 = new JMenu();
	JMenu jMenu2 = new JMenu();
	JMenuItem jMenuItem1 = new JMenuItem();
	JMenuItem jMenuItem2 = new JMenuItem();
	JMenuItem jMenuItem3 = new JMenuItem();
	JMenuItem jMenuItem4 = new JMenuItem();
	JMenuItem jMenuItem6 = new JMenuItem();
	JMenuItem jMenuItem7 = new JMenuItem();
	JPopupMenu jPopupMenu1 = new JPopupMenu();
	JMenuItem jMenuItem8 = new JMenuItem();
	JMenuItem jMenuItem9 = new JMenuItem();
	JMenuItem jMenuItem10 = new JMenuItem();
	XYLayout xYLayout1 = new XYLayout();
	XYLayout xYLayout3 = new XYLayout();
	XYLayout xYLayout4 = new XYLayout();
	//�������ڴ洢������Ϣ�Ĺ�ϣ��
	private Hashtable friendInfoTable = new Hashtable();
	private Socket socket = null;
	private DataInputStream in = null;
	private DataOutputStream out = null;
	private int QQNUM = 0;
	private String PASSWORD = null;
	//һ���ĸ��������ڴ洢��������ƶ��¼�����õ���Ϣ
	private int currentIndex = 0;//�����ָ���б�����
	private String currentInfo = "";//�����ָ���б�ֵ
	private Integer currentQQNUM = null;//�����ָ���ѵ�QQ����
	private UserInfoBean currentFriend = null;//�����ָ���ѵ���Ϣ��
	private UserInfoBean myInfo = new UserInfoBean();//�洢�Լ�����Ϣ
	//�洢���ҵ����û�����Ϣ��
	UserInfoBean findUserBean = new UserInfoBean();
	Login login = null;
	InetAddress logAddress = null;//������IP
	int serverPort = 0;//�������˿�
	int udpPort = getPort("udp.Port");//���ļ��л�ȡUDP�ĳ�ʼ�˿ں�
	int usePort = getNextPort(udpPort);//��ǰ�û�ʹ�õĶ˿ں�
	//����������Ϣ�����ݰ��׽���
	private DatagramSocket receiveSocket = null;
	//����������Ϣ�����ݰ�
	private DatagramPacket receivePacket = null;
	//��������Ĵ�С
	public static final int BUFFER_SIZE = 5120;
	private byte inBuf[] = null;
	JLabel ownPic = new JLabel();
	JLabel ownInfo = new JLabel();
	Border border1 = BorderFactory.createLineBorder(UIManager.getColor(
			"InternalFrame.inactiveTitleBackground"), 1);
	JLabel jLabel1 = new JLabel();
	
	public ClientManageFrame(int QQNUM, String PASSWORD, Login log, InetAddress address,
			int port){
		this.QQNUM = QQNUM;
		this.PASSWORD = PASSWORD;
		this.login = log;
		this.logAddress = address;
		this.serverPort = port;
		try{
			jbInit();
		}catch(Exception exception){
			exception.printStackTrace();
		}
		try{
			socket = new Socket(logAddress, serverPort);
			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());
			//����������Ϣ�����ݱ��׽���
			receiveSocket  = new DatagramSocket(usePort);
			inBuf = new byte[BUFFER_SIZE];
			//����������Ϣ�����ݱ�
			receivePacket = new DatagramPacket(inBuf, BUFFER_SIZE);
		}catch(IOException ex){
			login.loginFail();
			return;
		}
		//��¼
		loadUserInfo();
		//�����̣߳�����ˢ�º�����Ϣ
		new Thread(this).start();
		this.setSize(210, 490);
		this.setVisible(true);
	}
	//��ú�����Ϣ
	private void loadUserInfo(){
		if(login()){
			getFriendInfo();//��ú�����Ϣ�б�
			userList.setCellRenderer(new FriendLabel());
			ownPic.setIcon(new ImageIcon(myInfo.getPic()));
			ownInfo.setText(myInfo.getName() + "[" + myInfo.getQqnum() + "]");
		}else{
			login.loginFail();
			return;
		}
	}
	
	//���̵߳�������ÿ��10��ˢ��һ�κ�����Ϣ
	public void run(){
		while(true){
			try{
				Thread.sleep(10000);
			}catch(InterruptedException ex){
				ex.printStackTrace();
			}
			loadUserInfo();
		}
	}
	//�����ڹر��¼�
	protected void processWindowEvent(WindowEvent e){
		if(e.getID() == WindowEvent.WINDOW_CLOSING){
			exit();
		}
	}
	//���ý���
	private void jbInit() throws Exception{
		getContentPane().setLayout(xYLayout4);
		this.setJMenuBar(jMenuBar1);
		this.setResizable(false);
		this.setTitle("MyQQ�������");
		mainPane.setLayout(xYLayout2);
		infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
		infoLabel.setText("�ҵĺ����б�");
		userListPane.setBackground(Color.white);
		userListPane.setLayout(borderLayout1);
		find.setText("����");
		find.addActionListener(this);
		update.setText("����");
		update.addActionListener(this);
		deleteFriend.setText("ɾ������");
		deleteFriend.addActionListener(this);
		addFriend.setText("��Ӻ���");
		addFriend.addActionListener(this);
		buttonPane.setLayout(xYLayout3);
		exit.setText("�˳�");
		exit.addActionListener(this);
		jMenu1.setText("�û�����");
		jMenu2.setText("����");
		jMenuItem1.setText("����");
		jMenuItem1.addActionListener(this);
		jMenuItem2.setText("����");
		jMenuItem2.addActionListener(this);
		jMenuItem3.setText("ɾ������");
		jMenuItem3.addActionListener(this);
		jMenuItem4.setText("��Ӻ���");
		jMenuItem4.addActionListener(this);
		jMenuItem6.setText("�˳�");
		jMenuItem6.addActionListener(this);
		jMenuItem7.setText("����");
		jMenuItem7.addActionListener(this);
		jMenuItem8.setText("�鿴��ϸ��Ϣ");
		jMenuItem8.addActionListener(this);
		jMenuItem9.setText("�Ӻ����б���ɾ��");
		jMenuItem9.addActionListener(this);
		jMenuItem10.setText("������Ϣ");
		jMenuItem10.addActionListener(this);
	    labelPane.setLayout(xYLayout1);
	    userList.addMouseListener(new ClientManageFrame_userList_mouseAdapter());
	    userList.addMouseMotionListener(new ClientManageFrame_userList_mouseMotionAdapter());
	    xYLayout4.setWidth(200);
	    xYLayout4.setHeight(453);
	    labelPane.setBorder(border1);
	    ownInfo.setFont(new java.awt.Font("΢���ź�", Font.PLAIN, 13));
	    ownInfo.setForeground(Color.blue);
	    ownPic.addMouseListener(new ClientManageFrame_ownPic_mouseAdapter());
	    jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
	    jLabel1.setText("������Ϣÿ��10��ˢ��һ��");
	    jMenuBar1.add(jMenu1);
	    jMenuBar1.add(jMenu2);
	    jMenu1.add(jMenuItem1);
	    jMenu1.add(jMenuItem2);
	    jMenu1.add(jMenuItem3);
	    jMenu1.add(jMenuItem4);
	    jMenu1.addSeparator();
	    jMenu1.add(jMenuItem6);
	    jMenu1.add(jMenuItem7);
	    jPopupMenu1.add(jMenuItem10);
	    jPopupMenu1.add(jMenuItem8);
	    jPopupMenu1.add(jMenuItem9);
	    userList.setComponentPopupMenu(jPopupMenu1);
	    buttonPane.add(deleteFriend, new XYConstraints(106, 36, -1, -1));
	    buttonPane.add(addFriend, new XYConstraints(6, 36, -1, -1));
	    buttonPane.add(update, new XYConstraints(103, 4, 80, -1));
	    buttonPane.add(find, new XYConstraints(6, 4, 81, -1));
	    buttonPane.add(exit, new XYConstraints(56, 67, 80, -1));
	    mainPane.add(infoLabel, new XYConstraints(3, 42, 190, -1));
	    mainPane.add(userListPane, new XYConstraints(2, 60, 191, 238));
	    userListPane.add(jScrollPane1, java.awt.BorderLayout.CENTER);
	    jScrollPane1.getViewport().add(userList);
	    this.getContentPane().add(buttonPane, new XYConstraints(2, 338, 194, -1));
	    this.getContentPane().add(mainPane, new XYConstraints(2, 4, -1, 330));
	    mainPane.add(labelPane, new XYConstraints(3, 4, 190, -1));
	    labelPane.add(ownInfo, new XYConstraints(67, 5, 119, 23));
	    labelPane.add(ownPic, new XYConstraints(2, 1, 37, 29));
	    mainPane.add(jLabel1, new XYConstraints(23, 301, 168, 22));
	}
	//�÷������ڻ�ú��ѵ���Ϣ���Դ����б�
	public void getFriendInfo(){
		listModel.removeAllElements();
		Enumeration it  = friendInfoTable.elements();
		String name = "";
		int currentQQNUM = 0;
		String pic = "";
		String friendInfo  = "";
		int status = 0;
		while(it.hasMoreElements()){
			UserInfoBean user = (UserInfoBean) it.nextElement();
			name = user.getName();
			currentQQNUM = user.getQqnum();
			pic = user.getPic();
			status = user.getStatus();
			friendInfo = status + name + "[" + currentQQNUM + "]" + "*" + pic;
			listModel.addElement(friendInfo);
		}
	}
	//��ȡ�˿ں�
	private int getPort(String key){
		int port = 0;
		Properties p = new Properties();
		String file_separator = System.getProperty("file_separator");
		try{
			File file = new File("property" + file_separator + "dbProperties.txt");
			FileInputStream in = new FileInputStream(file);
			FileOutputStream out = new FileOutputStream(file, true);
			p.load(in);//���������ж�ȡ�����б�
			port = Integer.parseInt(p.getProperty(key));
			port  = port + 1;
			p.setProperty("udp.port", new Integer(port).toString());
			p.store(out, "new udp.port");
			in.close();
			out.close();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return port;
	}
	//�÷������ڵ�¼ʱ��ȡ�Լ��ͺ��ѵ���Ϣ
	private Boolean login(){
		try{
			out.writeUTF("login");
			out.writeUTF(new Integer(QQNUM).toString());
			out.writeUTF(PASSWORD);
			out.writeUTF(new Integer(usePort).toString());
			String loginInfo1 = in.readUTF();
			//��ȡ�û��Լ�����Ϣ
			if(loginInfo1.equals("loginFail")){
				return false;
			}else if(loginInfo1.equals("sendUserInfo")){
				String flag1 = in.readUTF();
				if(flag1.equals("queryUserFail")){
					return false;
				}else{
					myInfo.setQqnum(Integer.parseInt(flag1));
					myInfo.setName(in.readUTF());
					myInfo.setPassword(in.readUTF());
					myInfo.setStatus(Integer.parseInt(in.readUTF()));
					myInfo.setIp(in.readUTF());
					myInfo.setInfo(in.readUTF());
					myInfo.setPic(in.readUTF());
					myInfo.setSex(in.readUTF());
					myInfo.setEmail(in.readUTF());
					myInfo.setPlace(in.readUTF());
					myInfo.setBirthday(in.readUTF());
					myInfo.setPort(Integer.parseInt(in.readUTF()));
				}
			}
			String loginInfo2 = in.readUTF();
			//�����¼�ɹ����Ͷ�ȡ���ѵ�����
			if(loginInfo2.equals("loginSuccess")){
				//����ù�ϣ���е����ݲ�Ϊ�գ��������
				//if(!friendInfoTable.isEmpty()){
					friendInfoTable.clear();
				//}
				String flag2 = "";
				do{//��ȡ������Ϣ
					flag2  = in.readUTF();
					System.out.println("flag2" + flag2);
					//�ж���Ϣ�Ƿ������ϣ����������˳�
					if(flag2.equals("queryFriendOver")){
						break;
					}else{
						int f_qqnum = Integer.parseInt(flag2);
						String f_name = in.readUTF();
						String f_password = in.readUTF();
						int  f_status = Integer.parseInt(in.readUTF());
						String f_ip =in.readUTF();
						String f_info = in.readUTF();
						String f_pic = in.readUTF();
						String f_sex = in.readUTF();
						String f_email = in.readUTF();
						String f_place = in.readUTF();
						String f_birthday = in.readUTF();
						int f_port = Integer.parseInt(in.readUTF());
						//�����洢������Ϣ����
						UserInfoBean friendBean  = new UserInfoBean();
						//�����ѵ���Ϣ�洢��������
						friendBean.setQqnum(f_qqnum);
						friendBean.setName(f_name);
						friendBean.setPassword(f_password);
						friendBean.setStatus(f_status);
						friendBean.setIp(f_ip);
						friendBean.setInfo(f_info);
						friendBean.setPic(f_pic);
						friendBean.setSex(f_sex);
						friendBean.setEmail(f_email);
						friendBean.setPlace(f_place);
						friendBean.setBirthday(f_birthday);
						friendBean.setPort(f_port);
						//����ź�����Ϣ��������ϣ����
						friendInfoTable.put(f_qqnum, friendBean);
					}
				}while(!flag2.equals("queryFriendOver"));
			}else if(loginInfo2.equals("loginFail")){//�����¼ʧ�ܣ�Ҫ����������¼�Ի���
				return false;
			}
		}catch(IOException ex){
			ex.printStackTrace();
		}
		return false;
	}
	//�����û������˳���ť
	public void exit_actionPerformed(ActionEvent e){
		exit();
	}
	//�÷��������û��Ĳ��Һ��ѵ�����
	public void findUser(int qqnum){
		System.out.println("�����û�");
		try{
			//����������Ͳ����û���Ҫ��
			out.writeUTF("queryUser");
			//����Ҫ���ҵ��û���QQ��
			out.writeUTF(new Integer(qqnum).toString());
			String msg = in.readUTF();
			if(msg.equals("queryUserFail")){
				JOptionPane.showMessageDialog(this, "����ʧ��");
			}else if(msg.equals("noUser")){
				JOptionPane.showMessageDialog(this, "���û�������");
			}else{
				findUserBean.setQqnum(Integer.parseInt(msg));
				findUserBean.setName(in.readUTF());
				findUserBean.setPassword(in.readUTF());
				findUserBean.setStatus(Integer.parseInt(in.readUTF()));
				findUserBean.setIp(in.readUTF());
				findUserBean.setInfo(in.readUTF());
				findUserBean.setPic(in.readUTF());
				findUserBean.setSex(in.readUTF());
				findUserBean.setEmail(in.readUTF());
				findUserBean.setPlace(in.readUTF());
				findUserBean.setBirthday(in.readUTF());
				findUserBean.setPort(Integer.parseInt(in.readUTF()));
				//������ɺ���ʾ���û�����Ϣ
				FindUserInfo fuiDlg = new FindUserInfo(this, "�û���Ϣ", false, 
						findUserBean, this);
				SetCenter.setDialogCenter(this, fuiDlg);
				fuiDlg.setVisible(true);
			}
		}catch(IOException ex){
			ex.printStackTrace();
		}
	}
	//�÷��������û�����º��ѵ�����
	public void addNewFriend(){
		try{
			//�������������Ӻ��ѵ�����
			out.writeUTF("addFriend");
			//�����Լ���QQ����
			out.writeUTF(new Integer(myInfo.getQqnum()).toString());
			//���ͺ��ѵ�QQ��
			out.writeUTF(new Integer(findUserBean.getQqnum()).toString());
			//��ȡ���������ص���Ϣ
			String msg = in.readUTF();
			if(msg.equals("addFriendOver")){
				JOptionPane.showMessageDialog(this, "��ӳɹ���");
				//������ӵĺ�����Ϣ�����ϣ����
				friendInfoTable.put(findUserBean.getQqnum(), findUserBean);
				//���б�����ʾ����ӵĺ���
				String name = findUserBean.getName();
				int qqnum = findUserBean.getQqnum();
				String pic = findUserBean.getPic();
				int status = findUserBean.getStatus();
				String friendInfo = status + name + "[" + qqnum + "]" + "*" + pic;
				
				if(listModel.contains(friendInfo)){
					JOptionPane.showMessageDialog(this, "���û��Ѵ����ں����б���");
				}else{
					listModel.addElement(friendInfo);
				}
			}else{
				JOptionPane.showMessageDialog(this, "���ʧ�ܣ����ݿ��в����ڸ��û�����Ϣ");
			}
		}catch(IOException e){
			
		}
	}
	
	//�÷��������û��Լ���ͷ����û���
	public void refreshOwnInfo(){
		ownPic.setIcon(new ImageIcon(myInfo.getPic()));
		ownPic.setText(myInfo.getName() + "[" + myInfo.getQqnum() + "]");
	}
	
	//�÷��������û���ɾ����������
	public void deleteFriend(){
		int index  = userList.getSelectedIndex();
		Integer friendQqnum = null;
		if(index == -1){
			JOptionPane.showMessageDialog(this, "�뵥�����ѡ��һ���û�");
		}else{
			String userInfo = (String) listModel.getElementAt(index);
			friendQqnum = new Integer(userInfo.substring(userInfo.indexOf("[")+1,
					userInfo.indexOf("]")));
			UserInfoBean delFriend  = (UserInfoBean) friendInfoTable.get(friendQqnum);
			int myQqnum = myInfo.getQqnum();
			try{
				//�����������ɾ�����ѵ�����
				out.writeUTF("deleteFriend");
				//�����Լ���QQ��
				out.writeUTF(new Integer(myQqnum).toString());
				//����Ҫɾ���ĺ��ѵ�QQ��
				out.writeUTF(new Integer(friendQqnum).toString());
				String msg = in.readUTF();
				if(msg.equals("deleteFriendOver")){
					JOptionPane.showMessageDialog(this, "����  [" + 
							delFriend.getQqnum() + "] �ѱ��ɹ�ɾ����");
					listModel.remove(index);
				}else if(msg.equals("deleteFriendFail")){
					JOptionPane.showMessageDialog(this, "ɾ������ [" + 
							delFriend.getQqnum() + "]ʱʧ�ܣ����Ժ����ԣ�");
				}
			}catch(IOException ex){
				ex.printStackTrace();
			}
		}
	}
	
	//�÷��������û�������
	public void exit(){
		int option = JOptionPane.showConfirmDialog(this, "ȷ��Ҫ�˳�ô��");
		if(option == JOptionPane.YES_OPTION){
			try{
				//������������
				out.writeUTF("logout");
				//�����Լ���QQ��
				out.writeUTF(new Integer(myInfo.getQqnum()).toString());
				String msg = in.readUTF();
				if(msg.equals("logout")){
					in.close();
					out.close();
					socket.close();
				}
			}catch(IOException ex){
				ex.printStackTrace();
			}finally{
				this.dispose();
				System.exit(0);
			}
		}
	}
	
	//�÷��������û�����ĳ������ͷ��
	public void userList_mouseClicked(MouseEvent e){
		if(e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1){
			new Thread(new ChatFrame(myInfo, currentFriend, this, usePort, 
					receiveSocket, receivePacket, friendInfoTable)).start();
		}
	}
	
	//��ȡ��һ���˿�
	private int getNextPort(int port){
		int nextPort = port;
		Boolean flag = true;
		DatagramSocket testSocket = null;
		//�������ѭ��ץҩ���ڼ��˿��Ƿ�ռ��
		while(true){
			flag = true;
			try{
				testSocket = new DatagramSocket(++nextPort); 
			}catch(SocketException ex){
				flag = false;
			}
			if(flag = true){
				break;
			}
			System.out.println(nextPort);
		}
		testSocket.close();
		return nextPort;
	}
	
	//�����û�������ƶ���ĳһͷ����
	public void userList_mouseMoved(MouseEvent e){
		if(!listModel.isEmpty()){
			//�����굱ǰ���ڵ��б�����
			currentIndex = userList.locationToIndex(e.getPoint());
			//��õ�ǰ�б��ֵ
			currentInfo = listModel.getElementAt(currentIndex).toString();
			//�ӵ�ǰ�б��ֵ����ȡQQ��
			currentQQNUM = new Integer(currentInfo.substring(currentInfo.indexOf(
					"[")+1, currentInfo.indexOf("]")));
			//���ݺ��ѵ�QQ�Ų��Һ��ѵ���Ϣ
			currentFriend = (UserInfoBean) friendInfoTable.get(currentQQNUM);
			String name = currentFriend.getName();
			String sex = currentFriend.getSex();
			String birth = currentFriend.getBirthday();
			int status = currentFriend.getStatus();
			String address = currentFriend.getPlace();
			String tooTip = "����" + name + "�Ա�" + sex + "����" + birth + "����" + address;
			
			if(status == 0){
				userList.setToolTipText("[����]" + tooTip);
			}else{
					userList.setToolTipText(tooTip);
			}
		}
	}
	
	//�����û�������Ƶ��Լ�ͷ���ϵ��¼�
	public void ownPic_mouseEntered(MouseEvent e){
		ownPic.setCursor(new Cursor(Cursor.HAND_CURSOR));
	}
	
	//�����û������Լ�ͷ���¼�
	public void ownPic_mousePressed(MouseEvent e){
		UserInfo userInfo = new UserInfo(this, "�ҵĻ�����Ϣ", false, myInfo);
		SetCenter.setDialogCenter(this, userInfo);
		userInfo.setVisible(true);
	}
	
	//�������˳��˵��¼�
	public void jMenuItem6_actionPerformed(ActionEvent e){
		exit();
	}
	
	//�����û�������ɾ�����ѡ���ť
	public void deleteFriend_actionPerformed(ActionEvent e){
		int option = JOptionPane.showConfirmDialog(this, "ȷ��Ҫɾ���ú���ô��");
		if(option == JOptionPane.YES_OPTION){
			deleteFriend();
		}
	}
	
	//�����û������ɾ�����ѡ��˵�
	public void jMenuItem3_actionPerformed(ActionEvent e){
		int option = JOptionPane.showConfirmDialog(this, "ȷ��Ҫɾ���ú���ô��");
		if(option == JOptionPane.YES_OPTION){
			deleteFriend();
		}
	}
	
	//�����û����������ҡ���ť
	public void find_actionPerformed(ActionEvent e){
		FindUserDlg find = new FindUserDlg(this, "�����û�", false, this);
		SetCenter.setDialogCenter(this, find);
		find.setVisible(true);
	}
	
	//�����û���������ҡ��˵�
	public void jMenuItem1_actionPerFormed(ActionEvent e){
		find.doClick();
	}
	
	//�����û���������Ӻ��ѡ���ť
	public void addFriend_actionPerformed(ActionEvent e){
		find.doClick();
	}
	//�����û���������Ӻ��ѡ���ť
	public void jMenuItem4_actionPerformed(ActionEvent e){
		find.doClick();
	}
	
	//�޸���Ϣ�İ�ť�¼�
	public void update_actioPerFormed(ActionEvent e){
		UpdateDialog udl = new UpdateDialog(this, "�޸���Ϣ", true, 
				logAddress, serverPort, myInfo);
		SetCenter.setDialogCenter(this, udl);
		udl.setVisible(true);
		this.refreshOwnInfo();
	}
	
	//�޸���Ϣ�Ĳ˵��¼�
	public void jMenuItem2_actionPerFormed(ActionEvent e){
		UpdateDialog udl = new UpdateDialog(this, "�޸���Ϣ", true, 
					logAddress, serverPort, myInfo);
		SetCenter.setDialogCenter(this, udl);
		udl.setVisible(true);
		System.out.println("�޸��¼�����");
		this.refreshOwnInfo();
	}
	
	//������Ϣ�Ĳ˵��¼�
	public void jMenuItem10_actionPeformed(ActionEvent e){
		new Thread(new ChatFrame(myInfo, currentFriend, this, usePort, receiveSocket,
				receivePacket, friendInfoTable)).start();
	}
	
	//�����û����"ɾ������"�˵�
	public void jMenuItem9_actionPerformed(ActionEvent e){
		int option = JOptionPane.showConfirmDialog(this, "ȷ��Ҫɾ���ú���ô��");
		if(option == JOptionPane.YES_OPTION){
			deleteFriend();
		}
	}
	
	//�����û�������鿴������ϸ��Ϣ���˵�
	public void jMenuItem8_actionPerformed(ActionEvent E){
		UserInfo userInfo = new UserInfo(this, "�ҵĻ�����Ϣ", false, currentFriend);
		SetCenter.setDialogCenter(this, userInfo);
		userInfo.setVisible(true);
	}
	
	//�����û���������ڡ��˵�
	public void jMenuItem7_actionPerformed(ActionEvent e){
		MyInfo_AboutBox dailog = new MyInfo_AboutBox(this);
		SetCenter.setDialogCenter(this, dailog);
		dailog.pack();
		dailog.setModal(true);
		dailog.setVisible(true);
	}
	
	//ͳһ��Ӧ���ְ�ť�Ͳ˵��¼����ж��¼�Դ��������Ӧ�Ĵ�����
	public void actionPerformed(ActionEvent e){
		Object obj;
		obj = e.getSource();
		if(obj == jMenuItem1){
			jMenuItem1_actionPerFormed(e);
		}else if(obj == jMenuItem2){
			jMenuItem2_actionPerFormed(e);
		}else if(obj == jMenuItem3){
			jMenuItem2_actionPerFormed(e);
		}else if(obj == jMenuItem4){
			jMenuItem2_actionPerFormed(e);
		}else if(obj == jMenuItem6){
			jMenuItem2_actionPerFormed(e);
		}else if(obj == jMenuItem7){
			jMenuItem2_actionPerFormed(e);
		}else if(obj == jMenuItem8){
			jMenuItem2_actionPerFormed(e);
		}else if(obj == jMenuItem9){
			jMenuItem2_actionPerFormed(e);
		}else if(obj == jMenuItem10){
			jMenuItem2_actionPerFormed(e);
		}else if(obj == find){
			find_actionPerformed(e);
		}else if(obj == addFriend){
			addFriend_actionPerformed(e);
		}else if(obj == update){
			update_actioPerFormed(e);
		}else if(obj == deleteFriend){
			deleteFriend_actionPerformed(e);
		}else if(obj == exit){
			exit_actionPerformed(e);
		}
	}	
	
	//�ڲ��࣬��Ӧ�û��Լ�ͷ���ϵ�������͵����¼�
	class ClientManageFrame_ownPic_mouseAdapter extends MouseAdapter{
		public void mouseEntered(MouseEvent e){
			ownPic_mouseEntered(e);
		}
		public void mousePressed(MouseEvent e){
			ownPic_mousePressed(e);
		}
	}
	//�ڲ��࣬��Ӧ����ͷ���ϵ���굥���¼�
	class ClientManageFrame_userList_mouseAdapter extends MouseAdapter{
		public void mouseClicked(MouseEvent e){
			userList_mouseClicked(e);
		}
		
	}
	//�ڲ��࣬��Ӧ����ͷ���ϵ���굥���¼�
	class ClientManageFrame_userList_mouseMotionAdapter extends MouseMotionAdapter{
		public void mouseMoved(MouseEvent e){
			userList_mouseMoved(e);
		}
	}
}
	

