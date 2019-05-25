# Property Trader

## Description
A full stack Website built using Java Server Faces (JSF) as part of a project at American University of Sharjah

The website allows people to put up their properties (apartments/villas/land) up for sale or to search for properties for purchase. Dummy payment methods are used for processing transactions in case of a sale which can easily be replaced by appropriate API calls if required. The website also provides statistics for properties available in different areas or number of properties in different price ranges etc. Additionally, the user can also provide reviews for the seller which can be viewed by other users on the seller's profile.

## Requirments
* [JAVA 8](https://www.java.com/en/download/)
* [Netbeans 8.2](https://netbeans.org/downloads/8.2/)

## Installation
1. Clone the repository
```bash
$ git clone https://github.com/Aranyak-Ghosh/PropertyTrader.git
```
2. Open the project in Netbeans IDE.
3. Edit the Database configurations in `web/WEB-INF/faces-config.xml` file
4. Connect to the database and execute the SQL script available in `src/PropertyTrader.sql`
5. Ensure that GlassFish Server is running
6. Build and Run the project
7. View the project on localhost:8080/PropertyTrader

>Icons made by [Freepik](https://www.freepik.com/) from [Flaticon](https://www.flaticon.com/) and is licensed by [Creative Commons BY 3.0](http://creativecommons.org/licenses/by/3.0/) 