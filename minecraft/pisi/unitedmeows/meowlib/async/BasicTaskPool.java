package pisi.unitedmeows.meowlib.async;

// ignore this error in eclipse VVVVVVV
import com.sun.jmx.remote.internal.ArrayQueue;
import pisi.unitedmeows.meowlib.MeowLib;
import pisi.unitedmeows.meowlib.etc.MLibSettings;
import pisi.unitedmeows.meowlib.thread.kThread;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.*;

public class BasicTaskPool implements ITaskPool {

    private List<TaskWorker> taskWorkers;
    private ArrayDeque<Task<?>> taskQueue;
    private Thread workerCThread;

    @Override
    public void setup() {
        taskWorkers = new ArrayList<>();
        taskQueue = new ArrayDeque<Task<?>>();
        workerCThread = new Thread(this::workerC);
        workerCThread.start();

        for (int i = 0; i < 3; i++) {
            TaskWorker taskWorker = new TaskWorker(this);
            taskWorkers.add(taskWorker);
            taskWorker.startWorker();
        }

    }

    public void workerC() {
        while (true) {
            boolean allBusy = true;
            List<TaskWorker> nWorkings = new ArrayList<>();
            long nWorkingTime = (long) MeowLib.settings().get(MLibSettings.ASYNC_NWORKING_TIME).getValue();
            for (TaskWorker worker : taskWorkers) {
                if (!worker.isBusy()) {
                    allBusy = false;
                } else if (worker.lastWorkTimeElapsed() > nWorkingTime) {
                    nWorkings.add(worker);
                }
            }
            
            if (taskQueue.size() > workerCount() * 20) {
                allBusy = true;
            }

            if (allBusy) {
                TaskWorker freeWorker = new TaskWorker(this);
                taskWorkers.add(freeWorker);
                freeWorker.startWorker();
            } else if (taskWorkers.size() > 3){
                for (TaskWorker nWorking : nWorkings) {
                    taskWorkers.remove(nWorking);
                }
            }

            kThread.sleep((long) MeowLib.settings().get(MLibSettings.ASYNC_CHECK_BUSY).getValue());
        }
    }

    @Override
    public void queue(Task<?> task) {
        taskQueue.add(task);
    }

    @Override
    public Task<?> poll() {
        if (!taskQueue.isEmpty()) {
            return taskQueue.poll();
        }

        long waitTime = (long)MeowLib.settings().get(MLibSettings.ASYNC_POLL_WAIT_DELAY).getValue();

        while (taskQueue.isEmpty()) {
            kThread.sleep(waitTime);
        }

        return taskQueue.poll();
    }

    @Override
    public int workerCount() {
        return taskWorkers.size();
    }

    @Override
    public void close() {

    }

}
