package fr.cdudit.weather.models

import com.google.android.gms.maps.model.LatLng
import java.io.Serializable

data class City(
    val name: String,
    val latLng: LatLng,
    val degree: Double? = null,
    val logo: String? = null
): Serializable
