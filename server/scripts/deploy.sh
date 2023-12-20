# download web from git
cd /home
mkdir -p Sudoo
rm -rf Sudoo/{*,.*}
cd Sudoo

wget https://raw.githubusercontent.com/Sudo248/Sudoo/web-dev/client/web/sudoo/deploy/web.zip
wget https://raw.githubusercontent.com/Sudo248/Sudoo/be-dev/server/database.zip
wget https://raw.githubusercontent.com/Sudo248/Sudoo/be-dev/server/scripts/docker-remove.sh
wget https://raw.githubusercontent.com/Sudo248/Sudoo/be-dev/server/nginx/conf/sudoo.conf

unzip web.zip
unzip database.zip

rm *.zip
rm -rf __MACOSX/

cp sudoo.conf /etc/nginx/conf.d

systemctl restart nginx

wget https://raw.githubusercontent.com/Sudo248/Sudoo/be-dev/server/docker-compose.yml

#docker network create --subnet 172.18.0.0/16 --gateway 172.18.0.1 sudoo-network

docker network create --driver=overlay --attachable --subnet 172.18.0.0/16 --gateway 172.18.0.1 sudoo-network

mkdir mysql

sudo docker stack deploy -c docker-compose.yml sudoo
