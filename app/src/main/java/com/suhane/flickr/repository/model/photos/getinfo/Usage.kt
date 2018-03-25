package com.suhane.flickr.repository.model.photos.getinfo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Usage {

    @SerializedName("candownload")
    @Expose
    var candownload: Int? = null
    @SerializedName("canblog")
    @Expose
    var canblog: Int? = null
    @SerializedName("canprint")
    @Expose
    var canprint: Int? = null
    @SerializedName("canshare")
    @Expose
    var canshare: Int? = null

}
