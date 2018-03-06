/* 一部のテクスチャは”Gregorius Techneticies”から借りています
 * sousuke(sousuke0422)及びsousuke0422' Teamのものではありません
 * その他ソースコード等はhttps://www.tntmodders.com/tutorial/を参考にしています
*/

package sousuke.sousukeMod_addon;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

@Mod(modid = "sousukeMod_addon_items",name = "sousukeMod Addon Items",version = "rv0 beta")

public class SA_Items {
	
	public static Item Iridium_Ingot;
	
	@EventHandler
	public static void preInti(FMLPreInitializationEvent event) {
		
		Iridium_Ingot = new Item()
				.setUnlocalizedName("Iridium_Ingot")
				.setCreativeTab(CreativeTabs.tabMaterials)
				.setTextureName("sousuke:Iridium_Ingot");
		GameRegistry.registerItem(Iridium_Ingot, "Iridium Ingot");
	}

}
