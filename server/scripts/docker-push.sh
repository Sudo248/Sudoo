strings=(
  sudo248dev/sudoo-registry
  sudo248dev/sudoo-api-gateway
  sudo248dev/sudoo-storage-service
  sudo248dev/sudoo-auth-service
  sudo248dev/sudoo-product-service
  sudo248dev/sudoo-user-service
  sudo248dev/sudoo-cart-service
  sudo248dev/sudoo-order-service
)
for i in "${strings[@]}"; do
  echo "Push docker image $i"
  docker push "$i"
done