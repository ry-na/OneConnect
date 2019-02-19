package localhost.android.fragment

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.view.*
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_opinion.*
import localhost.android.Presenter.InfoFragmentPresenter

import localhost.android.R
import localhost.android.model.ReplyResponseData

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [Opinion_new.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [Opinion_new.newInstance] factory method to
 * create an instance of this fragment.
 */
class Opinion_new : DialogFragment() {
    var lat = 0.0
    var lng = 0.0
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        Dialog(activity).let {
            // タイトル非表示
            it.window!!.requestFeature(Window.FEATURE_NO_TITLE)
            // フルスクリーン
            it.window!!.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN)
            it.setContentView(R.layout.fragment_opinion)
            // 背景を透明にする
            it.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            it.connect_button.setOnClickListener {
                //TODO:発信ボタン押下

            }
            it.close_button.setOnClickListener { dismiss() }
            (it.findViewById(R.id.m_title) as TextView).text = "場所：" + lat + "," + lng
            return it
        }
    }

    override fun show(m: android.support.v4.app.FragmentManager, tag: String) {
        super.show(m, tag)
        val data = tag.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        this.lat = data[0].toDouble()
        this.lng = data[1].toDouble()
    }

}// Required empty public constructor
