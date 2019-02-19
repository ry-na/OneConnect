package localhost.android.model

import java.io.Serializable

data class ErrorResponseData(
        val error: List<String>? = null
) : Serializable