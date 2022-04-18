package fastcampus.aop.part3.aop_part4_chapter01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fastcampus.aop.part3.aop_part4_chapter01.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}