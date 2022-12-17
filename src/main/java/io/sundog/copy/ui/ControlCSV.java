package io.sundog.copy.ui;

import io.sundog.copy.manager.EncryptionDecryption;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class ControlCSV {

	/**
	 * CSVファイルを読み込む
	 *
	 * @param fileName
	 *            CSVファイルの名前(注意：文字列に拡張子もつけて渡すこと)
	 * @return <html>読み込んだCSVファイルの中身<br>
	 *         String[][]で帰ってくる
	 */
	public String[][] loadCSV(String fileName) {

		String dir = "./CopyData";
		BufferedReader br = null;

		ArrayList<String> tempDisplayData = new ArrayList<String>();
		ArrayList<String> tempCopyData = new ArrayList<String>();

		try {
			File csv = new File(dir + "/" + fileName);

			if (!(csv.exists())) {
				// Fileオブジェクト生成時の例外捕捉
				File makeDir = new File(dir);
				if (makeDir.exists()) {
					// CopyDataフォルダが存在する場合
					String test[][] = {
							{ "ボタン1", "ボタン2", "ボタン3", "ボタン4", "ボタン5" },
							{ "コピー1", "コピー2", "コピー3", "コピー4", "コピー5" } };
					saveCSV(test, fileName);
				} else {
					// CopyDataがない場合
					makeDir.mkdir();
					String test[][] = {
							{ "ボタン1", "ボタン2", "ボタン3", "ボタン4", "ボタン5" },
							{ "コピー1", "コピー2", "コピー3", "コピー4", "コピー5" } };
					saveCSV(test, fileName);
				}
				JOptionPane.showMessageDialog(null, new SettingWindow()
						.makeLabel("ファイルが見つかりませんでしたので、データファイルを作成しました。",
								new Font("メイリオ", Font.PLAIN, 12)), "メッセージ",
						JOptionPane.INFORMATION_MESSAGE);
				csv = new File(dir + "/" + fileName);
			}

			br = new BufferedReader(new FileReader(csv));

			String line = "";
			for (int i = 0; (line = br.readLine()) != null; i++) {

				// 1行をデータの要素に分割
				StringTokenizer st = new StringTokenizer(line, ",");

				while (st.hasMoreTokens()) {
					if (i == 0) {
						tempDisplayData.add(EncryptionDecryption.decrypt(st.nextToken()));
					} else {
						tempCopyData.add(EncryptionDecryption.decrypt(st.nextToken()));
					}
				}
			}
			br.close();

		} catch (IOException e) {
			// BufferedReaderオブジェクトのクローズ時の例外捕捉
			JOptionPane.showMessageDialog(null, new SettingWindow().makeLabel(
					"エラーが起こりました。(絶望)", new Font("メイリオ", Font.PLAIN, 12)),
					"Error", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}

		String[][] data;
		if(tempDisplayData.size()!=tempCopyData.size()){
			JOptionPane.showMessageDialog(null, new SettingWindow()
			.makeLabel("ファイルが壊れているので、バックアップから最新のデータを復元します。",
					new Font("メイリオ", Font.PLAIN, 12)), "メッセージ",
			JOptionPane.INFORMATION_MESSAGE);

			data=backupLoad();
			saveCSV(data,fileName);
		}else{

			data = new String[2][tempDisplayData.size()];

			for (int i = 0; i < data[0].length; i++) {
				data[0][i] = tempDisplayData.get(i);
			}

			for (int i = 0; i < data[1].length; i++) {
				data[1][i] = tempCopyData.get(i);
			}
		}

		return data;

	}

	/**
	 * CSVファイルを保存するメソッド
	 *
	 * @param data
	 *            CSVファイルとして保存したい内容
	 * @param fileName
	 *            CSVファイルの名前(注意：文字列に拡張子もつけて渡すこと)
	 */
	public void saveCSV(String[][] data, String fileName) {

		String dir = "./CopyData";
		PrintWriter pw = null;

		try {
			File csv = new File(dir + "/" + fileName);

			FileWriter fw = new FileWriter(csv);
			pw = new PrintWriter(new BufferedWriter(fw));

			for (int i = 0; i < data.length; i++) {
				for (int j = 0; j < data[i].length; j++) {
					pw.print(EncryptionDecryption.encrypt(data[i][j]));
					pw.print(",");
				}
				pw.println();
			}

		} catch (IOException ex) {
			// 例外時処理
			JOptionPane.showMessageDialog(null, new SettingWindow().makeLabel(
					"エラーが起こりました。(絶望)", new Font("メイリオ", Font.PLAIN, 12)),
					"Error", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		} finally {
			pw.close();
		}
		backupSave(data);
	}

	public String[][] backupLoad(){
		BufferedReader br = null;

		ArrayList<String> tempDisplayData = new ArrayList<String>();
		ArrayList<String> tempCopyData = new ArrayList<String>();

		try {
//			makeBackupTestFile(new File(System.getenv().get("APPDATA") + "/SUNDOG_Copy_Backup/backup.csv"));
			makeBackupTestFile(new File("./SUNDOG_Copy_Backup/backup.csv"));
//			File csv = new File(System.getenv().get("APPDATA") + "/SUNDOG_Copy_Backup/backup.csv");
			File csv = new File("./SUNDOG_Copy_Backup/backup.csv");

			br = new BufferedReader(new FileReader(csv));

			String line = "";
			for (int i = 0; (line = br.readLine()) != null; i++) {

				// 1行をデータの要素に分割
				StringTokenizer st = new StringTokenizer(line, ",");

				while (st.hasMoreTokens()) {
					if (i == 0) {
						tempDisplayData.add(EncryptionDecryption.decrypt(st.nextToken()));
					} else {
						tempCopyData.add(EncryptionDecryption.decrypt(st.nextToken()));
					}
				}
			}
			br.close();

		} catch (IOException e) {
			// BufferedReaderオブジェクトのクローズ時の例外捕捉
			JOptionPane.showMessageDialog(null, new SettingWindow().makeLabel(
					"エラーが起こりました。(絶望)", new Font("メイリオ", Font.PLAIN, 12)),
					"Error", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}

		String[][] data;

		if(tempDisplayData.size()!=tempCopyData.size()){
			JOptionPane.showMessageDialog(null, new SettingWindow()
			.makeLabel("<html>バックアップからの復元および起動に失敗しました。<br><br>SUNDOG Copyを初期化します。</html>",
					new Font("メイリオ", Font.PLAIN, 12)), "SUNDOG Copyデータ復元機能",
			JOptionPane.INFORMATION_MESSAGE);

			String test[][] = {
					{ "ボタン1", "ボタン2", "ボタン3", "ボタン4", "ボタン5" },
					{ "コピー1", "コピー2", "コピー3", "コピー4", "コピー5" } };
			backupSave(test);
			data=test;
		}else{
			data = new String[2][tempDisplayData.size()];
			for (int i = 0; i < data[0].length; i++) {
				data[0][i] = tempDisplayData.get(i);
			}

			for (int i = 0; i < data[1].length; i++) {
				data[1][i] = tempCopyData.get(i);
			}
		}

		return data;
	}

	public void makeBackupTestFile(File csv) {
		if (!(csv.exists())) {
			// Fileオブジェクト生成時の例外捕捉
//			File makeDir = new File(System.getenv().get("APPDATA") + "/SUNDOG_Copy_Backup");
			File makeDir = new File("./SUNDOG_Copy_Backup");
			if (makeDir.exists()) {
				// CopyDataフォルダが存在する場合
				String test[][] = {
						{ "ボタン1", "ボタン2", "ボタン3", "ボタン4", "ボタン5" },
						{ "コピー1", "コピー2", "コピー3", "コピー4", "コピー5" } };
				backupSave(test);
			} else {
				// CopyDataがない場合
				makeDir.mkdir();
				String test[][] = {
						{ "ボタン1", "ボタン2", "ボタン3", "ボタン4", "ボタン5" },
						{ "コピー1", "コピー2", "コピー3", "コピー4", "コピー5" } };
				backupSave(test);
			}

		}
	}

	public void backupSave(String[][] data){
		PrintWriter pw = null;

		try {
//			win用
//			File csv = new File(System.getenv().get("APPDATA") + "/SUNDOG_Copy_Backup/backup.csv");

			File csv=new File("./SUNDOG_Copy_Backup/backup.csv");
			FileWriter fw = new FileWriter(csv);
			pw = new PrintWriter(new BufferedWriter(fw));

			for (int i = 0; i < data.length; i++) {
				for (int j = 0; j < data[i].length; j++) {
					pw.print(EncryptionDecryption.encrypt(data[i][j]));
					pw.print(",");
				}
				pw.println();
			}

		} catch (IOException ex) {
			// 例外時処理
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, new SettingWindow().makeLabel(
					"エラーが起こりました。(絶望)", new Font("メイリオ", Font.PLAIN, 12)),
					"Error", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		} finally {
			pw.close();
		}
	}
}
