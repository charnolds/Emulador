@ECHO off
@COLOR 0e
TITLE Aion-Anarchy - Hybrid GameServer Console by Uriel Quintero
:START
CLS
SET JAVAVER=1.7
SET NUMAENABLE=false
CLS
IF "%MODE%" == "" (
CALL PanelGS.bat
)

IF "%JAVAVER%" == "1.7" (
SET JAVA_OPTS=-XX:-UseSplitVerifier -XX:+TieredCompilation %JAVA_OPTS%
)
IF "%NUMAENABLE%" == "true" (
SET JAVA_OPTS=-XX:+UseNUMA %JAVA_OPTS%
)
ECHO Starting Aion-Core GameServer in %MODE% mode.
JAVA -version:"1.7" %JAVA_OPTS% -Duser.timezone=Etc/GMT+3 -ea -javaagent:./libs/ac-commons-1.3.jar -cp ./libs/*;AL-Game.jar com.aionemu.gameserver.GameServer
SET CLASSPATH=%OLDCLASSPATH%
IF ERRORLEVEL 2 GOTO START
IF ERRORLEVEL 1 GOTO ERROR
IF ERRORLEVEL 0 GOTO END
:ERROR
ECHO.
ECHO Game Server has terminated abnormaly!
ECHO.
PAUSE
EXIT
:END
ECHO.
ECHO Game Server is terminated!
ECHO.
PAUSE
EXIT