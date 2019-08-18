package localhost.android

import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.view.View

import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.content_register.*
import localhost.android.Presenter.RegisterActivityPresenter
import localhost.android.model.UserModel
import java.util.HashMap

class RegisterActivity : AppCompatActivity() {
    private lateinit var presenter: RegisterActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        presenter = RegisterActivityPresenter(this)

        button2.setOnClickListener { attemptRegister() }
        setSupportActionBar(toolbar)
    }

    private fun attemptRegister() {
        progressBar_register.visibility= View.VISIBLE
        val email = email_text.text.toString()
        val password = password_text.text.toString()
        val userModel = UserModel(email, password) //TODO:リファクタ ユーザーモデルをそのまま送信できるようにしたい
        val requestData = HashMap<String, String>().apply {
            put("email", email)
            put("pass", userModel.Hashing(password))//SHA-256ハッシュ化
        }
        presenter.sendRegisterRequest(requestData)
    }
}
