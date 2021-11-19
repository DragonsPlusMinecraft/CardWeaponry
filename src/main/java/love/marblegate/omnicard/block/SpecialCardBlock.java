package love.marblegate.omnicard.block;


import com.google.common.collect.Lists;
import love.marblegate.omnicard.block.tileentity.SpecialCardBlockTileEntity;
import love.marblegate.omnicard.misc.CardType;
import love.marblegate.omnicard.misc.SpecialCardType;
import love.marblegate.omnicard.registry.TileEntityRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.WallHeight;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.material.PushReaction;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.item.TNTEntity;
import net.minecraft.entity.item.minecart.TNTMinecartEntity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.WitherSkullEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.BeehiveTileEntity;
import net.minecraft.tileentity.ShulkerBoxTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class SpecialCardBlock extends Block {
    private static final VoxelShape SHAPE = Block.box(5.5D, 2.5D, 5.5D, 10.5D, 12.5D, 10.5D);

    public SpecialCardBlock() {
        super(Properties.of(new Material(MaterialColor.NONE,false,false,false,false,false,false, PushReaction.BLOCK)).noCollission().strength(0.1F,5F));
    }

    @Override
    public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
        return SHAPE;
    }

    @Override
    public BlockRenderType getRenderShape(BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public List<ItemStack> getDrops(BlockState blockState, LootContext.Builder builder) {
        Entity entity = builder.getOptionalParameter(LootParameters.THIS_ENTITY);
        if (entity instanceof PlayerEntity) {
            TileEntity tileentity = builder.getOptionalParameter(LootParameters.BLOCK_ENTITY);
            if (tileentity instanceof SpecialCardBlockTileEntity) {
                SpecialCardBlockTileEntity specialCardBlockTileEntity = (SpecialCardBlockTileEntity)tileentity;
                if(specialCardBlockTileEntity.getCardType().canRetrieveByBreak){
                    return Lists.newArrayList(specialCardBlockTileEntity.getCardType().retrievedItem.get().getDefaultInstance());
                }
            }
        }
        return new ArrayList<>();
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return TileEntityRegistry.SPECIAL_CARD_BLOCK_TILEENTITY.get().create();
    }

    @Override
    public boolean canHarvestBlock(BlockState state, IBlockReader world, BlockPos pos, PlayerEntity player) {
        return super.canHarvestBlock(state, world, pos, player);
    }

    @Override
    public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader world, BlockPos pos, PlayerEntity player) {
        SpecialCardType type = ((SpecialCardBlockTileEntity)world.getBlockEntity(pos)).getCardType();
        return type.retrievedItem.get().getDefaultInstance();
    }
}