# PowerShell脚本测试BUFF程序
Write-Host "测试BUFF程序..." -ForegroundColor Green

# 检查Java版本
Write-Host "1. 检查Java版本..." -ForegroundColor Yellow
java -version

# 检查编译状态
Write-Host "2. 检查编译状态..." -ForegroundColor Yellow
if (Test-Path "target\classes\text\BUFF.class") {
    Write-Host "已编译" -ForegroundColor Green
} else {
    Write-Host "未编译，正在编译..." -ForegroundColor Yellow
    & "d:\vibe\javaproject\apache-maven-3.9.9\bin\mvn.cmd" compile -DskipTests
}

# 测试最简单的运行（会失败但可以看到错误）
Write-Host "3. 测试运行（不带JavaFX）..." -ForegroundColor Yellow
try {
    java -cp "target\classes;C:\Users\cwz20\.m2\repository\com\mysql\mysql-connector-j\8.0.33\mysql-connector-j-8.0.33.jar" text.BUFF
} catch {
    Write-Host "错误：$_" -ForegroundColor Red
}

Write-Host "4. 正确的运行命令（需要JavaFX SDK）..." -ForegroundColor Yellow
Write-Host "请先安装JavaFX SDK，然后运行以下命令：" -ForegroundColor Cyan
Write-Host 'java --module-path "C:\Program Files\Java\javafx-sdk-26\lib" --add-modules javafx.controls,javafx.fxml,javafx.graphics -cp "target\classes;C:\Users\cwz20\.m2\repository\org\openjfx\javafx-controls\26\javafx-controls-26.jar;C:\Users\cwz20\.m2\repository\org\openjfx\javafx-controls\26\javafx-controls-26-win.jar;C:\Users\cwz20\.m2\repository\org\openjfx\javafx-graphics\26\javafx-graphics-26.jar;C:\Users\cwz20\.m2\repository\org\openjfx\javafx-graphics\26\javafx-graphics-26-win.jar;C:\Users\cwz20\.m2\repository\org\openjfx\javafx-base\26\javafx-base-26.jar;C:\Users\cwz20\.m2\repository\org\openjfx\javafx-base\26\javafx-base-26-win.jar;C:\Users\cwz20\.m2\repository\com\mysql\mysql-connector-j\8.0.33\mysql-connector-j-8.0.33.jar" text.BUFF' -ForegroundColor White