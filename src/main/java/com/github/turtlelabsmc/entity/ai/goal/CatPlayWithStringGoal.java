package com.github.turtlelabsmc.entity.ai.goal;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.MathHelper;

import java.util.List;

public class CatPlayWithStringGoal extends Goal {
    private CatEntity cat;
    private int nextPlayingTime;

    public CatPlayWithStringGoal(CatEntity cat) {
        this.cat = cat;
    }

    @Override
    public boolean canStart() {
        if (this.nextPlayingTime > this.cat.age) {
            return false;
        } else {
            List<ItemEntity> itemEntityList = this.cat.world.getEntitiesByClass(ItemEntity.class, this.cat.getBoundingBox().expand(8.0D, 3.0D, 8.0D), itemEntity -> itemEntity.getStack().isItemEqual(Items.STRING.getDefaultStack()));
            return !itemEntityList.isEmpty() || !this.cat.getEquippedStack(EquipmentSlot.MAINHAND).isEmpty();
        }
    }

    @Override
    public void start() {
        List<ItemEntity> itemEntityList = this.cat.world.getEntitiesByClass(ItemEntity.class, this.cat.getBoundingBox().expand(8.0D, 3.0D, 8.0D), itemEntity -> itemEntity.getStack().isItemEqual(Items.STRING.getDefaultStack()));
        if (!itemEntityList.isEmpty()) {
            this.cat.getNavigation().startMovingTo(itemEntityList.get(0), 0.6D);
        }

        this.nextPlayingTime = 0;
    }
    @Override
    public void stop() {
        ItemStack itemStack = this.cat.getEquippedStack(EquipmentSlot.MAINHAND);
        if (!itemStack.isEmpty()) {
            this.spitOutItem(itemStack);
            this.cat.equipStack(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
            this.nextPlayingTime = this.cat.age + this.cat.getRandom().nextInt(100);
        }

    }
    @Override
    public void tick() {
        List<ItemEntity> list = this.cat.world.getEntitiesByClass(ItemEntity.class, this.cat.getBoundingBox().expand(8.0D, 3.0D, 8.0D), itemEntity -> itemEntity.getStack().isItemEqual(Items.STRING.getDefaultStack()));
        ItemStack itemStack = this.cat.getEquippedStack(EquipmentSlot.MAINHAND);
        if (!itemStack.isEmpty()) {
            this.spitOutItem(itemStack);
            this.cat.equipStack(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
        } else if (!list.isEmpty()) {
            this.cat.getNavigation().startMovingTo(list.get(0), 0.6D);
        }

    }

    private void spitOutItem(ItemStack stack) {
        if (!stack.isEmpty()) {
            double d = this.cat.getEyeY() - 0.30000001192092896D;
            ItemEntity itemEntity = new ItemEntity(this.cat.world, this.cat.getX(), d, this.cat.getZ(), stack);
            itemEntity.setPickupDelay(40);
            itemEntity.setThrower(this.cat.getUuid());
            float f = 0.3F;
            float g = this.cat.getRandom().nextFloat() * 6.2831855F;
            float h = 0.02F * this.cat.getRandom().nextFloat();
            itemEntity.setVelocity(0.3F * -MathHelper.sin(this.cat.getYaw() * 0.017453292F) * MathHelper.cos(this.cat.getPitch() * 0.017453292F) + MathHelper.cos(g) * h, 0.3F * MathHelper.sin(this.cat.getPitch() * 0.017453292F) * 1.5F, 0.3F * MathHelper.cos(this.cat.getYaw() * 0.017453292F) * MathHelper.cos(this.cat.getPitch() * 0.017453292F) + MathHelper.sin(g) * h);
            this.cat.world.spawnEntity(itemEntity);
        }
    }
}
