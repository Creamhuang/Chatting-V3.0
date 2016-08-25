package simulateqq;

import java.awt.AWTEvent;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import com.borland.jbcl.layout.BoxLayout2;
import com.borland.jbcl.layout.XYConstraints;
import com.borland.jbcl.layout.XYLayout;

public class Login extends JFrame implements ActionListener{
	JLabel jLabel2 = new JLabel();
	JLabel jLabel3 = new JLabel();
	JTextField qqnum = new JTextField();
	JPasswordField passWord = new JPasswordField();
	JButton submit = new JButton();
	JButton resect = new JButton();
	JButton register = new JButton();
	JPanel jPanel1 = new JPanel();
	FlowLayout flowLayout1 = new FlowLayout();
	JPanel jPanel2 = new JPanel();
	XYLayout xYLayout1 = new XYLayout();
	Border border1 = BorderFactory.createLineBorder(UIManager.getColor(
			"InternalFrame.inactiveTitleBackground"), 1);
	Border border2 = new TitledBorder(border1, "�������û���������");
	JPanel jPanel3 = new JPanel();
	Border border3 = BorderFactory.createLineBorder(UIManager.getColor(
			"InternalFrame.inactiveTitleBackground"), 1);
	Border border4 = new TitledBorder(border3, "�����������IP�Ͷ˿�");
	JLabel jLabel1 = new JLabel();
	JLabel jLabel4 = new JLabel();
	JTextField ip = new JTextField();
	JTextField port = new JTextField();
	FlowLayout flowLayout2 = new FlowLayout();
	BoxLayout2 boxLayout21 = new BoxLayout2();
	Boolean pass = true;//���ڱ�ʶ�Ƿ��½�ɹ�
	String localIP = null;//��¼�ͻ���������IP��ַ
	InetAddress serverIP = null;//�û�����ķ������˵�IP��ַ
	int serverPort = 0;//��¼�û�����Ķ˿ں�
	
	public Login(){
		enableEvents(AWTEvent.WINDOW_EVENT_MASK);
		try{
			InetAddress localAddr = InetAddress .getLocalHost();
			localIP = localAddr.getHostAddress();
		}catch(UnknownHostException ex){
			ex.printStackTrace();
		}
	}
	
	//���ÿؼ�
	private void jbInit() throws Exception{
		this.getContentPane().setLayout(boxLayout21);
		jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel3.setHorizontalAlignment(SwingConstants.RIGHT);
		qqnum.setColumns(15);
		qqnum.addActionListener(this);
		passWord.setFont(new java.awt.Font("Dialog", Font.PLAIN, 12));
		passWord.setColumns(15);
		passWord.addActionListener(this);
		submit.setMnemonic('T');
		submit.setText("��¼T");
		submit.addActionListener(this);
		resect.setMnemonic('S');
		resect.setText("����S");
		resect.addActionListener(this);
		jLabel3.setText("��  �룺");
		this.setResizable(false);
		this.setTitle("MyQQ�û���½");
		register.setMnemonic('R');
		register.setText("ע��R");
		register.addActionListener(this);
		jLabel2.setText("QQ�ţ�");
		jPanel1.setBorder(null);
		jPanel1.setLayout(flowLayout1);
		flowLayout1.setHgap(15);
		jPanel2.setBorder(border2);
		jPanel2.setLayout(xYLayout1);
		jPanel3.setBorder(border4);
		jPanel3.setLayout(flowLayout2);
		jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel1.setText("IP��ַ");
		jLabel4.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel4.setText("�˿ں�");
		ip.setText(localIP);
		ip.setColumns(15);
		ip.addActionListener(this);
		port.setText(new Integer(getPort("tcp.ip.port")).toString());
		port.setColumns(10);
		port.addActionListener(this);
		boxLayout21.setAxis(BoxLayout.Y_AXIS);
		jPanel1.add(submit);
		jPanel1.add(resect);
		jPanel1.add(register);
		this.getContentPane().add(jPanel2);
		this.getContentPane().add(jPanel3);
		jPanel3.add(jLabel1, null);
		jPanel3.add(ip, null);
		jPanel3.add(jLabel4, null);
		jPanel3.add(port, null);
		this.getContentPane().add(jPanel1);
		jPanel2.add(qqnum, new XYConstraints(107, 11, 125, -1));
		jPanel2.add(passWord, new XYConstraints(106, 48, 126, 20));
		jPanel2.add(jLabel2, new XYConstraints(24, 15, 60, -1));
		jPanel2.add(jLabel3, new XYConstraints(23, 53, 61, -1));
	}
	
	//�����¼��رմ���
	public void processWindowEvent(WindowEvent e){
		super.processWindowEvent(e);
		if(e.getID() == WindowEvent.WINDOW_CLOSED){
			System.exit(0);
		}
	}
	
	//�����û������ύ��ť
	public void submit_actionPerferformed(ActionEvent e){
		String qqnumInfo = qqnum.getText().trim();
		String passwordInfo = new String(passWord.getPassword()).trim();
		String ipInfo = ip.getText().trim();
		String portInfo = port.getText().trim();
		if(qqnumInfo.equals("")){
			JOptionPane.showMessageDialog(this, "���������QQ�ţ�");
			qqnum.requestFocus();
		}else if(passwordInfo.equals("")){
			JOptionPane.showMessageDialog(this, "���������룡");
			passWord.requestFocus();
		}else if(!isNum(qqnumInfo)){
			JOptionPane.showMessageDialog(this, "�����QQ��������");
		}else if(!isLength(8, 16, ipInfo)){
			JOptionPane.showMessageDialog(this, "IP��ַ��Ч��");
		}else if(!isNum(portInfo)){
			JOptionPane.showMessageDialog(this, "�˿���Ч��");
		}else if(Integer.parseInt(portInfo) > 65535 ||
				Integer.parseInt(portInfo) < 0){
			JOptionPane.showMessageDialog(this, "�˿���Ч��");
		}else{
			this.setVisible(false);
			try{
				serverIP = InetAddress.getByName(ip.getText().trim());
			}catch(UnknownHostException ex){
				ex.printStackTrace();
			}
			serverPort = Integer.parseInt(port.getText().trim());
			ClientManageFrame frame = new ClientManageFrame(Integer.parseInt(qqnumInfo),
					passwordInfo, this, serverIP, serverPort);
			SetCenter.setScreenCenter(frame);
		}
		
		if(!pass){
			JOptionPane.showMessageDialog(null, "��Ǹ��¼ʧ�ܣ�����QQ���롢���롢IP���˿��Ƿ��������");
			Login log = new Login();
			log.pack();
			SetCenter.setScreenCenter(log);
			log.setVisible(true);
		}
		
	}
	
	public void loginSuccess(){
		pass = true; 
	}
	public void loginFail(){
		pass = false;
	}
	//�ж�������ַ��Ƿ�ȫΪ����
	public boolean isNum(String text){
		boolean error = false;
		try{
			Integer.parseInt(text);
		}catch(Exception e){
			error = true;
		}
		if(error){
			return false;//��ʾ������
		}else{
			return true;//��ʾ������
		}
	}
	//�ж����볤���Ƿ����ƶ���Χ
	public Boolean isLength(int low, int high, String text){
		if(text.length() >= low && text.length() <= high){
			return true;//�ڷ�Χ��
		}else{
			return false;//���ڷ�Χ��
		}
	}
	
	//��ȡ�˿ں�
	public int getPort(String key){
		int port = 0;
		Properties p =new Properties();
		String file_separator = System.getProperty("file.separator");
		try{
			FileInputStream in = new FileInputStream("property" + file_separator + 
					"dbProperties.txt");
			p.load(in);//���������ж�ȡ�����б�
			port = Integer.parseInt(p.getProperty(key));
			in.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return port;
	}
	
	//�����û��ڿ������봰���лس��¼�
	public void passWord_actionPerformed(ActionEvent e){
		ip.requestFocus();
	}
	//�����û����˺����봰���лس��¼�
	public void userName_actionPerformed(ActionEvent e){
		passWord.requestFocus();
	}
	//�����û�����ע�ᰴť�¼�
	public void register_actionPerformed(ActionEvent e){
		serverPort = Integer.parseInt(port.getText().trim());
		try{
			serverIP = InetAddress.getByName(ip.getText().trim());
		}catch(UnknownHostException ex){
			ex.printStackTrace();
		}
		RegisterDialog reg  = new RegisterDialog(this, "�û�ע�����", true, 
				serverIP, serverPort);
		SetCenter.setDialogCenter(this, reg);
		reg.setVisible(true);
	}
	//�������
	public static void main(String[] args){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}catch(Exception ex){
			ex.printStackTrace();
		}
		Login log = new Login();
		log.pack();
		SetCenter.setScreenCenter(log);
		log.setVisible(true);
	}
	//�ж��û���������¼���ť
	public void resect_actionPerformed(ActionEvent e){
		qqnum.setText("");
		passWord.setText("");
		ip.setText(localIP);
		port.setText("5501");
	}
	//�����û���IP���봰���лس��¼�
	public void ip_actionPerformed(ActionEvent e){
		port.requestFocus();
	}
	//�����û��ڶ˿����봰���еĻس��¼�
	public void port_actionPerformed(ActionEvent e){
		submit.doClick(0);
	}
	//ͳһ��Ӧ��ť����¼�����������Ӧ�Ĵ�����
	public void actionPerformed(ActionEvent e){
		Object obj = e.getSource();
		if(obj == register)
			register_actionPerformed(e);
		else if(obj == port)
			port_actionPerformed(e);
		else if(obj == ip)
			ip_actionPerformed(e);
		else if(obj == resect)
			resect_actionPerformed(e);
		//else if(obj == submit)
			//submit_actionPerformed(e);
		else if(obj == passWord)
			passWord_actionPerformed(e);
		else if(obj == qqnum)
		    userName_actionPerformed(e);
	
	}
	
}
