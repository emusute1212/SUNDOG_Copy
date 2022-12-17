package io.sundog.copy.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class SettingGUI extends JFrame implements WindowListener {
	SettingGUIPanel sgp;
	private String[][] checkCopyData;

	public SettingGUI(String title) {
		super(title);
	}

	public void init() {
		SettingWindow window = new SettingWindow();
		checkCopyData = new String[TopGUI.copyText.length][TopGUI.copyText[0].length];
		for(int i=0;i<TopGUI.copyText[0].length;i++){
			checkCopyData[0][i] = TopGUI.copyText[0][i];
			checkCopyData[1][i] = TopGUI.copyText[1][i];
		}
		window.makeWindow(this, 500, 400);
		sgp = new SettingGUIPanel(this);
		this.add(sgp);
		sgp.setVisible(true);
		this.addWindowListener(this);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
	}

	public void PanelChange() {
		sgp.setVisible(false);
		sgp = new SettingGUIPanel(this);
		this.add(sgp);
		sgp.setVisible(true);
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		boolean flag=false;
		for(int i=0;i<TopGUI.copyText[0].length;i++){
			TopGUI.copyText[0][i]=ActionController.setting.textBoxButton[i].getText();
			TopGUI.copyText[1][i]=ActionController.setting.textBoxContent[i].getText();

			if((TopGUI.copyText[0][i].length()==0 || TopGUI.copyText[1][i].length()==0) && !flag){
				int check = JOptionPane
						.showConfirmDialog(
								null,
								new SettingWindow()
										.makeLabel(
												"<html>入力されていない箇所があります。<br>設定画面に戻りますか？<br><br>「いいえ」をクリックすると変更を破棄して設定画面を閉じます。</html>",
												new Font("メイリオ", Font.PLAIN, 12)),
								"確認", JOptionPane.YES_NO_CANCEL_OPTION);
				if (check == 1) {
					this.setVisible(false);
					TopGUI top = new TopGUI("SUNDOG Copy");
					top.init();
				}
				flag=true;
			}
		}
		if(!flag){
			boolean twoDimensionFlag=false;
			if(checkCopyData[0].length!=TopGUI.copyText[0].length || checkCopyData[0].length!=TopGUI.copyText[0].length){
				twoDimensionFlag=true;
			}else{
				for(int i=0;i<TopGUI.copyText[0].length;i++){
					if(!(checkCopyData[0][i].equals(TopGUI.copyText[0][i])) || !(checkCopyData[1][i].equals(TopGUI.copyText[1][i]))){
						twoDimensionFlag=true;
					}
				}
			}
			if (twoDimensionFlag) {
				int check = JOptionPane
						.showConfirmDialog(
								null,
								new SettingWindow()
										.makeLabel(
												"<html>変更を保存せずに設定画面を閉じようとしています。<br>変更を保存しますか？<br><br>変更内容を保存するには「はい」をクリックします。</html>",
												new Font("メイリオ", Font.PLAIN, 12)),
								"確認", JOptionPane.YES_NO_CANCEL_OPTION);
				if (check == 0) {
					new ControlCSV().saveCSV(TopGUI.copyText,"data.csv");
					this.setVisible(false);
					TopGUI top = new TopGUI("SUNDOG Copy");
					top.init();
				} else if (check == 1) {
					this.setVisible(false);
					TopGUI top = new TopGUI("SUNDOG Copy");
					top.init();
				}
			}else{
				this.setVisible(false);
				TopGUI top = new TopGUI("SUNDOG Copy");
				top.init();
			}
		}
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
	}
}
