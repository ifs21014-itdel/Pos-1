package android.pos1

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LoginResponse(
    val status: String,
    val message: String,
    val user: UserData?
)

data class UserData(
    val id: String,
    val name: String,
    val username: String,
    val language: String
)
