package localhost.android.Presenter

import android.content.Intent
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.util.Base64
import localhost.android.MainActivity
import localhost.android.RegisterActivity
import localhost.android.model.RegisterResponseData
import localhost.android.network.NetworkInterface
import localhost.android.network.NetworkService
import localhost.android.util.AndroidKeyStoreManager
import retrofit2.adapter.rxjava.HttpException
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.HashMap

class RegisterActivityPresenter(private val registerActivity: RegisterActivity) {

    private val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(registerActivity)
    private val mKeyStoreManager: AndroidKeyStoreManager = AndroidKeyStoreManager(registerActivity)

    fun sendRegisterRequest(body: HashMap<String, String>?) {
        val retrofit = NetworkService.getRetrofit()
        retrofit.create(NetworkInterface::class.java)
                .register("", body)    // TODO: 第1引数で if(sId == null) "" else sId こんな関数を呼ぶ
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<List<RegisterResponseData?>>() {
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
                            var b = arrayOf(a)
                            registerResult(false, retrofit.responseBodyConverter<List<RegisterResponseData?>>(RegisterResponseData::class.java, arrayOf(a)).convert(e.response().errorBody()))
                            //    registerResult(false, null) //TODO: 修正
                        } else registerResult(false, listOf(RegisterResponseData()))
                    }

                    /**
                     * 成功
                     */
                    override fun onNext(t: List<RegisterResponseData?>?) {
                        registerResult(true, t)
                    }
                })
    }

    fun registerResult(status: Boolean, response: List<RegisterResponseData?>?) {
        if (status) {
            //ログイン成功
            val editor = sharedPreferences.edit()
            val SID = response?.get(0)?.sid
            val SID_ = mKeyStoreManager.encrypt(SID?.toByteArray())
            editor.apply {
                putString("SID", Base64.encodeToString(SID_, Base64.DEFAULT))
                commit()
            }
            //TODO: メイン画面に推移する
            val intent = Intent(registerActivity, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            registerActivity.startActivity(intent)
        } else {
            //TODO :エラーメッセージ表示
            //registerActivity.setEmailError("エラー内容")
        }
        //ret

    }
}

