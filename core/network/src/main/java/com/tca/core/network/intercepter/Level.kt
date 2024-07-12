package com.tca.core.network.intercepter

/**
 * Created by Tam Nguyen on 11/07/2024.
 */
enum class Level {
    /**
     * No logs.
     */
    NONE,

    /**
     *
     * Example:
     * <pre>`- URL
     * - Method
     * - Headers
     * - Body
     `</pre> *
     */
    BASIC,

    /**
     *
     * Example:
     * <pre>`- URL
     * - Method
     * - Headers
     `</pre> *
     */
    HEADERS,

    /**
     *
     * Example:
     * <pre>`- URL
     * - Method
     * - Body
     `</pre> *
     */
    BODY,

    // CustomLog
    CloudHMS,
}
