package com.notcharrow.monopearl.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.EnderPearlItem;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Colors;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(EnderPearlItem.class)
public abstract class EnderPearlItemMixin {
	@Inject(method = "use", at = @At("HEAD"), cancellable = true)
	public void preventMultiplePearls(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
		MinecraftServer server = world.getServer();

		if (server != null) {
			for (ServerWorld serverWorld : server.getWorlds()) {
				boolean hasPearl = !serverWorld.getEntitiesByType(
						EntityType.ENDER_PEARL,
						pearl -> user.getUuid().equals(Objects.requireNonNull(pearl.getOwner()).getUuid())
				).isEmpty();
				if (hasPearl) {
					user.sendMessage(Text.literal("You already have an active pearl!").fillStyle(Style.EMPTY.withColor(Colors.CYAN)), true);
					cir.setReturnValue(ActionResult.FAIL);
					return;
				}
			}
		}
	}
}
