package net.masterzach32.imageconverter

import java.io.File

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

fun main(args: Array<String>) {
    val imageTypes = Image.ImageType.values()

    if (args.isEmpty()) {
        println("convert images between file types")
        println()
        println("usage: imgconverter <img path> <filetype> -o [output path]")
        println()
        println("supported image types")

        var i = 0
        while (i < imageTypes.size) {
            print("\t\t${imageTypes[i].extension}")
            if (i % 3 == 2)
                println()
            i++
        }

        System.exit(0)
    }
    if (args.size < 2) {
        println("error: required args not found")
        System.exit(1)
    }

    val image: Image
    try {
        image = Image(args[0])
    } catch (t: Throwable) {
        println("error: could not read image")
        println(t.message)
        System.exit(1)
        return
    }

    val imgType = parseImageType(args[1])
    if (imgType == null) {
        println("error: image type ${args[1]} not supported")
        System.exit(1)
        return
    }

    val output: File
    if (args.size == 4 && args[2] == "-o") {
        output = File(args[3])
        if (output.isDirectory || !output.canWrite()) {
            println("error: cannot write to ${output.absolutePath}")
            System.exit(1)
        }
    } else {
        output = File(".").canonicalFile
    }

    try {
        image.saveAs(imgType, output.absolutePath)
        println("image converted and written to ${output.absolutePath}")
    } catch (t: Throwable) {
        println("error: could not write to file")
        println(t.message)
    }
}

fun parseImageType(str: String): Image.ImageType? {
    return Image.ImageType.values().filter { it.extension == str }.firstOrNull()
}