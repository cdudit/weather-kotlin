package fr.cdudit.weather.models

import com.google.gson.annotations.SerializedName


data class WeatherData(
    @SerializedName("coord")    var coord: Coord? = Coord(),
    @SerializedName("weather")  var weather: ArrayList<Weather> = arrayListOf(),
    @SerializedName("base")     var base: String? = null,
    @SerializedName("main")     var main: Main? = Main(),
)

data class Coord (
    @SerializedName("lon") var lon: Double? = null,
    @SerializedName("lat") var lat: Double? = null
)

data class Weather (
    @SerializedName("id"         ) var id          : Int?    = null,
    @SerializedName("main"       ) var main        : String? = null,
    @SerializedName("description") var description : String? = null,
    @SerializedName("icon"       ) var icon        : String? = null
)

data class Main (
    @SerializedName("temp"      ) var temp      : Double? = null,
    @SerializedName("feels_like") var feelsLike : Double? = null,
    @SerializedName("temp_min"  ) var tempMin   : Double? = null,
    @SerializedName("temp_max"  ) var tempMax   : Double? = null,
    @SerializedName("pressure"  ) var pressure  : Int?    = null,
    @SerializedName("humidity"  ) var humidity  : Int?    = null
)
