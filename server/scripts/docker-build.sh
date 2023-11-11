strings=(
  registry
  api-gateway
  auth-service
  product-service
  user-service
  cart-service
  order-service
  storage-service
#  sudoo-notification-service
)
admin="sudo248dev"
# shellcheck disable=SC2046
docker rm -f $(docker container ps -a -q)

for i in "${strings[@]}"; do
  docker rmi "${admin}/sudoo-${i}"
done

count=0
for i in "${strings[@]}"; do
  image="${admin}/sudoo-$i"
  echo "Build docker image: $i context: $i"
  docker build -t "${image}" "../${i}"
  ((count=count+1))
done
echo "Total build $count docker image"
