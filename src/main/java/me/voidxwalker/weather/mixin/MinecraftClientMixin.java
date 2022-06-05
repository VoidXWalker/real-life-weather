package me.voidxwalker.weather.mixin;

import me.voidxwalker.weather.RealLifeWeather;
import net.minecraft.client.MinecraftClient;
import org.apache.logging.log4j.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @Inject(method = "render",at = @At("HEAD"))
    public void tick(CallbackInfo ci){
        if(RealLifeWeather.dataCoolDown ==0){
            try {
                RealLifeWeather.updateIp();
                RealLifeWeather.updateLocation();
                RealLifeWeather.updateWeather();
            } catch (IOException e) {
                RealLifeWeather.log(Level.WARN,"Failed to update weather data");
            }

            RealLifeWeather.dataCoolDown=6000;
        }
        RealLifeWeather.dataCoolDown--;
    }
}
