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
import javax.swing.SwingConstants;

import com.borland.jbcl.layout.XYConstraints;
import com.borland.jbcl.layout.XYLayout;

public class MyInfo_AboutBox extends JDialog implements ActionListener{
	JPanel panel1 = new JPanel();
	JPanel insetsPanel1 = new JPanel();
	JButton button1 = new JButton();
	ImageIcon image1 = new ImageIcon();
	BorderLayout boederLayout1 = new BorderLayout();
	JPanel jPanel1 = new JPanel();
	JLabel jLabel1 = new JLabel();
	XYLayout xYLayout1 = new XYLayout();
	JLabel jLabel2 = new JLabel();
	JLabel jLabel3 = new JLabel();
	JLabel jLabel4 = new JLabel();
	JLabel jLabel5 = new JLabel();
	JLabel jLabel6= new JLabel();
	JLabel jLabel7 = new JLabel();
	JLabel jLabel8 = new JLabel();
	JLabel jLabel9 = new JLabel();
	JLabel jLabel10 = new JLabel();
	JLabel jLabel11 = new JLabel();
	
	public MyInfo_AboutBox(Frame parent){
		super(parent);
		try{
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			jbInit();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public MyInfo_AboutBox(){
		this(null);
	}
	
	private void jbInit() throws Exception{
		image1 = new ImageIcon(simulateqq.ServerFrame.class.getResource("about.png"));
		setTitle("About");
		panel1.setLayout(boederLayout1);
		button1.setForeground(Color.blue);
		button1.setText("ȷ��");
		button1.addActionListener(this);
		jLabel1.setForeground(Color.blue);
		jLabel1.setText("����һ��ģ��QQ�ļ�ʱͨѶ���");
		jPanel1.setLayout(xYLayout1);
		jLabel2.setForeground(Color.blue);
		jLabel2.setText("�������ߵ�ʱ���ˮƽ���ޣ�ֻ");
		jLabel3.setForeground(Color.blue);
		jLabel3.setText("ʵ����һ���ֹ��ܣ���ӭ������ָ����");
		jLabel4.setForeground(Color.blue);
		jLabel4.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel4.setText("���ߣ�");
		jLabel5.setForeground(Color.blue);
		jLabel5.setText("�³�  ����");
		jLabel6.setForeground(Color.blue);
		jLabel6.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel6.setText("���ʱ��");
		jLabel7.setForeground(Color.blue);
		jLabel7.setText("2016��6��20��");
		jLabel8.setForeground(Color.blue);
		jLabel8.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel8.setText("Email");
		jLabel9.setForeground(Color.blue);
		jLabel9.setText("hgl_stephanie@foxmial.com");
		jLabel10.setForeground(Color.blue);
		jLabel10.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel10.setText("������������а�Ȩ");
		jLabel11.setForeground(Color.blue);
		jLabel11.setText("����ת�أ�����������ϵ");
		jPanel1.setBorder(BorderFactory.createEtchedBorder());
		getContentPane().add(panel1, null);
		insetsPanel1.add(button1, null);
		panel1.add(jPanel1, java.awt.BorderLayout.CENTER);
		panel1.add(insetsPanel1, BorderLayout.SOUTH);
		jPanel1.add(jLabel1, new XYConstraints(45, 24, 96, -1));
		jPanel1.add(jLabel10, new XYConstraints(132, 215, 45, 22));
		jPanel1.add(jLabel8, new XYConstraints(122, 188, 56, 22));
		jPanel1.add(jLabel6, new XYConstraints(133, 159, 46, 24));
		jPanel1.add(jLabel4, new XYConstraints(134, 127, 45, 26));
		jPanel1.add(jLabel3, new XYConstraints(45, 86, 320, 25));
		jPanel1.add(jLabel2, new XYConstraints(63, 52, 303, 25));
		jPanel1.add(jLabel11, new XYConstraints(187, 215, 140, 22));
		jPanel1.add(jLabel9, new XYConstraints(187, 188, 141, 22));
		jPanel1.add(jLabel5, new XYConstraints(187, 127, 70, 26));
		jPanel1.add(jLabel7, new XYConstraints(187, 159, 103, 24));
		setResizable(false);
	}
	
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == button1)
			dispose();
	}
}
