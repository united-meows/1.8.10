package pisi.unitedmeows.meowlib.async;

public interface ITaskPool {

    void queue(Task<?> task);
    Task<?> poll();
    int workerCount();
    void close();
    void setup();
}
