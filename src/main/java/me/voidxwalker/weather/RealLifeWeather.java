package me.voidxwalker.weather;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.stream.Collectors;

public class RealLifeWeather implements ModInitializer {
    private static final Logger LOGGER = LogManager.getLogger();
    private static String ipAddress;
    private static double latitude;
    private static double longitude;
    public static int weatherID;
    public static double windSpeed;
    public static int clouds;
    public static int windDegree;
    public static int dataCoolDown;

    public static void log(Level level, String message) {
        LOGGER.log(level, message);
    }


    @Override
    public void onInitialize() {
        log(Level.INFO, "Initializing weather magic!");
    }
    public static void updateIp() throws IOException {
        if(ipAddress == null || ipAddress.equals("")){
            ipAddress= new BufferedReader(new InputStreamReader(
                    new URL("http://checkip.amazonaws.com").openStream())).readLine();
        }

    }
    public static void updateLocation() throws IOException {
        String location = new JsonParser().parse(new BufferedReader(new InputStreamReader(
                new URL("https://ipinfo.io/"+ipAddress+"/json").openStream())).lines().collect(Collectors.joining())).getAsJsonObject().get("loc").getAsString();
        int index = location.indexOf(',');
        RealLifeWeather.latitude=Double.parseDouble( location.substring(0,index));
        RealLifeWeather.longitude=Double.parseDouble( location.substring(index+1));
    }
    public static void updateWeather() throws IOException {
        JsonObject weatherJson=new JsonParser().parse(new BufferedReader(new InputStreamReader(
                new URL("https://api.openweathermap.org/data/2.5/onecall?lat="+latitude+"&lon="+longitude+"&exclude=minutely,hourly,daily,alerts&appid=278259241608b6bfe55523cd612e1af8").openStream())).lines().collect(Collectors.joining())).getAsJsonObject().get("current").getAsJsonObject();
        RealLifeWeather.clouds= weatherJson.get("clouds").getAsInt();
        RealLifeWeather.windDegree= weatherJson.getAsJsonObject().get("wind_deg").getAsInt();
        RealLifeWeather.windSpeed= weatherJson.getAsJsonObject().get("wind_speed").getAsDouble();
        RealLifeWeather.weatherID=weatherJson.getAsJsonObject().get("weather").getAsJsonArray().get(0).getAsJsonObject().get("id").getAsInt();
    }
}
