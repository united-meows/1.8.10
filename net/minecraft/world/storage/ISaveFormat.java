package net.minecraft.world.storage;

import java.util.List;
import net.minecraft.client.AnvilConverterException;
import net.minecraft.util.IProgressUpdate;

public interface ISaveFormat {
   String getName();

   ISaveHandler getSaveLoader(String saveName, boolean storePlayerdata);

   List<SaveFormatComparator> getSaveList() throws AnvilConverterException;

   void flushCache();

   WorldInfo getWorldInfo(String saveName);

   boolean func_154335_d(String p_154335_1_);

   boolean deleteWorldDirectory(String p_75802_1_);

   void renameWorld(String dirName, String newName);

   boolean func_154334_a(String saveName);

   boolean isOldMapFormat(String saveName);

   boolean convertMapFormat(String filename, IProgressUpdate progressCallback);

   boolean canLoadWorld(String p_90033_1_);
}
