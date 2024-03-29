package tabacowang.me.moviego

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import tabacowang.me.moviego.ui.home.MovieGoFragment
import tabacowang.me.moviego.util.openFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            openFragment(MovieGoFragment.newInstance(), false)
        }
    }
}