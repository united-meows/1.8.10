package eu.the5zig.mod.util;

public class FPSCalculator {
   
   public static FPSCalculator INSTANCE = new FPSCalculator();
   
   private int currentFPS = 0;
   
   private FPS[] timers = new FPS[20];
   
   public FPSCalculator() {
     long startTime = System.nanoTime();
     for (int i = 0; i < this.timers.length; i++) {
       long plus = startTime + i * 1000000000L / this.timers.length;
       this.timers[i] = new FPS(plus);
     } 
   }
   
   public int getCurrentFPS() {
     return this.currentFPS;
   }
   
   public void render() {
     for (FPS fps : this.timers) {
       fps.updateFPSCount();
       if (fps.isOver()) {
         this.currentFPS = fps.getFpsCount();
         fps.updateStartTime();
       } 
     } 
   }
   
   public class FPS {
     private long startTime;
     
     private int fpsCount;
     
     public FPS(long startTime) {
       this.startTime = startTime;
       this.fpsCount = 0;
     }
     
     public void updateFPSCount() {
       this.fpsCount++;
     }
     
     public int getFpsCount() {
       return this.fpsCount;
     }
     
     public boolean isOver() {
       return (System.nanoTime() - this.startTime >= 1000000000L);
     }
     
     public void updateStartTime() {
       while (isOver())
         this.startTime += 1000000000L; 
       this.fpsCount = 0;
     }
   }
 }
