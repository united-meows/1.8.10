package pisi.unitedmeows.meowlib;

import pisi.unitedmeows.meowlib.async.BasicTaskPool;
import pisi.unitedmeows.meowlib.async.ITaskPool;
import pisi.unitedmeows.meowlib.etc.IAction;
import pisi.unitedmeows.meowlib.etc.MLibSetting;
import pisi.unitedmeows.meowlib.etc.MLibSettings;
import pisi.unitedmeows.meowlib.variables.ubyte;

import java.io.Serializable;
import java.util.HashMap;

public class MeowLib {

    /* change this to meowlib map */
    private static HashMap<MLibSettings, MLibSetting<Serializable>> SETTINGS;

    private static ITaskPool taskPool;

    static {
        SETTINGS = new HashMap<>();
        taskPool = new BasicTaskPool();
        setup();
    }

    private static void setup() {
        for (MLibSettings setting : MLibSettings.values()) {
            SETTINGS.put(setting, new MLibSetting<Serializable>(setting, (Serializable)setting.getValue()));
        }
        taskPool.setup();
    }

    public static HashMap<MLibSettings, MLibSetting<Serializable>> settings() {
        return SETTINGS;
    }


    public static ubyte ubyte(byte value) {
        return new ubyte(value);
    }

    public static void useTaskPool(ITaskPool newPool) {
        if (taskPool != null) {
            taskPool.close();
        }
        taskPool = newPool;
        taskPool.setup();
    }

    public static ITaskPool getTaskPool() {
        return taskPool;
    }

    public static Exception run(IAction action) {
        try {
            action.run();
            return null;
        } catch (Exception ex) {
            return ex;
        }
    }
}
