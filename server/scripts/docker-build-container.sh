admin="sudo248dev"
service="storage-service"

# shellcheck disable=SC2046
docker rm -f $service
docker rmi -f "${admin}/sudoo-$service"

image="${admin}/sudoo-$service"
echo "Build docker image $image"

docker build -t "${image}" "../$service"
