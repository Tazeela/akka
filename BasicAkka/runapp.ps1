param (
    # -force %True
    [bool]$force = $false
 )

$imagename = "darrenh/akkaapp"
$containername1 = "akka-app"
$jarPath = "./target/app.jar"

if(-Not (Test-Path $jarPath))
{
    Write-Host 'Missing jar, did you build?'
    exit
}

$imageExists = docker images -q $imagename | Out-String

# TODO: Support force flag
if ( (-Not ($imageExists)) -Or ($force) ) { 
    Write-Host 'Building app image'
    docker build -t $imagename -f Dockerfile .
} 

docker rm -f $containername1
docker run --name $containername1 -p 8081:8080 -p 2552:2552 -it $imagename 