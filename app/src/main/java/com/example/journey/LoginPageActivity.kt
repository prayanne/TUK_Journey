package com.example.journey

import android.content.Context

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.journey.databinding.ActivityMainpageBinding
import com.example.utility.LoginRequest
import com.example.utility.RetrofitClient
import kotlinx.coroutines.launch

class LoginPageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        var binding = ActivityMainpageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.welcombt.setOnClickListener{
            binding.mainImageView.animate()
                .translationY(-400f)
                .setDuration(300)
                .start()

            binding.welcome.animate()
                .alpha(0f)
                .setDuration(300)
                .withEndAction{binding.welcome.visibility = View.GONE}
                .start()

            binding.welcombt.animate()
                .alpha(0f)
                .setDuration(300)
                .withEndAction{binding.welcombt.visibility = View.GONE}
                .start()

            binding.loginForm.visibility = View.VISIBLE
            binding.loginForm.animate()
                .alpha(1f)
                .translationY(-300f)
                .setDuration(300)
                .start()
//            logo.isClickable = false
//            logo.isFocusable = false
        }
        binding.idtext.setOnClickListener{
            val intent = Intent(this, SignupPageActivity::class.java)
            startActivity(intent)
        }

        binding.buttonLogin.setOnClickListener {
            var usr_id_txt = binding.loginTextId.text.toString()
            var usr_pw_txt = binding.loginTextPassword.text.toString()

            lifecycleScope.launch {
                val request = LoginRequest(usr_id_txt, usr_pw_txt)
                val response = RetrofitClient.instance.login(request)

                if (response.isSuccessful) {
                    val res = response
                    val result = res.body()

                    val pref = getSharedPreferences("auth", Context.MODE_PRIVATE)
                    pref.edit().putString("jwt_token", res.body()?.message /* 또는 it.token */).apply()

                    Toast.makeText(this@LoginPageActivity, result?.message, Toast.LENGTH_SHORT).show()

                } else {
                    Toast.makeText(this@LoginPageActivity, "로그인 실패", Toast.LENGTH_SHORT).show()
                }
                Log.d("token", MyApplication.appContext.getSharedPreferences("auth", Context.MODE_PRIVATE).getString("jwt_token", null).toString())

            }
        }
    }
}
