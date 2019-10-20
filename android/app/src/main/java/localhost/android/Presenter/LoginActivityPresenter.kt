package localhost.android.Presenter

import android.content.Intent
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.text.TextUtils
import android.util.Base64
import kotlinx.android.synthetic.main.content_register.*
import localhost.android.LoginActivity
import localhost.android.MainActivity
import localhost.android.model.LoginResponseData
import localhost.android.network.NetworkInterface
import localhost.android.network.NetworkService
import localhost.android.util.AndroidKeyStoreManager
import retrofit2.adapter.rxjava.HttpException
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*
import java.util.regex.Pattern
import android.os.Bundle
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.result.Result
import localhost.android.config.Network
import localhost.android.network.NetworkServiceV2
import org.json.JSONObject
import java.nio.charset.Charset


class LoginActivityPresenter(private val loginActivity: LoginActivity) {
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    //var mAuthTask: UserLoginTask? = null
    private val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(loginActivity)
    private val mKeyStoreManager: AndroidKeyStoreManager = AndroidKeyStoreManager(loginActivity)

    companion object {
        fun isPasswordValid(password: String): Boolean {
            //TODO: 強度基準を修正する
            return !TextUtils.isEmpty(password) && password.length > 4
        }

        /**
         * @see http://techtechnolog.com/blog/archives/832
         */
        fun isEmailValid(email: String): Boolean {
            return !TextUtils.isEmpty(email) &&
                    Pattern.compile("[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}").matcher(email).matches()
        }

        fun Hashing(password: String): String {
            try {
                val digest = MessageDigest.getInstance("SHA-256")
                digest.update(password.toByteArray())
                return bytesToHexString(digest.digest()) //SHA-256ハッシュ化
            } catch (e: NoSuchAlgorithmException) {
                e.printStackTrace()
                return ""
            }
        }

        private fun bytesToHexString(bytes: ByteArray): String {
            // http://stackoverflow.com/questions/332079
            val sb = StringBuffer()
            for (aByte in bytes) {
                val hex = Integer.toHexString(0xFF and aByte.toInt())
                if (hex.length == 1) {
                    sb.append('0')
                }
                sb.append(hex)
            }
            return sb.toString()
        }
    }

    fun sendLoginRequest(body: HashMap<String, String>?) {
        //TODO: Fuel Implemented
        // 非同期処理
        ////////////////////////////
        FuelManager.instance.removeAllResponseInterceptors()
        val header: HashMap<String, String> = hashMapOf("sid" to "","Content-Type" to "application/json")  //HTTP HEADER

        Fuel.post(Network.BASE_URL + Network.USER_API_URL + Network.LOGIN).header(header).body(JSONObject(body).toString()).response { request, response, result ->
            when (result) {
                is Result.Success -> {
                    // レスポンスボディを表示
                    val res= LoginResponseData()
                    NetworkServiceV2<LoginResponseData>().ConvertResponse(res,result.value.toString(Charset.forName("UTF-8")))
                    println("非同期処理の結果：" + result.value.toString(Charset.forName("UTF-8")))
                    loginResult(true, res)
                }
                is Result.Failure -> {
                    //Error Debug Message
                    println("====HTTP ERROR!("+result.getException().exception.toString()+")====")
                    println("URL:" + request.url)
                    println("ResponseCode:" + result.error.response.statusCode)
                    println("Message:" + result.error.message)
                //    println("ResponseBody:" + String(result.response.data))
                    println("==================")
                    println("通信に失敗しました。")
val res= LoginResponseData()
                    NetworkServiceV2<LoginResponseData>().ConvertResponse(res,result.error.errorData.toString())
                   // loginResult(false, listOf(result.error.response.body().asString("json")))
                }
            }
        }
        ////////////////////////////
        if(true) return
        val retrofit = NetworkService.getRetrofit()
        retrofit.create(NetworkInterface::class.java)
                .login("", body)    // TODO: 第1引数で if(sId == null) "" else sId こんな関数を呼ぶ
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<List<LoginResponseData?>>() {
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
                            var b =arrayOf(a)
                           loginResult(false, retrofit.responseBodyConverter<List<LoginResponseData?>>(LoginResponseData::class.java, arrayOf(a)).convert(e.response().errorBody()))
                        //    loginResult(false, null) //TODO: 修正
                        } else loginResult(false, listOf(LoginResponseData()))
                    }

                    /**
                     * 成功
                     */
                    override fun onNext(t: List<LoginResponseData?>?) {
                        loginResult(true, t)
                    }
                })
    }

    fun loginResult(status: Boolean, response: LoginResponseData) {
        //mAuthTask = null
        loginActivity.showProgress(false)

        if (status) {
            //ログイン成功
            val editor = sharedPreferences.edit()
            val SID = response.sid
            val SID_ = mKeyStoreManager.encrypt(SID?.toByteArray())
            editor.apply {
                putString("SID", Base64.encodeToString(SID_, Base64.DEFAULT))
                commit()
            }
            //TODO: メイン画面に推移する
            val intent = Intent(loginActivity, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            val extras = Bundle()
            extras.putString("user_id", loginActivity.email_text.text.toString())
            intent.putExtras(extras)
            loginActivity.startActivity(intent)
        } else {
            //TODO :エラーメッセージ表示
            loginActivity.setEmailError("エラー内容")
        }
        //return null
    }



    fun loginResult(status: Boolean, response: List<LoginResponseData?>?) {
        //mAuthTask = null
        loginActivity.showProgress(false)

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
            val intent = Intent(loginActivity, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            val extras = Bundle()
            extras.putString("user_id", loginActivity.email_text.text.toString())
            intent.putExtras(extras)
            loginActivity.startActivity(intent)
        } else {
            //TODO :エラーメッセージ表示
            loginActivity.setEmailError("エラー内容")
        }
        //return null
    }


    /**
     * ログインできたか確認する

     * @return ログイン処理完了
     */
    fun initLogin(): Boolean {
        try {
            val decodedSID = Base64.decode(sharedPreferences.getString("SID", ""), Base64.DEFAULT)
            // https://github.com/ry-na/OneConnect/issues/28
            val SID: String = ""//if (decodedSID.isNotEmpty()) String(mKeyStoreManager.decrypt(decodedSID)) else "" //Session ID
            if (SID == "") {
                Thread.sleep(2000L)
                return false
            }
            return true
            //TODO:SIDを基にチェック処理 必要に応じてSIDを更新
        } catch (e: InterruptedException) {
            e.printStackTrace()
            return false
        }



    }
}

