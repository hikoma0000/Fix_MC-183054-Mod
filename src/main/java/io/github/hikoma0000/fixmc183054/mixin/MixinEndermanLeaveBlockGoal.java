package io.github.hikoma0000.fixmc183054.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(targets = "net.minecraft.world.entity.monster.EnderMan$EndermanLeaveBlockGoal")
public class MixinEndermanLeaveBlockGoal {

    @Shadow
    @Final
    private EnderMan enderman;

    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;I)Z"))
    private boolean setBlockAndPlacedBy(Level level, BlockPos pos, BlockState state, int flags) {
        boolean placed = level.setBlock(pos, state, flags);
        if (placed) {
            state.getBlock().setPlacedBy(level, pos, state, this.enderman, ItemStack.EMPTY);
        }
        return placed;
    }
}
