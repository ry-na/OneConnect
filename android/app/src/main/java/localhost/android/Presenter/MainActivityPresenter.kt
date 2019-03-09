package localhost.android.Presenter

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.util.Base64
import localhost.android.model.OpinionResponseData
import localhost.android.network.NetworkInterface
import localhost.android.network.NetworkService
import localhost.android.util.AndroidKeyStoreManager
import retrofit2.adapter.rxjava.HttpException
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 *
 * @author Ryo Natori (ryo.natori0809@gmail.com)
 */
class MainActivityPresenter {
    fun getOpinion(context: Context, callback: (status: Boolean, response: List<OpinionResponseData?>) -> Unit) {
        val retrofit = NetworkService.getRetrofit()
        val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        // val mKeyStoreManager: AndroidKeyStoreManager = AndroidKeyStoreManager(context)
        val mKeyStoreManager: AndroidKeyStoreManager = AndroidKeyStoreManager(context)
        val SID_ = Base64.decode(sharedPreferences.getString("SID", "AAAAAAAA"), Base64.DEFAULT)
        val SID: String = if (SID_.isNotEmpty()) String(mKeyStoreManager.decrypt(SID_)) else "AAAAAA" //Session ID
        retrofit.create(NetworkInterface::class.java)
                .opinion(SID)    // TODO: 第二引数で if(sId == null) "" else sId こんな関数を呼ぶ
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<List<OpinionResponseData?>>() {

                    /**
                     * 完了
                     */
                    override fun onCompleted() {
                        println("onCompleted!!")
                    }

                    /**
                     * 失敗
                     */
                    override fun onError(e: Throwable?) {
                        if (e is HttpException) {
                            val a = object : Annotation {}
                            callback(false, retrofit.responseBodyConverter<List<OpinionResponseData?>>(OpinionResponseData::class.java, arrayOf(a))
                                    .convert(e.response().errorBody()))
                        } else callback(false, listOf(OpinionResponseData()))
                    }

                    /**
                     * 成功
                     */
                    override fun onNext(t: List<OpinionResponseData?>) {
                        callback(true, t)
                    }
                })
    }
}
