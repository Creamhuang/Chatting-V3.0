package simulateqq;

/*
 * ServerThread类是一个线程类，它会启动ServerSocket套接字等待客户端的连接，主要完成以下功能:
 * 1.从文件dbProperties.txt文件中读取预先设定好的端口号，以创建ServerSocket;
 * 2.在服务器控制面板中显示用户的请求信息。*/
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.swing.JTextArea;
import javax.xml.crypto.Data;

public class ServerThread extends Thread{
	JTextArea area = null;
	Boolean flag = true;
	String line_separator = System.getProperty("line separator");
	
	public void serverThread(JTextArea area){
		this.area = area;
	} 
	
	//从属性文件中读取端口号
	private int getPort(){
		int port = 0;
		Properties p =new Properties();
		String file_separator = System.getProperty("file.separator");
		try{
			FileInputStream in = new FileInputStream("property"+file_separator+
					"dbProperties.txt");
			p.load(in);//从数据流中读取属性列表
			port = Integer.parseInt(p.getProperty("tcp.ip.port"));
			in.close();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return port;
	}
	
	//暂停服务
	public void pauseThread(){
		this.flag = false;
	}
	
	//恢复服务
	public void reStartThread(){
		this.flag = true;
	}
	
	//线程中的主方法
	public void run(){
		try{
			ServerSocket s = new ServerSocket(getPort());
			area.append("正在等待客户的请求......"+line_separator);
			area.append(line_separator);
			
			//循环处理客户端的连接
			while(flag){
				System.out.println("服务器"+flag);
				//监听客户端的连接请求
				Socket socket = new Socket();
				area.append("************" + line_separator);
				area.append("Connection accept:" + socket +line_separator);
				Date time = new java.util.Date();
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
				String timeInfo = format.format(time);
				area.append("处理时间：" + timeInfo + line_separator);
				area.append("************" + line_separator);
				//创建套接字与客户端通信
				new Thread(new Server(socket)).start();
			}
		}catch(IOException ex){
			ex.printStackTrace();
		}
	}
}

