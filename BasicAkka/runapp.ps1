param (
    # -force %True
    [bool]$force = $false
 )

$imagename = "darrenh/akkaapp"
$jarPath = "./target/app.jar"

if(-Not (Test-Path $jarPath))
{
    Write-Host 'Missing jar, did you build?'
    exit
}

if ( (-Not (docker images -q $imagename | Out-String)) -Or ($force) ) { 
    Write-Host 'Building app image'
    docker build -t $imagename -f Dockerfile .
} 



$containername1 = "akka-app-1"
$command1 = "/c docker run --name $containername1 -p 8081:8080 -p 2552:2552 -it $imagename"
docker rm -f $containername1
Start-Process cmd -Argument $command1

$containername2 = "akka-app-2"
$command2 = "/c docker run --name $containername2 -p 8082:8080 -p 2553:2552 -it $imagename"
docker rm -f $containername2
Start-Process cmd -Argument $command2
