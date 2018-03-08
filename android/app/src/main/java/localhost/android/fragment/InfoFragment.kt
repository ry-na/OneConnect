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
import localhost.android.Presenter.InfoFragmentPresenter
import localhost.android.R
import localhost.android.model.ReplyResponseData

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [InfoFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [InfoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class InfoFragment : DialogFragment() {
    private var title = ""
    private var detail = ""

    private val presenter = InfoFragmentPresenter()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        Dialog(activity).let {
            // タイトル非表示
            it.window!!.requestFeature(Window.FEATURE_NO_TITLE)
            // フルスクリーン
            it.window!!.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN)
            it.setContentView(R.layout.fragment_info)
            // 背景を透明にする
            it.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            it.findViewById(R.id.connect_button).setOnClickListener {
                //TODO:コネクトボタン押下
                // TODO: IDを取得できるようにする
                presenter.getReply(context,(hashMapOf("id" to id.toString())), { status: Boolean, response: List<ReplyResponseData?> -> replyResult(status, response) });
            }
            it.findViewById(R.id.close_button).setOnClickListener { dismiss() }
            (it.findViewById(R.id.m_title) as TextView).text = title
            (it.findViewById(R.id.m_detail) as TextView).text = detail
            return it
        }
    }

    override fun show(m: android.support.v4.app.FragmentManager, tag: String) {
        super.show(m, tag)
        val data = tag.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        this.title = data[0]
        this.detail = data[1]
    }

    private fun replyResult(status: Boolean, response: List<ReplyResponseData?>) {
        if (!status) return
        // ここから仮実装。デザイン実装とともに作り直す必要あり
        val responseList = arrayListOf<String>()
        response.forEach {
            if (it is ReplyResponseData) {
                println(it.toString())
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
