## Data Model 
The data model used in this project is composed of 4 classes corresponding to the tables in MySql database. All classes contain all data in table’s columns.
They contain gettter, setter and an override method of toSpring. They all represent an Entiity in the Spring framework.
* the Client data model contains a list of rentals and a list of cargo services
* the Rental class contains a client model and a dock that the client has rented
* the Cargo data model contains a client that made the appointment for that service
* the Dock data model contains a list of all rentals made in that dock in time
* the SaleNumber entity contains the number of available docks under witch the prices will drop with 20%. The number can be set and modified only by admin. The client dosen't have access to it. He will only observe the sale :)
All connections between data models were made by hybernate framework and implememntetd through special adnotations and properties and created in spring context.
![modelDiagr](ERFinal.PNG)

## Design Patterns 
* The project will contain the Observable Design Pattern. Whenever the schedule for cargo lading and unloading will be modified, the clients that are affected must be notified with the upgrated data. The clients will be the obervers and the server will be the one to notify them. An update on their cargo service must be made. 
* Another design pattern will be the Adapter Design Pattern. It will be used to map all information the server needs from client in order to provide a service. Not all client’s information will be used in the server part and the hibernate entity of aa model cannot be send through processes in the program. Mapper service interfaces will be used as adapters in order to solve this problem.

## Sequence Diagram

Buy dock :
![buy](seqDiagrBuy.PNG)

## Deployment diagram : 
![deployment](deploymentFinal.PNG)


## Use-cases : 
### Use case:  user requests
     Level: user-goal level
     Primary actor: client
     Main success scenario: user request -> server receives request -> server provide service -> user receives
     Extensions: 
          -	  User login -> incorrect password/ username -> failure -> try again
          - 	User request -> server couldn’t find the service requested -> failure -> ask for other available service
          -	  User request -> this service is not authorized for this user type -> 401 error
 ![firstUseCase](USERCASE1.PNG)   
 
 ### Use case:  user requests that involves the admin
    Level: user-goal level
    Primary actor: client & admin
    Main success scenario: admin requests modifications -> server modifies data  -> user perform action/ is notified
    Extensions: 
        -	  User login -> incorrect password/ username -> failure -> try again
        -	  User request -> this service is not authorized for this user type -> 401 error
        - 	Admin login -> incorrect password/ username -> failure -> try again
        - 	User request -> server couldn’t find the service requested -> failure -> ask for other available service
        - 	Admin request -> server asks admin password -> admin types incorrect password -> failure -> drop service request
![firstUseCase](usecaseAdmin.PNG)   

###  Use case:  Dock rental
    Level: subfunction level
    Primary actor: client 
    Main success scenario: user request -> server receives request -> server search special treatments -> server saves new data in DB -> server provide service -> user receives
    Extensions: 
        -	  User login -> incorrect password/ username -> failure -> try again
        -	  User request -> server couldn’t find the service requested -> failure -> ask for other available service
        - 	User register -> server found the account currency to low  -> failure -> ask for regular client registration
        -  	User request -> this service is not authorized for this user type -> 401 error
 ![firstUseCase](usecaseLowLevel.PNG)   


## Packadge diagram : 
 ![packadge](packadgeDiagr.png)   
 
 
 
 
 ## Class diagram :
  ![class](classDiagram.png)   





