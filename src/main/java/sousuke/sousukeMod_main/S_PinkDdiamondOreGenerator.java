package sousuke.sousukeMod_main;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderSurface;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;

public class S_PinkDdiamondOreGenerator {

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
	if (world.provider instanceof WorldProviderSurface) {
	generateOre(world, random, chunkX << 4, chunkZ << 4);
	}
	}
	private void generateOre(World world, Random random, int x, int z) {
	for(int i = 0; i < 10; i++) {
	int genX = x + random.nextInt(16);
	int genY = 1 + random.nextInt(15);
	int genZ = z + random.nextInt(16);
	new WorldGenMinable(S_Blocks.blockRubyore, 0, 20, Blocks.stone).generate(world, random, genX, genY, genZ);
	}
	}


}
