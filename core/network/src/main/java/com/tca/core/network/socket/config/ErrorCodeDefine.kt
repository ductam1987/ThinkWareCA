package com.tca.core.network.socket.config

object ErrorCodeDefine {
    const val ERRORCODE_IOException_VALUE = -1

    /**
     * `ERRORCODE_OK = 0;`
     */
    const val ERRORCODE_OK_VALUE = 0

    /**
     * `ERRORCODE_FAILED = 1;`
     */
    const val ERRORCODE_FAILED_VALUE = 1

    /**
     * `ERRORCODE_EXPIRED = 2;`
     */
    const val ERRORCODE_EXPIRED_VALUE = 2

    /**
     * <pre>
     * NotExistUser
     </pre> *
     *
     * `ERRORCODE_NONEXISTENT = 3;`
     */
    const val ERRORCODE_NONEXISTENT_VALUE = 3

    /**
     * `ERRORCODE_INVALID = 4;`
     */
    const val ERRORCODE_INVALID_VALUE = 4

    /**
     * <pre>
     * TypeFail
     </pre> *
     *
     * `ERRORCODE_WRONG_TYPE = 5;`
     */
    const val ERRORCODE_WRONG_TYPE_VALUE = 5

    /**
     * <pre>
     * ParseDataFail
     </pre> *
     *
     * `ERRORCODE_PARSING_DATA_FAILURE = 6;`
     */
    const val ERRORCODE_PARSING_DATA_FAILURE_VALUE = 6

    /**
     * <pre>
     * LoginConflict
     </pre> *
     *
     * `ERRORCODE_LOGIN_CONFLICT = 7;`
     */
    const val ERRORCODE_LOGIN_CONFLICT_VALUE = 7

    /**
     * <pre>
     * NotFoundUser
     </pre> *
     *
     * `ERRORCODE_USER_NOT_FOUND = 8;`
     */
    const val ERRORCODE_USER_NOT_FOUND_VALUE = 8

    /**
     * <pre>
     * DecodeHeaderFail
     </pre> *
     *
     * `ERRORCODE_HEADER_DECODING_FAILURE = 9;`
     */
    const val ERRORCODE_HEADER_DECODING_FAILURE_VALUE = 9

    /**
     * <pre>
     * MapSessionWrong
     </pre> *
     *
     * `ERRORCODE_WRONG_SESSION = 10;`
     */
    const val ERRORCODE_WRONG_SESSION_VALUE = 10
}
