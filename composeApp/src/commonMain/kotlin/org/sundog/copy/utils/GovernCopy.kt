package org.sundog.copy.utils

import java.awt.Toolkit
import java.awt.datatransfer.StringSelection

/**
 * コピーを司るクラス
 */
object GovernCopy {
    /**
     * 引数として受け取った文字列をクリップボードにコピーする
     */
    fun doCopy(text: String) {
        StringSelection(text).also {
            Toolkit.getDefaultToolkit().systemClipboard.setContents(it, it)
        }
    }
}