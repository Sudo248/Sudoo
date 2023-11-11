strings=(
  domain
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
cd ..
mvn clean

# shellcheck disable=SC2164
for i in "${strings[@]}"; do
  # shellcheck disable=SC2164
  cd "$i"
  mvn clean package
  # shellcheck disable=SC2103
  cd ..
done