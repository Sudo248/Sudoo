echo "Build service"
chmod +x ./build-jar.sh
./build-jar.sh

echo "Build docker service"
chmod +x docker-build.sh
./docker-build.sh

echo "Push docker service to dockerhub"
chmod +x docker-push.sh
./docker-push.sh
