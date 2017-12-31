package ui.anwesome.com.kotlinninesidedcircleview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import ui.anwesome.com.ninesidedballview.NineSidedBallView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        NineSidedBallView.create(this)
    }
}
