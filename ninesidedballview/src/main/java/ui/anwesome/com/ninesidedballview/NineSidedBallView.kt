package ui.anwesome.com.ninesidedballview

/**
 * Created by anweshmishra on 01/01/18.
 */
import android.app.Activity
import android.view.*
import android.content.*
import android.graphics.*
import java.util.concurrent.ConcurrentLinkedQueue

class NineSidedBallView(ctx:Context):View(ctx) {
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    val renderer = NineSideBallRenderer(this)
    override fun onDraw(canvas:Canvas) {
        renderer.render(canvas,paint)
    }
    override fun onTouchEvent(event:MotionEvent):Boolean {
        when(event.action) {
            MotionEvent.ACTION_DOWN -> {
                renderer.startUpdating()
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
        val state = NineSideBallState()
        val balls:ConcurrentLinkedQueue<NineSidedBall> = ConcurrentLinkedQueue()
        init {
            val x_gap = w/3
            val y_gap = h/3
            for(i in 0..n-1) {
                val x = x_gap*(i%3) + w/6
                val y = y_gap*(i/3) + h/6
                balls.add(NineSidedBall(x,y))
            }
        }
        fun draw(canvas:Canvas,paint:Paint) {
            canvas.save()
            canvas.translate(w/2,h/2)
            balls.forEach {
                it.draw(canvas,paint,w/2,h/2,Math.min(w,h)/15,state.scale)
            }
            canvas.restore()
        }
        fun update(stopcb:(Float)->Unit) {
            state.update(stopcb)
        }
        fun startUpdating(startcb:()->Unit) {
            state.startUpdating(startcb)
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
    data class NineSideBallRenderer(var view:NineSidedBallView,var time:Int = 0) {
        var container:NineSideBallContainer?=null
        val animator = NineSideBallAnimator(view)
        fun render(canvas:Canvas,paint:Paint) {
            if(time == 0) {
                val w = canvas.width.toFloat()
                val h = canvas.height.toFloat()
                container = NineSideBallContainer(w,h)
            }
            canvas.drawColor(Color.parseColor("#212121"))
            paint.color = Color.parseColor("#673AB7")
            container?.draw(canvas,paint)
            time++
            animator.update{
                container?.update{scale ->
                    animator.stop()
                }
            }
        }
        fun startUpdating() {
            container?.startUpdating{
                animator.start()
            }
        }
    }
    data class NineSideBallAnimator(var view:NineSidedBallView,var animated:Boolean = false) {
        fun start() {
            if(!animated) {
                animated = true
                view.postInvalidate()
            }
        }
        fun stop() {
            if(animated) {
                animated = false
            }
        }
        fun update(updatecb:()->Unit) {
            if(animated) {
                updatecb()
                try {
                    Thread.sleep(50)
                    view.invalidate()
                }
                catch(ex:Exception) {

                }
            }
        }
    }
    companion object {
        fun create(activity:Activity):NineSidedBallView {
            val view = NineSidedBallView(activity)
            activity.setContentView(view)
            return view
        }
    }
}