param (
    # -force %True
    [bool]$force = $false
 )

$imagename = "darrenh/akkasdk"
$containername = "akka-build-env"
$path = (Get-Item -Path ".\" -Verbose).FullName
$imageExists = docker images -q $imagename | Out-String

# TODO: Support force flag
if ( (-Not ($imageExists)) -Or ($force) ) { 
    Write-Host 'Building sdk image'
    # Push-Location .\sdkimage
    # docker build -t $imagename -f Dockerfile .
    # Pop-Location
} 

docker rm -f $containername
docker run -v ${path}:/src --name $containername -it $imagename