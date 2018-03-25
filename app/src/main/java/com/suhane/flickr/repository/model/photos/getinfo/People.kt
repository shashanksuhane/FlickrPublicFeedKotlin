package com.suhane.flickr.repository.model.photos.getinfo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class People {

    @SerializedName("haspeople")
    @Expose
    var haspeople: Int? = null

}
