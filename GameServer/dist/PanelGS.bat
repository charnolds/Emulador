@ECHO off
@COLOR 0e
TITLE Aion-Core GS v4.7.5.x by GiGatR00n
:MENU
CLS
ECHO.
ECHO   ^*--------------------------------------------------------------------------^*
ECHO                         Aion-Core GS v4.7.5.x by GiGatR00n                  
ECHO   ^*--------------------------------------------------------------------------^*
ECHO   ^|                                                                          ^|
ECHO   ^|    1 - Development                                       4 - Quit        ^|
ECHO   ^|    2 - Production X1                                                     ^|
ECHO   ^|    3 - Production X2                                                     ^|
ECHO   ^|                                                                          ^|
ECHO   ^*--------------------------------------------------------------------------^*
ECHO.
SET MODE=PRODUCTION
SET JAVA_OPTS=-Xms8500m -Xmx8500m -server
CALL StartGS.bat
)
SET MODE=PRODUCTION X2
SET JAVA_OPTS=-Xms8500m -Xmx8500m -XX:MaxHeapSize=8500m -Xdebug -Xrunjdwp:transport=dt_socket,address=8991,server=y,suspend=n -Duser.timezone="Etc/GMT+3" -ea
CALL StartGS.bat
)
IF %OPTION% == 4 (
EXIT
)
IF %OPTION% GEQ 5 (
GOTO :MENU
)