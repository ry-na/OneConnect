package localhost.android.model

import java.io.Serializable

data class LoginResponseData(
        val sid: String? = "",
        val error: List<String>? = null
) : Serializable