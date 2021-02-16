package pisi.unitedmeows.meowlib.async;

import com.sun.istack.internal.NotNull;
import pisi.unitedmeows.meowlib.MeowLib;
import pisi.unitedmeows.meowlib.etc.MLibSettings;
import pisi.unitedmeows.meowlib.thread.kThread;

import java.util.ArrayDeque;
import java.util.Queue;

public class TaskWorker extends Thread {

    private boolean running;
    private Task<?> runningTask;
    private ITaskPool pool;
    private long lastWork;

    public TaskWorker(ITaskPool owner) {
        pool = owner;
        lastWork = curTime();
    }

    @Override
    public void run() {
        while (running) {
            try {
                runningTask = pool.poll();
                runningTask.pre();
                runningTask.run();
                runningTask.post();
                runningTask = null;
                lastWork =  curTime();
            } catch (NoSuchMethodError | NullPointerException ex) {
                runningTask = null;
            }
        }
    }


    private long curTime() {
        return System.nanoTime() / 1000000L;
    }

    public long lastWorkTimeElapsed() {
        return curTime() - lastWork;
    }


    public Task getRunningTask() {
        return runningTask;
    }

    public boolean isBusy() {
        if (isWorking()) {
            return runningTask.timeElapsed() >= (long) MeowLib.settings().get(MLibSettings.ASYNC_WORKER_BUSY).getValue();
        }
        return false;
    }



    public boolean isWorking() {
        return getRunningTask() != null;
    }

    public boolean isFree() {
        return !isWorking();
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void stopWorker() {
        running = false;
    }

    public void startWorker() {
        running = true;
        start();
    }
}
