create table convenios
(
    partner_id   int auto_increment
        primary key,
    partner_name varchar(255) not null,
    status       tinyint      not null,
    description  varchar(255) not null
);

create table configuracion
(
    configuracion_id int auto_increment
        primary key,
    partner_id       int           not null comment 'Identificador del convenio al que está asociada la configuración',
    tipo_servicio    varchar(20)   not null comment 'indica si el tipo de servicio expuesto por el convenio es REST o SOAP',
    url              varchar(300)  not null comment 'url del endpoint del servicio',
    operacion        varchar(50)   not null comment 'Indica la operación que representa ese servicio
CONSULTAR, PAGAR, COMPENSAR',
    metodo           varchar(200)  not null comment 'Para los servicios REST corresponde al GET, POST, PUT, etc..., para
los servicios SOAP es el SOAPAction',
    payload          varchar(4000) null comment 'Paylad con los espacios para colocar los valores que se deben agragar para el llamado',
    reference_path   varchar(300)  null comment 'ruta dentro del objeto de respuesta para hallar el valor de la referencia',
    valor_path       varchar(300)  null comment 'ruta dentro del objeto de respuesta para hallar el valor de la factura',
    mensaje_path     varchar(300)  null comment 'ruta dentro del objeto de respuesta para hallar el mensaje',
    constraint configuracion_convenios_partner_id_fk
        foreign key (partner_id) references convenios (partner_id)
)
    comment 'Tabla que contiene la configuración de los servicios de cada convenio';

create table encabezados
(
    encabezado_id    int auto_increment
        primary key,
    configuracion_id int          not null comment 'identificador de la configuración asociada al encabezado',
    nombre           varchar(100) not null comment 'Nombre del encabezado',
    valor            varchar(200) not null comment 'valor del encabezado.',
    constraint encabezados_configuracion_configuracion_id_fk
        foreign key (configuracion_id) references configuracion (configuracion_id)
)
    comment 'Tabla que contiene los encabezados personalizados que se requieren para la invocación del servicio';

create table notificacionesBD.notificaciones
(
    notification_id int auto_increment
        primary key,
    partner_name    varchar(255)                        not null,
    user_id         int                                 not null,
    status          tinyint                             not null,
    user_name       varchar(255)                        not null,
    user_email      varchar(255)                        not null,
    invoice_ref     int                                 not null,
    message         varchar(255)                        not null,
    paid_invoice    decimal(15, 2)                      null,
    updated_at      timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    created_at      timestamp                           not null
);

create table pagoDispatcherBD.pagoDispatcher
(
    transaction_id int auto_increment
        primary key,
    partner_id     varchar(255) not null,
    user_id        int          not null,
    user_name      varchar(255) not null,
    user_email     varchar(255) not null,
    invoice_ref    int          not null,
    invoice        int          null,
    created_at     timestamp    not null,
    message        varchar(255) not null
);

create table usuariosDB.usuarios
(
    user_id    int auto_increment
        primary key,
    status     tinyint                             not null,
    user_email varchar(255)                        not null,
    user_name  varchar(255)                        not null,
    updated_at timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    created_at timestamp                           not null,
    password   char(64)                            not null
);

create view information_schema.CHARACTER_SETS as -- missing source code
;

create view information_schema.COLLATIONS as -- missing source code
;

create view information_schema.COLLATION_CHARACTER_SET_APPLICABILITY as -- missing source code
;

create view information_schema.COLUMNS as -- missing source code
;

create view information_schema.COLUMN_PRIVILEGES as -- missing source code
;

create view information_schema.ENGINES as -- missing source code
;

create view information_schema.EVENTS as -- missing source code
;

create view information_schema.FILES as -- missing source code
;

create view information_schema.GLOBAL_STATUS as -- missing source code
;

create view information_schema.GLOBAL_VARIABLES as -- missing source code
;

create view information_schema.INNODB_BUFFER_PAGE as -- missing source code
;

create view information_schema.INNODB_BUFFER_PAGE_LRU as -- missing source code
;

create view information_schema.INNODB_BUFFER_POOL_STATS as -- missing source code
;

create view information_schema.INNODB_CMP as -- missing source code
;

create view information_schema.INNODB_CMPMEM as -- missing source code
;

create view information_schema.INNODB_CMPMEM_RESET as -- missing source code
;

create view information_schema.INNODB_CMP_PER_INDEX as -- missing source code
;

create view information_schema.INNODB_CMP_PER_INDEX_RESET as -- missing source code
;

create view information_schema.INNODB_CMP_RESET as -- missing source code
;

create view information_schema.INNODB_FT_BEING_DELETED as -- missing source code
;

create view information_schema.INNODB_FT_CONFIG as -- missing source code
;

create view information_schema.INNODB_FT_DEFAULT_STOPWORD as -- missing source code
;

create view information_schema.INNODB_FT_DELETED as -- missing source code
;

create view information_schema.INNODB_FT_INDEX_CACHE as -- missing source code
;

create view information_schema.INNODB_FT_INDEX_TABLE as -- missing source code
;

create view information_schema.INNODB_LOCKS as -- missing source code
;

create view information_schema.INNODB_LOCK_WAITS as -- missing source code
;

create view information_schema.INNODB_METRICS as -- missing source code
;

create view information_schema.INNODB_SYS_COLUMNS as -- missing source code
;

create view information_schema.INNODB_SYS_DATAFILES as -- missing source code
;

create view information_schema.INNODB_SYS_FIELDS as -- missing source code
;

create view information_schema.INNODB_SYS_FOREIGN as -- missing source code
;

create view information_schema.INNODB_SYS_FOREIGN_COLS as -- missing source code
;

create view information_schema.INNODB_SYS_INDEXES as -- missing source code
;

create view information_schema.INNODB_SYS_TABLES as -- missing source code
;

create view information_schema.INNODB_SYS_TABLESPACES as -- missing source code
;

create view information_schema.INNODB_SYS_TABLESTATS as -- missing source code
;

create view information_schema.INNODB_SYS_VIRTUAL as -- missing source code
;

create view information_schema.INNODB_TEMP_TABLE_INFO as -- missing source code
;

create view information_schema.INNODB_TRX as -- missing source code
;

create view information_schema.KEY_COLUMN_USAGE as -- missing source code
;

create view information_schema.OPTIMIZER_TRACE as -- missing source code
;

create view information_schema.PARAMETERS as -- missing source code
;

create view information_schema.PARTITIONS as -- missing source code
;

create view information_schema.PLUGINS as -- missing source code
;

create view information_schema.PROCESSLIST as -- missing source code
;

create view information_schema.PROFILING as -- missing source code
;

create view information_schema.REFERENTIAL_CONSTRAINTS as -- missing source code
;

create view information_schema.ROUTINES as -- missing source code
;

create view information_schema.SCHEMATA as -- missing source code
;

create view information_schema.SCHEMA_PRIVILEGES as -- missing source code
;

create view information_schema.SESSION_STATUS as -- missing source code
;

create view information_schema.SESSION_VARIABLES as -- missing source code
;

create view information_schema.STATISTICS as -- missing source code
;

create view information_schema.TABLES as -- missing source code
;

create view information_schema.TABLESPACES as -- missing source code
;

create view information_schema.TABLE_CONSTRAINTS as -- missing source code
;

create view information_schema.TABLE_PRIVILEGES as -- missing source code
;

create view information_schema.TRIGGERS as -- missing source code
;

create view information_schema.USER_PRIVILEGES as -- missing source code
;

create view information_schema.VIEWS as -- missing source code
;


