
# download web from git
cd /home
mkdir -p Sudoo
rm -rf Sudoo/{*,.*}
cd Sudoo

wget https://raw.githubusercontent.com/Sudo248/Sudoo/web-dev/client/web/sudoo/deploy/web.zip
wget https://raw.githubusercontent.com/Sudo248/Sudoo/be-dev/server/database.zip
wget https://raw.githubusercontent.com/Sudo248/Sudoo/be-dev/server/scripts/docker-pull.sh
wget https://raw.githubusercontent.com/Sudo248/Sudoo/be-dev/server/scripts/docker-remove.sh

unzip web.zip
unzip database.zip

rm *.zip
rm -rf __MACOSX/

#pull all image
chmod +x ./docker-pull.sh
./docker-pull.sh

# run nginx
docker run -d -p 80:80 -v /Users/leduong/Sudo248/Sudoo/client/web/sudoo/build/web:/app/web --add-host=host.docker.internal:host-gateway --name sudoo-nginx sudo248dev/sudoo-nginx

wget https://raw.githubusercontent.com/Sudo248/Sudoo/be-dev/server/docker-compose.yml

docker network create --subnet 172.18.0.0/16 --gateway 172.18.0.1 sudoo-network

docker compose up -d
