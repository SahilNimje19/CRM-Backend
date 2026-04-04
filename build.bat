@echo off
echo ====================================
echo Building CRM Backend
echo ====================================

cd /d "c:\Users\SAHIL\Desktop\Spring-boot\CRM Project\crm-backend"

echo.
echo Cleaning and installing dependencies...
call mvnw.cmd clean install -DskipTests

if %ERRORLEVEL% EQU 0 (
    echo.
    echo ========================================
    echo Build successful!
    echo ========================================
    echo.
    echo To run the application, execute:
    echo   mvnw.cmd spring-boot:run
    echo.
    echo Or double-click run.bat
    echo.
) else (
    echo.
    echo Build failed! Check errors above.
)

pause
