package com.tca.feature.videoffmpeg

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arthenica.ffmpegkit.FFmpegKit
import com.arthenica.ffmpegkit.ReturnCode
import com.tca.core.ui.ffmpeg.VideoFfmpegUtil
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class VideoFfmpegViewModel : ViewModel() {
    private var selectedCodec by mutableStateOf("mpeg4")
    var videoUri by mutableStateOf<Uri?>(null)

    fun encodeVideo(context: Context, onProgress: (Int) -> Unit) {
        viewModelScope.launch {
            val cacheDir = context.cacheDir
            val image1File = File(cacheDir, "machupicchu.jpg")
            val image2File = File(cacheDir, "pyramid.jpg")
            val image3File = File(cacheDir, "stonehenge.jpg")
            val videoFile = getVideoFile(cacheDir)

            try {
                if (!image1File.exists()) {
                    copyDrawableToFile(context, R.drawable.machupicchu, image1File)
                }
                if (!image2File.exists()) {
                    copyDrawableToFile(context, R.drawable.pyramid, image2File)
                }
                if (!image3File.exists()) {
                    copyDrawableToFile(context, R.drawable.stonehenge, image3File)
                }

                if (videoFile.exists()) {
                    videoFile.delete()
                }

                val ffmpegCommand = VideoFfmpegUtil.generateEncodeVideoScript(
                    image1File.absolutePath,
                    image2File.absolutePath,
                    image3File.absolutePath,
                    videoFile.absolutePath,
                    getSelectedVideoCodec(),
                    getPixelFormat(),
                    getCustomOptions()
                )

                FFmpegKit.executeAsync(ffmpegCommand,
                    { session ->
                        val returnCode = session.returnCode
                        if (ReturnCode.isSuccess(returnCode)) {
                            videoUri = Uri.parse("file://${videoFile.absolutePath}")
                        } else {
                            Log.d("VideoFfmpegViewModel", "Encode failed: $returnCode")
                        }
                    }, { log ->
                        Log.d("VideoFfmpegViewModel", log.message)
                    }, { statistics ->
                        val progressValue = (statistics.time * 100 / 9000).toInt()
                        onProgress(progressValue)
                    })

            } catch (e: IOException) {
                Log.e("VideoFfmpegViewModel", "Encode video failed", e)
            }
        }
    }

    private fun copyDrawableToFile(context: Context, drawableId: Int, file: File) {
        val drawable = ContextCompat.getDrawable(context, drawableId) as BitmapDrawable
        val bitmap = drawable.bitmap
        val outputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        outputStream.flush()
        outputStream.close()
    }

    private fun getPixelFormat(): String {
        return if (selectedCodec == "x265") "yuv420p10le" else "yuv420p"
    }

    private fun getSelectedVideoCodec(): String {
        return when (selectedCodec) {
            // VIDEO CODEC SPINNER HAS BASIC NAMES, FFMPEG NEEDS LONGER AND EXACT CODEC NAMES.
            // APPLYING NECESSARY TRANSFORMATION HERE
            "x264" -> "libx264"
            "h264_mediacodec" -> "h264_mediacodec"
            "hevc_mediacodec" -> "hevc_mediacodec"
            "openh264" -> "libopenh264"
            "x265" -> "libx265"
            "xvid" -> "libxvid"
            "vp8" -> "libvpx"
            "vp9" -> "libvpx-vp9"
            "aom" -> "libaom-av1"
            "kvazaar" -> "libkvazaar"
            "theora" -> "libtheora"
            else -> "mpeg4"
        }
    }

    private fun getCustomOptions(): String {
        return when (selectedCodec) {
            "x265" -> "-crf 28 -preset fast "
            "vp8" -> "-b:v 1M -crf 10 "
            "vp9" -> "-b:v 2M "
            "aom" -> "-crf 30 -strict experimental "
            "theora" -> "-qscale:v 7 "
            else -> ""
        }
    }

    private fun getVideoFile(cacheDir: File): File {
        val extension = when (selectedCodec) {
            "vp8", "vp9" -> "webm"
            "aom" -> "mkv"
            "theora" -> "ogv"
            else -> "mp4"
        }
        return File(cacheDir, "video.$extension")
    }
}