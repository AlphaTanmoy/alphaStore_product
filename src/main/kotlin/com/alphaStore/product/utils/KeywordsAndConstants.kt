package com.alphaStore.product.utils

object KeywordsAndConstants {

    const val TOKEN_PREFIX = "Alpha "
    const val ENCRYPTION_PASSWORD_CHOICE_ONE = "/h9lW;L-~n>l8j\$!IQJHx1Yx{0Ot7:j%W;5b[^8]q}\"G]9jRJgc#P;pDakVkB}G62twX+M6CpscBD;a="
    const val ENCRYPTION_PASSWORD_CHOICE_TWO = "teA5o?MxI<Mcp+\"RZ|UYK{[>{geCYQdo)bUN57U2p|Ea,lwg4dTK+RBcvird5DdmfakeB[Rdn*wIIhmN"
    const val ENCRYPTION_PASSWORD_CHOICE_THREE = "/aSM6'A8J,zz9~0)sX*d3pXoo].u<!k+ZThF\\}cRvR7i-\"`X(&'F%4#4c{X8s^/'4Ivh/qKgq;0Q6lz8"
    const val ENCRYPTION_PASSWORD_SALT_CHOICE = "18237012"

    const val REDIS_PASSWORD = "password"
    const val REDIS_HOST = "127.0.0.1"
    const val REDIS_PORT = 6379

    const val REDIS_KEY_BLACKLISTED_IPS = "ALPHA_STORE_REDIS_KEY_BLACKLISTED_IPS"
    const val REDIS_KEY_WHITELISTED_IPS = "ALPHA_STORE_REDIS_KEY_WHITELISTED_IPS"
    const val REDIS_KEY_JWT_BLACK_LIST = "ALPHA_STORE_REDIS_KEY_JWT_BLACK_LIST"
    const val REDIS_KEY_ACCESS_ROLE = "ALPHA_STORE_REDIS_KEY_ACCESS_ROLE"
    const val REDIS_KEY_USER_SERIAL = "ALPHA_STORE_REDIS_KEY_USER_SERIAL"
    const val REDIS_KEY_ACCESS_CONTROL = "ALPHA_STORE_REDIS_KEY_ACCESS_CONTROL"
    const val REDIS_KEY_DATABASE_RESULT = "ALPHA_STORE_REDIS_KEY_DATABASE_RESULT"
    const val REDIS_KEY_RESULT = "ALPHA_STORE_REDIS_KEY_RESULT"
    const val REDIS_KEY_DATABASE_TABLE_LOCK = "ALPHA_STORE_REDIS_KEY_DATABASE_TABLE_LOCK"

    //for request sanitation
    const val RABBIT_MQ_QUEUE_FOR_REQUEST_SANITATION = "alphaStoreRabbitMqRequestSanitationQueue"
    const val RABBIT_MQ_ROUTE_KEY_FOR_REQUEST_SANITATION = "alphaStoreRabbitMqRequestSanitationQueueKey"

    //user logged in from a new country
    const val RABBIT_MQ_QUEUE_FOR_NEW_DEVICE_LOGIN = "LivenessRabbitNewDeviceLoginQueue"
    const val RABBIT_MQ_ROUTE_KEY_FOR_NEW_DEVICE_LOGIN =
        "alphaStoreRabbitMqNewDeviceLoginQueueKey"

    //for sending emails to system admin
    const val RABBIT_MQ_QUEUE_FOR_SENDING_EMAIL_TO_SYSTEM_ADMIN =
        "alphaStoreRabbitMqSendingEmailsToSuperAdmin"
    const val RABBIT_MQ_ROUTE_KEY_FOR_SENDING_EMAIL_TO_SYSTEM_ADMIN =
        "alphaStoreRabbitMqSendingEmailsToSuperAdminKey"


    //send otp for verification
    const val RABBIT_MQ_QUEUE_FOR_SEND_OTP_TO_EMAIL_FOR_VERIFICATION =
        "alphaStoreRabbitMqSendOtpToEmailForVerification"
    const val RABBIT_MQ_ROUTE_KEY_FOR_SEND_OTP_TO_EMAIL_FOR_VERIFICATION =
        "alphaStoreRabbitMqSendOtpToEmailForVerificationKey"

    //user password reset due to multiple login fails
    const val RABBIT_MQ_QUEUE_FOR_PASSWORD_RESET_DUE_TO_MULTIPLE_LOGIN_FAILS =
        "alphaStoreRabbitMqPasswordResetDueToMultipleLoginFails"
    const val RABBIT_MQ_ROUTE_KEY_FOR_PASSWORD_RESET_DUE_TO_MULTIPLE_LOGIN_FAILS =
        "alphaStoreRabbitMqPasswordResetDueToMultipleLoginFailsKey"

    //for change password
    const val RABBIT_MQ_QUEUE_FOR_CHANGE_PASSWORD_EVENT = "alphaStoreRabbitMqChangePasswordEvent"
    const val RABBIT_MQ_ROUTE_KEY_FOR_CHANGE_PASSWORD_EVENT = "alphaStoreRabbitMqChangePasswordEventKey"

    //for forgot password
    const val RABBIT_MQ_QUEUE_FOR_FORGOT_PASSWORD_EVENT = "alphaStoreRabbitMqForgotPasswordEvent"
    const val RABBIT_MQ_ROUTE_KEY_FOR_FORGOT_PASSWORD_EVENT = "alphaStoreRabbitMqForgotPasswordEventKey"

    //New sub user created
    const val RABBIT_MQ_QUEUE_FOR_NEW_SUB_USER_CREATED =
        "alphaStoreRabbitMqNewSubUserCreated"
    const val RABBIT_MQ_ROUTE_KEY_FOR_NEW_SUB_USER_CREATED =
        "alphaStoreRabbitMqNewSubUserCreatedKey"

    //New user created
    const val RABBIT_MQ_QUEUE_FOR_NEW_USER_CREATED =
        "alphaStoreRabbitMqNewUserCreated"
    const val RABBIT_MQ_ROUTE_KEY_FOR_NEW_USER_CREATED =
        "alphaStoreRabbitMqNewUserCreatedKey"

    // For profile status updated
    const val RABBIT_MQ_QUEUE_FOR_USER_PROFILE_STATUS_UPDATED = "alphaStoreRabbitMqUserProfileStatusUpdatedQueue"
    const val RABBIT_MQ_ROUTE_KEY_FOR_USER_PROFILE_STATUS_UPDATED = "alphaStoreRabbitMqUserProfileStatusUpdatedKey"
}