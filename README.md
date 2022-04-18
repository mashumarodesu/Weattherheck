# Weattherheck
<img src="https://user-images.githubusercontent.com/42204567/163782092-0f7e494d-4f89-4a51-b52a-0e0a9fd39629.png" width="250" height="250">
A simple weather app using OpenWeatherMap API

## Features
- Current temperature and condition
- Hourly forecast for next 24 hours
  - Temperature
  - Condition
  - Wind speed
  - Wind direction
- Daily forecast for next 5 days
  - Maximum temperature
  - Minimum temperature
  - Condition

## User Interface
Day             |  Night
:-------------------------:|:-------------------------:
![Day](https://user-images.githubusercontent.com/42204567/163783348-a99051e9-63eb-493a-9b74-63132557aba9.jpg) | ![Night](https://user-images.githubusercontent.com/42204567/163783347-d5295011-4e6e-49d0-bf46-e16384bfd089.jpg)

## API
This app uses One Call API 1.0 from [OpenWeatherMap](https://openweathermap.org/) which provides:
- Minute forecast for 1 hour
- Hourly forecast for 48 hours
- Daily forecast for 7 days
- Historical data for 5 previous days
- National weather alerts

**API Call** : 
```java
https://api.openweathermap.org/data/2.5/onecall?lat={lat}&lon={lon}&exclude={part}&appid={API key}
```
{lat} and {lon} are latitude and longitude provided by [Geocoding Service](https://developers.google.com/maps/documentation/javascript/geocoding)
