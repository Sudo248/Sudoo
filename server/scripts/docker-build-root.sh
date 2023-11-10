admin="sudo248dev"
root="$admin/sudoo:0.0.1"
nginx="$admin/sudoo-nginx:0.0.1"

docker rmi -f root nginx


#build root image
docker build -t "$admin/sudoo:0.0.1" .

#build nginx
docker build -t "$admin/sudoo-nginx:0.0.1" ./nginx

