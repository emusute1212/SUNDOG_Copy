package io.sundog.copy.manager;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

/**
 * コピーを司るクラス
 * @author SUNDOG
 *
 */
public class GovernCopy {

	/**
	 * 引数として受け取った文字列をクリップボードにコピーする
	 *
	 * @param text
	 *            コピーしたいテキスト
	 */
	public void doCopy(String text) {

		Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
		StringSelection selection = new StringSelection(text);
		cb.setContents(selection, selection);

	}
}