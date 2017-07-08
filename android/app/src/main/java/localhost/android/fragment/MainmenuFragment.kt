package localhost.android.fragment

import android.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import localhost.android.R
import localhost.android.config.Network
import localhost.android.network.NetworkService

public class MainMenuFragment : Fragment() {
    companion object {
        fun newInstance(): MainMenuFragment {
            val fragment = MainMenuFragment()
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val mainMenu: View = inflater.inflate(R.layout.fragment_mainmenu, container, false)

        val talkButton: Button = mainMenu.findViewById(R.id.talkButton) as Button
        val mypageButton: Button = mainMenu.findViewById(R.id.mypageButton) as Button
        val shopButton: Button = mainMenu.findViewById(R.id.shopButton) as Button
        val missionButton: Button = mainMenu.findViewById(R.id.missionButton) as Button

        talkButton.setOnClickListener { view ->
            NetworkService.sendHttpPostRequest(Network.LOGIN,
                    hashMapOf("email" to "natori.ryo@gmail.com", "pass" to "05d49692b755f99c4504b510418efeeeebfd466892540f27acf9a31a326d6504"),
                    { status, response ->
                        println(status)
                        println(response.toString())
                    }
            )
        }
        mypageButton.setOnClickListener { view ->
            Log.i("test", "testdayo")
        }
        shopButton.setOnClickListener { view ->
            Log.i("test", "testdayo")
        }
        missionButton.setOnClickListener { view ->
            Log.i("test", "testdayo")
        }
        return mainMenu
    }
}
