build:
	mvn package

docker-mongo:
	docker run --name dbkanghuawu368 -d mongo

docker-build:
	docker build -t app .
	docker images

docker-net-create:
	docker network create cmpe282
	docker network ls

docker-run:
	docker run -td -p 8080:8080 --name appkanghuawu368 app
	docker ps

docker-connect:
	docker network connect cmpe282 dbkanghuawu368
	docker network connect cmpe282 appkanghuawu368

docker-run-link:
	docker run -td -p 8080:8080 --link dbkanghuawu368:mongo --name appkanghuawu368 app
	docker ps

docker-stop:
	docker stop appkanghuawu368

docker-clean:
	docker rm appkanghuawu368
	docker rmi app