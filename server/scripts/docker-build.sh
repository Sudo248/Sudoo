strings=(
  sudoo-registry
  sudoo-api-gateway
  sudoo-auth-service
  sudoo-product-service
  sudoo-user-service
  sudoo-cart-service
  sudoo-order-service
  sudoo-storage-service
  sudoo-notification-service
)
admin="sudo248dev/"
# shellcheck disable=SC2046
docker rm -f $(docker container ps -a -q)

for i in "${strings[@]}"; do
  docker rmi "$admin$i"
done

count=0
for i in "${strings[@]}"; do
  image="${admin}$i"
  folder=$(echo $i | cut -d':' -f 1)
  echo "Build docker image: $i context: $folder"
  docker build -t "${image}" "${folder}"
  ((count=count+1))
done
echo "Total build $count docker image"
docker network create --subnet 172.18.0.0/16 --gateway 172.18.0.1 sudoo-network
