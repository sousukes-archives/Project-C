package sousuke.sousukeMod_main;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;

@Mod(modid = "sousuke_s_mod_recipes",name = "sousuke's Mod recipes",version = "rc0 alpha")

public class Sousuke_Recipes {

	@EventHandler
	public void init(FMLInitializationEvent event) {

		//nullブロック

		GameRegistry.addRecipe(
			    new ShapedOreRecipe(
			    		new ItemStack(S_Blocks.blockNull,1),

			    		"CNC",
			    		"NCN",
			    		"CNC",

			    		'C',"itemCrime",
			    		'N',"itemNegime"
			    )
			);
		//ピンクダイヤブロック           

		GameRegistry.addRecipe(
			    new ShapedOreRecipe(
			    		new ItemStack(S_Blocks.blockPinkdiamondblock,1),

			    		"PPP",
			    		"PPP",
			    		"PPP",

			    		'P',"gemPinkDiamond"

			    )
		    );
		
		GameRegistry.addShapelessRecipe(new ItemStack(Sousuke_Items.Pink_diamond,9),
				new ItemStack(S_Blocks.blockPinkdiamondblock));
		
		//ルビーブロック
		
		GameRegistry.addRecipe(
			    new ShapedOreRecipe(
			    		new ItemStack(S_Blocks.blockRuby),

			    		"RRR",
			    		"RRR",
			    		"RRR",

			    		'R',"gemRuby"
			    		)
			    );
		

		GameRegistry.addShapelessRecipe(new ItemStack(Sousuke_Items.ruby,9),
				new ItemStack(S_Blocks.blockRuby));
		
		//精錬レシピ
		
		GameRegistry.addSmelting(S_Blocks.blockPinkdiamondore,new ItemStack(Sousuke_Items.Pink_diamond), 0.8F);
		
		GameRegistry.addSmelting(S_Blocks.blockRubyore,new ItemStack(Sousuke_Items.ruby), 0.8F);
		
		GameRegistry.addSmelting(Sousuke_Items.ruby,new ItemStack(Sousuke_Items.ruby_dust), 0.8F);

	}


}
