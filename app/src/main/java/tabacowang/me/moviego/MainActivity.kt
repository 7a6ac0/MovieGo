package tabacowang.me.moviego

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import tabacowang.me.moviego.ui.home.HomeFragment
import tabacowang.me.moviego.util.openFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            openFragment(HomeFragment.newInstance(), false)
        }
    }
}