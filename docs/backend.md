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

backend.CurrentLocation : Location
```

Backend calls
-------------
```java
getWeatherForDay( day : int ) : Weather // 0 for today...
getGraphData( day : int ) : GraphData
searchLocation( name : string ) : List<Location>
getSavedLocations() : List<Location>
saveLocation( location : Location )
setLocation( location : Location )
getCurrentLocation() : Location
getDate( day : int ) : string
```
