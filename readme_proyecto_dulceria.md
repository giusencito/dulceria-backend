# 🍬 Proyecto  – Flujo Completo de Compra

Este proyecto implementa un **flujo completo de compra de dulces**  con **Frontend en React + TypeScript** y **Backend en Spring Boot**, integrando **autenticación JWT / Google Sign-In**, **pagos con PayU (sandbox)** y **persistencia de órdenes mediante Stored Procedures en MySQL**.

---
- **Frontend**: React + TypeScript + Styled Components
- **Backend**: Spring Boot (JPA + Stored Procedures) - MicroServicios 
- **Auth**: JWT + Google OAuth
- **Pagos**: PayU Latam (pruebas)
- **DB**: MySQL

---

## 🧩 Funcionalidades

### Flujo del Usuario
1. **Home**
   - Lista de imágenes + textos
   - Click en imagen → Login

2. **Login**
   - Google Sign-In (opcional)
   - Ingreso como Invitado
   - Modal de bienvenida
   - Guarda JWT en `localStorage`

3. **Dulcería** (requiere token)
   - Listado de productos en cards
   - Selección de uno o más productos
   - Control de cantidad
   - Total dinámico
   - Continuar a Pago Al Seleccionar Productos

4. **Pago**
   - Formulario con Validaciones de Campos
   - Tarjeta de Credito, Fecha de Expiración,CVV, Correo , Nombre, Documento, Tipos de documento Perú (DNI, CE, PAS)
   - Integración PayU
   - Registro de orden + detalles

5. **Órdenes del Usuario** (Adicional)
   - Accesible si inició sesión con Google
   - Listado de Ordenes en cards
   - Modal con detalle de compras
   - Aparece solo cuando esta logueado con Google
   
6**Cierre de Sesión**
- Accesible si inició sesión con Google


---

## 🎨 Frontend (React + TypeScript)

### Tecnologías
- React 19
- TypeScript
- React Router DOM
- Styled Components
- Axios
- React Hook Form
- Redux
- @react-oauth/google


## 🔐 Autenticación

- Google OAuth
- JWT generado por backend
- Token almacenado en `localStorage`
- La ruta **Dulcería** se deshabilita si no hay token.
- Interceptor Axios añade:
```
Authorization: Bearer <token>
```



## 🍭 Backend – Microservicio Complete

### Tecnologías
- Spring Boot 
- Spring Security + JWT
- JPA / Hibernate
- MySQL
- Stored Procedures
- RestTemplate
- Swagger
---

### Entidades Principales

#### Order
- email
- name
- documentType
- documentNumber
- transactionId
- operationDate

#### OrderDetail
- order_id
- product_id
- quantity

Clave compuesta con `@EmbeddedId`.

---

## 🗄️ Stored Procedures

### Crear Orden + Detalles

```sql
CALL sp_create_order(
  email,
  name,
  document_type,
  document_number,
  transaction_id,
  operation_date,
  items_json
);
```
- Maneja transacción completa
- Inserta order + order_details
- Usa `NOW()` para `created_at`
- Manejo de errores con `SIGNAL`

---

## Endpoints

### OrderController
   - email/{email}   Consiste en obtener ordenes segun el correo
   - orderId/{orderId}  Consiste en obtener ordenes según orderId
### PaymentController
   - check Valida la compra con PAYUP
   - confirm Insertar la orden y su detalle en la base de datos
---

## 💳 Integración PayU (Pruebas)

### Datos Usados
- API Login
- API Key
- Merchant ID
- Account ID

### Respuesta
Se obtiene:
- transactionId
- operationDate
- state
---

## 🔁 Comunicación entre Microservicios

- Órdenes → Productos
- RestTemplate
- Token JWT propagado vía Authorization Header

---

## 🧪 Swagger

Disponible en:
```
http://localhost:8083/swagger-ui/index.html
```

Incluye autenticación JWT.

---


## 🍭 Backend – Microservicio Premieres

### Tecnologías
- Spring Boot
- JPA / Hibernate
- MySQL
- Stored Procedures
- Swagger
---

### Entidades Principales

#### Premiere
- imageUrl
- description

## 🗄️ Stored Procedures

### Obtener los Premieres

```sql
CALL sp_get_premieres(
);
```
## Endpoints

### PremiereController
- premieres   Consiste en obtener premieres

### Adicional
- Se cuenta con una funcion para al momento de compilar se regsitren premieres base


## 🧪 Swagger

Disponible en:
```
http://localhost:8081/swagger-ui/index.html
```

## 🍭 Backend – Microservicio CandyStore

### Tecnologías
- Spring Boot
- Spring Security + JWT
- JPA / Hibernate
- MySQL
- Stored Procedures
- Swagger
---

### Entidades Principales

#### Product
- name
- description
- price

## 🗄️ Stored Procedures

### Obtener los Productos

```sql
CALL sp_get_products(
);
```
### Obtener los Productos segun IDS

```sql
CALL sp_get_products(
     ids JSON
);
```

## Endpoints

### ProductoController
- products   Consiste en obtener productos
- ids  Obtener los productos según los ids

### AuthController 
-login Generar token  si es una cuenta de Google o Invitado

### Adicional
- Se decidio para este caso colocar funciones en relación a generar Token este Microservicio al ser el primero que tiene que ir gestionando la orden


## 🧪 Swagger

Disponible en:
```
http://localhost:8082/swagger-ui/index.html
```


Incluye autenticación JWT.

---

## Alcaraciones
- Login con Google
  - Actualmente esta con usuarios de pruebas definidos comunicarse conmigo en caso se quiera probar para agendar un correo
  - Uri tambien esta definido en caso no deje avanzar comunicar uri que se esta usando para agregarlo


---




