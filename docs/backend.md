Backend
=======

Classes
-------

`uk.ac.cam.cl.interactionDesign.group1.backend` detailed below

```
backend.Weather :
   temperature : int          //celsius
   windspeed : int            //km/h
   rainLikelyness : RainEnum
   alerts : List<Alert>
   humidity : int             //percent
   sunrise : string           //eg. "6:30"
   sunset : string
   
backend.Alert :
   name : string
   detail : string
   
backend.RainEnum : {UNLIKELY, SHOWERS, HEAVY SHOWERS, THUNDER}

backend.GraphData :
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
getWeatherForDay( location : int, day : int ) : Weather //locationId from searchLocation, 0 for today...
getGraphData( location : int, day : int ) : GraphData
searchLocation( name : string ) : List<Location>
```
