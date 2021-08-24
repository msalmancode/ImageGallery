package com.application.imagegallery.data.webService.request
 class ApiRequest {

    private var key: String? = null
    private var q: String? = null
    private var image_type: String? = null

     constructor(key: String?, q: String?, image_type: String?) {
         this.key = key
         this.q = q
         this.image_type = image_type
     }


     override fun toString(): String {
        return "ApiRequest(key=$key, q=$q, image_type=$image_type)"
    }


}
