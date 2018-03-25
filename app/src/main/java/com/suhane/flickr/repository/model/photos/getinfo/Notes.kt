package com.suhane.flickr.repository.model.photos.getinfo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Notes {

    @SerializedName("note")
    @Expose
    var note: List<Any>? = null

}
