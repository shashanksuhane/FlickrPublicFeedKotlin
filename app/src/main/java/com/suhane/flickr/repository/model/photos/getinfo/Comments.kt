package com.suhane.flickr.repository.model.photos.getinfo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Comments {

    @SerializedName("_content")
    @Expose
    var content: Int? = null

}
