package simulateqq;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

public class FindUserDlg extends JDialog implements ActionListener{
	JPanel panel1 = new JPanel();
	JLabel infoLabel = new JLabel();
	JTextField qqnumField = new JTextField();
	JButton findButton = new JButton();
	JPanel findPane = new JPanel();
	Border border1 = BorderFactory.createLineBorder(UIManager.getColor(
			"InternalFrame.inactiveTitleBackground"), 1);
	FlowLayout flowLayout1 = new FlowLayout();
	ClientManageFrame father = null;
	BorderLayout boederLayout1 = new BorderLayout();
	//���췽��
	public FindUserDlg(JFrame owner, String title, boolean modal, ClientManageFrame father){
		super(owner, title, modal);
		this.father = father;
		try{
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			jbInit();
			pack();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public FindUserDlg(){
		this(new JFrame(), "FindUserDlg", false, null);
	}
	
	//���ÿؼ�
	private void jbInit() throws Exception{
		border1 = new TitledBorder(BorderFactory.createLineBorder(
				new Color(122, 150, 223), 1), "�����û�");
		panel1.setLayout(boederLayout1);
		infoLabel.setText("��������Ҫ���ҵ�QQ�ţ�");
		qqnumField.setColumns(0);
		qqnumField.addActionListener(this);
		findButton.setText("����");
		findButton.addActionListener(this);
		findPane.setLayout(flowLayout1);
		findPane.setBorder(border1);
		panel1.setBorder(null);
		getContentPane().add(panel1);
		findPane.add(infoLabel, null);
		findPane.add(qqnumField, null);
		findPane.add(findButton, null);
		panel1.add(findPane, java.awt.BorderLayout.CENTER);
		pack();
	}
	
	//�ж������Ƿ�Ϊ����
	public boolean isNum(String num){
		boolean error = false;
		try{
			Integer.parseInt(num);//�������׳��쳣
		}catch(Exception e){
			error = true;
		}
		if(error){
			return false;//��ʾ������
		}else{
			return true;//��ʾ����
		}
	}
	
	//�����û��������Ұ�ť
	public void findButton_actionPerformed(ActionEvent e){
		String info = qqnumField.getText().trim();
		if(info.equals("")||info == null){
			JOptionPane.showMessageDialog(this, "��������Ҫ���ҵ��û���QQ��");
		}else if(isNum(info)){
			this.setVisible(false);
			//���������ڵĲ��ҷ���
			father.findUser(Integer.parseInt(info));
		}else{
			JOptionPane.showMessageDialog(this, "�Բ����������QQ��������");
		}
	}
	
	//�����û���������лس��¼�
	public void qqnumField_actionPerformed(ActionEvent e){
		findButton.doClick();
	}
	
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == qqnumField)
			qqnumField_actionPerformed(e);
		else if(e.getSource() == findButton)
			findButton_actionPerformed(e);
	}
}
