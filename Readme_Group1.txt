Cyclist weather app
===================

On macOS and Linux, extract the code and run
```
javac -cp *:.:./*.jar src/uk/ac/cam/cl/interaction_design/group1/**/*.java -d ./bin -encoding iso8859-15
```
in the root directory to compile the code.

Then run
```
java -cp *:./bin/ uk.ac.cam.cl.interaction_design.group1.frontend.MainScreen 
```
to start the app.

We have used the org.json library to decode/encode json strings, JFreechart to display the graph and JCommon as it is a dependency of JFreechart. 
