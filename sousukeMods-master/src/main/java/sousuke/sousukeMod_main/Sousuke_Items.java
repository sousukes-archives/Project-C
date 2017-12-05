package sousuke.sousukeMod_main;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

@Mod(modid = "Sousuke_sMod_Items",name = "sousuke's Mod Items",version = "rc0 alpha")

public class Sousuke_Items {
	
	//変数の定義
	
		public static Item ruby;
		
		public static Item ruby_dust;
		
		public static Item Iridium_alloy;
		
		
		//アイテム追加
		@EventHandler
		
		public static void preInti(FMLPreInitializationEvent event) {
			
			ruby = new Item()
		    		 .setUnlocalizedName("ruby")
		    		 .setCreativeTab(CreativeTabs.tabMaterials)
		    		 .setTextureName("ruby");
		     GameRegistry.registerItem(ruby, "ruby");
		     
		     ruby_dust = new Item()
		    		 .setUnlocalizedName("ruby_dust")
		    		 .setCreativeTab(CreativeTabs.tabMaterials)
		    		 .setTextureName("sousuke:ruby_dust");
			GameRegistry.registerItem(ruby_dust, "ruby dust");
			
			Iridium_alloy = new Item()
					.setUnlocalizedName("Iridium_alloy")
					.setCreativeTab(CreativeTabs.tabMaterials)
					.setTextureName("sousuke:Iridium_alloy");
			GameRegistry.registerItem(Iridium_alloy, "Iridium alloy");
			
		}

}
