package localhost.android.config

/**
 * 通信関連の設定値をまとめたクラス
 * @author Ryo Natori (ryo.natori0809@gmail.com)
 */
public class Network() {
    companion object {
        const val BASE_URL = "http://100.112.100.3/ "
        const val USER_API_URL = "api/v1/user/"    // TODO: 引数としてパスを入れる際に"/"が普通に入らないっぽいため、"user/"をこっちに避難。後でどうにかする。
        const val REGISTER = "register"
        const val LOGIN = "login"
        const val OPINION_API_URL = "api/v1/opinion/"
        const val GET = "get"
        const val REPLY = "reply"
    }
}
