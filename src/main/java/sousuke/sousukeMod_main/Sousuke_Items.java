package sousuke.sousukeMod_main;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraftforge.oredict.OreDictionary;

@Mod(modid = "sousuke_s_mod_items",name = "sousuke's Mod Items",version = "rc0 alpha")

public class Sousuke_Items {

	//変数の定義

		public static Item ruby;

		public static Item ruby_dust;

		public static Item Iridium_alloy;

		public static Item crime;

		public static Item tawasi;

		public static Item Pink_diamond;

		public static Item negime;

		public static Item Green_Onion;

		public static Item endon;

		public static Item test;


	//鉱石辞書







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

		    crime = new Item()
		    		 .setUnlocalizedName("crime")
		    		 .setCreativeTab(CreativeTabs.tabMaterials)
		    		 .setTextureName("sousuke:crime");
		     GameRegistry.registerItem(crime, "crime");

		     tawasi = new Item()
		    		 .setUnlocalizedName("tawasi")
		    		 .setCreativeTab(CreativeTabs.tabMaterials)
		    		 .setTextureName("sousuke:tawasi");
		     GameRegistry.registerItem(tawasi, "tawasi");

		     Pink_diamond = new Item()
		    		 .setUnlocalizedName("Pink_diamond")
		    		 .setCreativeTab(CreativeTabs.tabMaterials)
		    		 .setTextureName("sousuke:Pink_diamond");
		     GameRegistry.registerItem(Pink_diamond, "Pink diamond");

		     negime = new Item()
						.setUnlocalizedName("negime")
						.setCreativeTab(CreativeTabs.tabMaterials)
						.setTextureName("sousuke:negime");
				GameRegistry.registerItem(negime, "negime");

			Green_Onion = new ItemFood(1,2.0F, false)
					.setUnlocalizedName("Green_Onion")
					.setCreativeTab(CreativeTabs.tabFood)
					.setTextureName("sousuke:negi");
			GameRegistry.registerItem(Green_Onion,"Green onion");

			endon = new ItemFood(9,4.5F,false)
					.setUnlocalizedName("endon")
					.setCreativeTab(CreativeTabs.tabFood)
					.setTextureName("sousuke:null");
			GameRegistry.registerItem(endon, "enndonn");



		//鉱石辞書

				OreDictionary.registerOre("gemRuby" , ruby);

				OreDictionary.registerOre("dustRuby" , ruby_dust);

				OreDictionary.registerOre("itemCrime" , crime);

			    OreDictionary.registerOre("itemcrime" , crime);

			    OreDictionary.registerOre("itemTawasi" , tawasi);

			    OreDictionary.registerOre("itemScrub" , tawasi);

			    OreDictionary.registerOre("itemNegime" , negime);

			    OreDictionary.registerOre("gemPinkDiamond" , Pink_diamond);



		}

}
