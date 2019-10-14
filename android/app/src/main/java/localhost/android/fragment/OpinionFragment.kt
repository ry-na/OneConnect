package localhost.android.fragment

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.view.*
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_opinion.*
import localhost.android.MainActivity
import localhost.android.Presenter.InfoFragmentPresenter

import localhost.android.R
import localhost.android.model.ParticipantResponseData
import localhost.android.model.ReplyResponseData

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [OpinionFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [OpinionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OpinionFragment : DialogFragment()  {
    private var title = ""
    private var detail = ""
    private var oId = ""
    private var user_id = ""
    private val presenter = InfoFragmentPresenter()
    lateinit var c: Context
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        Dialog(activity).let { dialog_object ->

            // タイトル非表示
            dialog_object.window!!.requestFeature(Window.FEATURE_NO_TITLE)
            // フルスクリーン
            dialog_object.window!!.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN)
            dialog_object.setContentView(R.layout.fragment_opinion)
            // 背景を透明にする
            dialog_object.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            dialog_object.sendnew_button.setOnClickListener {
                //TODO:返信ボタン押下
                val reply = dialog_object.reply_box2.text.toString()

                presenter.sendReply(c, oId, reply, { status: Boolean, response: List<ReplyResponseData?> -> replyResult(status, response) })
                //返信送信後、更新

                presenter.getReply(c, oId, { status: Boolean, response: List<ReplyResponseData?> -> replyResult(status, response) })
            }
            dialog_object.func_button.setOnClickListener {
                //TODO:完了/参加ボタン押下
                if ((activity as MainActivity).user_id == user_id ){
                    //完了ボタン
                    val callback = {responseData: ByteArray ->
                        println(String(responseData))
                        dismiss()
                    }
                    presenter.opinionComplete(c, oId, callback)
                }else {
                    //参加ボタン
                    presenter.Participant(c,oId, "",{ status: Boolean, response: List<ParticipantResponseData?> -> replyResult_part(status, response) })
                }
             }
            dialog_object.close_button.setOnClickListener { dismiss() }
            (dialog_object.findViewById(R.id.m_title) as TextView).text = title
            //func_button
            if ((activity as MainActivity).user_id == user_id ){
                (dialog_object.findViewById(R.id.func_button) as Button).text = "完了"
            }else {
                (dialog_object.findViewById(R.id.func_button) as Button).text = "参加"

            }
            return dialog_object
        }
    }

    override fun onAttach(context: Context) {
        c = context
        presenter.getReply(context, oId, { status: Boolean, response: List<ReplyResponseData?> -> replyResult(status, response) })//返信データ取得・表示
        super.onAttach(context)
    }

    override fun show(m: android.support.v4.app.FragmentManager, tag: String) {
        super.show(m, tag)
        val data = tag.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        this.title = data[0]
        this.detail = data[1]
        this.oId = data[2]
        this.user_id = data[3]

    }
    private fun replyResult_part(status: Boolean, response: List<ParticipantResponseData?>) {

    }
    private fun replyResult(status: Boolean, response: List<ReplyResponseData?>) {
        if (!status) return
        //TODO: ここから仮実装。デザイン実装とともに作り直す必要あり
        val responseList = arrayListOf<String>()
        response.forEach {
            if (it is ReplyResponseData) {
                println(it.toString())
                responseList.add("[" + it.user_id + "]" + it.reply_message + "(" + it.created_at + ")")
            }
        }
        val arrayAdapter = ArrayAdapter<String>(
                activity,
                R.layout.list_replies,
                responseList
        )
        val myListView = dialog.replies_list
        if (myListView is ListView) {
            myListView.adapter = arrayAdapter
        }
    }
}// Required empty public constructor
