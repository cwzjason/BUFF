# Batch translation script for weapon detail files
$baseDir = "d:\vibe\javaproject"

# Define all replacements for weapon detail Java files
$replacements = @(
    # Button labels
    @{old='new Button("购买")'; new='new Button("Buy")'},
    @{old='new Button("确定")'; new='new Button("Confirm")'},
    @{old='new Button("确认")'; new='new Button("Confirm")'},
    # Price label
    @{old='new Text("价格:")'; new='new Text("Price:")'},
    # Quantity prompt
    @{old='new Text("请输入需要购买的数量: ")'; new='new Text("Enter quantity: ")'},
    # Product names
    @{old='new Text("AK（★） | 一发入魂")'; new='new Text("AK-47 | Redline")'},
    @{old='new Text("AK（★） | 二西莫夫")'; new='new Text("AK-47 | Asiimov")'},
    @{old='new Text("AK（★） | 火蛇")'; new='new Text("AK-47 | Fire Serpent")'},
    @{old='new Text("AK（★） | 可燃冰")'; new='new Text("AK-47 | Fuel Injector")'},
    @{old='new Text("AK（★） | 皇后")'; new='new Text("AK-47 | Empress")'},
    @{old='new Text("AK（★） | 霓虹革命")'; new='new Text("AK-47 | Neon Revolution")'},
    @{old='new Text("AK（★） | 火神")'; new='new Text("AK-47 | Vulcan")'},
    @{old='new Text("AK（★） | 墨岩")'; new='new Text("AK-47 | Black Laminate")'},
    @{old='new Text("AWP（★） | 永恒之枪")'; new='new Text("AWP | Dragon Lore")'},
    @{old='new Text("AWP（★） | 九头金蛇")'; new='new Text("AWP | Hydra")'},
    @{old='new Text("AWP（★） | 野火")'; new='new Text("AWP | Wildfire")'},
    @{old='new Text("M4A1（★） | 印花集")'; new='new Text("M4A1-S | Printstream")'},
    @{old='new Text("M4A1（★） | 毁灭者")'; new='new Text("M4A1-S | Decimator")'},
    @{old='new Text("M9（★） | 渐变大理石")'; new='new Text("M9 Bayonet | Marble Fade")'},
    @{old='new Text("M9（★） | 虎牙")'; new='new Text("M9 Bayonet | Tiger Tooth")'},
    @{old='new Text("M9（★） | 多普勒")'; new='new Text("M9 Bayonet | Doppler")'},
    @{old='new Text("M9（★） | 致命紫罗兰")'; new='new Text("M9 Bayonet | Ultraviolet")'},
    @{old='new Text("蝴蝶刀（★） | 黑色层压板")'; new='new Text("Butterfly Knife | Black Laminate")'},
    @{old='new Text("蝴蝶刀（★） | 渐变之色")'; new='new Text("Butterfly Knife | Fade")'},
    @{old='new Text("蝴蝶刀（★） | 屠夫")'; new='new Text("Butterfly Knife | Slaughter")'},
    @{old='new Text("爪子刀（★） | 传说")'; new='new Text("Karambit | Lore")'},
    @{old='new Text("爪子刀（★） | 深红之网")'; new='new Text("Karambit | Crimson Web")'},
    @{old='new Text("爪子刀（★） | 大理石")'; new='new Text("Karambit | Marble Fade")'},
    @{old='new Text("运动手套（★） | 迈阿密风云")'; new='new Text("Sport Gloves | Vice")'},
    @{old='new Text("运动手套（★） | 弹弓")'; new='new Text("Sport Gloves | Slingshot")'},
    @{old='new Text("运动手套（★） | 树篱迷宫")'; new='new Text("Sport Gloves | Hedge Maze")'},
    @{old='new Text("专业手套（★） | 渐变之色")'; new='new Text("Specialist Gloves | Fade")'},
    @{old='new Text("专业手套（★） | 一线特工")'; new='new Text("Specialist Gloves | Field Agent")'}
)

# Process all Java files in weapon directories and text/
$targetDirs = @(
    "$baseDir\AK",
    "$baseDir\AWP", 
    "$baseDir\M4A1",
    "$baseDir\M9Knife",
    "$baseDir\Karambit",
    "$baseDir\BufferflyKnife",
    "$baseDir\SportGloves",
    "$baseDir\SpecialistGloves",
    "$baseDir\text"
)

foreach ($dir in $targetDirs) {
    Get-ChildItem -Path $dir -Filter "*.java" | ForEach-Object {
        $filePath = $_.FullName
        $content = Get-Content $filePath -Raw -Encoding UTF8
        $changed = $false
        
        foreach ($r in $replacements) {
            if ($content -match [regex]::Escape($r.old)) {
                $content = $content.Replace($r.old, $r.new)
                $changed = $true
                Write-Host "  [$($_.Name)] $($r.old) -> $($r.new)"
            }
        }
        
        if ($changed) {
            Set-Content $filePath -Value $content -Encoding UTF8 -NoNewline
            Write-Host "Updated: $($_.Name)" -ForegroundColor Green
        }
    }
}

Write-Host "`nDone!" -ForegroundColor Green
