package com.suhane.flickr.repository.model.photos.getinfo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Visibility {

    @SerializedName("ispublic")
    @Expose
    var ispublic: Int? = null
    @SerializedName("isfriend")
    @Expose
    var isfriend: Int? = null
    @SerializedName("isfamily")
    @Expose
    var isfamily: Int? = null

}
