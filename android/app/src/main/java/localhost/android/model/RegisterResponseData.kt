package localhost.android.model

import java.io.Serializable

data class RegisterResponseData(
        val sid: String? = "",
        val error: String? = ""
) : Serializable