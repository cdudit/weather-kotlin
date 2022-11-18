package fr.cdudit.weather.managers

import android.content.Context
import android.location.Geocoder
import com.google.android.gms.maps.model.LatLng
import java.util.*

object LocationManager {
    fun getLatLngFromLocationName(context: Context, cityName: String): LatLng {
        val geocoder = Geocoder(context, Locale.getDefault())
        val address = geocoder.getFromLocationName(cityName, 1).first()
        return LatLng(address.latitude, address.longitude)
    }
}