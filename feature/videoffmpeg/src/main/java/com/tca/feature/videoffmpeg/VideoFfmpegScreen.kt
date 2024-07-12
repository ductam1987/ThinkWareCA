package com.tca.feature.videoffmpeg

import android.widget.VideoView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import org.koin.androidx.compose.koinViewModel

@Composable
fun VideoFfmpegScreen(
    viewModel: VideoFfmpegViewModel = koinViewModel(),
    navBack: () -> Unit,
) {
    val context = LocalContext.current
    var isEncoding by remember { mutableStateOf(false) }
    var progress by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            isEncoding = true
            viewModel.encodeVideo(context) { progressValue ->
                progress = progressValue
                if (progressValue == 100) {
                    isEncoding = false
                }
            }
        }) {
            Text("FFMPEG Encode Video")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (isEncoding) {
            LinearProgressIndicator(progress = progress / 100f)
            Text("Encoding video: ${if (progress >= 98) 100 else progress}%")
        }

        Spacer(modifier = Modifier.height(16.dp))
        if (viewModel.videoUri != null) {
            AndroidView(
                factory = { context ->
                    VideoView(context).apply {
                        setVideoURI(viewModel.videoUri)
                        setOnPreparedListener { start() }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(Color.Black)
            )
        }
    }
}