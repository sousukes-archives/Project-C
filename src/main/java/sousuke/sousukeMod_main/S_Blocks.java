package sousuke.sousukeMod_main;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

@Mod(modid = "sousuke_s_mod_blocks",name = "sousuke's Mod Blocks",version = "rc0 ")

public class S_Blocks {
	
	
	//追加するブロックの宣言
	public Block blockRuby;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
	
	blockRuby = new S_RubyBlock(Material.rock)
			.setBlockName("block_of_ruby")
			.setBlockTextureName("ruby_of_block")
			.setBlockTextureName("sousuke:ruby_of_block");
	GameRegistry.registerBlock(blockRuby, "Ruby of Block");

}

}
