# gestor-empleo

Aplicacion web para organizar la busqueda de empleo y la preparacion de entrevistas.

## Objetivo

El sistema permite gestionar candidaturas, empresas, contactos, estados del proceso, notas de seguimiento, recordatorios, notificaciones y material de preparacion para entrevistas.

## Arquitectura

```text
Angular
  -> REST + JWT
Microservicios Spring Boot
  |-- auth-service
  |-- application-service
  `-- notification-service
       -> PostgreSQL + MongoDB + Kafka
```

## Modulos

- `auth-service`: registro, login, JWT, validacion de token y roles basicos.
- `application-service`: candidaturas, empresas, contactos, estados, recordatorios, notas, historial y preparacion de entrevistas. Usa arquitectura hexagonal con dominio, puertos y adaptadores.
- `notification-service`: consumo de eventos Kafka y generacion de avisos.
- `frontend`: aplicacion Angular.

## Arquitectura hexagonal en application-service

```text
domain/model
  modelos de negocio sin dependencias de Spring ni JPA

domain/port/in
  casos de uso que puede ejecutar la aplicacion

domain/port/out
  contratos que la aplicacion necesita hacia fuera

application/service
  implementacion de los casos de uso

infrastructure/adapter/in/web
  controladores REST

infrastructure/adapter/out/persistence
  adaptador JPA/PostgreSQL
```

## Infraestructura local pendiente

La infraestructura local se creara paso a paso de forma manual. Los servicios previstos son:

- PostgreSQL para datos relacionales.
- MongoDB para informacion flexible de preparacion.
- Zookeeper y Kafka para eventos entre servicios.

## Puertos previstos

- Auth service: `8081`
- Application service: `8082`
- Notification service: `8083`
- PostgreSQL: `5432`
- MongoDB: `27017`
- Kafka: `9092`

## Roadmap recomendado

1. Crear estructura base del proyecto.
2. Configurar PostgreSQL de forma manual.
3. Implementar empresas y candidaturas en `application-service`.
4. Anadir contactos, recordatorios y notas de seguimiento.
5. Crear `auth-service` con Spring Security y JWT.
6. Proteger endpoints con JWT.
7. Anadir MongoDB para preparacion de entrevistas.
8. Publicar eventos Kafka al cambiar el estado de una candidatura.
9. Crear `notification-service` para consumir eventos.
10. Anadir Spring Batch para resumen semanal y recordatorios vencidos.
11. Crear frontend Angular.
12. Anadir tests con JUnit y Mockito.
13. Documentar APIs con OpenAPI.
14. Dockerizar microservicios.
15. Anadir Kubernetes de forma guiada.
16. Documentar despliegue final en AWS.

## Resumen para entrevista

He desarrollado una plataforma web para gestionar candidaturas de empleo y preparacion de entrevistas usando microservicios con Spring Boot y Angular. Utilizo PostgreSQL para datos relacionales, MongoDB para documentacion flexible del usuario, Kafka para comunicacion asincrona, Spring Security con JWT para autenticacion, Spring Batch para procesos automaticos, Docker y Kubernetes para despliegue, y AWS como plataforma cloud.
