package com.tca.core.ui

/**
 * Created by Tam Nguyen on 11/07/2024.
 */
fun String.convertImageURL(): String = "https://image.tmdb.org/t/p/w500".plus(this)

fun String.convertYoutubeThumbnails(): String = "https://img.youtube.com/vi/".plus(this).plus("/0.jpg")

fun String.convertYoutubeUrl(): String = "https://www.youtube.com/watch?v=".plus(this)

fun String.convertAvatarURL(): String =
    if (this.first().toString() == "/") {
        val stringBuilder = StringBuilder(this)
        stringBuilder.deleteCharAt(0).toString()
    } else {
        this
    }
