package Other;

public class AnimationTiming {

    private float progress = 0.0f;
    private long duration;
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

    public boolean update(float et) {
        progress = (float)((System.currentTimeMillis() - startedAt) / (double)duration);

        if(progress >= 1.0f) {
            if(rFun == RepeatableFun.repeat) {
                while (progress > 1.0f) {
                    progress -= 1.0f;
                }
            }else {
                progress = 1.0f;
            }

            return false;
        }

        progress = (progress-0.5f) * (float)Math.PI;
        progress = (float)Math.sin(progress);
        progress = (progress+1.0f) / 2.0f;

        return true;
    }

    public void start() { startedAt = System.currentTimeMillis(); }

    public float getProgress() { return progress; }
}
