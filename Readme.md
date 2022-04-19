# Chapter01 - Youtube
## MotionLayout
- 미리 정해둔 동작이 발생할 때 모션을 통해 레이아웃을 바꿔주는 기능을 함
- start, end를 기준으로 레이아웃을 정의함
- `KeyFrameSet`을 통해 모션이 일어나는 중간에 Alpha, Position등을 변경 할 수 잇음
- `CustomMotionLayout`을 만들어 터치 이벤트를 커스텀 할 수 있음
```kotlin
// mainContainerLayout밖에서 이벤트가 발생 할 때 해당 레이아웃으로 이벤트를 넘겨줌
// recyclerView로 스크롤, 터치 이벤트를 넘겨줌
class CustomMotionLayout(context : Context, attrs : AttributeSet? = null) : MotionLayout(context, attrs) {
    private var motionTouchStarted = false
    private val mainContainerLayout by lazy {
        findViewById<View>(R.id.mainContainerLayout)
    }
    private val hitRect = Rect()

    init {
        setTransitionListener(object : TransitionListener{
            override fun onTransitionStarted(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int
            ) { }
            override fun onTransitionChange(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int,
                progress: Float
            ) { }
            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                motionTouchStarted = false
            }
            override fun onTransitionTrigger(
                motionLayout: MotionLayout?,
                triggerId: Int,
                positive: Boolean,
                progress: Float
            ) { }
        })
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when(event.actionMasked){
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                motionTouchStarted = false
                return super.onTouchEvent(event)
            }
        }

        if(motionTouchStarted.not()){
            mainContainerLayout.getHitRect(hitRect)
            motionTouchStarted = hitRect.contains(event.x.toInt(), event.y.toInt())
        }

        return super.onTouchEvent(event) && motionTouchStarted
    }

    private val gestureListener by lazy {
        object : GestureDetector.SimpleOnGestureListener() {
            override fun onScroll(
                e1: MotionEvent,
                e2: MotionEvent,
                distanceX: Float,
                distanceY: Float
            ): Boolean {
                mainContainerLayout.getHitRect(hitRect)
                return hitRect.contains(e1.x.toInt(), e1.y.toInt())
            }
        }
    }

    private val gestureDetector by lazy {
        GestureDetector(context, gestureListener)
    }

    override fun onInterceptTouchEvent(event: MotionEvent?): Boolean {
        return gestureDetector.onTouchEvent(event)
    }
}
```

## ExoPlayer
- 미디어 플레이어
```kotlin
// init player 
player = SimpleExoPlayer.Builder(context).build()
val dataSourceFactory = DefaultDataSourceFactory(context)
val mediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
    .createMediaSource(MediaItem.fromUri(Uri.parse(url)))
player?.setMediaSource(mediaSource)
player?.prepare()
player?.play()
```
- lifeCycle에 따른 동작을 정의 해주는 것을 권장