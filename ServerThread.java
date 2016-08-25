package simulateqq;

/*
 * ServerThread����һ���߳��࣬��������ServerSocket�׽��ֵȴ��ͻ��˵����ӣ���Ҫ������¹���:
 * 1.���ļ�dbProperties.txt�ļ��ж�ȡԤ���趨�õĶ˿ںţ��Դ���ServerSocket;
 * 2.�ڷ����������������ʾ�û���������Ϣ��*/
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
	
	//�������ļ��ж�ȡ�˿ں�
	private int getPort(){
		int port = 0;
		Properties p =new Properties();
		String file_separator = System.getProperty("file.separator");
		try{
			FileInputStream in = new FileInputStream("property"+file_separator+
					"dbProperties.txt");
			p.load(in);//���������ж�ȡ�����б�
			port = Integer.parseInt(p.getProperty("tcp.ip.port"));
			in.close();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return port;
	}
	
	//��ͣ����
	public void pauseThread(){
		this.flag = false;
	}
	
	//�ָ�����
	public void reStartThread(){
		this.flag = true;
	}
	
	//�߳��е�������
	public void run(){
		try{
			ServerSocket s = new ServerSocket(getPort());
			area.append("���ڵȴ��ͻ�������......"+line_separator);
			area.append(line_separator);
			
			//ѭ������ͻ��˵�����
			while(flag){
				System.out.println("������"+flag);
				//�����ͻ��˵���������
				Socket socket = new Socket();
				area.append("************" + line_separator);
				area.append("Connection accept:" + socket +line_separator);
				Date time = new java.util.Date();
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
				String timeInfo = format.format(time);
				area.append("����ʱ�䣺" + timeInfo + line_separator);
				area.append("************" + line_separator);
				//�����׽�����ͻ���ͨ��
				new Thread(new Server(socket)).start();
			}
		}catch(IOException ex){
			ex.printStackTrace();
		}
	}
}

