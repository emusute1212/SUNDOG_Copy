package io.sundog.copy.ui;

import javax.swing.*;
import java.awt.*;

public class AboutGUI extends JFrame {
    private final static String VERSION="1.0 Shichimi";
    public final static String UP_DATE="update";

    private ActionController action=new ActionController(this);

    public AboutGUI(String title){

        super(title);

    }

    public void init(){
        JPanel panel = new JPanel();
        BoxLayout boxlayout = new BoxLayout(this.getContentPane(),BoxLayout.Y_AXIS);
        this.setLayout(boxlayout);
        panel.setBackground(Color.WHITE);


        Font titleFont = new Font("メイリオ",Font.PLAIN,36);
        SettingWindow window = new SettingWindow();
        window.initLookFeel();

        JLabel title =window.makeLabel("更新の確認",titleFont,new Color(0x708090));

        Font p = new Font("メイリオ", Font.PLAIN, 16);
        JLabel about = window.makeLabel("お使いのバージョンは", p);
        about.setAlignmentX(1.0f);

        Font version = new Font("メイリオ", Font.PLAIN,28);
        JLabel yourVersion = window.makeLabel(VERSION, version);


        JLabel about2 = window.makeLabel("です。", p);

        JButton updateBtn = window.makeButton("更新を確認", p);
        updateBtn.addActionListener(action);
        updateBtn.setActionCommand(UP_DATE);

        Font openBrowser = new Font("メイリオ", Font.PLAIN, 12);
        JLabel update = window.makeLabel("ブラウザが起動します。", openBrowser);


        Font footer = new Font("メイリオ", Font.PLAIN,9);
        JLabel footerLabel = window.makeLabel("<html><br>このソフトウェアは、Apache 2.0ライセンスで<br>配布されている制作物が含まれています。<br><br>Copyright © 2015 - SUNDOGソフトウェア開発プロジェクト</html>", footer);

        panel.add(title);
        panel.add(Box.createRigidArea(new Dimension(0,10))); // 空白ピクセルの挿入
        panel.add(about);
        panel.add(yourVersion);
        panel.add(about2);
        panel.add(Box.createRigidArea(new Dimension(0,50)));
        panel.add(updateBtn);
        panel.add(Box.createRigidArea(new Dimension(200,0)));
        panel.add(update);
        panel.add(footerLabel);
        panel.add(Box.createRigidArea(new Dimension(0,50)));
        this.getContentPane().add(panel);
        window.makeWindow(this,300,330);
        this.setResizable(false);
    }
}
