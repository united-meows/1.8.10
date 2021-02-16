package net.minecraft.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.IntBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.event.ClickEvent;
import net.minecraft.src.Config;
import pisi.unitedmeows.minecraft.MinecraftInstance;
import pisi.unitedmeows.minecraft.Uploader;

public class Screenshot {
   static class ScreenshotSaver implements Runnable {
	  private int width;
	  private int height;
	  private String captureTime;
	  private int[] pixels;
	  private Framebuffer frameBuffer;

	  public static void saveScreenshotAsync(int width, int height, int[] pixels, Framebuffer frameBuffer) {
		 ScreenshotSaver saver = new ScreenshotSaver();
		 int k = Config.getScreenshotSize();
		 boolean flag = OpenGlHelper.isFramebufferEnabled() && k > 1;
		 saver.width = flag ? width * k : width;
		 saver.height = flag ? height * k : height;
		 saver.captureTime = (new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss")).format(new Date());
		 saver.pixels = pixels;
		 saver.frameBuffer = frameBuffer;
		 // TODO && FIXME: screenShotSize is completely broken.
		 if (flag) {
			saver.frameBuffer.framebufferWidth = saver.frameBuffer.framebufferWidth * k;
			saver.frameBuffer.framebufferHeight = saver.frameBuffer.framebufferHeight * k;
			saver.frameBuffer.framebufferTextureWidth = saver.frameBuffer.framebufferTextureWidth * k;
			saver.frameBuffer.framebufferTextureHeight = saver.frameBuffer.framebufferTextureHeight * k;
		 }
		 (new Thread(saver)).start();
		 if (flag) {
			saver.frameBuffer.framebufferWidth = saver.frameBuffer.framebufferWidth / k;
			saver.frameBuffer.framebufferHeight = saver.frameBuffer.framebufferHeight / k;
			saver.frameBuffer.framebufferTextureWidth = saver.frameBuffer.framebufferTextureWidth / k;
			saver.frameBuffer.framebufferTextureHeight = saver.frameBuffer.framebufferTextureHeight / k;
		 }
	  }

	  public void run() {
		 BufferedImage image = null;
		 int i = Config.getGameSettings().guiScale;
		 int prevSize = -1;
		 ScaledResolution scaledresolution = new ScaledResolution(MinecraftInstance.INSTANCE.mc);
		 int j = scaledresolution.getScaleFactor();
		 if (OpenGlHelper.isFramebufferEnabled()) {
			image = new BufferedImage(this.frameBuffer.framebufferWidth, this.frameBuffer.framebufferHeight, 1);
			int diff = this.frameBuffer.framebufferTextureHeight - this.frameBuffer.framebufferHeight;
			for (int i1 = diff; i1 < this.frameBuffer.framebufferTextureHeight; i1++) {
			   for (int j1 = 0; j1 < this.frameBuffer.framebufferWidth; j1++) {
				  int pixel = this.pixels[i1 * this.frameBuffer.framebufferTextureWidth + j1];
				  image.setRGB(j1, i1 - diff, pixel);
			   }
			}
		 }
		 else {
			image = new BufferedImage(this.width, this.height, 1);
			image.setRGB(0, 0, this.width, this.height, this.pixels, 0, this.width);
		 }
		 File ssDir = new File("screenshots");
		 File ssFile = new File("screenshots", this.captureTime + ".png");
		 int iterator = 0;
		 while (ssFile.exists()) {
			iterator++;
			ssFile = new File("screenshots", this.captureTime + "_" + iterator + ".png");
		 }
		 try {
			ssDir.mkdirs();
			ImageIO.write(image, "png", ssFile);
			ChatComponentText ichatcomponent = new ChatComponentText(ssFile.getName());
			ichatcomponent.getChatStyle()
				  .setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_FILE, ssFile.getAbsolutePath()));
			ichatcomponent.getChatStyle().setBold(true);
			ichatcomponent.getChatStyle().setColor(EnumChatFormatting.RED);
			MinecraftInstance.mc.ingameGUI.getChatGUI()
				  .printChatMessage(new ChatComponentTranslation("screenshot.success", ichatcomponent));
			/**
			 * Upload
			 */
			Uploader.uploadFile("https://catgirlsare.sexy/api/upload?key=" + Uploader.TOKEN, ssFile);
		 }
		 catch (IOException e) {
			e.printStackTrace();
			ChatComponentText errorChatComponentText = (new ChatComponentText(
				  "Couldnt save for some fucking reason. (IOException)"));
			errorChatComponentText.getChatStyle().setBold(true);
			errorChatComponentText.getChatStyle().setUnderlined(true);
			MinecraftInstance.INSTANCE.mc.ingameGUI.getChatGUI().printChatMessage(errorChatComponentText);
		 }
	  }
   }

   public static class ScreenshotTaker {
	  private static IntBuffer pixelBuffer;
	  private static int[] pixelValues;

	  public static void takeScreenshot() {
		 Minecraft mc = MinecraftInstance.INSTANCE.mc;
		 Framebuffer frameBuffer = mc.getFramebuffer();
		 int screenshotWidth = mc.displayWidth;
		 int screenshotHeight = mc.displayHeight;
		 if (OpenGlHelper.isFramebufferEnabled()) {
			screenshotWidth = frameBuffer.framebufferTextureWidth;
			screenshotHeight = frameBuffer.framebufferHeight;
		 }
		 int targetCapacity = screenshotWidth * screenshotHeight;
		 if (pixelBuffer == null || pixelBuffer.capacity() < targetCapacity) {
			pixelBuffer = BufferUtils.createIntBuffer(targetCapacity);
			pixelValues = new int[targetCapacity];
		 }
		 GL11.glPixelStorei(3333, 1);
		 GL11.glPixelStorei(3317, 1);
		 pixelBuffer.clear();
		 if (OpenGlHelper.isFramebufferEnabled()) {
			GlStateManager.bindTexture(frameBuffer.framebufferTexture);
			GL11.glGetTexImage(3553, 0, 32993, 33639, pixelBuffer);
		 }
		 else {
			GL11.glReadPixels(0, 0, screenshotWidth, screenshotHeight, 32993, 33639, pixelBuffer);
		 }
		 pixelBuffer.get(pixelValues);
		 TextureUtil.processPixelValues(pixelValues, screenshotWidth, screenshotHeight);
		 int[] pixelCopy = new int[pixelValues.length];
		 System.arraycopy(pixelValues, 0, pixelCopy, 0, pixelValues.length);
		 int k = Config.getScreenshotSize();
		 boolean flag = OpenGlHelper.isFramebufferEnabled() && k > 1;
		 ScreenshotSaver.saveScreenshotAsync(screenshotWidth, screenshotHeight, pixelCopy, frameBuffer);
	  }
   }
}