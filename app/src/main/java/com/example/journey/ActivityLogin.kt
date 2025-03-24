package com.example.journey

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.journey.databinding.ActivityLoginBinding

class ActivityLogin : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonLogin.setOnClickListener{
            var recv_code = -1
            var st_id = false
            var st_pw = false
            var user_id = binding.loginTextId.text.toString()
            var user_pw = binding.loginTextPassword.text.toString()
            // 서버에 로그인 요청
            // return value로 확인
            fun shootPOST2Serv(id: String, pw: String){
                if(getString(R.string.user_id) == id) st_id = true
                if(getString(R.string.user_pw) == pw) st_pw = true
                if(!(st_id || st_pw)){
                    recv_code = 405
                    Toast.makeText(this, "invaild id or pw. code: ${recv_code}", Toast.LENGTH_LONG).show()
                }
                else {
                    recv_code = 200
                    Toast.makeText(this, "vaild Acount. code: ${recv_code}", Toast.LENGTH_LONG).show()
                }
            }
            shootPOST2Serv(user_id, user_pw)
            // 응답 코드로 분배

            if (recv_code == 200){
                finish()
            } else if ( recv_code == 405){
                finish()
            }
        }

    }
}