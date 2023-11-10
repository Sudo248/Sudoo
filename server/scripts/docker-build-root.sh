admin="sudo248dev"
root="$admin/sudoo"
nginx="$admin/sudoo-nginx"

docker rmi -f root nginx


#build root image
docker build -t "$root" .

#build nginx
docker build -t "$nginx" ./nginx

