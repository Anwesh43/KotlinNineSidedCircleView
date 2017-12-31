package ui.anwesome.com.ninesidedballview

/**
 * Created by anweshmishra on 01/01/18.
 */
import android.view.*
import android.content.*
import android.graphics.*
class NineSidedBallView(ctx:Context):View(ctx) {
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    override fun onDraw(canvas:Canvas) {

    }
    override fun onTouchEvent(event:MotionEvent):Boolean {
        when(event.action) {
            MotionEvent.ACTION_DOWN -> {

            }
        }
        return true
    }
    data class NineSidedBall(var dx:Float,var dy:Float) {
        fun draw(canvas:Canvas,paint:Paint,x:Float,y:Float,r:Float,scale:Float) {
            canvas.save()
            canvas.translate((dx-x)*scale,(dy-y)*scale)
            canvas.drawCircle(0f,0f,r,paint)
            canvas.restore()
        }
    }
}