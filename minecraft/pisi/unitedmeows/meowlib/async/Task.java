package pisi.unitedmeows.meowlib.async;

public abstract class Task<X> {

    private State state = State.IDLE;
    protected X result;
    private long startTime;
    private IAsyncAction action;

    public Task(IAsyncAction action) {
        this.action = action;
    }

    public void pre() {
        state = State.RUNNING;
        startTime = runningTime();
    }

    public void run() {

    }


    public void post() {
        state = State.FINISHED;

    }



    public long runningTime() {
        return System.nanoTime() / 1000000L;
    }

    public long timeElapsed() {
        return runningTime() - startTime;
    }

    public State state() {
        return state;
    }

    public X result() {
        return result;
    }

    public void setResult(Object value) {
        result = (X) value;
    }

    public enum State {
        FINISHED,
        RUNNING,
        ERROR,
        IDLE
    }
}
