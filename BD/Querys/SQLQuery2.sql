SELECT TOP (1000) [dni_cliente]
      ,[nombre_cliente]
      ,[apellido1_cliente]
      ,[apellido2_cliente]
      ,[genero_cliente]
      ,[celular_cliente]
      ,[fnacimiento_cliente]
      ,[cliente_discapacitado]
      ,[detalle_discapacidad]
  FROM [ZariSpa].[dbo].[Cliente]

  insert into Cliente values('12312311','Carlos','Zari','Hidalgo',1,'191181777','10/01/1994',0,null)
  insert into Cliente values('12312322','Joshua','Zari','Hidalgo',1,'191181666','10/02/2000',0,null)


select*from PaqueteServiciosSolicitados

insert into PaqueteServiciosSolicitados values('12312311',0,'p01')
insert into PaqueteServiciosSolicitados values('12312322',0,'p02')

select* from ServiciosSolicitados

alter table ServiciosSolicitados
add codigo_paquete varchar(20)

insert into ServiciosSolicitados values('s01','baño alcalino','11111111','04/11/2024','09:00','10:00','tina 1',0,'p01','12312311');
insert into ServiciosSolicitados values('s02','baño super magnesio','22222222','04/11/2024','12:00','13:00','tina 1',0,'p01','12312311');
insert into ServiciosSolicitados values('s03','baño alcalino','33333333','04/11/2024','14:00','15:00','tina 1',0,'p01','12312311');
insert into ServiciosSolicitados values('s04','baño super magnesio','22222222','04/11/2024','11:00','12:00','tina 2',0,'p02','12312322');

exec sp_rename 'ServiciosSolicitados.hora_termino', 'hora_fin', 'COLUMN'