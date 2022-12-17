package io.sundog.copy.ui;

import javax.swing.*;
import java.awt.*;

/**
 * GUI部品を作ったり、フレームを作成するクラス
 * @author SUNDOG
 *
 */
public class SettingWindow {

	/**
	 * ボタンを作るメソッド
	 * @param text ボタンに表示させたい文字
	 * @param font ボタンの文字のフォント
	 * @return JButton
	 */
	public JButton makeButton(String text,Font font){

		JButton button=new JButton(text);
		button.setFocusPainted(false);
		button.setFont(font);

		return button;
	}

	/**
	 * JLanelを作るメソッド
	 * @param text ラベルにしたい文字
	 * @param font ラベルの文字のフォント
	 * @return JLabel
	 */
	public JLabel makeLabel(String text,Font font){

		JLabel label=new JLabel(text);
		label.setFont(font);

		return label;
	}
	/**
	 * JLanelを作るメソッド
	 * @param text ラベルにしたい文字
	 * @param font ラベルの文字のフォント
	 * @param color ラベルの文字の色
	 * @return JLabel
	 */
	public JLabel makeLabel(String text,Font font,Color color){

		JLabel label=new JLabel(text);
		label.setFont(font);
		label.setForeground(color);

		return label;
	}

	/**
	 * テキストフィールドを作るクラス
	 * @param font テキストフィールド入力された文字のフォント
	 * @return JTextField
	 */
	public JTextField makeTextField(Font font){

		JTextField textField = new JTextField(40);
		textField.setFont(font);

		return textField;
	}

	/**
	 * フレームを作るメソッド
	 * @param frame 作りたいフレーム
	 * @param x 横の長さ
	 * @param y 縦の長さ
	 */
	public void makeWindow(JFrame frame,int x,int y){
		ImageIcon icon = new ImageIcon("src/main/resources/icon/dog.png");
		frame.setIconImage(icon.getImage());
		frame.setSize(x,y);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	/**
	 * 外観をOSのものに似せる （WindowsならWindowsの外観に似る）
	 */
	public void initLookFeel() {
		try {
			String look =
			// "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
			UIManager.getSystemLookAndFeelClassName();
			UIManager.setLookAndFeel(look);
		} catch (Exception e) {
			// 駄目なときは諦める
			e.printStackTrace();
		}
	}
}
