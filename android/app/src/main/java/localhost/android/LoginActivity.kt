package localhost.android

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_login.*
import localhost.android.Presenter.LoginActivityPresenter
import java.util.*
import android.view.animation.*
import android.content.Intent




/**
 * A login screen that offers login via email/password.
 */
class LoginActivity : AppCompatActivity() {
    private val handler = Handler()
    // UI references.
    private lateinit var presenter: LoginActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        presenter = LoginActivityPresenter(this)

        val mRegisterButton = findViewById(R.id.register_button) as Button
        mRegisterButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        password_text.setOnEditorActionListener(TextView.OnEditorActionListener { textView, id, keyEvent ->
            if (id == R.id.login || id == EditorInfo.IME_NULL) {
                attemptLogin()
                return@OnEditorActionListener true
            }
            false
        })

        val mEmailSignInButton = findViewById(R.id.email_sign_in_button) as Button
        mEmailSignInButton.setOnClickListener { attemptLogin() }

        login_form.visibility = View.INVISIBLE
        Thread(Runnable {
            if (!presenter.initLogin()) {
                handler.post {
                    val x = login_logo.left
                    val left = login_logo.left.toFloat()
                    val top = login_logo.top.toFloat()
                    val animation_translate = TranslateAnimation(0.0f, 0.0f, 0.0f, -top + (login_logo.measuredHeight / 2))
                    animation_translate.duration = 500L
                    val animation_set = AnimationSet(false)
                    animation_set.interpolator = AccelerateDecelerateInterpolator()
                    animation_set.addAnimation(animation_translate)
                    animation_set.fillAfter = true
                    animation_set.isFillEnabled = true
                    animation_set.setAnimationListener(object : Animation.AnimationListener {
                        override fun onAnimationStart(arg0: Animation) {}

                        override fun onAnimationRepeat(arg0: Animation) {}

                        override fun onAnimationEnd(arg0: Animation) {
                            login_form.visibility = View.VISIBLE //ログイン画面常時
                        }
                    })
                    title_text.startAnimation(animation_set)
                    login_logo.startAnimation(animation_set)
                }
            }
        }).start()
    }


    /**
     * Attempts to sign in or newOpinion the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private fun attemptLogin() {
//        if (presenter.mAuthTask != null) {
//            return
//        }

        // Reset errors.
        email_text.error = null
        password_text.error = null

        // Store values at the time of the login attempt.
        val email = email_text.text.toString()
        val password = password_text.text.toString()

        var cancel = false

        // Check for a valid password, if the user entered one.
        if (!LoginActivityPresenter.isPasswordValid(password)) {            //TODO:リファクタ候補 UserModelを使う
            password_text.error = getString(R.string.error_invalid_password)
            password_text.requestFocus()
            cancel = true
        }

        // Check for a valid email address.
        if (!LoginActivityPresenter.isEmailValid(email)) {
            email_text.error = getString(R.string.error_invalid_email)
            email_text.requestFocus()
            cancel = true
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            return
        }
        // Show a progress spinner, and kick off a background task to
        // perform the user login attempt.
        showProgress(true)
        val requestData = HashMap<String, String>().apply {
            put("email", email)
            put("pass", LoginActivityPresenter.Hashing(password))//SHA-256ハッシュ化
        }
        presenter.sendLoginRequest(requestData)

        //presenter.mAuthTask = presenter.UserLoginTask(email, password)
        //presenter.mAuthTask!!.execute(null as Void?)

    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    fun showProgress(show: Boolean) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            val shortAnimTime = resources.getInteger(android.R.integer.config_shortAnimTime)

            login_form.visibility = if (show) View.INVISIBLE else View.VISIBLE
            login_form.animate().setDuration(shortAnimTime.toLong()).alpha(
                    (if (show) 0 else 1).toFloat()).setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    login_form.visibility = if (show) View.INVISIBLE else View.VISIBLE
                }
            })

            login_progress.visibility = if (show) View.INVISIBLE else View.GONE
            login_progress.animate().setDuration(shortAnimTime.toLong()).alpha(
                    (if (show) 1 else 0).toFloat()).setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    login_progress.visibility = if (show) View.VISIBLE else View.GONE
                }
            })
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            login_progress.visibility = if (show) View.VISIBLE else View.GONE
            login_form.visibility = if (show) View.INVISIBLE else View.VISIBLE
        }
    }

    fun setEmailError(message: String) {
        email_text.error = message
        email_text.requestFocus()
    }


}