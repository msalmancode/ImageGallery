package com.application.imagegallery.data.webServices.Response

open class BaseResponse {

    var total: Int? = null
    var totalHits: Int? = null
    var hits: ArrayList<HitsResponse>? = null

}