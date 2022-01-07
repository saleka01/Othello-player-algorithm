$wins = 0;
$average = 0
Start-Process javac -ArgumentList "-cp game_engine.jar Agent.java"

for($j = 1; $j -le 10; $j++) {
    $localWins = 0;
    $seed = 0
    for ($i = 1; $i -le 10; $i++) {
        if($i % 2 -eq 1){
            $seed = Get-Random;
            $consoleOutput = java -jar game_engine.jar 0 game.oth.OthelloGame $seed 10 3 2000 game.oth.players.GreedyPlayer Agent

            $resultLine1 = $consoleOutput.Split([Environment]::NewLine) | Select -Last 2
            $resultLine2 = $consoleOutput.Split([Environment]::NewLine) | Select -Last 1

            $result1 = [int]$resultLine1.Split(" ")[3];
            $result2 = [int]$resultLine2.Split(" ")[3];
        }else{
            $consoleOutput = java -jar game_engine.jar 0 game.oth.OthelloGame $seed 10 3 2000 Agent game.oth.players.GreedyPlayer

            $resultLine1 = $consoleOutput.Split([Environment]::NewLine) | Select -Last 2
            $resultLine2 = $consoleOutput.Split([Environment]::NewLine) | Select -Last 1

            $result1 = [int]$resultLine2.Split(" ")[3];
            $result2 = [int]$resultLine1.Split(" ")[3];
        }
        Write-Output "Batch #$j | Game #$i : $result2 vs $result1";
        if($result2 -gt $result1) {
            $localWins++;
        }
    }
    if($localWins -ge 8) {
        $wins++;
    }
    Write-Output "Batch #$j finished. $localWins / 10 matches won";
    $average = $average + $localWins;
}
$bux = 10 - $wins;
$average = $average / 10

Write-Output "Wins: $wins";
Write-Output "Average wins: $average";
Write-Output "Bux: $bux";
Read-Host 'Press enter to exit'