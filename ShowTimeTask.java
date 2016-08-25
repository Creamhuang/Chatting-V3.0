package simulateqq;
/*
 * ��ʾʱ��Ĺ�����
 * ShowTimeTask����һ����ʱ���������࣬����ˢ�²���ʾ��ǰʱ��*/
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JLabel;

public class ShowTimeTask extends java.util.TimerTask{
	private JLabel showTime = null;
	
	ShowTimeTask(JLabel showTime){
		this.showTime = showTime;
	}
	
	public void run(){
		Date time = new java.util.Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
		String timeInfo = format.format(time);
		showTime.setText("����ʱ�䣺" + timeInfo + " ");
	}
}
