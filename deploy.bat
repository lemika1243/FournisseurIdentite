set webapps=C:\Program Files\Apache Software Foundation\Tomcat 10.1\webapps\
set applicationName=.
set temp=temp\
set src=%applicationName%\src\
set lib=%applicationName%\lib\
set web=%applicationName%\web\
set bin=%applicationName%\bin\
set webXML=%applicationName%\*.xml
set tempjava=tempjava\
set appName=FournisseurIdentite

rd /q /s "%temp%"
rd /q /s "%tempjava%"
mkdir "%temp%"
mkdir "%tempjava%"
mkdir "%temp%WEB-INF\lib\"
mkdir "%temp%WEB-INF\classes\"
@REM mkdir "%temp%WEB"
@REM mkdir "%temp%WEB\pages"

xcopy "%webXML%" "%temp%WEB-INF\"
@REM xcopy /s /e /i "%web%*" "%temp%WEB\pages\"
xcopy /s /e /i "%web%*" "%temp%"
xcopy /s /e /i "%lib%*" "%temp%WEB-INF\lib\"
xcopy /s /e /i "%src%/controller" "%tempjava%"
xcopy /s /e /i "%src%/model" "%tempjava%"
xcopy /s /e /i "%src%/helper" "%tempjava%"
javac -parameters -cp %lib%* -d %bin% %tempjava%*.java
robocopy .\bin "temp\WEB-INF\classes" /e
cd /d "%temp%" && jar -cvf "../%appName%.war" ./*
cd ..
copy "%appName%.war" "%webapps%"
pause;