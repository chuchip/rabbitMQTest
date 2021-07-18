# rabbitMQTest
Pruebas con colas de RabbitMQ en Spring Boot

Cada segundo manda el mensaje 'Hola Mundo' a la cola `LISTENER_MANUAL`.

Si se ejecuta  con el profile "connection" establece la conexión manualmente a RabbitMQ en la función *connectionFactory* de  la clase *RabbitMQApplication*

Se crea un  *binding* con el exchange `spring-boot-exchange` a traves de la ruta `${listener.route}`.

Existen dos controladores REST:

- GET localhost:8080/{exchange}/{ruta}/{msg} 
- GET localhost:8080//{ruta}/{msg}
 
###Ejemplos:

Mandar mensaje "holarabbit1" a cola `LISTENER_MANUAL`
- curl localhost:8081/profe-rabbit/holarabbit1

Mandar mensaje *"holarabbitlocal"*  a RUTA `ruta-profesor`
- curl localhost:8081/exchange/ruta-profesor/holarabbitlocal
