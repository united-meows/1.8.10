package pisi.unitedmeows.meowlib.etc;

public enum MLibSettings
{
   ASYNC_WAIT_DELAY("ASYNC_WAIT_NEXT", Long.class, 200L),
   ASYNC_POLL_WAIT_DELAY("ASYNC_POLL_WAIT_DELAY", Long.class, 10L),
   ASYNC_WORKER_BUSY("ASYNC_WORKER_BUSY", Long.class, 500L),
   ASYNC_AWAIT_CHECK_DELAY("ASYNC_AWAIT_CHECK_DELAY", Long.class, 50L),
   ASYNC_NWORKING_TIME("ASYNC_NWORKING_TIME", Long.class, 8000L),
   ASYNC_CHECK_BUSY("ASYNC_CHECK_BUSY", Long.class, 300L),
   MATH_SQRT_MORE_ACCURACY_MC("MATH_SQRT_MORE_ACCURACY", Boolean.class, false),
   FIX_MEMORY_LEAKS_MCGC("FIX_MEMORY_LEAKS_MC", Boolean.class, true),
   FIX_MEMORY_LEAKS_ENCHANTMENTHELPERMCC("FIX_MEMORY_LEAKS_ENCHANTMENTHELPER", Boolean.class, false),
   CHUNKFIXPLAYER("CHUNK_FIX", Boolean.class, false), REVERT_OLD_ANIMATIONS("OLD_ANIMATIONS", Boolean.class, true),
   REMOVELEFTCLICKCOUNTERMC("NO_CPS_LIMIT", Boolean.class, false),
   REMOVEFPSLIMIT("NO_FPS_LIMIT_IN_GUIS", Boolean.class, true);

   private String name;
   private Class<?> type;
   private Object value;

   MLibSettings(String name, Class<?> type, Object value)
   {
	  this.name = name;
	  this.type = type;
	  this.value = value;
   }

   public String getName() { return name; }

   public Class<?> getType() { return type; }

   public Object getValue() { return value; }
}
