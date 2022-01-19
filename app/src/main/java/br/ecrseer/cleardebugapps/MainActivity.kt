package br.ecrseer.cleardebugapps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.ecrseer.cleardebugapps.ui.main.AppParaDeletarFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, AppParaDeletarFragment.newInstance(1))
                .commitNow()
        }
    }
}