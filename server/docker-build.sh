strings=(
  soc:0.0.1
  soc-registry:0.0.1
  api-gateway:0.0.1
  auth-service:0.0.1
  discovery-service:0.0.1
  payment-service:0.0.1
  user-service:0.0.1
  cart-service:0.0.1
  invoice-service:0.0.1
  promotion-service:0.0.1
  image-service:0.0.1
  notification-service:0.0.1
  chat-service:0.0.1
)
admin="sudo248dev/"
# shellcheck disable=SC2046
docker rm -f $(docker container ps -a -q)
for i in "${strings[@]}"; do
  docker rmi "$admin$i"
done

docker build -t "${admin}soc:0.0.1" .
count=0
for i in "${strings[@]}"; do
  image="${admin}$i"
  folder=$(echo $i | cut -d':' -f 1)
  echo "Build docker image: $i context: $folder"
  docker build -t "${image}" "${folder}"
  ((count=count+1))
done
echo "Total build $count docker image"
docker network create --subnet 172.18.0.0/16 --gateway 172.18.0.1 soc-network
