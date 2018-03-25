package com.suhane.flickr.repository.model.photos.getinfo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Tag {

    @SerializedName("id")
    @Expose
    var id: String? = null
    @SerializedName("author")
    @Expose
    var author: String? = null
    @SerializedName("authorname")
    @Expose
    var authorname: String? = null
    @SerializedName("raw")
    @Expose
    var raw: String? = null
    @SerializedName("_content")
    @Expose
    var content: String? = null
    @SerializedName("machine_tag")
    @Expose
    var machineTag: String? = null

}
