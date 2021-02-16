package pisi.unitedmeows.meowlib.async;

import java.util.UUID;

@FunctionalInterface
public interface IAsyncAction {
    void start(UUID uuid);
}
