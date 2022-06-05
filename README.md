# World Preview
[![ko-fi](https://ko-fi.com/img/githubbutton_sm.svg)](https://ko-fi.com/voidxwalker)

Minecraft mod that makes the minecraft weather mimic the weather outside!
Currently the mod copies these features from the real weather at your current location:
-Rain
-Thunder
-Snow 
-Clear weather
-Cloud speed
-Cloud direction
-Cloudiness

The mod does work on servers although it will only modify the weather client side (it looks like it's thundering but there isn't actually any lightning strikes).

## How does the mod get the weather data?

The mod get's your public ip adress by calling this website: http://checkip.amazonaws.com
The mod get's your current location (lat and lon) by calling this website: https://ipinfo.io/{ip_address}/json
the mod get's the weather at your current location by calling this website: "https://api.openweathermap.org/data/2.5/onecall?lat={latitude}&lon={longitude}&exclude=minutely,hourly,daily,alerts&appid=278259241608b6bfe55523cd612e1af8"

These are the only 3 website calls the mod makes, no data is logged or stored anywhere.
Both the location and the weather get's updated every 5 minutes.

The weather data comes from openweathermap.org so if it isn't 100% accurate yell at them not at me.

## Authors

- [@Void_X_Walker](https://www.github.com/voidxwalker) (https://ko-fi.com/voidxwalker)

