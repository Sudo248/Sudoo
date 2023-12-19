set -e

# Wait for MySQL to be ready
./wait-db.sh db-service:3306 -t 1000

# Start the Java application
java -jar $APP