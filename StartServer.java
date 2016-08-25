package simulateqq;
//服务器的启动类，用于启动服务器端的主程序
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
		
		//创建窗口
		//获取屏幕的大小
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		//获取主窗口的大小
		Dimension frameSize = frame.getSize();
		if(frameSize.height > screenSize.height)
			frameSize.height = screenSize.height;
		if(frameSize.width > screenSize.width)
			frameSize.width = screenSize.width;
		
		//计算窗口位置
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
 * SwingUtilities类的invokelatre方法，用于请求事件派发线程运行特定代码。
 * 程序必须把要运行的代码放到一个Runnaable对象的run()方法中，
 * 并将此Runable对象作为invokeLater()方法的参数。
 * invokeLater()方法会立即返回，不等待事件派发线程指定执行代码。
 * */
 