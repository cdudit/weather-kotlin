package fr.cdudit.weather.features.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.gms.maps.model.LatLng
import fr.cdudit.weather.databinding.FragmentHomeBinding
import fr.cdudit.weather.managers.ApiManager
import fr.cdudit.weather.managers.LocationManager
import fr.cdudit.weather.managers.NetworkManager
import fr.cdudit.weather.models.City
import fr.cdudit.weather.models.Coord
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private var city: City? = null
    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private val viewModel: HomeViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        this.binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return this.binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
    }

    override fun onResume() {
        super.onResume()
        city?.let { this.setupCity(it.name) }
    }

    private fun setupListeners() {
        this.binding.buttonOK.setOnClickListener {
            setupCity(this.binding.inputTextCity.text.toString())
        }

        this.binding.buttonWeather.setOnClickListener {
            doIfNetworkAvailable {
                city?.let {
                    findNavController().navigate(
                        HomeFragmentDirections.navigateToMapsFragment(it)
                    )
                }
            }
        }
    }

    private fun setupCity(cityName: String) {
        doIfNetworkAvailable {
            viewModel.getWeather(
                coroutineScope,
                cityName,
                onSuccess = {
                    this@HomeFragment.city = City(
                        cityName,
                        this@HomeFragment.viewModel.getLatLng(it.coord, cityName, requireContext()),
                        it.main?.temp,
                        it.weather.first().icon
                    )
                    requireActivity().runOnUiThread {
                        this@HomeFragment.setupUiWithCity()
                    }
                },
                onError = {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
                }
            )
        }
    }

    private fun setupUiWithCity() {
        this.binding.cityTitle.text = city?.name
        city?.degree?.let { this.binding.cityDegree.text = "$it°C" }
        Glide.with(this)
            .load("https://openweathermap.org/img/wn/${city?.logo}@2x.png")
            .into(this.binding.cityWeather)
        this.binding.buttonWeather.isVisible = true
    }

    private fun doIfNetworkAvailable(callback: () -> Unit) {
        if (viewModel.isNetworkAvailable(requireContext())) {
            callback()
        } else {
            Toast.makeText(requireContext(), "Network unavailable", Toast.LENGTH_LONG).show()
        }
    }
}