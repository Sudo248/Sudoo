docker rm db-service
docker run \
-p 3309:3306 \
--name db-service \
-e MYSQL_ROOT_PASSWORD=03092001 \
-e MYSQL_DATABASE=sudoo \
-v create-db.sql:/docker-entrypoint-initdb.d/create-db.sql \
mysql:8.0.32-debian