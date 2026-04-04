@echo off
echo ====================================
echo Building and Running CRM Backend
echo ====================================

cd /d "c:\Users\SAHIL\Desktop\Spring-boot\CRM Project\crm-backend"

echo.
echo Step 1: Cleaning and building project...
call mvnw.cmd clean install -DskipTests

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo Build failed! Please check the error messages above.
    pause
    exit /b 1
)

echo.
echo Step 2: Starting Spring Boot application...
call mvnw.cmd spring-boot:run

pause
