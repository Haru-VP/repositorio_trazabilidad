# PowerUp - Microservicio Trazabilidad

Microservicio del ecosistema **Plazoleta** responsable del registro de auditoría, trazabilidad de pedidos y análisis de eficiencia operativa. Construido bajo **Arquitectura Hexagonal**, base de datos NoSQL **MongoDB**, seguridad basada en tokens **JWT**, y documentación viva **OpenAPI / Swagger**.

---

## 1. Características Técnicas
- **Puerto del Servicio**: `8084`
- **Base de Datos NoSQL**: MongoDB (`powerup_trazabilidad`, colección `trazabilidad`)
- **Seguridad**: Spring Security con autenticación Stateless mediante JWT Bearer tokens
- **Documentación Interactiva**: Swagger UI en `http://localhost:8084/swagger-ui/index.html`

---

## 2. Historias de Usuario Soportadas
- **HU-17 (Cliente)**: Consultar historial de trazabilidad cronológico del pedido verificando pertenencia del cliente.
- **HU-18 (Propietario)**: Consultar métricas de eficiencia operativa y tiempos de respuesta (preparación y entrega).

---

## 3. Endpoints Principales
- `POST /api/v1/trazabilidad`: Registra un evento o cambio de estado de un pedido (invocado por `powerup-plazoleta`).
- `GET /api/v1/trazabilidad/pedido/{idPedido}`: Obtiene el listado de cambios de estado en orden cronológico (restringido al cliente dueño del pedido).
