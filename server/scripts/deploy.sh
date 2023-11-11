
# download web from git
cd /
mkdir -p Sudoo
cd Sudoo

curl --request GET -sL \
     --url 'https://raw.githubusercontent.com/Sudo248/Sudoo/be-dev/client/web/sudoo/web.zip'\
     --output './'

unzip web.zip

rm web.zip

# run nginx
docker run -d -p 80:80 -v /Sudoo/web:/app/web --restart always --name sudoo-nginx sudo248dev/sudoo-nginx

curl --request GET -sL \
     --url 'https://raw.githubusercontent.com/Sudo248/Sudoo/be-dev/server/docker-compose.yml'\
     --output '/Sudoo/docker-compose.yml'

docker-compose up -d
