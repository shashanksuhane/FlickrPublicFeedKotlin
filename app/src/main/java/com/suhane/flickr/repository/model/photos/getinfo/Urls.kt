package com.suhane.flickr.repository.model.photos.getinfo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Urls {

    @SerializedName("url")
    @Expose
    var url: List<Url>? = null

}
