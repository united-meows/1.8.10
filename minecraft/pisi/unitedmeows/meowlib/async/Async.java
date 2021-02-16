package pisi.unitedmeows.meowlib.async;

import pisi.unitedmeows.meowlib.MeowLib;
import pisi.unitedmeows.meowlib.etc.IAction;
import pisi.unitedmeows.meowlib.etc.MLibSettings;
import pisi.unitedmeows.meowlib.thread.kThread;

import java.util.HashMap;
import java.util.UUID;

public class Async {
    /* replace this to meowmap */
    private static HashMap<UUID, Task<?>> pointers;

    static {
        pointers = new HashMap<>();
    }

    public static Task<?> task(UUID uuid) {
        return pointers.get(uuid);
    }

    /* this code shouldn't exists but looks cool */
    public static void async_t(IAction action) {
        new Thread(()-> {
            try {
                action.run();
            } catch (Exception exception) {}
        }).start();
    }

    public static UUID async(IAsyncAction action) {
        // change this uuid alternative
        final UUID pointer = UUID.randomUUID();
        //todo: check for if pointer already exists


        Task<?> task = new Task<Object>(action) {
            @Override
            public void run() {
               action.start(pointer);
            }
        };


        pointers.put(pointer, task);
        MeowLib.getTaskPool().queue(task);
        return pointer;
    }

    public static Task<?> await(UUID uuid) {
        Task<?> task = pointers.get(uuid);
        long checkTime = (long)MeowLib.settings().get(MLibSettings.ASYNC_AWAIT_CHECK_DELAY).getValue();
        while (task.state() == Task.State.RUNNING || task.state() == Task.State.IDLE) {
            kThread.sleep(checkTime);
        }
        return task;
    }


}
