package localhost.android.fragment

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.view.Window
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_info.*
import localhost.android.R
import localhost.android.config.Network
import localhost.android.model.PostResponseData
import localhost.android.model.ReplyResponseData
import localhost.android.network.NetworkService.Companion.sendHttpPostRequest
import java.io.Serializable

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [InfoFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [InfoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class InfoFragment : DialogFragment() {
    internal var title = ""
    internal var detail = ""
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val dialog = Dialog(activity)
        // タイトル非表示
        dialog.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        // フルスクリーン
        dialog.window!!.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN)
        dialog.setContentView(R.layout.fragment_info)
        // 背景を透明にする
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.findViewById(R.id.connect_button).setOnClickListener {
            //TODO:コネクトボタン押下
            getReply()
        }
        dialog.findViewById(R.id.close_button).setOnClickListener { dismiss() }
        (dialog.findViewById(R.id.m_title) as TextView).text = title
        (dialog.findViewById(R.id.m_detail) as TextView).text = detail
        return dialog
    }

    override fun show(m: android.support.v4.app.FragmentManager, tag: String) {
        super.show(m, tag)
        val data = tag.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        this.title = data[0]
        this.detail = data[1]
    }

    private fun getReply() = sendHttpPostRequest(Network.OPINION_API_URL + Network.REPLY, hashMapOf("opinion_id" to this.id.toString()), { status: Boolean, response: List<Serializable?>? -> replyResult(status, response) })

    private fun replyResult(status: Boolean, response: List<Serializable?>?) {
        if (status && response != null) {
            val responseList = arrayListOf<String>()
            response.forEach {
                if (it is PostResponseData) {
                    responseList.add("$it.user_id $it.reply_message  $it.created_at")
                }
            }
            val arrayAdapter = ArrayAdapter<String>(
                    activity,
                    R.layout.list_replies,
                    responseList
            )
            val myListView = dialog.findViewById(R.id.replies_list)
            if (myListView is ListView) {
                myListView.adapter = arrayAdapter
            }
        }
    }

}
