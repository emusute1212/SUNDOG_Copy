package io.sundog.copy.ui;

import javax.swing.*;
import java.awt.*;

public class SettingGUIPanel extends JPanel{
	public final static String MOVE_ABOUT_GUI="about";
	public final static String SAVE_COMMAND="save";
	public final static String ADD_COMMAND="add";
	public static Dimension panelSize;
	SettingGUI setting;
	JTextField textBoxButton[];
    JTextField textBoxContent[];
	private ActionController action=new ActionController(this);
	private String tableTitle[]={"ボタンに表示する内容","コピーする内容","削除"};

	public SettingGUIPanel(SettingGUI set){
		setting = set;
		SettingWindow window=new SettingWindow();
		int numberOfCopy=TopGUI.copyText[0].length;

		window.initLookFeel();
		this.setBackground(Color.WHITE);
		this.setSize(setting.getContentPane().getWidth(),setting.getContentPane().getHeight());
		panelSize=this.getSize();

		this.setLayout(new BorderLayout());
		this.setBackground(Color.WHITE);

		Font titleFont = new Font("メイリオ",Font.PLAIN,36);
		JLabel title =window.makeLabel(" 設定",titleFont,new Color(0x708090));


		// ------------------------------------------
		JPanel gridLayoutPanel = new JPanel();
	    GridLayout gridLayout;
	    if(numberOfCopy<9){
	    	gridLayout = new GridLayout(10,3);
	    }else{
	    	gridLayout = new GridLayout(numberOfCopy+1,3);
	    }
	    gridLayoutPanel.setBackground(Color.WHITE);
		Font settingButtonFont = new Font("メイリオ",Font.PLAIN,12);

	    textBoxButton = new JTextField[numberOfCopy];
	    textBoxContent = new JTextField[numberOfCopy];

	    JLabel titleLabel[]=new JLabel[tableTitle.length];

	    for(int i=0;i<tableTitle.length;i++){
	    	titleLabel[i]=window.makeLabel(tableTitle[i],new Font("メイリオ", Font.PLAIN,12));
	    	titleLabel[i].setHorizontalAlignment(JLabel.CENTER);
	    	gridLayoutPanel.add(titleLabel[i]);
	    }

	    JButton delBtn[] = new JButton[numberOfCopy];
	    for(int i=0;i<numberOfCopy;i++){
	    	textBoxButton[i] = new JTextField(TopGUI.copyText[0][i]);
	    	textBoxContent[i] = new JTextField(TopGUI.copyText[1][i]);
	    	delBtn[i] = window.makeButton("削除", settingButtonFont);
	    	delBtn[i].setActionCommand("delete" + "," + i);
	    	delBtn[i].addActionListener(action);

	    	gridLayoutPanel.add(textBoxButton[i]);
		    gridLayoutPanel.add(textBoxContent[i]);
		    gridLayoutPanel.add(delBtn[i]);
	    }

	    if(numberOfCopy<9){
	    	for(int i=0;i<9-numberOfCopy;i++){
	    		for(int j=0;j<tableTitle.length;j++){
	    			gridLayoutPanel.add(new JLabel());
	    		}
	    	}
	    }
		JScrollPane scroll=new JScrollPane(gridLayoutPanel);
		scroll.getViewport().setBackground(Color.WHITE);
	    gridLayoutPanel.setLayout(gridLayout);

		// ------------------------------------------



		// -------------------------------------------
		// Footer
		JPanel flowLayoutPanel = new JPanel();
		flowLayoutPanel.setBackground(Color.white);
		flowLayoutPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

		JButton addButton = window.makeButton("+", settingButtonFont);
		addButton.addActionListener(action);
		addButton.setActionCommand(ADD_COMMAND);

		JButton saveButton = window.makeButton("保存", settingButtonFont);
		saveButton.addActionListener(action);
		saveButton.setActionCommand(SAVE_COMMAND);

		JButton updateButton = window.makeButton("更新確認", settingButtonFont);
		updateButton.addActionListener(action);
		updateButton.setActionCommand(MOVE_ABOUT_GUI);

		flowLayoutPanel.add(addButton);
		flowLayoutPanel.add(saveButton);
		flowLayoutPanel.add(updateButton);
		// -------------------------------------------

		this.add(title, BorderLayout.NORTH);
		this.add(scroll);
		this.add(flowLayoutPanel, BorderLayout.SOUTH);
	}

	public void transitionPanel(){
        setting.PanelChange();
    }


	public void closeWindow(){
		setting.setVisible(false);
	}
}
