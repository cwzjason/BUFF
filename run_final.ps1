# 最终运行脚本 - 包含所有依赖
Write-Host "运行BUFF程序..." -ForegroundColor Green
Write-Host "当前目录: $(Get-Location)" -ForegroundColor Yellow

# 设置类路径
$classpath = @(
    "target\classes",
    "$env:USERPROFILE\.m2\repository\org\openjfx\javafx-controls\26\javafx-controls-26.jar",
    "$env:USERPROFILE\.m2\repository\org\openjfx\javafx-controls\26\javafx-controls-26-win.jar",
    "$env:USERPROFILE\.m2\repository\org\openjfx\javafx-graphics\26\javafx-graphics-26.jar",
    "$env:USERPROFILE\.m2\repository\org\openjfx\javafx-graphics\26\javafx-graphics-26-win.jar",
    "$env:USERPROFILE\.m2\repository\org\openjfx\javafx-base\26\javafx-base-26.jar",
    "$env:USERPROFILE\.m2\repository\org\openjfx\javafx-base\26\javafx-base-26-win.jar",
    "$env:USERPROFILE\.m2\repository\com\mysql\mysql-connector-j\8.0.33\mysql-connector-j-8.0.33.jar"
) -join ";"

Write-Host "类路径已设置" -ForegroundColor Green

# 检查是否安装了JavaFX SDK
$javafxPath = "C:\Program Files\Java\javafx-sdk-26\lib"
if (Test-Path $javafxPath) {
    Write-Host "找到JavaFX SDK: $javafxPath" -ForegroundColor Green
    
    # 方法1：使用JavaFX模块系统（推荐）
    Write-Host "使用JavaFX模块系统运行..." -ForegroundColor Yellow
    try {
        java --module-path "$javafxPath" --add-modules javafx.controls,javafx.fxml,javafx.graphics -cp "$classpath" text.BUFF
    } catch {
        Write-Host "模块系统运行失败，尝试传统方法..." -ForegroundColor Red
        # 方法2：传统方式
        java -cp "$classpath" text.BUFF
    }
} else {
    Write-Host "未找到JavaFX SDK，尝试传统方式运行..." -ForegroundColor Yellow
    Write-Host "如果失败，请下载JavaFX SDK: https://gluonhq.com/products/javafx/" -ForegroundColor Cyan
    java -cp "$classpath" text.BUFF
}

Write-Host "程序运行结束" -ForegroundColor Green
Read-Host "按Enter键退出..."