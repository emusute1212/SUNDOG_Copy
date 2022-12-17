package io.sundog.copy.ui;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class TopGUI extends JFrame{
	public final static String TOP_WINDOW_CHECKBOX="checkBox";
	public final static String MOVE_SETTING_GUI="setting";
	/**
	 * [0][適当な数字]ボタンの名前
	 * [1][適当な数字]簿コピーする内容
	 */
	public static String copyText[][];
	private static JCheckBox topWindowCheckbox;
	private ActionController action=new ActionController(this);

	public TopGUI(String title){

		super(title);

		ControlCSV load=new ControlCSV();
		load.makeBackupTestFile(new File(System.getenv().get("APPDATA") + "/SUNDOG_Copy_Backup/backup.csv"));
		copyText=load.loadCSV("data.csv");


	}

	public static void main(String[] args) {

		TopGUI top=new TopGUI("SUNDOG Copy");
		top.init();

	}

	public void init(){
		SettingWindow window=new SettingWindow();
		window.initLookFeel();

		JPanel borderLayoutPanel = new JPanel();
		borderLayoutPanel.setLayout(new BorderLayout());
		borderLayoutPanel.setBackground(Color.WHITE);


		Font titleFont = new Font("メイリオ",Font.PLAIN,36);
		JLabel title =window.makeLabel(" SUNDOG Copy",titleFont,new Color(0x708090));


		// ------------------------------------------
		JPanel gridLayoutPanel = new JPanel();
	    GridLayout gridLayout = new GridLayout(copyText[0].length, 1);

		JScrollPane scroll=new JScrollPane(gridLayoutPanel);
	    gridLayout.setVgap(5);
	    gridLayoutPanel.setLayout(gridLayout);

		Font p = new Font("メイリオ",Font.PLAIN,24);


		JButton copyBtn[]=new JButton[copyText[0].length];
		for(int i=0;i<copyBtn.length;i++){
			copyBtn[i] = window.makeButton(copyText[0][i], p);
			copyBtn[i].setActionCommand(copyText[1][i]);
			copyBtn[i].addActionListener(action);
			gridLayoutPanel.add(copyBtn[i]);
		}

		// ------------------------------------------



		// -------------------------------------------
		// Footer
		JPanel flowLayoutPanel = new JPanel();
		Font settingButtonFont = new Font("メイリオ",Font.PLAIN,12);
		flowLayoutPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		topWindowCheckbox = new JCheckBox("このウィンドウを常に手前に表示");
		topWindowCheckbox.addActionListener(action);
		topWindowCheckbox.setFont(settingButtonFont);
		topWindowCheckbox.setActionCommand(TOP_WINDOW_CHECKBOX);
		JButton settingButton = window.makeButton("設定", settingButtonFont);
		settingButton.addActionListener(action);
		settingButton.setActionCommand(MOVE_SETTING_GUI);

		flowLayoutPanel.add(topWindowCheckbox);
		flowLayoutPanel.add(settingButton);
		// -------------------------------------------

		borderLayoutPanel.add(title, BorderLayout.NORTH);
		borderLayoutPanel.add(scroll, BorderLayout.CENTER);
		borderLayoutPanel.add(flowLayoutPanel, BorderLayout.SOUTH);
		this.getContentPane().add(borderLayoutPanel);
		window.makeWindow(this,300,400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void topWindow(){
		setAlwaysOnTop(topWindowCheckbox.isSelected());
	}

	public void delateData(int delateIndex) {
		String temp[][]=new String[copyText.length][copyText[0].length-1];
		boolean flag;
		for(int i=0;i<copyText.length;i++){
			flag=false;
			for(int j=0;j<copyText[i].length;j++){
				if(flag){
					temp[i][j-1]=copyText[i][j];
				}else if(j!=delateIndex){
					temp[i][j]=copyText[i][j];
				}else{
					flag=true;
				}
			}
		}
		copyText=temp;
	}

	public void addDate() {
		String temp[][]=new String[copyText.length][copyText[0].length+1];
		for(int i=0;i<copyText.length;i++){
			for(int j=0;j<copyText[i].length;j++){
					temp[i][j]=copyText[i][j];
			}
		}
		temp[0][temp[0].length-1]="";
		temp[1][temp[1].length-1]="";
		copyText=temp;
	}
}
