package simulateqq;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import com.borland.jbcl.layout.XYConstraints;
import com.borland.jbcl.layout.XYLayout;

public class UpdateDialog extends JDialog implements ActionListener, ItemListener{
	JPanel panel1 = new JPanel();
	JLabel jLabel1 = new JLabel();
	JLabel jLabel2 = new JLabel();
	JLabel jLabel3 = new JLabel();
	JLabel jLabel4 = new JLabel();
	JLabel jLabel5 = new JLabel();
	JLabel jLabel6 = new JLabel();
	JLabel jLabel7 = new JLabel();
	JLabel jLabel8 = new JLabel();
	JLabel jLabel9 = new JLabel();
	JPanel jPanel1 = new JPanel();
	JPanel iconPane = new JPanel();
	Border border1 = BorderFactory.createLineBorder(UIManager.getColor(
			"EditorPane.selectionBackground"), 1);
	Border border2 = new TitledBorder(border1, "修改信息");
	JTextField userName = new JTextField();
	JTextField email = new JTextField();
	JTextField address = new JTextField();
	JScrollPane jScrollPane1 = new JScrollPane();
	JTextArea introduceMe = new JTextArea();
	ButtonGroup group = new ButtonGroup();
	JRadioButton men = new JRadioButton();
	JRadioButton women = new JRadioButton();
	DefaultComboBoxModel yearModel = new DefaultComboBoxModel();
	DefaultComboBoxModel monthModel = new DefaultComboBoxModel();
	JComboBox year = new JComboBox();
	JLabel jLabel10 = new JLabel();
	JComboBox month = new JComboBox();
	JLabel jLabel11 = new JLabel();
	JPanel jPanel2 = new JPanel();
	JButton reset = new JButton();
	JButton submit = new JButton();
	JLabel imageLabel = new JLabel();
	JScrollPane iconScrollPane = new JScrollPane();
	JList pictureList = new JList();
	BorderLayout borderLayout1 = new BorderLayout();
	FlowLayout flowLayout1 = new FlowLayout();
	BorderLayout borderLayout2 = new BorderLayout();
	XYLayout xYLayout1 = new XYLayout();
	String file_separate = System.getProperty("file_separator");
	ImageIcon defaultIcon = new ImageIcon("image" + file_separate + "face" + 
			file_separate + "1-1.jpg");
	//用户选择的图像路径
	String imagePath = "image" + file_separate + "face" + file_separate + "1-1.jpg";
	String sex = "男";//记录用户选择的性别
	InetAddress logAddress = null;//服务器IP
	int serverPort = 0;//服务器端口
	//存储用户的基本类型的类
	UserInfoBean userInfo = null;
	JPasswordField password = new JPasswordField();
	JPasswordField configPassword = new JPasswordField();
	public UpdateDialog(Frame owner, String title, boolean modal, InetAddress address, 
			int port, UserInfoBean userInfo){
		super(owner, title, modal);
		this.logAddress = address;
		this.serverPort = port;
		this.userInfo = userInfo;
		jLabel5.setBounds(new Rectangle(41, 165, 61, 15));
		jLabel6.setHorizontalAlignment(SwingConstants.RIGHT);
		try{
			jbInit();
			makeIcon();
			iconScrollPane.getViewport().add(iconPane);
			showInfo();
			pack();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
		
	public UpdateDialog(){
		this(new Frame(), "UpdateDialog", false, null, 0, null);
	}
	
	//这个方法用来获取用户的信息，并显示在修改面板上
	public void showInfo(){
		userName.setText(userInfo.getName());
		address.setText(userInfo.getPlace());
		email.setText(userInfo.getEmail());
		imageLabel.setIcon(new ImageIcon(userInfo.getPic()));
		introduceMe.setText(userInfo.getInfo());
		String sex = userInfo.getSex();
		password.setText(userInfo.getPassword());
		configPassword.setText(userInfo.getPassword());
		if(sex.equals("男")){
			men.setSelected(true);
		}else{
			women.setSelected(true);
		}
		String birth  = userInfo.getBirthday();
		String yearBirth  = birth.substring(0, birth.indexOf("-"));
		String monthBirth  = birth.substring(birth.indexOf("-")+1, birth.length());
		yearModel.setSelectedItem(yearBirth);
		monthModel.setSelectedItem(monthBirth);
	}
	
	//这个方法从文件中读取图像的路径，并创建图像
	private void makeIcon(){
		String path = "imag" + file_separate + "face";
		try{
			RandomAccessFile file = new RandomAccessFile(path+file_separate+"face.ini", "r");
			long fileLongth = file.length();
			System.out.println("fileLongth" + fileLongth);
			long filePointer = 0;
			JLabel[] iconLabel = new JLabel[85];
			int i = 0;
			while(filePointer < fileLongth){
				iconLabel[i] = new JLabel(new ImageIcon(new String(path +
						file_separate + file.readLine())));
				iconLabel[i].addMouseListener(new MouseAdapter() {
					public void mousePressed(MouseEvent e){
						String iconInfo = e.toString();
						int beginIndex = iconInfo.indexOf("image" + file_separate + "face");
						int endIndex = iconInfo.lastIndexOf("-1.jpg");
						imagePath = iconInfo.substring(beginIndex, endIndex+6);
						imageLabel.setIcon(new ImageIcon(imagePath));
					}
				});
				iconPane.add(iconLabel[i]);
				i += 1;
				filePointer = file.getFilePointer();
			}
			file.close();
		}catch(IOException ex){
			ex.printStackTrace();
		}
	}
	
	//布置控件
	private void jbInit() throws Exception{
		border2 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white,
				new Color(164, 163, 165)));
		panel1.setLayout(borderLayout2);
		jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel1.setText("用户名");
		jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel2.setText("新密码");
		jLabel3.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel3.setText("确认密码");
		jLabel4.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel4.setText("性别");
		jLabel5.setBounds(new Rectangle(24, 165, 78, 15));
		jLabel6.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel5.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel5.setText("出生日期");
		jLabel6.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel6.setText("籍贯");
		jLabel7.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel7.setText("邮箱");
		jLabel8.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel8.setText("头像");
		jLabel9.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel9.setText("自我介绍");
		jPanel1.setBorder(border2);
		jPanel1.setLayout(xYLayout1);
		men.setSelected(true);
		men.setText("男");
		//men.addItemListener(new UpdateDialog_radioButton_itemAdapter(this));
		men.addItemListener(this);
		women.setSelected(false);
		women.setText("女");
		//women.addItemListener(new UpdateDialog_radioButton_itemAdapter(this));
		women.addItemListener(this);
		jLabel10.setText("年");
		jLabel11.setText("月");
		jPanel2.setLayout(flowLayout1);
		reset.setText("取消");
		//reset.addActionListener(new UpdateDialog_reset_itemAdapter(this));
		reset.addActionListener(this);
		submit.setText("修改");
		//submit.addActionListener(new UpdateDialog_submit_itemAdapter(this));
		submit.addActionListener(this);
		imageLabel.setIcon(defaultIcon);
		this.getContentPane().setLayout(borderLayout1);
		jPanel2.setBorder(BorderFactory.createEtchedBorder());
		flowLayout1.setHgap(50);
		iconScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		this.getContentPane().add(panel1, java.awt.BorderLayout.CENTER);
		jPanel2.add(submit, null);
		jPanel2.add(reset, null);
		panel1.add(jPanel1, java.awt.BorderLayout.CENTER);
		panel1.add(jPanel2, java.awt.BorderLayout.SOUTH);
		for(int i = 1950; i < Calendar.getInstance().get(Calendar.YEAR); i++){
			yearModel.addElement(i);
		}
		for(int j = 1; j < 12; j++){
			monthModel.addElement(j);
		}
		year.setModel((ComboBoxModel) yearModel);
		month.setModel((ComboBoxModel) monthModel);
		pictureList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		this.setResizable(false);
		group.add(men);
		group.add(women);
		jPanel1.add(email, new XYConstraints(115, 219, 120, -1));
		jPanel1.add(jLabel7, new XYConstraints(35, 221, 61, -1));
		jPanel1.add(address, new XYConstraints(115, 180, 121, -1));
		jPanel1.add(jLabel6, new XYConstraints(35, 180, 61, -1));
		jPanel1.add(jLabel5, new XYConstraints(15, 146, 81, -1));
		jPanel1.add(jLabel11, new XYConstraints(295, 145, 21, -1));
		jPanel1.add(month, new XYConstraints(222, 141, 66, -1));
		jPanel1.add(jLabel10, new XYConstraints(198, 145, 19, -1));
		jPanel1.add(year, new XYConstraints(115, 141, 77, -1));
		jPanel1.add(women, new XYConstraints(168, 111, 40, -1));
		jPanel1.add(men, new XYConstraints(115, 111, 46, -1));
		jPanel1.add(jLabel3, new XYConstraints(18, 82, 78, -1));
		jPanel1.add(jLabel2, new XYConstraints(35, 50, 61, -1));
		jPanel1.add(jLabel1, new XYConstraints(35, 17, 61, -1));
		jPanel1.add(userName, new XYConstraints(115, 12, 120, -1));
		jPanel1.add(jLabel4, new XYConstraints(19, 114, 77, -1));
		jPanel1.add(jLabel9, new XYConstraints(23, 339, 73, -1));
		jPanel1.add(jLabel8, new XYConstraints(35, 261, 61, -1));
		jPanel1.add(iconScrollPane, new XYConstraints(180, 260, 218, 58));
		iconScrollPane.getViewport().add(pictureList);
		jPanel1.add(imageLabel, new XYConstraints(116, 261, 56, 42));
		jPanel1.add(jScrollPane1, new XYConstraints(114, 338, 284, 58));
		jScrollPane1.getViewport().add(introduceMe);
		jPanel1.add(password, new XYConstraints(115, 47, 120, 20));
		jPanel1.add(configPassword, new XYConstraints(115, 80, 120, 20));
	}
	
	//处理用户单击提交按钮事件
	public void submit_actionPerformed(ActionEvent e){
		String name  = userName.getText().trim();
		String passwordInfo = new String(password.getPassword()).trim();
		String configPasswordInfo = new String(configPassword.getPassword()).trim();
		String info  =introduceMe.getText().trim();
		String sexInfo = sex;
		String birthday = year.getSelectedItem().toString() + "-" + 
				month.getSelectedItem().toString();
		String place = address.getText().trim();
		String emailInfo = email.getText().trim();
		String pic = imagePath;
		//下面进行错误检测
		int nameLength = name.length();
		int passwordLength = passwordInfo.length();
		if(name == null ||name.equals("")){
			JOptionPane.showMessageDialog(this, "用户名不能为空");
			userName.requestFocus();
		}else if(!passwordInfo.equals(configPassword)){
			JOptionPane.showInternalMessageDialog(this, "两次输入的密码不一致");
		}else if(nameLength > 12 || nameLength < 4){
			JOptionPane.showMessageDialog(this, "用户名的长度不在有效范围之类");
			userName.setText("");
			userName.requestFocus();
		}else if(passwordLength > 12 || passwordLength < 4){
			JOptionPane.showMessageDialog(this, "密码长度不在有效范围之类");
			password.setText("");
			configPassword.setText("");
			password.requestFocus();
		}else if(emailInfo == "" || emailInfo.indexOf('@') == -1 || 
				emailInfo.indexOf('.') == -1){
			JOptionPane.showMessageDialog(this, "请输入正确的e-mail地址");
			email.requestFocus();
		}else{
			updateOwnInfo(userInfo.getQqnum(), name, passwordInfo, info, pic, sexInfo,
					emailInfo, place, birthday);
		}
	}
	
	//向服务器提交信息
	public void updateOwnInfo(int qqnum, String name, String password, String info, 
			String pic, String sex, String email, String place, String birthday){
		String serverInfo = "";
		try{
			//定义套接字
			Socket socket = new Socket();
			//定义输入流
			DataInputStream in = new DataInputStream(socket.getInputStream());
			//定义输出流
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
			//向服务器发送注册用户的申请
			out.writeUTF("updateOwnInfo");
			//向服务器发送注册用户的信息
			out.writeUTF(new Integer(qqnum).toString());
			out.writeUTF(name);
			out.writeUTF(password);
			out.writeUTF(info);
			out.writeUTF(pic);
			out.writeUTF(sex);
			out.writeUTF(email);
			out.writeUTF(place);
			out.writeUTF(birthday);
			//读取用户注册的QQ号码
			serverInfo = in.readUTF();
			if(serverInfo.equals("updateOver")){
				userInfo.setName(name);
				userInfo.setPassword(password);
				userInfo.setInfo(info);
				userInfo.setPic(pic);
				userInfo.setBirthday(birthday);
				userInfo.setEmail(email);
				userInfo.setPlace(place);
				JOptionPane.showMessageDialog(this, "更新成功！");
				this.dispose();
			}else{
				JOptionPane.showMessageDialog(this, "更新失败！");
			}
		}catch(IOException ex){
			ex.printStackTrace();
		}
	}
	
	//处理重置按钮单击事件
	public void reset_actionPerformed(ActionEvent e){
		this.dispose();
	}
	
	//处理单选按钮改变事件
	public void radioButton_itemStateChange(ItemEvent e){
		if(men.isSelected()){
			sex = "男"; 
		}else if(women.isSelected()){
			sex = "女";
		}
	}
	
	//统一响应按钮事件，并调用对应的处理方法
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == reset){
			reset_actionPerformed(e);
		}else if(e.getSource() == submit){
			submit_actionPerformed(e);
		}
	}
	
	//响应单选按钮事件
	public void itemStateChanged(ItemEvent e){
		radioButton_itemStateChange(e);
	}
}
