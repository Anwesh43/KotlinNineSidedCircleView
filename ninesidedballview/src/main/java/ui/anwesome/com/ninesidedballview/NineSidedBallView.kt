package ui.anwesome.com.ninesidedballview

/**
 * Created by anweshmishra on 01/01/18.
 */
import android.view.*
import android.content.*
import android.graphics.*
import java.util.concurrent.ConcurrentLinkedQueue

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
    data class NineSideBallContainer(var w:Float,var h:Float,val n:Int = 9) {
        val balls:ConcurrentLinkedQueue<NineSidedBall> = ConcurrentLinkedQueue()
        init {
            val x_gap = w/3
            val y_gap = h/3
            for(i in 0..n-1) {
                val x = x_gap*i + w/6
                val y = y_gap*i + h/6
                balls.add(NineSidedBall(x,y))
            }
        }
        fun draw(canvas:Canvas,paint:Paint) {
            canvas.save()
            canvas.translate(w/2,h/2)
            balls.forEach {
                it.draw(canvas,paint,w/2,h/2,Math.min(w,h)/15,1f)
            }
            canvas.restore()
        }
        fun update(stopcb:(Float)->Unit) {

        }
        fun startUpdating(startcb:()->Unit) {

        }
    }
    data class NineSideBallState(var scale:Float = 0f,var dir:Float = 0f,var prevScale:Float = 0f) {
        fun update(stopcb:(Float)->Unit) {
            scale += 0.1f*dir
            if(Math.abs(scale-prevScale) > 1) {
                scale = prevScale+dir
                dir = 0f
                prevScale = scale
                stopcb(scale)
            }
        }
        fun startUpdating(startcb:()->Unit) {
            dir = 1f-2*scale
            startcb()
        }
    }
}