package com.tca.core.network.config

/**
 * Created by Tam Nguyen on 11/07/2024.
 */
data class RecommendConfigDb(
    var offset: Long,
    var limit: Long = 20,
)
