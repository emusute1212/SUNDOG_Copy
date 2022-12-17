package io.sundog.copy.ui;

import io.sundog.copy.manager.GovernCopy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * アクションリスナーを制御するクラス
 * @author SUNDOG
 *
 */
public class ActionController implements ActionListener {
	static TopGUI top;
	static SettingGUIPanel setting;
	static AboutGUI about;

	public ActionController(TopGUI top){
		ActionController.top=top;
	}

	public ActionController(SettingGUIPanel setting){
		ActionController.setting=setting;
	}

	public ActionController(AboutGUI about){
		ActionController.about=about;
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		GovernCopy copy=new GovernCopy();
		String command=ae.getActionCommand();
		String delete[]=command.split(",");

		for(int i=0;i<TopGUI.copyText[1].length;i++){
			if(command==TopGUI.copyText[1][i]){
				copy.doCopy(command);
			}
		}

		if(command==TopGUI.MOVE_SETTING_GUI){
			SettingGUI setting = new SettingGUI("設定");
			top.setVisible(false);
			setting.init();
		}

		if(command==SettingGUIPanel.MOVE_ABOUT_GUI){
			AboutGUI about = new AboutGUI("更新の確認");
			about.init();
		}

		if(command == TopGUI.TOP_WINDOW_CHECKBOX){
			top.topWindow();
		}


		if(delete[0].equals("delete")){
			for(int i=0;i<TopGUI.copyText[0].length;i++){
				TopGUI.copyText[0][i]=setting.textBoxButton[i].getText();
				TopGUI.copyText[1][i]=setting.textBoxContent[i].getText();
			}

			int delateIndex=Integer.valueOf(delete[1]);
			top.delateData(delateIndex);

			setting.transitionPanel();
		}

		if(command==SettingGUIPanel.ADD_COMMAND){
			for(int i=0;i<TopGUI.copyText[0].length;i++){
				TopGUI.copyText[0][i]=setting.textBoxButton[i].getText();
				TopGUI.copyText[1][i]=setting.textBoxContent[i].getText();
			}

			top.addDate();
			setting.transitionPanel();
		}

		if(command==SettingGUIPanel.SAVE_COMMAND){
			boolean flag=false;
			for(int i=0;i<TopGUI.copyText[0].length;i++){
				TopGUI.copyText[0][i]=setting.textBoxButton[i].getText();
				TopGUI.copyText[1][i]=setting.textBoxContent[i].getText();

				if((TopGUI.copyText[0][i].length()==0 || TopGUI.copyText[1][i].length()==0) && !flag){
					JOptionPane.showMessageDialog(null, new SettingWindow()
							.makeLabel("入力されていない箇所があるので、保存できません。",
							new Font("メイリオ", Font.PLAIN, 12)), "メッセージ",
							JOptionPane.ERROR_MESSAGE);
					flag=true;
				}
			}

			if(!flag){
				ControlCSV csv=new ControlCSV();
				csv.saveCSV(TopGUI.copyText,"data.csv");

				JOptionPane.showMessageDialog(null, new SettingWindow()
				.makeLabel("保存しました。",
						new Font("メイリオ", Font.PLAIN, 12)), "メッセージ",
				JOptionPane.INFORMATION_MESSAGE);

				setting.closeWindow();
				top=new TopGUI("SUNDOG Copy");
				top.init();
			}
		}

		if(command == AboutGUI.UP_DATE){
			Desktop browser = Desktop.getDesktop();
			String uriString = "http://contents.webcrow.jp/sundog/copy/update/shichimi/";
			try {
				URI uri = new URI(uriString);
				browser.browse(uri);
			} catch (URISyntaxException | IOException e) {
				e.printStackTrace();
			}
        }
	}

}
