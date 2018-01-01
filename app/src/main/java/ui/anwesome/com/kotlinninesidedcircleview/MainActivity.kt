package ui.anwesome.com.kotlinninesidedcircleview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import ui.anwesome.com.ninesidedballview.NineSidedBallView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        val view =  NineSidedBallView.create(this)
        view.setOnExpandCollapseListener({
            Toast.makeText(this,"expanded",Toast.LENGTH_SHORT).show()
        },{
            Toast.makeText(this,"collapsed",Toast.LENGTH_LONG).show()
        })
    }
}
