Backend
=======

Classes
-------

`uk.ac.cam.cl.interactionDesign.group1.backend` detailed below

```
backend.Weather :
   temperature : int          //celsius
   windspeed : int            //km/h
   rainLikelihood : RainEnum
   alerts : List<Alert>
   humidity : int             //percent
   sunrise : string           //eg. "6:30"
   sunset : string

backend.Weather.Alert :
   name : string
   detail : string

backend.Weather.RainEnum : {UNLIKELY, LIGHT_SHOWERS, HEAVY_SHOWERS, THUNDER}

backend.Weather.GraphData :
   temperature : List<int>
   windspeed : List<int>

backend.Location :
   name : string
   countryCode : string
   locationId : id
```

Backend calls
-------------
```java
WeatherApi.getWeatherForDay( day : int ) : Weather // 0 for today...
WeatherApi.getGraphData( day : int ) : Weather.GraphData
WeatherApi.searchLocation( name : string ) : List<Location>
LocationState.getSavedLocations() : List<Location>
LocationState.saveLocation( location : Location )
LocationState.setLocation( location : Location )
LocationState.getCurrentLocation() : Location
WeatherApi.getDate( day : int ) : string
```

Example
-------
```java
import uk.ac.cam.cl.interactionDesign.group1.backend

backend.Location cam = backend.WeatherApi.searchLocation("Cambridge UK");
```
