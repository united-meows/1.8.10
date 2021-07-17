package net.minecraft.util;

public interface IRegistry<K, V> extends Iterable<V> {
   V getObject(K name);

   void putObject(K p_82595_1_, V p_82595_2_);
}
