package simulateqq;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.JDialog;
import javax.swing.JFrame;

public class SetCenter {
	public SetCenter(){}
	
	//该方法用于将窗口设置在屏幕的中央
	public static void setScreenCenter(JFrame frame){
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = frame.getSize();
		if(frameSize.height > screenSize.height){
			frameSize.height = screenSize.height;
		}
		if(frameSize.width > screenSize.width){
			frameSize.width = screenSize.width;
		}
		frame.setLocation((screenSize.width - frameSize.width)/2, 
				(screenSize.height - frameSize.height)/2);
	}
	
	//该方法将对话框设置在其父窗口的中央
	public static void setDialogCenter(JFrame frame, JDialog dlg){
		Dimension dlgSize = dlg.getPreferredSize();
		Dimension frmSize = frame.getSize();
		Point loc = frame.getLocation();
		dlg.setLocation((frmSize.width - dlgSize.width)/2 + loc.x, 
				(frmSize.height - dlgSize.height)/2 +loc.y);
	}
	
	//该方法用于将窗口设置在其父窗口的中央
	public static void setFrameCenter(JFrame frame, JFrame subframe){
		Dimension subSize = subframe.getPreferredSize();
		Dimension frmSize = frame.getSize();
		Point loc = frame.getLocation();
		subframe.setLocation((frmSize.width - subSize.width)/2 + loc.x, 
				(frmSize.height - subSize.height)/2 +loc.y);
	}
}
