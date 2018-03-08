package localhost.android.Presenter

import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.util.Base64
import localhost.android.SplashActivity
import localhost.android.util.AndroidKeyStoreManager

/**
 * Created by takao on 2018/03/08.
 */

@Suppress("UNREACHABLE_CODE")
class SplashActivityPresenter(private val splashActivity: SplashActivity) {
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    //var mAuthTask: UserLoginTask? = null
    private val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(splashActivity)
    private val mKeyStoreManager: AndroidKeyStoreManager = AndroidKeyStoreManager(splashActivity)
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
            } else {
                //TODO:SIDを基にチェック処理 必要に応じてSIDを更新
            }
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        return false
    }
}