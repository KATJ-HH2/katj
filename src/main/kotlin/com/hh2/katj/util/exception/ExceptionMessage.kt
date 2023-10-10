package com.hh2.katj.util.exception

enum class ExceptionMessage(message: String, status: Int) {
    ID_DOES_NOT_EXIST("id does not exist", 400),
    DUPLICATED_DATA_ALREADY_EXISTS("duplicated data already exists", 400),
    USER_DOES_NOT_EXIST("user does not exist", 400),
    INTERNAL_SERVER_ERROR_FROM_DATABASE("internal server error from database", 500),
    INCORRECT_STATUS_VALUE("incorrect status value", 500),
    NO_SEARCH_LOCATION_RESULT("no search results", 400),
    NO_SUCH_VALUE_EXISTS("no such value exists", 400),
    INVALID_PAYMENT_METHOD("invalid payment method", 500),
}