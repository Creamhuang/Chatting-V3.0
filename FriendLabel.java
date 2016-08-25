package simulateqq;
/*
 * 实现头像显示功能的公用类*/
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
	
	//实现ListCellRexderer接口的方法
	public Component getListCellRendererComponent(JList list, Object value, int index, 
			boolean isSelected, boolean cellHasFocus){
		String s = value.toString();
		int beginIndex = s.indexOf("*");
		//在线用户的头像
		String picURL = s.substring(beginIndex + 1, s.length());
		//离线用户的头像
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
		//如果拥有焦点，绘制凹凸的边框
		if(cellHasFocus){
			this.setBorder(lineBorder);
		}else{
			this.setBorder(emptyBorder);
			this.setForeground(list.getForeground());
		}
		//如果好友不在线，应将其背景设置为灰色
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
		this.setFont(new java.awt.Font("微软雅黑", Font.PLAIN, 15));
	}
}
