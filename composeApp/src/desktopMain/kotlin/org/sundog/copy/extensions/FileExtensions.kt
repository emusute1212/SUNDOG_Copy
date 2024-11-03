package org.sundog.copy.extensions

import java.io.File

fun File.child(path: String): File {
    return File(this, path)
}