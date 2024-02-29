package com.example.sendproduct

import android.os.Parcel
import android.os.Parcelable

data class Product(
    val id: Int,
    val name: String,
    val description: String,
    val seller: String,
    val price: Double,
    val pictureUri: String
) : Parcelable {
    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(id)
        dest.writeString(name)
        dest.writeString(description)
        dest.writeString(seller)
        dest.writeDouble(price)
        dest.writeString(pictureUri)
    }

    companion object {
        @JvmField
        val CREATOR = object : Parcelable.Creator<Product> {
            override fun createFromParcel(source: Parcel): Product {
                return Product(
                    source.readInt(),
                    source.readString() ?: "",
                    source.readString() ?: "",
                    source.readString() ?: "",
                    source.readDouble(),
                    source.readString() ?: "",
                )
            }

            override fun newArray(size: Int): Array<Product?> {
                return arrayOfNulls(size)
            }
        }
    }
}