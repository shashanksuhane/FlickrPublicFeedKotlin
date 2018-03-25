package com.suhane.flickr.repository.model.photos.getinfo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Editability {

    @SerializedName("cancomment")
    @Expose
    var cancomment: Int? = null
    @SerializedName("canaddmeta")
    @Expose
    var canaddmeta: Int? = null

}
