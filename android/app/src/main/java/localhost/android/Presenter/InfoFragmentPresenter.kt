package localhost.android.Presenter

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.util.Base64
import localhost.android.model.ParticipantResponseData
import localhost.android.model.ReplyResponseData
import localhost.android.network.NetworkInterface
import localhost.android.network.NetworkService
import localhost.android.util.AndroidKeyStoreManager
import retrofit2.adapter.rxjava.HttpException
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.*
import kotlin.collections.HashMap

class InfoFragmentPresenter {
    fun newOpinion(context: Context, m: String, lat: Double, lng: Double,
                   callback: (status: Boolean, response: List<ReplyResponseData?>) -> Unit) {
        val retrofit = NetworkService.getRetrofit()
        val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val mKeyStoreManager: AndroidKeyStoreManager = AndroidKeyStoreManager(context)
        val SID_ = Base64.decode(sharedPreferences.getString("SID", ""), Base64.DEFAULT)
        val SID: String = if (SID_.isNotEmpty()) String(mKeyStoreManager.decrypt(SID_)) else "" //Session ID
        var post = HashMap<String, String>().apply {
            put("lat", String.format("%.6f", lat))
            put("lon", String.format("%.6f", lng))
            put("opinion_message", m)
        }
        retrofit.create(NetworkInterface::class.java)
                .newOpinion(SID, post)
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

    fun Participant(context: Context, id: String, m: String,
                  callback: (status: Boolean, response: List<ParticipantResponseData?>) -> Unit) {
        val retrofit = NetworkService.getRetrofit()
        val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val mKeyStoreManager: AndroidKeyStoreManager = AndroidKeyStoreManager(context)
        val SID_ = Base64.decode(sharedPreferences.getString("SID", ""), Base64.DEFAULT)
        val SID: String = if (SID_.isNotEmpty()) String(mKeyStoreManager.decrypt(SID_)) else "" //Session ID
        var post = HashMap<String, String>().apply {
            put("reply_message", m)
            put("opinion_id", id)
        }
        retrofit.create(NetworkInterface::class.java)
                .participant(SID, post)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<List<ParticipantResponseData?>>() {
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
                            callback(false, retrofit.responseBodyConverter<List<ParticipantResponseData?>>(ParticipantResponseData::class.java, arrayOf(a))
                                    .convert(e.response().errorBody()))
                        } else callback(false, listOf(ParticipantResponseData()))
                    }

                    /**
                     * 成功
                     */
                    override fun onNext(t: List<ParticipantResponseData?>) {
                        callback(true, t)
                    }
                })
    }

    fun sendReply(context: Context, id: String, m: String,
                  callback: (status: Boolean, response: List<ReplyResponseData?>) -> Unit) {
        val retrofit = NetworkService.getRetrofit()
        val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val mKeyStoreManager: AndroidKeyStoreManager = AndroidKeyStoreManager(context)
        val SID_ = Base64.decode(sharedPreferences.getString("SID", ""), Base64.DEFAULT)
        val SID: String = if (SID_.isNotEmpty()) String(mKeyStoreManager.decrypt(SID_)) else "" //Session ID
        var post = HashMap<String, String>().apply {
            put("reply_message", m)
            put("opinion_id", id)
        }
        retrofit.create(NetworkInterface::class.java)
                .reply(SID, post)
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

    fun getReply(context: Context, id: String,
                 callback: (status: Boolean, response: List<ReplyResponseData?>) -> Unit) {
        val retrofit = NetworkService.getRetrofit()
        val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val mKeyStoreManager: AndroidKeyStoreManager = AndroidKeyStoreManager(context)
        val SID_ = Base64.decode(sharedPreferences.getString("SID", ""), Base64.DEFAULT)
        val SID: String = if (SID_.isNotEmpty()) String(mKeyStoreManager.decrypt(SID_)) else "" //Session ID
        retrofit.create(NetworkInterface::class.java)
                .get_reply(SID, id)
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

