package sousuke.sousukeMod_main;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;

public class S_RubyBlockOre extends Block {
	
	public Random
	
	Random = new Random();

	public S_RubyBlockOre(Material material) {
		

super(material);

		//クリエイティブの登録
		this.setCreativeTab(CreativeTabs.tabBlock);

		//硬さの設定
		this.setHardness(6.0F);

		//回収するのに必要なツールを設定
		this.setHarvestLevel("pickaxe", 3);

		//明るさの設定
		this.setLightLevel(0.0F);

		//爆発耐性
		//知らんな

	}

		//ドロップ

		@Override
		public Item getItemDropped(int meta, Random random, int fortune) {
		return Sousuke_Items.ruby;
		}

		@Override
		public int quantityDroppedWithBonus(int fortune, Random random) {
		if (fortune > 0 && Item.getItemFromBlock(this) != this.getItemDropped(0, random, fortune)) {
		int i = random.nextInt(fortune + 2) - 1;
		if (i < 0) {
		i = 0;
		}
		return this.quantityDropped(random) * (i + 1);
		} else {
		return this.quantityDropped(random);
		}
		}
		@Override
        public int getExpDrop(IBlockAccess iBlockAccess, int meta, int fortune) {
		return MathHelper.getRandomIntegerInRange(Random, 3, 7);
		}
		//生成高度設定

}



