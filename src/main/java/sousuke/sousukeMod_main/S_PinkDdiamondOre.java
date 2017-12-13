package sousuke.sousukeMod_main;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class S_PinkDdiamondOre extends Block {	
	
	public S_PinkDdiamondOre(Material material) {
	
super(material);
		
		//クリエイティブの登録
		this.setCreativeTab(CreativeTabs.tabBlock);
		
		//硬さの設定
		this.setHardness(6.0F);
		
		//回収するのに必要なツールを設定
		this.setHarvestLevel("pickaxe", 3);
		
		//明るさの設定
		this.setLightLevel(0.0F);
		
		//生成高度設定
		
		
	}

}
