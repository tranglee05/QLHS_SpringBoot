@echo off
echo ==============================================================
echo       TIEN TRINH DONG GOI UNG DUNG QUAN LY HOC SINH (.EXE)
echo ==============================================================
echo.

echo [1/4] Dang xoa thu muc output-exe cu (neu co)...
if exist "output-exe" rmdir /s /q "output-exe"

echo [2/4] Dang don dep thu muc dau vao (jpackage-input)...
if not exist "jpackage-input" mkdir "jpackage-input"
del /q "jpackage-input\*.jar" 2>nul

echo [3/4] Dang copy file .jar moi nhat tu thu muc target...
if not exist "target\QuanLyHocSinh-1.0-SNAPSHOT-jar-with-dependencies.jar" (
    echo.
    echo [LOI] Khong tim thay file jar! 
    echo Ban can build ung dung bang Maven truoc de tao ra thu muc target.
    echo.
    pause
    exit /b
)
copy "target\QuanLyHocSinh-1.0-SNAPSHOT-jar-with-dependencies.jar" "jpackage-input\QuanLyHocSinh.jar" >nul

echo [4/4] Dang chay JPackage de dong goi phan mem (Vui long doi trong vai chuc giay)...
jpackage --type app-image -n QuanLyHocSinh --input jpackage-input --main-jar QuanLyHocSinh.jar --main-class com.qlhs.main.QuanLyHocSinh --dest output-exe --icon "src\main\java\TienIch\iconApp.ico"

if %errorlevel% neq 0 (
    echo.
    echo [LOI] Qua trinh build bi loi! Vui long kiem tra lai.
) else (
    echo.
    echo ==============================================================
    echo   BUILD THANH CONG! Da tao xong file .exe moi nhat.
    echo   Thu muc chua app: output-exe\QuanLyHocSinh\
    echo ==============================================================
)

echo.
pause
