package Other;

public class AnimationTiming {

    private float progress = 0.0f;
    private long duration;
    private int dir = 1; // 1 or -1
    private long startedAt;
    private TimingFun fun;
    private RepeatableFun rFun;

    public enum TimingFun {
        linear,
        ease
    }

    public enum RepeatableFun {
        repeat,
        norepeat
    }

    public AnimationTiming(long duration, TimingFun fun, RepeatableFun rFun) {
        this.duration = duration;
        this.fun = fun;
        this.rFun = rFun;
        start();
    }

    public boolean update() {
        progress = (float) ((System.currentTimeMillis() - startedAt) / (double) duration);

        if (rFun == RepeatableFun.norepeat) {
            if (progress >= 1.0f) {
                if (dir == 1) {
                    progress = 1.0f;
                } else {
                    progress = 0.0f;
                }
                return false;
            }
        }else {
            while (progress > 1.0f) {
                progress -= 1.0f;
            }
        }

        if(fun == TimingFun.ease) {
            progress = (1.0f - (float)Math.cos(progress * Math.PI)) / 2.0f;
        }

        if(dir == -1) {
            progress = 1.0f - progress;
        }

        return true;
    }

    public void start() { startedAt = System.currentTimeMillis(); }

    public void front() { dir = 1; }
    public void back() { dir = -1; }



    public float getProgress() { return progress; }
}
