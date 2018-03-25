package com.suhane.flickr.repository.model.photos.getinfo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GetInfoResponse {

    @SerializedName("photo")
    @Expose
    var photo: Photo? = null
    @SerializedName("stat")
    @Expose
    var stat: String? = null

}
