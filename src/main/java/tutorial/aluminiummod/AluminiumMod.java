package tutorial.aluminiummod;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

@Mod(modid = "AluminiumMod", name = "Aluminium Mod", version = "1.0.0")

public class AluminiumMod {
public static Item aluminium;

//追加するブロックの宣言
public static Block blockAluminium;

@EventHandler
public void perInit(FMLPreInitializationEvent event) {
	
aluminium = new Item()
.setCreativeTab(CreativeTabs.tabMaterials)
.setUnlocalizedName("aluminium")
.setTextureName("aluminiummod:aluminium");
GameRegistry.registerItem(aluminium, "aluminium");

//Blockを継承したクラスのインスタンスを生成し、代入する。
blockAluminium = new AluminiumBlock(Material.rock)

//システム名の登録
.setBlockName("blockAluminium")

//テクスチャ名の登録
.setBlockTextureName("aluminiummod:aluminium_block");

//GameRegistryへの登録
GameRegistry.registerBlock(blockAluminium, "blockAluminium");
}
}