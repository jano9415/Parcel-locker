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
![Image5](https://github.com/jano9415/Parcel-locker/assets/87268161/4ad0ab07-9c60-4cd3-976d-a98be0f806d3)

Empty parcel locker
![Image6](https://github.com/jano9415/Parcel-locker/assets/87268161/bcf83bce-bc2f-4f2d-a96e-89b59ad414c3)

Fill parcel locker
![Image7](https://github.com/jano9415/Parcel-locker/assets/87268161/3362787a-4d09-410c-ba7a-d36df1744474)

Pick up your parcel and the email notification with the picking up code
![Image8](https://github.com/jano9415/Parcel-locker/assets/87268161/e33e7927-8db8-456f-b603-a130b1b8006a)

                                                        React
Home page
![Image1](https://github.com/jano9415/Parcel-locker/assets/87268161/c0908be3-f682-4456-8a06-6fa73b142007)
![Image3](https://github.com/jano9415/Parcel-locker/assets/87268161/d6fab2ad-eadc-4c67-b523-c6687ec8c47f)
![Image2](https://github.com/jano9415/Parcel-locker/assets/87268161/5963148e-d91c-44a2-8f4e-09941a9ee2db)

My profile and logout
![Image29](https://github.com/jano9415/Parcel-locker/assets/87268161/bf4d0807-35b0-4d98-87c3-4b1696013ba0)


Check the parcel lockers and the saturation datas
![Image4](https://github.com/jano9415/Parcel-locker/assets/87268161/36e345ef-eef9-45ad-a927-c6b409e1313d)

Check my parcel that I am waiting for
![Image5](https://github.com/jano9415/Parcel-locker/assets/87268161/e253632d-0b0a-48af-af82-88f87d784506)
![Image24](https://github.com/jano9415/Parcel-locker/assets/87268161/c7368fa1-144f-4183-9b99-67db27f2783d)


Prices
![Image6](https://github.com/jano9415/Parcel-locker/assets/87268161/d870808d-9f80-442a-bc7f-49059ae5c3a7)
![Image25](https://github.com/jano9415/Parcel-locker/assets/87268161/0f4d854e-32d4-4f64-b5eb-5a84873f087e)


Common questions
![Image7](https://github.com/jano9415/Parcel-locker/assets/87268161/fbdc1ed3-36ef-4f04-9dda-563cd17ca099)
![Image26](https://github.com/jano9415/Parcel-locker/assets/87268161/4a3854d7-f556-4404-83a0-f076f4b9a470)


Send parcel with sending code after login. This is just a pre-sending. You still have to go to the parcel locker, type the sending code that you got in email and situate the parcel
![Image8](https://github.com/jano9415/Parcel-locker/assets/87268161/e843046b-c894-474a-8cf0-1a0c9ebb2b74)

Check my parcels that I sent
![Image9](https://github.com/jano9415/Parcel-locker/assets/87268161/b8f4b3b8-fb6a-44af-8e57-f17f2585c5f8)
![Image10](https://github.com/jano9415/Parcel-locker/assets/87268161/26d838db-b60f-4499-bbd9-c20349740e33)
![Image12](https://github.com/jano9415/Parcel-locker/assets/87268161/5c880667-e812-4239-a6a1-41eb93c52173)


Delete my parcel that I didn't placed in the parcel locker yet
![Image11](https://github.com/jano9415/Parcel-locker/assets/87268161/c5c6d27b-54b6-4565-9dca-2b399884ddfb)


Admin functions
Add new courier
![Image13](https://github.com/jano9415/Parcel-locker/assets/87268161/4d64aa62-64d9-49c0-b115-e006cfb90a50)
![Image27](https://github.com/jano9415/Parcel-locker/assets/87268161/65f089c5-bc9b-425c-9b16-575059a70363)
![Image28](https://github.com/jano9415/Parcel-locker/assets/87268161/386312e3-c551-46c6-95e9-2a34478db200)



Check couriers
![Image14](https://github.com/jano9415/Parcel-locker/assets/87268161/71d9e3dc-2afa-45d4-8fe7-3156e256632d)

Modify courier
![Image15](https://github.com/jano9415/Parcel-locker/assets/87268161/eb1f97c9-ccc0-498b-8488-6acf1361b0b0)

Check parcels of the parcel locker, parcels of the courier, parcels of the store and modify the parcel for example the expiration dates of the parcels
![Image20](https://github.com/jano9415/Parcel-locker/assets/87268161/60fa5963-7a16-486d-8ee3-b8f0a05a2163)
![Image21](https://github.com/jano9415/Parcel-locker/assets/87268161/26adf2aa-ba21-4223-b328-a9fea4964541)
![Image22](https://github.com/jano9415/Parcel-locker/assets/87268161/7c384468-6933-4366-933d-e34a7c2eab5a)
![Image23](https://github.com/jano9415/Parcel-locker/assets/87268161/52cb7320-f821-45c5-a786-be6ed5310bd7)





Statistic datas
![Image16](https://github.com/jano9415/Parcel-locker/assets/87268161/839f714a-0dd7-4ef3-a2ec-1286a772be94)

![Image17](https://github.com/jano9415/Parcel-locker/assets/87268161/26ad2155-564a-43ec-8d96-a6e5e4ae6ca7)

![Image18](https://github.com/jano9415/Parcel-locker/assets/87268161/d704b3df-85dc-47b4-9f10-d590cfb38e4e)

![Image19](https://github.com/jano9415/Parcel-locker/assets/87268161/81b44ccf-fad2-4712-bc79-2880864d2d91)


                                                        Android

Splash screen after starting the application



![Screenshot_2023-09-19-14-07-42-643](https://github.com/jano9415/Parcel-locker/assets/87268161/d7f08d42-82aa-44c5-92b2-2529cc0234e2)

Home page



![Screenshot_2023-09-19-14-07-55-714](https://github.com/jano9415/Parcel-locker/assets/87268161/dd75f918-4196-4ac8-bbcb-d082075a7428)

Registration



![Screenshot_2023-09-19-14-09-05-819](https://github.com/jano9415/Parcel-locker/assets/87268161/0dceee2d-663d-46d8-848b-cf5eb14a6610)

Check parcel lockers



![Screenshot_2023-09-19-14-09-53-887](https://github.com/jano9415/Parcel-locker/assets/87268161/2a10fda3-f1c7-4a7c-bf56-404d8bdf2aed)

Check saturation datas of parcel locker



![Screenshot_2023-09-19-14-10-09-462](https://github.com/jano9415/Parcel-locker/assets/87268161/445f44a0-3df4-48a1-a6a2-8a53b92005ec)

Home page after login by the user



![Screenshot_2023-09-19-14-10-53-082](https://github.com/jano9415/Parcel-locker/assets/87268161/9fa82f19-c804-4a6e-8c9f-2737777bbc03)

Send parcel. You have to login for this function. This is just a pre-sending. You still have to go to the parcel locker, type the sending code that you got in email and situate the parcel



![Screenshot_2023-09-19-14-11-04-183](https://github.com/jano9415/Parcel-locker/assets/87268161/1967b740-9aa8-4384-b79f-9308cccfa8df)
![Screenshot_2023-09-19-14-11-47-040](https://github.com/jano9415/Parcel-locker/assets/87268161/7352087d-a42e-4fba-8d4c-d9904e9b2c03)

Follow my parcel that I am waiting for



![Screenshot_2023-09-19-14-16-17-082](https://github.com/jano9415/Parcel-locker/assets/87268161/72627e0c-2a49-45cf-a98b-70aa284899d3)

Home page after login by the courier



![Screenshot_2023-09-19-14-18-43-679](https://github.com/jano9415/Parcel-locker/assets/87268161/26c7cc9f-c0b3-462c-b8c8-40b6a6760c6b)

Hand a parcel to the store and pick up a parcel from the store by scanning the barcode



![Screenshot_2023-09-19-14-18-56-335](https://github.com/jano9415/Parcel-locker/assets/87268161/144e0ba7-e5ea-41f6-b15c-cf067b0af2ab)
![Barcode read](https://github.com/jano9415/Parcel-locker/assets/87268161/dfaae4d4-e942-4373-9430-2ba2e206a303)
![Screenshot_2023-09-19-14-21-21-328](https://github.com/jano9415/Parcel-locker/assets/87268161/10fa7f9a-3ce9-4c6f-b8be-3ccd4de79588)







































