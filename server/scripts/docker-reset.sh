strings=(
  registry
  api-gateway
  auth-service
  product-service
  user-service
  cart-service
  order-service
  storage-service
#  notification-service
)
admin="sudo248dev"
docker rm -f $(docker container ps -a -q)

for i in "${strings[@]}"; do
  docker rmi "${admin}/sudoo-${i}"
done

docker network rm sudoo-network