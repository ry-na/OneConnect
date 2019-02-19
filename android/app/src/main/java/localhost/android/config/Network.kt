package localhost.android.config

/**
 * 通信関連の設定値をまとめたクラス
 * @author Ryo Natori (ryo.natori0809@gmail.com)
 */
class Network {
    companion object {
        const val BASE_URL = "http://192.168.137.25/ "

        const val USER_API_URL = "api/v1/user/"
        const val OPINION_API_URL = "api/v1/opinion/"

        const val REGISTER = "register"
        const val LOGIN = "login"
        const val GET = "get"
        const val REPLY = "reply"
    }
}
