
# download web from git
cd /home
mkdir -p Sudoo
cd Sudoo

wget https://storage.googleapis.com/sudoo-buckets/web-storage/web.zip

wget

unzip web.zip

rm web.zip

# run nginx
docker run -d -p 80:80 -v /home/Sudoo/web:/app/web --restart always --name sudoo-nginx sudo248dev/sudoo-nginx

wget https://raw.githubusercontent.com/Sudo248/Sudoo/be-dev/server/docker-compose.yml

wget

docker compose up -d
