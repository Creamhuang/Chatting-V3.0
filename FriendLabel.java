package simulateqq;
/*
 * ʵ��ͷ����ʾ���ܵĹ�����*/
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.border.Border;

public class FriendLabel extends JLabel implements ListCellRenderer{
	private Border lineBorder  = BorderFactory.createLineBorder(Color.blue, 1);
	private Border emptyBorder  = BorderFactory.createEmptyBorder(2, 2, 2, 2);
	
	public FriendLabel(){
		try{
			jbInit();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	//ʵ��ListCellRexderer�ӿڵķ���
	public Component getListCellRendererComponent(JList list, Object value, int index, 
			boolean isSelected, boolean cellHasFocus){
		String s = value.toString();
		int beginIndex = s.indexOf("*");
		//�����û���ͷ��
		String picURL = s.substring(beginIndex + 1, s.length());
		//�����û���ͷ��
		String offLinePicURL = picURL.substring(picURL.indexOf("-") + 2, picURL.length());
		int status = Integer.parseInt(s.substring(1, beginIndex));
		this.setText(s.substring(1, beginIndex));
		this.setIcon(new ImageIcon(picURL));
		if(isSelected){
			this.setBackground(list.getSelectionBackground());
			this.setForeground(list.getSelectionBackground());
		}else{
			this.setBackground(list.getBackground());
			this.setForeground(list.getBackground());
		}
		//���ӵ�н��㣬���ư�͹�ı߿�
		if(cellHasFocus){
			this.setBorder(lineBorder);
		}else{
			this.setBorder(emptyBorder);
			this.setForeground(list.getForeground());
		}
		//������Ѳ����ߣ�Ӧ���䱳������Ϊ��ɫ
		if(status == 0){
			this.setIcon(new ImageIcon(offLinePicURL));
		}else if(status == 1){
			this.setIcon(new ImageIcon(picURL));
		}
		this.setEnabled(list.isEnabled());
		this.setOpaque(true);
		return this;
	}
	
	public void jbInit() throws Exception{
		this.setFont(new java.awt.Font("΢���ź�", Font.PLAIN, 15));
	}
}
