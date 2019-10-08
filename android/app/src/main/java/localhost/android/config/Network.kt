package localhost.android.config

/**
 * 通信関連の設定値をまとめたクラス
 * @author Ryo Natori (ryo.natori0809@gmail.com)
 */
class Network {
    companion object {
      //  const val BASE_URL = "http://192.168.3.8/ "
      const val BASE_URL = "http://192.168.1.159/ "
        const val USER_API_URL = "api/v1/user/"
        const val OPINION_API_URL = "api/v1/opinion/"

        const val NEW_OPINION = "newOpinion"
        const val LOGIN = "login"
        const val REGISTER = "register"
        const val GET = "get"
        const val GET_REPLY = "getReply"
        const val REPLY = "reply"
    }
}
