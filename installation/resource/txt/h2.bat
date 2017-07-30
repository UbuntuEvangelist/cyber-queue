@java -cp "dist/QSystem.jar;dist/lib/h2-1.4.185.jar;%H2DRIVERS%;%CLASSPATH%" org.h2.tools.Console %*
@if errorlevel 1 pause