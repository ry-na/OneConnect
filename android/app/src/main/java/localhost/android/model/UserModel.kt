package localhost.android.model

import android.text.TextUtils
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.regex.Pattern

class UserModel(
        emailAddress: String,
        password: String
) {
    private var emailAddress: String = emailAddress
        set(value) {
            !TextUtils.isEmpty(value) &&
                    Pattern.compile("[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}").matcher(value).matches()
        }
    private var password: String = password
        set(value) {
            !TextUtils.isEmpty(value) && value.length > 4
        }

    fun Hashing(password: String): String {
        try {
            val digest = MessageDigest.getInstance("SHA-256")
            digest.update(password.toByteArray())
            return bytesToHexString(digest.digest()) //SHA-256ハッシュ化
            digest.update(password.toByteArray())
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