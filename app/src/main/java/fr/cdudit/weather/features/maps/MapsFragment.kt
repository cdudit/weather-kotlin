package fr.cdudit.weather.features.maps

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions
import fr.cdudit.weather.databinding.FragmentMapsBinding
import fr.cdudit.weather.models.City

class MapsFragment : Fragment() {
    private val args: MapsFragmentArgs by navArgs()
    private lateinit var binding: FragmentMapsBinding
    private lateinit var city: City

    private val callback = OnMapReadyCallback { googleMap ->
        val marker = MarkerOptions()
            .position(city.latLng)
            .title(city.name)
        googleMap.addMarker(marker)
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(city.latLng))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        this.binding = FragmentMapsBinding.inflate(layoutInflater, container, false)
        this.city = this.args.city
        return this.binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(this.binding.map.id) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
}