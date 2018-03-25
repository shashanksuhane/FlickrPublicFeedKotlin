package com.suhane.flickr.repository.model.photos.getinfo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Dates {

    @SerializedName("posted")
    @Expose
    var posted: String? = null
    @SerializedName("taken")
    @Expose
    var taken: String? = null
    @SerializedName("takengranularity")
    @Expose
    var takengranularity: Int? = null
    @SerializedName("takenunknown")
    @Expose
    var takenunknown: Int? = null
    @SerializedName("lastupdate")
    @Expose
    var lastupdate: String? = null

}
