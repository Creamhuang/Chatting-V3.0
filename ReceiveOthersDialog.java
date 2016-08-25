package simulateqq;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import com.borland.jbcl.layout.XYConstraints;
import com.borland.jbcl.layout.XYLayout;

public class ReceiveOthersDialog extends JDialog implements ActionListener{
	JPanel panel1 = new JPanel();
	XYLayout xYLayout1 = new XYLayout();
	JLabel jLabel1 = new JLabel();
	JLabel jLabel2 = new JLabel();
	JLabel name = new JLabel();
	JLabel qqnum = new JLabel();
	JLabel jLabel3 = new JLabel();
	JButton OKButton = new JButton();
	JLabel jLabel4 = new JLabel();
	JScrollPane jScrollPane1 = new JScrollPane();
	JTextArea infoArea  = new JTextArea();
	int o_qqnum = 0;
	String o_name = "";
	String o_info = "";
	
	public ReceiveOthersDialog(Frame owner, String title, boolean modal, 
			int qqnum, String name, String info){
		super(owner, title, modal);
		this.o_qqnum = qqnum;
		this.o_name = name;
		this.o_info = info;
		try{
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			jbInit();
			this.setSize(375, 245);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public ReceiveOthersDialog(){
		this(new Frame(), "ReceiveOthersDialog", false, 0, "", "");
	}
	
	//布置控件
	private void jbInit() throws Exception{
		panel1.setLayout(xYLayout1);
		jLabel1.setFont(new java.awt.Font("微软雅黑", Font.PLAIN, 14));
		jLabel1.setForeground(Color.blue);
		jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel1.setText("收到陌生人消息");
		jLabel2.setForeground(Color.blue);
		jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel2.setText("名称");
		jLabel3.setForeground(Color.blue);
		jLabel3.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel3.setText("QQ号");
		name.setForeground(Color.blue);
		name.setBorder(BorderFactory.createEtchedBorder());
		qqnum.setForeground(Color.blue);
		qqnum.setBorder(BorderFactory.createEtchedBorder());
		OKButton.setForeground(Color.blue);
		OKButton.setText("确定");
		OKButton.addActionListener(this);
		jLabel4.setForeground(Color.blue);
		jLabel4.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel4.setText("消息");
		jScrollPane1.setBorder(BorderFactory.createEtchedBorder());
		getContentPane().add(panel1);
		panel1.add(name, new XYConstraints(97, 57, 90, 22));
		panel1.add(qqnum, new XYConstraints(250, 57, 80, 22));
		panel1.add(jLabel3, new XYConstraints(193, 57, 45, 22));
		panel1.add(jLabel2, new XYConstraints(15, 58, 61, 21));
		panel1.add(jLabel4, new XYConstraints(29, 101, 47, 23));
		panel1.add(jScrollPane1, new XYConstraints(96, 101, 231, 83));
		panel1.add(OKButton, new XYConstraints(143, 202, 84, 25));
		panel1.add(jLabel1, new XYConstraints(99, 19, 158, 25));
		jScrollPane1.getViewport().add(infoArea);
		qqnum.setText(new Integer(o_qqnum).toString());
		name.setText(o_name);
		infoArea.setText(o_info);
	}
	
	//响应按钮事件
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == OKButton)
			dispose();
	}
}
