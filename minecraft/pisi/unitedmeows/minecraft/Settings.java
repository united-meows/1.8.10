package pisi.unitedmeows.minecraft;

import pisi.unitedmeows.meowlib.MeowLib;
import pisi.unitedmeows.meowlib.etc.MLibSetting;
import pisi.unitedmeows.meowlib.etc.MLibSettings;

public class Settings {
   
   public static boolean getBool(MLibSettings setting) {
	  return (boolean) MeowLib.settings().get(setting).getValue();
   }
   
}
