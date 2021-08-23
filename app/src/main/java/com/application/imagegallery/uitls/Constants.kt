package com.application.imagegallery.uitls

open class Constants {
    companion object INSTANCE {
        var READ_TIMEOUT = 30L
        var CONNECT_TIMEOUT = READ_TIMEOUT

        const val BASE_URL = "https://pixabay.com/"
        const val API_KEY = "23054356-466770f03eea5baf030cc13ed"
    }
}
