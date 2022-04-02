package com.planradar.assessment.data.model

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity
@Keep
@Parcelize
data class City(
  @PrimaryKey @SerializedName("id") val id: Int,
  @SerializedName("name") val name: String? = null,
  @SerializedName("country") val countryCode: String? = null
) : Parcelable {
  override fun toString(): String {
    return if (name.isNullOrEmpty()) "" else "$name, " +
      if (countryCode.isNullOrEmpty()) "" else "$countryCode"
  }
}
