## Marketbase
Marketbase is an online platform for business. Application provides a functionality for building business web applications. To create site, user just need to specify some information about the organization and select the necessary site modules.

## 🎯 Features

* No need to write code, just enter some info about business and get production-ready web application
* Style templates - choose your preferred site style from a variety of templates
* Modules - select modules for your business needs 
  * Module variants: Booking engine, online store, blog etc.
## ⚡️ Getting started
Platform is built on microservise architecture. To start service, run Eureka Service, Application, API, Admin, techmanagers and Resourses microservices
```bash
# for each microservice:
# Eureka, admin, app, API, techmanagers and resources
mvn spring-boot:run
```
Application service will be available on `localhost:8080`

## How it works
The platform is built on a microservice architecture. `Java`/Spring Framework is used for the backend. Spring Cloud and Eureka Server provide interface to implement microservice project structure and hold the information about all client-service applications. 

### Screenshots

![mainpage](https://user-images.githubusercontent.com/40773987/159268366-10708233-8847-4308-89d1-619d08a0955a.png)
![order](https://user-images.githubusercontent.com/40773987/159268478-2df60659-b50e-4af9-8e02-4cb64a6680f1.png)
![deployment](https://user-images.githubusercontent.com/40773987/159268512-99bf7c27-835d-47e1-a800-f310927f7a27.png)
