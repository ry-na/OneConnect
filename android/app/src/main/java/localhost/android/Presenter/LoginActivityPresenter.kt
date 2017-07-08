package localhost.android.Presenter

import android.content.Intent
import android.content.SharedPreferences
import android.os.AsyncTask
import android.preference.PreferenceManager
import android.support.v4.content.ContextCompat.startActivity
import android.text.TextUtils
import android.util.Base64
import localhost.android.LoginActivity
import localhost.android.MainActivity
import localhost.android.model.PostResponseData
import localhost.android.util.AndroidKeyStoreManager
import java.io.Serializable
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.regex.Pattern

@Suppress("UNREACHABLE_CODE")
public class LoginActivityPresenter(private val loginActivity: LoginActivity) {
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    //var mAuthTask: UserLoginTask? = null
    val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(loginActivity);
    val mKeyStoreManager: AndroidKeyStoreManager = AndroidKeyStoreManager(loginActivity);

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
                digest.update(password.toByteArray())
            } catch (e: NoSuchAlgorithmException) {
                e.printStackTrace();
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

    fun loginResult(status: Boolean, response: List<Serializable?>?) {
        //mAuthTask = null
        loginActivity.showProgress(false)

        if (status) {
            //ログイン成功
            val editor = sharedPreferences.edit()
            val SID = ""  //←SIDを代入する
            val SID_ = mKeyStoreManager.encrypt(SID.toByteArray())
            editor.putString("SID", Base64.encodeToString(SID_, Base64.DEFAULT))
            editor.commit()
            //TODO: メイン画面に推移する
            val intent = Intent(loginActivity, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            loginActivity.startActivity(intent)
        } else {
            //エラーメッセージ表示
            loginActivity.setEmailError("エラー内容")
        }
        //return null
    }

    /**
     * ログインできたか確認する

     * @return ログイン処理完了
     */
    fun init_Login(): Boolean {
        try {
            val SID_ = Base64.decode(sharedPreferences.getString("SID", ""), Base64.DEFAULT)
            val SID: String = if (SID_.isNotEmpty()) String(mKeyStoreManager.decrypt(SID_)) else "" //Session ID
            if (SID == "") {
                Thread.sleep(2000L)
                return false
            }
            //TODO:SIDを基にチェック処理 必要に応じてSIDを更新
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        return false
    }
}

