import mysql.connector

mydb = mysql.connector.connect(
  host="localhost",
  user="root",
  password="",
  database="myflaskapp_"
)

mycursor = mydb.cursor()

mycursor.execute("CREATE TABLE users (id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255), email VARCHAR(255), username VARCHAR(255), password VARCHAR(255))")
mycursor.execute("CREATE TABLE schedule (id INT AUTO_INCREMENT PRIMARY KEY, start_day VARCHAR(255), finish_day VARCHAR(255), hour VARCHAR(255), author VARCHAR(255), taskName VARCHAR(255), descriptionTask VARCHAR(255))")