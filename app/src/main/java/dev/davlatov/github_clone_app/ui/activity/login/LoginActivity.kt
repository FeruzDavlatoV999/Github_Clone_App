package dev.davlatov.github_clone_app.ui.activity.login

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import dev.davlatov.github_clone_app.R
import dev.davlatov.github_clone_app.data.local.prefs.SharedPrefs
import dev.davlatov.github_clone_app.databinding.ActivityLoginBinding
import dev.davlatov.github_clone_app.ui.BaseActivity
import dev.davlatov.github_clone_app.ui.activity.main.MainActivity
import dev.davlatov.github_clone_app.utils.Constants.clientID
import dev.davlatov.github_clone_app.utils.Constants.oauthLoginURL
import dev.davlatov.github_clone_app.utils.Extensions.fireToast
import dev.davlatov.github_clone_app.utils.Logger
import javax.inject.Inject
@AndroidEntryPoint
class LoginActivity : BaseActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    @Inject
    lateinit var sharedPrefs: SharedPrefs

    companion object {
        private val TAG: String = LoginActivity::class.java.simpleName.toString()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        sharedPrefs = SharedPrefs.getInstance(this)
        binding.btnSignInWithGithub.setOnClickListener { processLogin() }

        viewModel.accessToken.observe(this) { accessToken ->
            sharedPrefs.accessToken = accessToken.accessToken
            Logger.d(TAG, "onCreate: access token: $accessToken")
            Logger.d(TAG, "shared: access token: ${sharedPrefs.accessToken}")
            callMainActivity()
        }
    }

    private fun callMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        //  intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    override fun onResume() {
        super.onResume()
        getAccessToken()
    }

    private fun getAccessToken() {
        val uri: Uri? = intent?.data
        if (uri != null) {
            val code = uri.getQueryParameter(getString(R.string.txt_code))
            if (code != null) {
                showDialog()
                viewModel.getAccessToken(code)
                fireToast(getString(R.string.txt_login_success))
            } else {
                val error = uri.getQueryParameter(getString(R.string.txt_error))
                if (error != null) {
                    Logger.d(TAG, "error: $error")
                    fireToast(getString(R.string.txt_something_went_wrong))
                }
            }
        }
    }

    private fun processLogin() {
        showDialog()
        val intent =
            Intent(Intent.ACTION_VIEW, Uri.parse("$oauthLoginURL?client_id=$clientID&scope=repo"))
        startActivity(intent)
    }
}