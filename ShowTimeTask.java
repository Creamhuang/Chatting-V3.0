package simulateqq;
/*
 * 显示时间的公用类
 * ShowTimeTask类是一个定时器的任务类，用于刷新并显示当前时间*/
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
		showTime.setText("现在时间：" + timeInfo + " ");
	}
}
