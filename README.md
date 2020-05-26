# Taller De Servicios de ModVal EAS

![picture](DiagramaServiciosFacturas.png)

En este diagrama modelamos la solución del problema de pago de facturas de diferentes proveedores y que sea transparente el ingreso de un nuevo convenio de pago de facturas ya sea a través de servicios Rest o Soap.
Como podemos ver tendremos un único punto de acceso de las peticiones desde los diferentes canales de pago a través de un API gateway, este API hará el llamado a los servicios que tenemos expuestos para los clientes, que son la validación del cliente, la consulta del valor a cancelar de una factura por un convenio específico, o el pago de una factura por un convenio, en ambos casos se hace el llamado del servicio de validación del convenio, una vez se confirma el pago se hace el llamado con el servicio de pago al servicio expuesto por el proveedor para realizar el pago y con la confirmación o no del mismo haremos el llamado al proceso de notificaciones que tenemos a través de los servicios de Kafka, esté al recibir el mensaje lo direcciona a las colas que tiene para realizar el evento de notificación que hace el llamado a nuestro servicio de notificación que tenemos con el que se le enviara por correo electrónico la evidencia al cliente de la confirmación del pago de la factura de servicios.
Todos nuestro servicios tienen su propia base de persisitencia de datos.

[Contribution guidelines for this project](Infraestructura/aws_eks/README.md)


