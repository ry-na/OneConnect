package localhost.android.config

/**
 * 通信関連の設定値をまとめたクラス
 * @author Ryo Natori (ryo.natori0809@gmail.com)
 */
class Network {
    companion object {
        const val BASE_URL = "http://10.255.161.168/"
        //const val BASE_URL = "http://172.21.3.23/"
        const val USER_API_URL = "api/v1/user/"
        const val OPINION_API_URL = "api/v1/opinion/"

        const val NEW_OPINION = "newOpinion"
        const val LOGIN = "login"
        const val REGISTER = "register"
        const val GET = "get"
        const val GET_REPLY = "getReply"
        const val REPLY = "reply"
        const val PARTICIPANT = "Participant"
        const val ISPARTICIPANT = "isParticipant"
    }
}
