package me.voidxwalker.weather.mixin;

import me.voidxwalker.weather.RealLifeWeather;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.packet.s2c.play.GameStateChangeS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {
    @Shadow private MinecraftClient client;

    @Shadow private ClientWorld world;

    @Inject(method = "onGameStateChange",at = @At("TAIL"))
    public void doClientWeatherChange(GameStateChangeS2CPacket packet, CallbackInfo ci){
        if(client.isInSingleplayer()){
            if(RealLifeWeather.weatherID>=700){
                this.world.getLevelProperties().setRaining(false);
                this.world.setRainGradient(0.0F);
            }
            else if(RealLifeWeather.weatherID>=200){
                this.world.getLevelProperties().setRaining(true);
                this.world.setRainGradient(1.0F);
            }
            if(RealLifeWeather.weatherID<300){
                this.world.setThunderGradient(1.0F);
            }
        }
    }
}
