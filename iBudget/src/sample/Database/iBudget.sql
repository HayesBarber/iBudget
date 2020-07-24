drop database iBudget;
create database iBudget;
use iBudget;


create table users (
userid INT NOT NULL AUTO_INCREMENT,
username VARCHAR(45) NOT NULL,
upassword VARCHAR(45) NOT NULL,
salary DOUBLE NOT NULL,
incomeGoal DOUBLE,
accountBalance DOUBLE,
primary key(userid)
);

create table income (
incomeid INT NOT NULL AUTO_INCREMENT,
incomeAmount DOUBLE NOT NULL,
incomeDescription VARCHAR(45) NOT NULL,
incomeDate DATE NOT NULL, 
userid INT NOT NULL,
primary key(incomeid),
foreign key(userid) REFERENCES users(userid)
ON DELETE CASCADE
);

create table expense(
expenseid INT NOT NULL AUTO_INCREMENT,
expenseAmount DOUBLE NOT NULL,
expenseDescription VARCHAR(45) NOT NULL,
expenseDate DATE NOT NULL, 
userid INT NOT NULL,
primary key(expenseid),
foreign key(userid) REFERENCES users(userid)
ON DELETE CASCADE
);
