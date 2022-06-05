package me.voidxwalker.weather.mixin;

import me.voidxwalker.weather.RealLifeWeather;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;
import net.minecraft.world.biome.Biome;
import org.apache.logging.log4j.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;
import java.util.function.BooleanSupplier;

@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin {

    @Shadow public abstract void setWeather(int clearDuration, int rainDuration, boolean raining, boolean thundering);

    @Inject(method = "tick",at=@At(value = "INVOKE",target = "Lnet/minecraft/server/world/ServerWorld;calculateAmbientDarkness()V",shift = At.Shift.BEFORE,ordinal = 0))
    public void changeWeather(BooleanSupplier shouldKeepTicking, CallbackInfo ci) {
        if(RealLifeWeather.weatherID>=700){
            setWeather(RealLifeWeather.dataCoolDown+50,0,false,false);
        }
        else if(RealLifeWeather.weatherID>=200){
            setWeather(0, RealLifeWeather.dataCoolDown+50,true, RealLifeWeather.weatherID<300);
        }

    }
}

