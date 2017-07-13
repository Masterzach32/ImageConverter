package net.masterzach32.imageconverter

import java.awt.image.BufferedImage
import java.io.File
import java.io.FileOutputStream
import javax.imageio.ImageIO

/*
 * ImageConverter - Created on 7/12/2017
 * Author: Zach Kozar
 * 
 * This code is licensed under the GNU GPL v3
 * You can find more info in the LICENSE file at the project root.
 */

/**
 * @author Zach Kozar
 * @version 7/12/2017
 */
class Image(path: String) {

    enum class ImageType(val extension: String) {
        GIF("gif"),
        PNG("png"),
        JPEG("jpg"),
        BMP("bmp"),
        WBMP("wbmp")
    }

    val image: BufferedImage
    val fileName: String

    init {
        val file = File(path).canonicalFile

        if (!file.exists())
            throw IllegalArgumentException("Cannot find image: $path")
        if (file.isDirectory)
            throw IllegalArgumentException("Cannot load a directory as an image: $path")

        try {
            image = ImageIO.read(file)
        } catch (t: Throwable) {
            throw t
        }

        fileName = file.nameWithoutExtension
    }

    fun saveAs(imgType: ImageType, path: String) {
        try {
            val fos = FileOutputStream(File("$path${File.separator}$fileName.${imgType.extension}"))
            ImageIO.write(image, imgType.extension, fos)
        } catch (t: Throwable) {
            throw t
        }
    }
}