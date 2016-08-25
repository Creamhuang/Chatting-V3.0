package simulateqq;
//�������������࣬���������������˵�������
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class StartServer {
	boolean packFrame = false;
	public StartServer() {
		ServerFrame frame = new ServerFrame();
		if (packFrame) {
			frame.pack();
		}
		else {
			frame.validate();
		}
		
		//��������
		//��ȡ��Ļ�Ĵ�С
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		//��ȡ�����ڵĴ�С
		Dimension frameSize = frame.getSize();
		if(frameSize.height > screenSize.height)
			frameSize.height = screenSize.height;
		if(frameSize.width > screenSize.width)
			frameSize.width = screenSize.width;
		
		//���㴰��λ��
		frame.setLocation((screenSize.width - frameSize.width)/2, 
				(screenSize.height - frameSize.height)/2);
		frame.pack();
		frame.setVisible(true);
	}
	
	public void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (Exception e) {
					e.printStackTrace();
				}
				new StartServer();
			}
		});
	}
}

/*
 * SwingUtilities���invokelatre���������������¼��ɷ��߳������ض����롣
 * ��������Ҫ���еĴ���ŵ�һ��Runnaable�����run()�����У�
 * ������Runable������ΪinvokeLater()�����Ĳ�����
 * invokeLater()�������������أ����ȴ��¼��ɷ��߳�ָ��ִ�д��롣
 * */
 