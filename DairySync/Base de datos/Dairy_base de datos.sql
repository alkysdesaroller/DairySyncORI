create database DairySync;

use DairySync;

/*Entidad Grajero*/
create table Granjero(
Id int primary key auto_increment,
Nombre varchar(255) not null, 
Correo varchar(255) unique not null,
Password varchar(255) not null 
);

/*Entidad Granja*/
create table Granja(
Id int primary key, 
Direccion varchar(255), 
cantidadAnminal varchar(255), 
granjeroId int, 
foreign key (grajeroId) references Granjero(Id)
);
/*Entidad Vaca*/
create table Vaca(
Id int primary key auto_increment, 
raza varchar(100) not null, 
edad int not null,  
madreId int,
padreId int,
granjaId int, 
foreign key(GranjaId) references Granja(Id), 
foreign key(padreID) references Vaca(id), 	/*Composicion del atributo genealogia y asi rastrear la genealogia de manera mas estructurada.*/
foreign key(madreID) references Vaca(id)
); 


create table Alimento(
Id int primary key auto_increment, 
nombre varchar(50) not null, 
Descripcion text
);

/*Entidad alimentacion*/
create table alimentacion(
Id int primary key auto_increment, 
alimentoId int,
cantidad float not null, 
fecha date not null, 
vacaId int, 
granjaId int, 
foreign key(alimentoId) references Alimento(Id),
foreign key (vacaId) references Vaca(Id), 
foreign key (granjaId) references Granja(Id)
);

create table produccion(
Id int primary key auto_increment, 
fecha date not null, 
litros float not null, 
foreign key (VacaId) references Vaca(Id),
foreign key (grajaId) references Granjas(Id)
);

create table reproduccion(
Id int primary key auto_increment, 
fechaInseminacion date not null, 
fechaParto date,
estado enum('Inseminada', 'Preñada', 'Parida') not null, 
madreId int,
padreId int,
VacaId int,
foreign key (VacaId) references Vaca(Id),
foreign key (madreId) references Vaca(Id),
foreign key (padreId) references  Vaca(Id)
);

create table tipo_reporte(
Id int primary key auto_increment, 
nombre varchar(255) not null, 
descripcion text
);

create table Report(
Id int primary key auto_increment, 
fechaGeneracion date not null, 
tipoRepoteId int, 
foreign key(tipoRepoteId) references tipo_reporte(Id)
);

create table tipo_alerta(
id int primary key auto_increment, 
nombre varchar(255) not null, 
descripcion text 
);

create table estado_Alerta(
id int primary key auto_increment, 
nombre varchar(255) not null 
);


create table Alerta(
id int primary key auto_increment, 
tipoAlertId int, 
descripcion text not null, 
estadoId int, 
reporteId int, 
vacaId int,
foreign key(tipoAlertId) references tipo_alerta(id),
foreign key(estadoId) references estado_alerta(id),
foreign key(reporteId) references Report(id),
foreign key(vacaId) references Vaca(id)
);

