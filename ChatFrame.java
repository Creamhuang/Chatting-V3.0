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
	Border border2 = new TitledBorder(border1, "������Ϣ");
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
	//�����δ˴���ĸ�����
	JFrame owner = null;
	//������ź�����Ϣ��Ĺ�ϣ��
	private UserInfoBean friend = null;
	private UserInfoBean myInfo = null;
	//������ź�����Ϣ�Ĺ�ϣ��
	Hashtable friendsInfo = null;
	//����������Ϣ�����ݱ��׽���
	private DatagramSocket sendSocket = null;
	//���������Ϣ�����ݰ�
	private DatagramPacket sendPacket = null;
	//����������Ϣ�����ݱ��׽���
	private DatagramSocket receiveSocket = null;
	//����������Ϣ�����ݰ�
	private DatagramPacket receivePacket = null;
	//�շ����ݵĶ˿�
	private int myPort = 0;
	//��������������IP��ַ
	private String friendIP = null;
	private int friendPort = 0;
	//��������Ĵ�С
	public static final int BUFFER_SIZE = 5120;
	//�������ݵĻ�������
	private byte outBuf[] = null;
	//��ȡϵͳ�Ļ��з�
	String line_separator = System.getProperty("line_separator");
	//���췽�����Գ�Ա�������г�ʼ��
	public ChatFrame(UserInfoBean myInfo, UserInfoBean friend, JFrame owner, int port,
			DatagramSocket receiveSocket, DatagramPacket receivePacket, Hashtable friends){
		this.myInfo = myInfo;
		this.friend = friend;
		this.owner = owner;
		this.myPort = myPort;
		this.receiveSocket = receiveSocket;
		this.receivePacket = receivePacket;
		this.friendsInfo = friends;
		showArea.append("����IP�� " + friendIP + " ���Ѷ˿ڣ�" + friendPort + line_separator);
		showArea.append("�ҵĶ˿ڣ�" + myPort + line_separator);
		try{
			jbInit();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		try{
			//����������Ϣ�����ݱ��׽���
			sendSocket  = new DatagramSocket();
		}catch(SocketException ex){
			ex.printStackTrace();
		}		
	}
	
	//����رմ����¼�
	protected void processWindowEvent(WindowEvent e){
		if(e.getID() == WindowEvent.WINDOW_CLOSING){
			this.dispose();
		}
	}
	
	//���ÿؼ�����
	private void jbInit()  throws Exception{
		border2 = new TitledBorder(BorderFactory.createLineBorder(
				UIManager.getColor("InternalFrame.inactiveTitleGradient"), 1), "������Ϣ");
		getContentPane().setLayout(xYLayout1);
		xYLayout1.setWidth(557);
		xYLayout1.setHeight(410);
		jPanel1.setBorder(border2);
		jPanel1.setLayout(xYLayout2);
		sendButton.setInputVerifier(null);
		sendButton.setMargin(new Insets(2, 8, 2, 8));
		sendButton.setText("����");
		sendButton.addActionListener(this);
		leftPane.setLayout(xYLayout4);
		leftPane.setBorder(null);
		jLabel1.setForeground(Color.blue);
		jLabel1.setText("(Alt+Enter)");
		rightPane.setLayout(borderLayout3);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane.setLastDividerLocation(250);
		showTime.setBorder(border3);
		showTime.setText("����ʱ��");
		jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel2.setText("����");
		jLabel3.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel3.setText("ͷ��");
		jLabel4.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel4.setText("�Ա�");
		jLabel5.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel5.setText("����");
		jLabel6.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel6.setText("���Ѽ��");
		this.setResizable(false);
		this.setTitle("�� [" + friend.getName() + "] ������");
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
		
		//�����þ���λ��Ϊ�ؼ���λ
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
		showFriendInfo();//��ʾ������Ϣ
		this.pack();
		SetCenter.setFrameCenter(owner, this);
		this.setVisible(true);
		splitPane.setDividerLocation(240);
		//�����ڱ�ǩ�ж�̬��ʾʱ��
		java.util.Timer myTimer = new java.util.Timer();
		java.util.TimerTask task = new ShowTimeTask(showTime);
		myTimer.schedule(task, 0, 1000);
	}
	
	//�÷��������ڱ�ǩ�ж�̬��ʾ���ѻ�����Ϣ
	private void showFriendInfo(){
		name.setText(friend.getName());
		pic.setIcon(new ImageIcon(friend.getPic()));
		sex.setText(friend.getSex());
		address.setText(friend.getPlace());
		friendInfo.setText(friend.getInfo());
	}
	
	//�����������ѷ�������Ϣ
	public void run(){
		String receiveInfo = "";
		while(true){
			try {
				receiveSocket.receive(receivePacket);
				receiveInfo = new String(receivePacket.getData(), 0, receivePacket.getLength());
				//��ȡ*���ֵ�λ��
				int num_Index  = receiveInfo.indexOf("*");
				//��ȡ/���ֵ�λ��
				int name_Index  = receiveInfo.indexOf("/");
				//��ȡ���ͷ���QQ��
				int f_qqnum = Integer.parseInt(receiveInfo.substring(num_Index+1, name_Index));
				//��ȡ���ͷ����û���
				String f_name = receiveInfo.substring(num_Index+1, name_Index);
				//��ȡ���ͷ�����Ϣ
				String f_info = receiveInfo.substring(name_Index);
				//��������б��в��������û�������ʾ���յ�İ������Ϣ�Ի���
				/*if(!friendInfo.contains(f_qqnum)){
					ReceiveOtherDialog rod = new ReceiveOtherDialog(null, "�յ�İ������Ϣ", true, 
							f_qqunm, f_name, f_info);
					SetCenter.setDialogCenter(null, rod);
				}else{*/
					showArea.append(friend.getName() + " : " + line_separator);
					showArea.append("   " + f_info + line_separator);
					showArea.append(line_separator);
				//}
			} catch (IOException e) {
				showArea.append("�������ݳ���" + line_separator);
			}
		}
	}
	
	public void sendButton_actionPerformed(){
		//��ȡ�ҵ�QQ��
		int qqnum = myInfo.getQqnum();
		//��ȡ�ҵ��û���
		String name = myInfo.getName();
		//��ȡ��Ҫ���͵���Ϣ
		String initInfo = sendArea.getText().trim();
		//װ����Ҫ���͵���Ϣ����3������ɣ��������ҵ�QQ�ţ��ҵ��û�������Ҫ���͵���Ϣ��
		String sendInfo = qqnum + "*" + name + "/" + initInfo;
		//����Ҫ���͵���Ϣת�����ֽ�����
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
				showArea.append("�Է������ߣ��޷����ӵ�ָ����ַ" + line_separator);
			}catch(IOException e1){
				showArea.append("��������ʧ��" + line_separator);
			}
		}
	}
	
	//��Ӧ���Ͱ�ť�����¼����������Ĵ�����
	public void actionPerformed(ActionEvent e){
		sendButton_actionPerformed();
	}
	
	//�����촰����������ALT+�س�
	public void keyTyped(KeyEvent e){
		if(e.isAltDown() && (e.getKeyChar() == '\n'))
			sendButton_actionPerformed();
	}
	
	//����������������Ϊ��ʵ�ֽӿ��������
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}

