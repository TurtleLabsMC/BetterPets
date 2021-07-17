package com.github.turtlelabsmc.event;

import com.github.turtlelabsmc.entity.ai.goal.CatPlayWithStringGoal;
import com.github.turtlelabsmc.mixin.entity.MobEntityMixin;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.server.world.ServerWorld;

public class AssignGoalsEvent {
    public static void assignAllGoals(Entity entity, ServerWorld serverWorld) {
        assignCatGoals(entity, serverWorld);
    }
    private static void assignCatGoals(Entity entity, ServerWorld serverWorld) {
        if(entity instanceof CatEntity cat) {
            ((MobEntityMixin) cat).getGoalSelector().add(2, new CatPlayWithStringGoal(cat));
        }
    }
}
