package simulateqq;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import com.borland.jbcl.layout.XYConstraints;
import com.borland.jbcl.layout.XYLayout;

public class FindUserInfo extends JDialog implements ActionListener{
	JPanel panel1 = new JPanel();
	XYLayout xYLayout1 = new XYLayout();
	JLabel jLabel1 = new JLabel();
	JLabel jLabel2 = new JLabel();
	JLabel jLabel3 = new JLabel();
	JLabel jLabel4 = new JLabel();
	JLabel jLabel5 = new JLabel();
	JLabel jLabel6 = new JLabel();
	JLabel jLabel7 = new JLabel();
	JLabel jLabel8 = new JLabel();
	JLabel jLabel9 = new JLabel();
	JLabel qqnum = new JLabel();
	JLabel ip = new JLabel();
	JLabel email = new JLabel();
	JLabel pic = new JLabel();
	JLabel sex = new JLabel();
	JLabel name = new JLabel();
	JScrollPane jScrollPane1 = new JScrollPane();
	JTextArea info = new JTextArea();
	JLabel birth = new JLabel();
	JLabel address = new JLabel();
	TitledBorder titledBorder1 = new TitledBorder("");
	Border border1 = BorderFactory.createEtchedBorder(Color.white, new Color(165, 163, 151));
	Border border2 = new TitledBorder(border1, "用户基本信息如下：");
	UserInfoBean userInfo = null;
	ClientManageFrame father = null;
	JButton addFriendButton = new JButton();
	BorderLayout borderLayout1 = new BorderLayout();
	
	public FindUserInfo(Frame owner, String title, boolean modal, UserInfoBean userInfo, 
			ClientManageFrame father){
		super(owner, title, modal);
		this.userInfo = userInfo;
		this.father = father;
		try{
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			jbInit();
			pack();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public FindUserInfo(){
		this(new Frame(), "FindUserInfo", false, null, null);
	}
	
	private void jbInit() throws Exception{
		panel1.setLayout(xYLayout1);
		jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel1.setText("QQ号：");
		jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel2.setText("用户名：");
		jLabel4.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel4.setText("IP地址：");
		jLabel5.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel5.setText("性别：");
		jLabel6.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel6.setText("E-MAIL：");
		jLabel7.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel7.setText("籍贯：");
		jLabel8.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel8.setText("出生年月：");
		jLabel9.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel9.setText("自我介绍");
		jLabel3.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel3.setText("头像：");
		this.getContentPane().setLayout(borderLayout1);
		addFriendButton.setText("加为好友");
		addFriendButton.addActionListener(this);
		panel1.add(jLabel3, new XYConstraints(34, 138, 57, -1));
		panel1.add(jLabel5, new XYConstraints(33, 179, 58, -1));
		panel1.add(jLabel4, new XYConstraints(33, 68, 58, -1));
		panel1.add(jLabel2, new XYConstraints(21, 16, 70, -1));
		panel1.add(jLabel7, new XYConstraints(26, 213, 65, -1));
		panel1.add(jLabel1, new XYConstraints(28, 42, 63, 15));
		panel1.add(jLabel6, new XYConstraints(33, 104, 58, -1));
		panel1.add(jLabel8, new XYConstraints(19, 246, 72, -1));
		panel1.add(jLabel9, new XYConstraints(25, 274, 66, -1));
		panel1.add(qqnum, new XYConstraints(111, 42, 200, -1));
		panel1.add(name, new XYConstraints(111, 12, 200, -1));
		panel1.add(ip, new XYConstraints(111, 68, 200, -1));
		panel1.add(email, new XYConstraints(111, 98, 200, 18));
		panel1.add(pic, new XYConstraints(111, 127, 200, 39));
		panel1.add(sex, new XYConstraints(111, 173, 200, 20));
		panel1.add(address, new XYConstraints(111, 207, 200, 18));
		panel1.add(birth, new XYConstraints(111, 238, 200, 20));
		panel1.add(jScrollPane1, new XYConstraints(111, 272, 200, 62));
		panel1.add(addFriendButton, new XYConstraints(129, 343, 90, -1));
		this.getContentPane().add(panel1, java.awt.BorderLayout.CENTER);
		jScrollPane1.getViewport().add(info);
		this.setResizable(false);
		this.setSize(340, 375);
	}
	
	//获取用户信息
	public void getInfo(){
		qqnum.setText(new Integer(userInfo.getQqnum()).toString());
		ip.setText(userInfo.getSex());
		email.setText(userInfo.getEmail());
		pic.setIcon(new ImageIcon(userInfo.getPic()));
		sex.setText(userInfo.getSex());
		name.setText(userInfo.getSex());
		info.setEditable(false);
		info.setText(userInfo.getInfo());
		birth.setText(userInfo.getBirthday());
		address.setText(userInfo.getPlace());
		panel1.setBorder(border2);
	}
	
	//响应单击按钮事件
	public void actionPerformed(ActionEvent e){
		//调用主窗口的方法添加好友
		father.addNewFriend();
		this.dispose();
	}
}

