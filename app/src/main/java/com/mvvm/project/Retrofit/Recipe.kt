package com.mvvm.project.Retrofit

import android.os.Parcel
import android.os.Parcelable

data class Recipe(
    var title: String?, var publisher: String?, var ingredients: Array<String>?,
    var recipe_id: String?, var image_url: String?, var social_rank:Float
                  ):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.createStringArray() as Array<String>?,
        parcel.readString(),
        parcel.readString(),
        parcel.readFloat()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(publisher)
        parcel.writeStringArray(ingredients)
        parcel.writeString(recipe_id)
        parcel.writeString(image_url)
        parcel.writeFloat(social_rank)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Recipe> {
        override fun createFromParcel(parcel: Parcel): Recipe {
            return Recipe(parcel)
        }

        override fun newArray(size: Int): Array<Recipe?> {
            return arrayOfNulls(size)
        }
    }
}