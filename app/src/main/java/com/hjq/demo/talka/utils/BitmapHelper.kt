package com.hjq.smallchat.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.nio.channels.FileChannel
import java.text.SimpleDateFormat
import java.util.Date

object BitmapHelper {
    /**
     * 改造后最终版 scal 方法（核心）
     * 直接兼容：content://路径 + 本地路径，无报错、无未定义
     * @param context 上下文（必须传，解决externalCacheDir获取问题）
     * @param path 图片路径（content://xxx 或 /storage/xxx）
     */
    fun scal(context: Context, path: String?): File? {
        if (path.isNullOrEmpty()) return null // 空路径直接返回，防崩溃

        var outputFile: File? = null
        var fos: FileOutputStream? = null
        var bitmap: Bitmap? = null
        val options = BitmapFactory.Options()

        try {
            // 1. 自动识别路径类型，解析Bitmap（原图尺寸，无失真）
            bitmap = if (path.startsWith("content://")) {
                val uri = Uri.parse(path)
                val inputStream = context.contentResolver.openInputStream(uri)
                BitmapFactory.decodeStream(inputStream, null, options)
            } else {
                BitmapFactory.decodeFile(path, options)
            }

            if (bitmap == null) return null // Bitmap解析失败，直接返回

            // 2. 初始化输出文件（核心：解决externalCacheDir未定义问题）
            outputFile = if (path.startsWith("content://")) {
                // content路径 → 创建临时缓存文件（使用context获取externalCacheDir）
                createImageFile(context)
            } else {
                // 本地路径 → 原逻辑不变
                File(path)
            }

            // 3. 质量压缩（和你原逻辑一致，100%画质）
            fos = FileOutputStream(outputFile)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)

        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            fos?.close() // 关闭流，防止内存泄漏
        }

        // 4. Bitmap回收 + 文件拷贝（原逻辑完整保留）
        if (bitmap != null && !bitmap.isRecycled) {
            bitmap.recycle()
        } else {
            val tempFile = outputFile
            outputFile = createImageFile(context)
            copyFileUsingFileChannels(tempFile, outputFile)
        }
        return outputFile
    }

// ===================== 所有缺失的方法，完整补全（无未定义）=====================
    /**
     * 修复核心：创建临时文件（正确获取externalCacheDir，带非空兜底）
     * @param context 上下文 → 必须传，用来获取缓存目录
     */
    private fun createImageFile(context: Context): File {
        // ✅ 关键1：通过context获取externalCacheDir，加非空兜底（核心修复）
        val baseCacheDir = context.externalCacheDir ?: context.cacheDir
        // ✅ 关键2：拼接子目录，确保目录存在
        val cacheDir = File(baseCacheDir.absolutePath + "/temp_image/")
        if (!cacheDir.exists()) cacheDir.mkdirs()
        // ✅ 生成唯一文件名，避免重复
        val fileName = "temp_${System.currentTimeMillis()}.jpg"
        return File(cacheDir, fileName)
    }

    /**
     * 文件拷贝工具方法（原方法依赖，完整补全）
     */
    private fun copyFileUsingFileChannels(source: File?, dest: File?) {
        var sourceChannel: FileChannel? = null
        var destChannel: FileChannel? = null
        try {
            sourceChannel = source?.inputStream()?.channel
            destChannel = dest?.outputStream()?.channel
            destChannel?.transferFrom(sourceChannel, 0, sourceChannel?.size() ?: 0)
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            sourceChannel?.close()
            destChannel?.close()
        }
    }
}