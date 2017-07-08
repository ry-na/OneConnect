package localhost.android

import android.app.FragmentTransaction
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapFragment
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem
import com.mikepenz.materialdrawer.model.SectionDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.Nameable
import kotlinx.android.synthetic.main.activity_main.*
import localhost.android.config.Network
import localhost.android.fragment.InfoFragment
import localhost.android.model.GetResponseData
import localhost.android.network.NetworkService
import java.io.Serializable
import java.util.*


class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    var Places: ArrayList<Place> = ArrayList()
    override fun onMapReady(googleMap: GoogleMap) {
        val success = googleMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                        this, R.raw.map))
        googleMap.run {
            setMinZoomPreference(8.0f)
            setMaxZoomPreference(19.0f)
            val d = LatLng(35.455865, 139.633103)
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(d, 12F));
            for (p in Places) {
                val ll = LatLng(p.lat.toDouble(), p.lng.toDouble())
                val m = googleMap.addMarker(MarkerOptions()
                        .position(ll)
                        .title(p.title)
                        .snippet(p.detail)
                )

            }
            setOnMarkerClickListener { marker ->
                val detail = marker.getSnippet()
                val newFragment = InfoFragment()
                newFragment.show(getSupportFragmentManager(), marker.getTitle() + "," + detail)
                false
            }
        }
    }

    // internal var tool_bar: Toolbar? =null
    internal var result: Drawer? = null
    //  internal var map: MapFragment? = null
    var mf: MapFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(tool_bar)
//TODO:Placesに追加
        //仮データ
        val p = Place()
        p.id = 0
        p.title = "テスト"
        p.detail = "ああああ"
        p.lat = 35.774337
        p.lng = 139.707746
        Places.add(p)
        getOpinion()
        mf = MapFragment.newInstance()
        mf!!.getMapAsync(this@MainActivity)
        //  if (map == null) map = MapFragment()
        val item1 = PrimaryDrawerItem().withName("ONE CONNECT").withDescription("<アカウント名>")
        val item2 = SecondaryDrawerItem().withName("マップ")
        val item3 = SecondaryDrawerItem().withName("項目2")
        result = DrawerBuilder()
                .withActivity(this)
                .withToolbar(tool_bar)
                .addDrawerItems(
                        item1,
                        item2,
                        SectionDrawerItem().withName("区切り1"),
                        item3,
                        SectionDrawerItem().withName("区切り2")
                )
                .withOnDrawerItemClickListener { view, position, drawerItem ->
                    if (drawerItem != null) {
                        if (drawerItem is Nameable<*>) {
                            tool_bar.title = drawerItem.name.getText(this@MainActivity)
                        }
                        if (position == 1) {
                            val ft = fragmentManager.beginTransaction()
                            ft.replace(R.id.fragment_container, mf)
                            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            ft.addToBackStack(null)
                            ft.commit()
                        }
                        if (position == 3) {
                            /*   val ft = fragmentManager.beginTransaction()
                               ft.replace(R.id.fragment_container, mf)
                               ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                               ft.addToBackStack(null)
                               ft.commit()*/
                        }
                        /*       if (onFilterChangedListener != null) {
                        onFilterChangedListener.onFilterChanged(drawerItem.getIdentifier());
                    }*/
                    }
                    //      Toast.makeText(getApplicationContext(), "Item Selected:-" + i + "  " + result.getPosition(iDrawerItem), Toast.LENGTH_LONG).show();
                    false
                }
                .build()
    }


    fun getOpinion() {
        NetworkService.sendHttpGetRequest(Network.OPINION_API_URL + Network.GET) { status: Boolean, response: List<Serializable?>? -> opinionResult(status, response) }
    }

    private fun opinionResult(status: Boolean, response: List<Serializable?>?) {
        if (status && response != null) {
            response.forEach {
                if (it is GetResponseData) {
                    println(it.toString())
                    val p = Place()
                    p.id = it.opinion_id.toInt()
                    p.title = it.opinion_message
                    p.detail = it.opinion_message
                    p.lat = it.lat.toDouble()
                    p.lng = it.lon.toDouble()
                    Places.add(p)
                }
            }
        }
    }
}

