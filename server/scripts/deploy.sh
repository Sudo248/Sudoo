cd /
mkdir -p Sudoo
cd Sudoo

curl --request GET -sL \
     --url 'https://raw.githubusercontent.com/Sudo248/Sudoo/be-dev/server/docker-compose.yml'\
     --output '/Sudoo/docker-compose.yml'


