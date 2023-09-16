strings=(
  sudo248dev/soc-registry:0.0.2
  sudo248dev/api-gateway:0.0.2
  sudo248dev/image-service:0.0.2
  sudo248dev/auth-service:0.0.2
  sudo248dev/discovery-service:0.0.2
  sudo248dev/payment-service:0.0.2
  sudo248dev/user-service:0.0.2
  sudo248dev/cart-service:0.0.2
  sudo248dev/invoice-service:0.0.2
  sudo248dev/promotion-service:0.0.2
  sudo248dev/notification-service:0.0.2
  sudo248dev/chat-service:0.0.2
)
for i in "${strings[@]}"; do
  echo "Push docker image $i"
  docker push "$i"
done