package localhost.android

import android.app.FragmentTransaction
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapFragment
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.*
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem
import com.mikepenz.materialdrawer.model.SectionDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.Nameable
import kotlinx.android.synthetic.main.activity_main.*
import localhost.android.Presenter.MainActivityPresenter
import localhost.android.fragment.InfoFragment
import localhost.android.fragment.OpinionFragment
import localhost.android.fragment.Opinion_new
import localhost.android.model.OpinionResponseData
import java.util.*


class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    private val places: ArrayList<Place> = ArrayList()
    private val presenter = MainActivityPresenter()
    val idlist = kotlin.collections.HashMap<String , Int>()
    override fun onMapReady(googleMap: GoogleMap) {
        googleMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(this, R.raw.map)
        )
        googleMap.run {
            setMinZoomPreference(8.0f)
            setMaxZoomPreference(19.0f)
            val d = LatLng(35.455865, 139.633103)
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(d, 12F))
            var i=1
            for (p in places) {
                val ll = LatLng(p.lat.toDouble(), p.lng.toDouble())

             var   m= googleMap.addMarker(MarkerOptions()
                        .position(ll)
                        .title(p.title)
                        .snippet(p.type.toString() + p.detail)
                        .icon(BitmapDescriptorFactory.defaultMarker(if (p.type == 0) BitmapDescriptorFactory.HUE_RED else BitmapDescriptorFactory.HUE_BLUE)) //TODO: 仮に色分け
                )
                idlist.put(m.id,p.id)
                i++
            }
            // TODO: IDをFragment側で取得できるようにする
            setOnMapLongClickListener { latlng ->
                var newFragment = Opinion_new()
                newFragment.show(supportFragmentManager, latlng.latitude.toString() + "," + latlng.longitude.toString() )
            }
            setOnMarkerClickListener { marker ->
                val detail = marker.snippet.substring(1, marker.snippet.lastIndex)

                if (marker.snippet.substring(0, 1).equals("0")) {
                    var newFragment = InfoFragment()
                    newFragment.show(supportFragmentManager, marker.title + "," + detail)
                } else if (marker.snippet.substring(0, 1).equals("1")) { //意見
                    var newFragment = OpinionFragment()
                    newFragment.show(supportFragmentManager, marker.title + "," + detail + "," + idlist.get(marker.id))
                }
                false
            }
        }
    }

    private lateinit var result: Drawer
    private lateinit var mf: MapFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(tool_bar)
        //TODO:Placesに追加
        //仮データ
        val p = Place()
     /*   p.run {
            id = 0
            type = 0
            title = "テスト"
            detail = "ああああ"
            lat = 35.774337
            lng = 139.707746
        }*/
     //   places.add(p)
        presenter.getOpinion(this, { status: Boolean, response: List<OpinionResponseData?> -> opinionResult(status, response) })
        mf = MapFragment.newInstance()
        mf.getMapAsync(this@MainActivity)
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

    private fun opinionResult(status: Boolean, response: List<OpinionResponseData?>) {
        if (!status) return
        response.forEach { data ->
            if (data is OpinionResponseData) {
                val p = Place()

                p.run {
                    id = data.id.toInt()
                    type = 1
                    title = data.opinion_message
                    detail = data.opinion_message
                    lat = data.lat.toDouble()
                    lng = data.lon.toDouble()
                }
                places.add(p)
            }
        }
    }
}

