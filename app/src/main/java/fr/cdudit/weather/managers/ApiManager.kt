package fr.cdudit.weather.managers

import fr.cdudit.weather.models.WeatherData
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherService {
    @GET("weather?units=metric&appid=")
    suspend fun getWeather(@Query("q") city: String): Response<WeatherData>
}

object ApiManager {
    var service: WeatherService
    private var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org/data/2.5/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    init {
        service = retrofit.create(WeatherService::class.java)
    }
}