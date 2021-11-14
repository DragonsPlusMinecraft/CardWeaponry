package love.marblegate.omnicard.item;

import love.marblegate.omnicard.entity.CardEntity;
import love.marblegate.omnicard.misc.CardType;
import love.marblegate.omnicard.misc.ModGroup;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class BlankCard extends Item {

    public BlankCard() {
        super(new Properties().tab(ModGroup.GENERAL));
    }

    @Override
    public ActionResult<ItemStack> use(World worldIn, PlayerEntity player, Hand hand) {
        if (!worldIn.isClientSide()) {
            Vector3d vector3d = player.getViewVector(1.0F);

            double x = (vector3d.x * 4D);
            double y = (vector3d.y * 4D);
            double z = (vector3d.z * 4D);

            CardEntity cardEntity = new CardEntity(player, x, y, z, worldIn, CardType.BLANK);
            cardEntity.setPos(player.getX(), player.getY() + player.getEyeHeight(player.getPose()), player.getZ());
            worldIn.addFreshEntity(cardEntity);
        }
        return super.use(worldIn, player, hand);
    }
}