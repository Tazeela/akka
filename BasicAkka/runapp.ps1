param (
    # -force %True
    [bool]$force = $false
 )

$imagename = "darrenh/akkaapp"
$jarPath = "./target/app.jar"
$hostname = [System.Net.Dns]::GetHostName()

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
$command1 = "/k docker run --name $containername1 -e CLUSTER_IP=$hostname -e CLUSTER_PORT=2552 -p 8080:8080 -p 2552:2552 -p 8090:8090 -it $imagename"
docker rm -f $containername1
Start-Process cmd -Argument $command1

$containername2 = "akka-app-2"
$command2 = "/c docker run --name $containername2 -e CLUSTER_IP=$hostname -e CLUSTER_PORT=2553 -p 8081:8080 -p 2553:2552 -p 8091:8090 -it $imagename"
docker rm -f $containername2
Start-Process cmd -Argument $command2

$containername2 = "akka-app-3"
$command2 = "/c docker run --name $containername2 -e CLUSTER_IP=$hostname -e CLUSTER_PORT=2554 -p 8082:8080 -p 2554:2552 -p 8092:8090 -it $imagename"
docker rm -f $containername2
Start-Process cmd -Argument $command2

$containername2 = "akka-app-4"
$command2 = "/c docker run --name $containername2 -e CLUSTER_IP=$hostname -e CLUSTER_PORT=2555 -p 8083:8080 -p 2555:2552 -p 8093:8090 -it $imagename"
docker rm -f $containername2
Start-Process cmd -Argument $command2

$containername2 = "akka-app-5"
$command2 = "/c docker run --name $containername2 -e CLUSTER_IP=$hostname -e CLUSTER_PORT=2556 -p 8084:8080 -p 2556:2552 -p 8094:8090 -it $imagename"
docker rm -f $containername2
Start-Process cmd -Argument $command2
