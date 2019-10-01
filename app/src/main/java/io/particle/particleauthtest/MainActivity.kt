package io.particle.particleauthtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.particle.android.sdk.cloud.ParticleCloud
import io.particle.android.sdk.cloud.ParticleCloudSDK
import io.particle.android.sdk.utils.Toaster
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    val job: Job = SupervisorJob()
    val mainThreadScope: CoroutineScope = CoroutineScope(Dispatchers.Main + job)
    val backgroundScope: CoroutineScope = CoroutineScope(Dispatchers.Default + job)
    val cloud: ParticleCloud by lazy {
        ParticleCloudSDK.getCloud()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        action_create_account.setOnClickListener {
            createAccount()
        }
        action_log_in.setOnClickListener {
            logIn()
        }

    }

    private fun getCreds(): Pair<String, String> {
        return Pair(email_edittext.text.toString(), password_edittext.text.toString())
    }

    private fun createAccount() {
        val (email, passwd) = getCreds()
        backgroundScope.launch {
            try {
                cloud.signUpWithUser(email, passwd)
            } catch (ex: Exception) {
                Log.e("AUTHTEST", "Error signing up user", ex)
            }

            mainThreadScope.launch {
                showToast()
            }
        }
    }

    private fun logIn() {
        val (email, passwd) = getCreds()
        backgroundScope.launch {
            try {
                cloud.logIn(email, passwd)
            } catch (ex: Exception) {
                Log.e("AUTHTEST", "Error logging in user", ex)
            }

            mainThreadScope.launch {
                showToast()
            }
        }
    }

    private fun showToast() {
        Toaster.s(this, "Action complete. Check the debug logs for more information")
    }
}
