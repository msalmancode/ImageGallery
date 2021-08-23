package com.application.imagegallery.data.model.WebServiceResponse

import com.application.imagegallery.data.model.WebServiceRequest.HitsResponse

open class BaseResponse {

    var total: Int? = null
    var totalHits: Int? = null
    var hits: ArrayList<HitsResponse>? = null

}