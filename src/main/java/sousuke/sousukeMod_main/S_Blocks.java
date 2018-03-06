package sousuke.sousukeMod_main;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.oredict.OreDictionary;

@Mod(modid = "sousuke_s_mod_blocks",name = "sousuke's Mod Blocks",version = "rc0 ")

public class S_Blocks {


	//追加するブロックの宣言

	public static Block blockRuby;

	public static Block blockRubyore;

	public static Block blockPinkdiamondore;

	public static Block blockPinkdiamondblock;

	public static Block blockNull;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {

	//ore


	blockRubyore = new S_RubyBlockOre(Material.rock)
			.setBlockName("biock_ruby_ore")
			.setBlockTextureName("sousuke:ruby_ore");
	GameRegistry.registerBlock(blockRubyore, "Roby ore");


	blockPinkdiamondore = new S_PinkDdiamondOre(Material.rock)
			.setBlockName("Pink_diamond_ore")
			.setBlockTextureName("sousuke:Pink_diamond_ore");
	GameRegistry.registerBlock(blockPinkdiamondore, "Pink diamond ore");

	//etcBlock

	blockRuby = new S_RubyBlock(Material.rock)
			.setBlockName("block_of_ruby")
			.setBlockTextureName("sousuke:ruby_of_block");
	GameRegistry.registerBlock(blockRuby, "Ruby of Block");

	blockPinkdiamondblock = new S_Pink_diamond_block(Material.rock)
			.setBlockName("Pink_diamond_block")
			.setBlockTextureName("sousuke:Pink_diamond_block");
	GameRegistry.registerBlock(blockPinkdiamondblock, "Pinkdiamondblock");

	blockNull = new S_NullBlock(Material.rock)
			.setBlockName("block_null")
			.setBlockTextureName("sousuke:null");
	GameRegistry.registerBlock(blockNull, "null Block");
	
	
	
	//鉱石辞書		
			
			OreDictionary.registerOre("blockPinkDiamond" , blockPinkdiamondblock);
			
			OreDictionary.registerOre("blockRuby" , blockRuby);

	}

}
