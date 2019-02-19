package localhost.android.fragment

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.view.*
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_opinion_new.*
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
    private val presenter = InfoFragmentPresenter()
    var lat = 0.0
    var lng = 0.0
    lateinit var c: Context
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        Dialog(activity).let {d->
            // タイトル非表示
            d.window!!.requestFeature(Window.FEATURE_NO_TITLE)
            // フルスクリーン
            d.window!!.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN)
            d.setContentView(R.layout.fragment_opinion_new)
            // 背景を透明にする
            d.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            d.sendnew_button.setOnClickListener {
                //TODO:発信ボタン押下
                val send = d.new_box2.text.toString()
                presenter.register(c, send, lat,lng,{ status: Boolean, response: List<ReplyResponseData?> -> replyResult(status, response) })
            }
            d.close_button.setOnClickListener { dismiss() }
            d.title.text = "場所：" + lat + "," + lng
            return d
        }
    }

    override fun onAttach(context: Context) {
        c = context
        super.onAttach(context)
    }
    override fun show(m: android.support.v4.app.FragmentManager, tag: String) {
        super.show(m, tag)
        val data = tag.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        this.lat = data[0].toDouble()
        this.lng = data[1].toDouble()
    }
    private fun replyResult(status: Boolean, response: List<ReplyResponseData?>) {

    }

}// Required empty public constructor
