package com.suhane.flickr.repository.model.photos.getinfo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Owner {

    @SerializedName("nsid")
    @Expose
    var nsid: String? = null
    @SerializedName("username")
    @Expose
    var username: String? = null
    @SerializedName("realname")
    @Expose
    var realname: String? = null
    @SerializedName("location")
    @Expose
    var location: String? = null
    @SerializedName("iconserver")
    @Expose
    var iconserver: String? = null
    @SerializedName("iconfarm")
    @Expose
    var iconfarm: Int? = null
    @SerializedName("path_alias")
    @Expose
    var pathAlias: String? = null

}
