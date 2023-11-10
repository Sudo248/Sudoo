admin="sudo248dev/"
service="payment-service:0.0.1"

# shellcheck disable=SC2046
#docker rm -f $(docker container ps -a -q)
docker rmi -f "$admin$service"

echo "Build docker image $service"

image="${admin}$service"
folder=$(echo $service | cut -d':' -f 1)

docker build -t "${image}" "$folder"
