package net.minecraft.world;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;

public interface IWorldAccess {
   void markBlockForUpdate(BlockPos pos);

   void notifyLightSet(BlockPos pos);

   void markBlockRangeForRenderUpdate(int x1, int y1, int z1, int x2, int y2, int z2);

   void playSound(String soundName, double x, double y, double z, float volume, float pitch);

   void playSoundToNearExcept(EntityPlayer except, String soundName, double x, double y, double z, float volume, float pitch);

   void spawnParticle(int particleID, boolean ignoreRange, double xCoord, double yCoord, double zCoord, double xOffset, double yOffset, double zOffset, int... p_180442_15_);

   void onEntityAdded(Entity entityIn);

   void onEntityRemoved(Entity entityIn);

   void playRecord(String recordName, BlockPos blockPosIn);

   void broadcastSound(int p_180440_1_, BlockPos p_180440_2_, int p_180440_3_);

   void playAuxSFX(EntityPlayer player, int sfxType, BlockPos blockPosIn, int p_180439_4_);

   void sendBlockBreakProgress(int breakerId, BlockPos pos, int progress);
}
