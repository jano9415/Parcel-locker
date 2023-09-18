# Parcel-locker

General operation:

The essence of the parcel delivery service that the customers can send their parcels from parcel lockers to other parcel lockers. It is a very useful service, because if I am waiting for a parcel I dont't have to sit home and waiting for the courier.
The software handles the parcel sending and parcel pick up by the customer, the parcel delivery and store by the courier, and the admin functions.

                                                        This is a parcel locker in the real life:
![parcellockerimage3](https://github.com/jano9415/Parcel-locker/assets/87268161/b5716d3e-c958-498e-8801-ac2f5e63cf04)


The main components of the software:

There is a spring boot server side application based on microservice architecture that serves the clients applications.
On the server side I use the following technologies:
Spring Data JPA, JWT token for authentication and authorization, Spring Cloud Eureka Netflix for discovery service, Spring Cloud Gateway for API Gateway service, Apache Kafka for the asynchronous communication between the services, Spring Webflux for the synchronous communication between the services, Postgre SQL with JPA and MongoDB.

There is an angular client side application. This software runs on the parcel lockers.
In the angular application I use the following technologies:
Arduino Uno microcontorller - I use the arduino to log in into the web application with RFID tag by the courier. The arduino receives the datas from a Mifare RC522 RFID modul.
Web Seriap API - I use this API in order to communicate the arudino uno microcontroller and the web application each other via serial protocoll.
Reactive Form Modul for handling the forms and the form validations, Angular Material design components.

There is a react client side application. This is a web application with domain address and everyone can react this app.
In the react application I use the following technologies:
Axios libary for handling the requests and the responses, React Material elements, Fromik and Yup for the forms and for the form validations.

There is a native android client side application made by android studio.
In the android application I use the following technologies:
Retrofit libary for handling the requests and the responses.


                                                        Architect plan:

![Architect-plan drawio](https://github.com/jano9415/Parcel-locker/assets/87268161/b37e3025-0fd1-43a8-ad62-f90f8d499c6f)

                                                        Authentication service relational database plan:

![Auth service relational db plan drawio](https://github.com/jano9415/Parcel-locker/assets/87268161/42d8132d-0b68-4c9b-82a6-8b8c0bd2192d)


                                                        Authentication service object relational database plan:

![Auth service object relational db plan drawio](https://github.com/jano9415/Parcel-locker/assets/87268161/dd34fefd-4b56-4e93-b4df-3bff32ff216f)


                                                        Parcel handler service relational database plan:

![Parcel handler service relational db plan drawio](https://github.com/jano9415/Parcel-locker/assets/87268161/ccf2f112-7470-4266-9d37-ee7b4124ef39)



                                                        Parcel handler service object relational database plan:
With red color I indicate the optional variables and with black the mandatory variables.

![Parcel handler service object relational db plan drawio](https://github.com/jano9415/Parcel-locker/assets/87268161/dba8a208-64ab-4c93-ad66-0a3355dd4c50)


                                                        Functions of the angular application:

![Angular-use-case drawio](https://github.com/jano9415/Parcel-locker/assets/87268161/f19153f6-12cf-4b6b-816c-96144e1e1ff7)

                                                        Functions of the react application:

![React-use-case drawio](https://github.com/jano9415/Parcel-locker/assets/87268161/279881f3-07af-4ae2-973a-03117090ddec)


                                                        Functions of the android mobile application:

![Android-use-case drawio](https://github.com/jano9415/Parcel-locker/assets/87268161/993a7417-4377-4f31-a460-32215562b453)

Pictures from the applications. The applications still contain only hungarian language but these will contain english too.
                                                        Angular:
The main screen of the parcel locker
![Image1](https://github.com/jano9415/Parcel-locker/assets/87268161/27680827-cae7-4cfb-8e83-bc5daf9940f6)

Send parcel without sending code
![Image2](https://github.com/jano9415/Parcel-locker/assets/87268161/fc1789a1-e30e-47e8-9173-4d970d3f9e06)
![Image3](https://github.com/jano9415/Parcel-locker/assets/87268161/a1678e80-7a70-4eb1-9b58-bf7e639ff051)

Email notification after parcel sending
![Image4](https://github.com/jano9415/Parcel-locker/assets/87268161/44a52162-7019-4190-92fa-dfbb266890cb)

Courier login with RFID tag
![Image5](https://github.com/jano9415/Parcel-locker/assets/87268161/1e611451-3fc9-4008-9e09-bbe178dc6509)

Empty parcel locker
![Image6](https://github.com/jano9415/Parcel-locker/assets/87268161/bcf83bce-bc2f-4f2d-a96e-89b59ad414c3)

Fill parcel locker
![Image7](https://github.com/jano9415/Parcel-locker/assets/87268161/3362787a-4d09-410c-ba7a-d36df1744474)

Pick up your parcel and the email notification with the picking up code
![Image8](https://github.com/jano9415/Parcel-locker/assets/87268161/e33e7927-8db8-456f-b603-a130b1b8006a)












