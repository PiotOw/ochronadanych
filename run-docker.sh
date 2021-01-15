cd ochronadanych && ./mvnw clean install -DskipTests
docker build . -t od-backend
cd ..
docker-compose up -d