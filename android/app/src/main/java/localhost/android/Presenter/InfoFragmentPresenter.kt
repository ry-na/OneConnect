package localhost.android.Presenter

import localhost.android.model.ReplyResponseData
import localhost.android.network.NetworkInterface
import localhost.android.network.NetworkService
import retrofit2.adapter.rxjava.HttpException
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.*

class InfoFragmentPresenter {
    fun getReply(body: HashMap<String, String>?,
                 callback: (status: Boolean, response: List<ReplyResponseData?>) -> Unit) {
        val retrofit = NetworkService.getRetrofit()
        retrofit.create(NetworkInterface::class.java)
                .reply("", body)    // TODO: 第1引数で if(sId == null) "" else sId こんな関数を呼ぶ
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<List<ReplyResponseData?>>() {
                    /**
                     * 完了
                     */
                    override fun onCompleted() {
                        println("OK")
                    }

                    /**
                     * 失敗
                     */
                    override fun onError(e: Throwable?) {
                        e!!.printStackTrace()
                        if (e is HttpException) {
                            val a = object : Annotation {}
                            callback(false, retrofit.responseBodyConverter<List<ReplyResponseData?>>(ReplyResponseData::class.java, arrayOf(a))
                                    .convert(e.response().errorBody()))
                        } else callback(false, listOf(ReplyResponseData()))
                    }

                    /**
                     * 成功
                     */
                    override fun onNext(t: List<ReplyResponseData?>) {
                        callback(true, t)
                    }
                })
    }
}

