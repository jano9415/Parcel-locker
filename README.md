# Parcel-locker

General operation:

The essence of the parcel delivery service that the customers can send their parcels from parcel lockers to parcel lockers. It is a very useful service, because if I am waiting for a parcel I dont't have to sit home and waiting for the courier.
The software handles the parcel sending and parcel pick up by the customer, the parcel delivery and store by the courier, and the admin functions.

                                                        This is a parcel locker:
![parcellockerimage3](https://github.com/jano9415/Parcel-locker/assets/87268161/b5716d3e-c958-498e-8801-ac2f5e63cf04)


The main components of the software:

There is a spring boot server side application based on microservice architecture that serves the clients applications.
On the server side I use the following technologies:
Spring Data JPA, JWT token for authentication and authorization, Spring Cloud Eureka Netflix for discovery service, Spring Cloud Gateway for API Gateway service, Apache Kafka for the asynchronous communication between the services, Spring Webflux for the synchronous communication between the services, Postgre SQL with JPA and MongoDB.

There is an angular client side application. This software runs on the parcel lockers.
There is a react client side application. This is a web application with domain address and everyone can react this app.
There is a native android client side application made by android studio.


                                                        Architect plan:

![Architect-plan drawio](https://github.com/jano9415/Parcel-locker/assets/87268161/b37e3025-0fd1-43a8-ad62-f90f8d499c6f)

                                                        Authentication service relational database plan:

![Auth service relational db plan drawio](https://github.com/jano9415/Parcel-locker/assets/87268161/42d8132d-0b68-4c9b-82a6-8b8c0bd2192d)


                                                        Authentication service object relational database plan:

![Auth service object relational db plan drawio](https://github.com/jano9415/Parcel-locker/assets/87268161/dd34fefd-4b56-4e93-b4df-3bff32ff216f)


                                                        Parcel handler service relational database plan:

![Parcel handler service relational db plan drawio](https://github.com/jano9415/Parcel-locker/assets/87268161/ccf2f112-7470-4266-9d37-ee7b4124ef39)



                                                        Parcel handler service object relational database plan:

![Parcel handler service object relational db plan drawio](https://github.com/jano9415/Parcel-locker/assets/87268161/dba8a208-64ab-4c93-ad66-0a3355dd4c50)


                                                        Functions of the angular application:

![Angular-use-case drawio](https://github.com/jano9415/Parcel-locker/assets/87268161/f19153f6-12cf-4b6b-816c-96144e1e1ff7)

                                                        Functions of the react application:

![React-use-case drawio](https://github.com/jano9415/Parcel-locker/assets/87268161/dcb98ce0-c433-4cc0-afa5-d9a373444570)



