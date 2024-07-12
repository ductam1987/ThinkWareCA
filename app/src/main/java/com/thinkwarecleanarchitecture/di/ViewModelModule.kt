package com.thinkwarecleanarchitecture.di

import com.tca.feature.detail.MovieDetailViewModel
import com.tca.feature.home.RecommendViewModel
import com.tca.feature.videoffmpeg.VideoFfmpegViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

/**
 * Created by Tam Nguyen on 11/07/2024.
 */
val viewModelModule =
    module {
        viewModelOf(::RecommendViewModel)
        viewModelOf(::MovieDetailViewModel)
        viewModelOf(::VideoFfmpegViewModel)
    }
