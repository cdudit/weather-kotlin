package fr.cdudit.weather.features.home

import android.content.Context
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import fr.cdudit.weather.managers.ApiManager
import fr.cdudit.weather.managers.LocationManager
import fr.cdudit.weather.managers.NetworkManager
import fr.cdudit.weather.models.Coord
import fr.cdudit.weather.models.WeatherData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    fun getLatLng(coord: Coord?, cityName: String, context: Context): LatLng {
        return if (coord?.lat != null && coord.lon != null) {
            LatLng(coord.lat!!, coord.lon!!)
        } else {
            LocationManager.getLatLngFromLocationName(context, cityName)
        }
    }

    fun isNetworkAvailable(context: Context): Boolean = NetworkManager.isNetworkAvailable(context)

    fun getWeather(
        scope: CoroutineScope,
        cityName: String,
        onSuccess: (WeatherData) -> Unit,
        onError: (String) -> Unit
    ) {
        scope.launch {
            val response = ApiManager.service.getWeather(cityName)
            if (response.isSuccessful && response.body() != null) {
                response.body()?.let(onSuccess)
            } else {
                onError(response.errorBody().toString())
            }
        }
    }
}