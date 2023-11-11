strings=(
  sudo248dev/sudoo-nginx
  sudo248dev/sudoo-registry
  sudo248dev/sudoo-api-gateway
  sudo248dev/sudoo-storage-service
  sudo248dev/sudoo-auth-service
  sudo248dev/sudoo-product-service
  sudo248dev/sudoo-user-service
  sudo248dev/sudoo-cart-service
  sudo248dev/sudoo-order-service
#  sudo248dev/sudoo-notification-service
)
for i in "${strings[@]}"; do
  echo "Pull docker image $i"
  docker pull "$i"
done

docker network create --subnet 172.18.0.0/16 --gateway 172.18.0.1 sudoo-network
