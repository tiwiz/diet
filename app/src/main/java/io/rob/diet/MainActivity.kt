package io.rob.diet

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import io.rob.diet.ui.theme.DietTheme

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

//    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)

        setContent {
            DietTheme {
                MainUI()
            }
        }
    }
}
