strings=(
  sudo248dev/registry:0.0.1
  sudo248dev/api-gateway:0.0.1
  sudo248dev/storage-service:0.0.1
  sudo248dev/auth-service:0.0.1
  sudo248dev/product-service:0.0.1
  sudo248dev/user-service:0.0.1
  sudo248dev/cart-service:0.0.1
  sudo248dev/order-service:0.0.1
  sudo248dev/notification-service:0.0.1
)
for i in "${strings[@]}"; do
  echo "Pull docker image $i"
  docker pull "$i"
done

docker network create --subnet 172.18.0.0/16 --gateway 172.18.0.1 sudoo-network
