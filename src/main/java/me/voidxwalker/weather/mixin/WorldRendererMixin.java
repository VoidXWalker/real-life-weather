package me.voidxwalker.weather.mixin;

import me.voidxwalker.weather.RealLifeWeather;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {
    @Shadow private int ticks;
    private float real_life_weather_tickDelta;
    private double real_life_weather_cameraX;
    private double real_life_weather_cameraZ;
    @Inject(method = "renderClouds(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/util/math/Matrix4f;FDDD)V",at = @At("HEAD"))
    public void getCloudParameters(MatrixStack matrices, Matrix4f projectionMatrix, float tickDelta, double $$3, double $$4, double $$5, CallbackInfo ci){
        this.real_life_weather_tickDelta=tickDelta;
        this.real_life_weather_cameraX=$$3;
        this.real_life_weather_cameraZ=$$5;
    }

    @ModifyVariable(method = "renderClouds(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/util/math/Matrix4f;FDDD)V", at = @At("STORE"), ordinal = 5)
    private double modifyCloudLocationX(double x) {
        if(RealLifeWeather.weatherID>=200){
            double e = ((double)this.ticks* RealLifeWeather.windSpeed + real_life_weather_tickDelta) * 0.03F;
            if(RealLifeWeather.windDegree>=45&& RealLifeWeather.windDegree<=135){
                return (real_life_weather_cameraX + e) / 12.0D;
            }
            else if(RealLifeWeather.windDegree>225&& RealLifeWeather.windDegree<315){
                return (real_life_weather_cameraX - e) / 12.0D;
            }
            else {
                return real_life_weather_cameraX / 12.0D + 0.33000001311302185D;
            }
        }
        return x;
    }
    @Inject(method = "renderClouds(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/util/math/Matrix4f;FDDD)V",cancellable = true,at = @At("HEAD"))
    public void cancelClouds( CallbackInfo ci){
        if(RealLifeWeather.clouds<20&&RealLifeWeather.weatherID>=200){
            ci.cancel();
        }

    }
    @ModifyVariable(method = "renderClouds(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/util/math/Matrix4f;FDDD)V", at = @At("STORE"), ordinal = 7)
    private double modifyCloudLocationZ(double z) {
        if (RealLifeWeather.weatherID >= 200) {
            double e = ((double) this.ticks * RealLifeWeather.windSpeed + real_life_weather_tickDelta) * 0.03F;
            if (RealLifeWeather.windDegree > 135 && RealLifeWeather.windDegree <= 225) {
                return (real_life_weather_cameraZ + e) / 12.0D;
            } else if (RealLifeWeather.windDegree > 315 || RealLifeWeather.windDegree <= 45) {
                return (real_life_weather_cameraZ - e) / 12.0D;
            } else {
                return real_life_weather_cameraZ / 12.0D + 0.33000001311302185D;
            }
        }
        return z;
    }
    @Redirect(method = "renderWeather",at = @At(value = "INVOKE",target = "Lnet/minecraft/world/biome/Biome;doesNotSnow(Lnet/minecraft/util/math/BlockPos;)Z",ordinal = 0))
    public boolean modifySnow(Biome instance, BlockPos blockPos){
        if(RealLifeWeather.weatherID>=600&&RealLifeWeather.weatherID<700){
            return false;
        }
        return instance.doesNotSnow(blockPos);
    }
    @Redirect(method = "tickRainSplashing",at = @At(value = "INVOKE",target = "Lnet/minecraft/world/biome/Biome;doesNotSnow(Lnet/minecraft/util/math/BlockPos;)Z",ordinal = 0))
    public boolean modifySnow2(Biome instance, BlockPos blockPos){
        if(RealLifeWeather.weatherID>=600&&RealLifeWeather.weatherID<700){
            return false;
        }
        return instance.doesNotSnow(blockPos);
    }
}
