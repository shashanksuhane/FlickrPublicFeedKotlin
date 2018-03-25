package com.suhane.flickr.repository.model.photos.search

import android.os.Parcel
import android.os.Parcelable

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Photo(inParcel: Parcel) : Parcelable {

    @SerializedName("id")
    @Expose
    var id: String? = null
    @SerializedName("owner")
    @Expose
    var owner: String? = null
    @SerializedName("secret")
    @Expose
    var secret: String? = null
    @SerializedName("server")
    @Expose
    var server: String? = null
    @SerializedName("farm")
    @Expose
    var farm: Int? = null
    @SerializedName("title")
    @Expose
    var title: String? = null
    @SerializedName("ispublic")
    @Expose
    var ispublic: Int? = null
    @SerializedName("isfriend")
    @Expose
    var isfriend: Int? = null
    @SerializedName("isfamily")
    @Expose
    var isfamily: Int? = null
    @SerializedName("url_s")
    @Expose
    var urlS: String? = null
    @SerializedName("height_s")
    @Expose
    var heightS: String? = null
    @SerializedName("width_s")
    @Expose
    var widthS: String? = null
    @SerializedName("url_c")
    @Expose
    var urlC: String? = null
    @SerializedName("height_c")
    @Expose
    var heightC: String? = null
    @SerializedName("width_c")
    @Expose
    var widthC: String? = null

    init {
        this.id = inParcel.readString()
        this.owner = inParcel.readString()
        this.secret = inParcel.readString()
        this.server = inParcel.readString()
        this.farm = inParcel.readInt()
        this.title = inParcel.readString()
        this.ispublic = inParcel.readInt()
        this.isfriend = inParcel.readInt()
        this.isfamily = inParcel.readInt()
        this.urlS = inParcel.readString()
        this.heightS = inParcel.readString()
        this.widthS = inParcel.readString()
        this.urlC = inParcel.readString()
        this.heightC = inParcel.readString()
        this.widthC = inParcel.readString()
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, i: Int) {
        parcel.writeString(this.id)
        parcel.writeString(this.owner)
        parcel.writeString(this.secret)
        parcel.writeString(this.server)
        parcel.writeInt(this.farm!!)
        parcel.writeString(this.title)
        parcel.writeInt(this.ispublic!!)
        parcel.writeInt(this.isfriend!!)
        parcel.writeInt(this.isfamily!!)
        parcel.writeString(this.urlS)
        parcel.writeString(this.heightS)
        parcel.writeString(this.widthS)
        parcel.writeString(this.urlC)
        parcel.writeString(this.heightC)
        parcel.writeString(this.widthC)
    }

    override fun toString(): String {
        return "Photo{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                '}'
    }

    companion object {

        @JvmField val CREATOR: Parcelable.Creator<Photo> = object : Parcelable.Creator<Photo> {
            override fun createFromParcel(inParcel: Parcel): Photo {
                return Photo(inParcel)
            }

            override fun newArray(size: Int): Array<Photo?> {
                return arrayOfNulls(size)
            }
        }
    }

}
