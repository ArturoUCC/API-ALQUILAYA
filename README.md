[Uploading README_AlquilaYa.md…]()
# AlquilaYa — API REST de alquiler de vehículos

API REST desarrollada para **AlquilaYa S.A.**, una empresa ficticia de alquiler de vehículos. Permite administrar oficinas, vehículos, clientes, alquileres y mantenimientos mediante operaciones HTTP.

**Repositorio:** https://github.com/ArturoUCC/API-ALQUILAYA

## Descripción y funcionalidades

- Gestión CRUD de oficinas, vehículos, clientes, alquileres y mantenimientos.
- Asociación de vehículos a oficinas, alquileres a clientes y vehículos, y mantenimientos a vehículos.
- Cálculo automático del costo de alquiler a partir de la tarifa diaria y las fechas.
- Administración de estados del alquiler: `Pendiente`, `Activo` y `Completado`.
- Al activar un alquiler, el vehículo pasa a `Alquilado`; al completarlo, vuelve a `Disponible`.
- Comprobación básica de reservas con fechas coincidentes.

> **Alcance:** es un proyecto académico. Se verificaron mediante Postman los registros de oficina, vehículo, cliente, alquiler y mantenimiento; la actualización de mantenimiento; la activación y finalización del alquiler; y la consulta del estado del vehículo. Otras reglas (por ejemplo, concurrencia, eliminación de registros relacionados y validación integral de datos) requieren pruebas o ajustes adicionales antes de usarlo en producción.

## Tecnologías utilizadas

- Java 21 (versión configurada en Maven).
- Spring Boot 4.1.1.
- Spring Web MVC.
- Spring Data JPA e Hibernate.
- MySQL y MySQL Connector/J.
- Maven Wrapper (`mvnw` / `mvnw.cmd`).
- Postman para pruebas HTTP.
- Thymeleaf incluido como dependencia opcional para vistas.

## Requisitos previos

- JDK 21 o una versión compatible con el proyecto.
- MySQL instalado y en ejecución (puerto habitual: `3306`).
- Acceso a MySQL con un usuario autorizado a crear o utilizar la base `alquilaya`.
- Visual Studio Code con soporte para Java (opcional; también puede utilizarse la terminal).

## Instalación y ejecución local

1. Clonar el repositorio:

   ```bash
   git clone https://github.com/ArturoUCC/API-ALQUILAYA.git
   cd API-ALQUILAYA
   ```

2. Configurar `src/main/resources/application.properties` sin guardar credenciales reales en Git:

   ```properties
   spring.application.name=alquilaya
   server.port=1500
   spring.datasource.url=jdbc:mysql://localhost:3306/alquilaya?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC
   spring.datasource.username=${DB_USERNAME:root}
   spring.datasource.password=${DB_PASSWORD}
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   ```

   `createDatabaseIfNotExist=true` permite crear la base de datos cuando el usuario MySQL dispone de permisos suficientes. `ddl-auto=update` permite que Hibernate cree o actualice las tablas a partir de las entidades; se usa aquí para desarrollo local.

3. Definir las variables de entorno **en la sesión desde la cual se iniciará la aplicación**.

   **PowerShell (Windows):**

   ```powershell
   $env:DB_USERNAME = "root"
   $env:DB_PASSWORD = Read-Host "Contraseña de MySQL"
   ```

   **Linux/macOS (Bash):**

   ```bash
   export DB_USERNAME=root
   read -rs -p 'Contraseña de MySQL: ' DB_PASSWORD; echo
   export DB_PASSWORD
   ```

   Si se ejecuta desde el botón **Run** de VS Code, las variables definidas en otra terminal podrían no heredarse; configure el entorno de la ejecución de Java o inicie Maven desde la misma terminal.

4. Iniciar la API desde la raíz del proyecto (carpeta que contiene `pom.xml`):

   **Windows (PowerShell):**

   ```powershell
   .\mvnw.cmd spring-boot:run
   ```

   **Linux/macOS:**

   ```bash
   ./mvnw spring-boot:run
   ```

5. Verificar el servicio mediante `GET http://localhost:1500/api/oficinas`. Si no existen registros, es normal recibir `[]`.

> **Nota:** el puerto `1500` debe estar libre. Si la API Biblioteca utiliza el mismo puerto, deténgala antes de ejecutar AlquilaYa.

## Variables de entorno

| Variable | Obligatoria | Descripción |
|---|---|---|
| `DB_PASSWORD` | Sí | Contraseña del usuario de MySQL. |
| `DB_USERNAME` | No | Usuario de MySQL. Por defecto: `root`. |

La URL de conexión y el puerto HTTP están definidos en `application.properties` y pueden adaptarse al entorno local.

## Endpoints disponibles

**Base URL:** `http://localhost:1500`

| Método | Endpoint | Descripción |
|---|---|---|
| `GET` | `/api/oficinas` | Listar oficinas. |
| `GET` | `/api/oficinas/{id}` | Obtener una oficina. |
| `POST` | `/api/oficinas` | Crear oficina. |
| `PUT` | `/api/oficinas/{id}` | Actualizar oficina. |
| `DELETE` | `/api/oficinas/{id}` | Eliminar oficina. |
| `GET` | `/api/vehiculos` | Listar vehículos. |
| `GET` | `/api/vehiculos/{id}` | Obtener un vehículo. |
| `POST` | `/api/vehiculos` | Crear vehículo. |
| `PUT` | `/api/vehiculos/{id}` | Actualizar vehículo. |
| `DELETE` | `/api/vehiculos/{id}` | Eliminar vehículo. |
| `GET` | `/api/clientes` | Listar clientes. |
| `GET` | `/api/clientes/{id}` | Obtener un cliente. |
| `POST` | `/api/clientes` | Crear cliente. |
| `PUT` | `/api/clientes/{id}` | Actualizar cliente. |
| `DELETE` | `/api/clientes/{id}` | Eliminar cliente. |
| `GET` | `/api/alquileres` | Listar alquileres. |
| `GET` | `/api/alquileres/{id}` | Obtener un alquiler. |
| `POST` | `/api/alquileres` | Registrar alquiler y calcular costo. |
| `PUT` | `/api/alquileres/{id}` | Actualizar alquiler pendiente. |
| `PUT` | `/api/alquileres/{id}/activar` | Activar alquiler. |
| `PUT` | `/api/alquileres/{id}/completar` | Completar alquiler. |
| `DELETE` | `/api/alquileres/{id}` | Eliminar alquiler pendiente. |
| `GET` | `/api/mantenimientos` | Listar mantenimientos. |
| `GET` | `/api/mantenimientos/{id}` | Obtener mantenimiento. |
| `POST` | `/api/mantenimientos` | Registrar mantenimiento. |
| `PUT` | `/api/mantenimientos/{id}` | Actualizar mantenimiento. |
| `DELETE` | `/api/mantenimientos/{id}` | Eliminar mantenimiento. |

Las rutas que contienen `{id}` reciben el identificador numérico del registro. Las solicitudes con JSON deben utilizar `Content-Type: application/json`.

## Ejemplos de peticiones y respuestas

Los ejemplos muestran los datos utilizados en las pruebas locales. **Los ID pueden variar** según el contenido de la base de datos. Para crear un vehículo, primero debe existir su oficina; para crear un alquiler, primero deben existir su cliente y vehículo.

### 1. Registrar una oficina

**POST** `/api/oficinas`

```json
{
  "nombreOficina": "AlquilaYa Veracruz",
  "direccion": "Av. Independencia 150",
  "ciudad": "Veracruz",
  "region": "Veracruz"
}
```

**Respuesta observada:** `200 OK`

```json
{
  "id": 1,
  "nombreOficina": "AlquilaYa Veracruz",
  "direccion": "Av. Independencia 150",
  "ciudad": "Veracruz",
  "region": "Veracruz"
}
```

### 2. Registrar un vehículo

**POST** `/api/vehiculos`

```json
{
  "marca": "Toyota",
  "modelo": "Corolla",
  "anio": 2023,
  "placa": "VER123A",
  "tarifaDiaria": 500.0,
  "estado": "Disponible",
  "oficina": { "id": 1 }
}
```

**Respuesta observada:** `200 OK`. El servidor devuelve el vehículo creado, su ID y la información de la oficina asociada.

### 3. Registrar un cliente

**POST** `/api/clientes`

```json
{
  "nombre": "Carlos",
  "apellido": "Ramirez",
  "correo": "carlos.ramirez@ejemplo.com",
  "licencia": "LIC123456",
  "telefono": "2291234567"
}
```

**Respuesta observada:** `200 OK`

```json
{
  "id": 1,
  "nombre": "Carlos",
  "apellido": "Ramirez",
  "correo": "carlos.ramirez@ejemplo.com",
  "licencia": "LIC123456",
  "telefono": "2291234567"
}
```

### 4. Registrar un alquiler

**POST** `/api/alquileres`

```json
{
  "vehiculo": { "id": 1 },
  "cliente": { "id": 1 },
  "fechaInicio": "2026-10-20",
  "fechaFin": "2026-10-23"
}
```

**Respuesta observada:** `200 OK`. Extracto de los campos principales de la respuesta (también incluye los objetos `cliente` y `vehiculo`):

```json
{
  "id": 1,
  "fechaInicio": "2026-10-20",
  "fechaFin": "2026-10-23",
  "costoTotal": 1500.0,
  "estado": "Pendiente"
}
```

El costo se calculó como **3 días × 500 por día = 1500**.

### 5. Activar y completar el alquiler

**PUT** `/api/alquileres/1/activar` — sin cuerpo. **Respuesta observada:** `200 OK`; el alquiler cambia a `Activo` y el vehículo a `Alquilado`.

**GET** `/api/vehiculos/1` — **respuesta observada:** `200 OK`, con `"estado": "Alquilado"`.

**PUT** `/api/alquileres/1/completar` — sin cuerpo. **Respuesta observada:** `200 OK`; el alquiler cambia a `Completado` y el vehículo vuelve a `Disponible`.

### 6. Registrar y actualizar un mantenimiento

**POST** `/api/mantenimientos`

```json
{
  "vehiculo": { "id": 1 },
  "fechaMantenimiento": "2026-10-14",
  "descripcion": "Cambio de aceite y revision general de frenos",
  "costo": 850.00
}
```

**Respuesta observada:** `200 OK`; se registró el mantenimiento con ID `1`.

**PUT** `/api/mantenimientos/1`

```json
{
  "vehiculo": { "id": 1 },
  "fechaMantenimiento": "2026-10-14",
  "descripcion": "Cambio de aceite, revision de frenos y reemplazo de filtros",
  "costo": 1200.00
}
```

**Respuesta observada:** `200 OK`; el mantenimiento mantuvo su ID `1` y actualizó el costo a `1200.0`.

## Seguridad y archivos excluidos

- **Nunca** subir al repositorio contraseñas reales, tokens ni archivos `.env` con secretos.
- Mantener las credenciales fuera de `application.properties` y utilizar variables de entorno.
- Excluir archivos generados y temporales como `target/`, `.classpath`, `.project`, `.settings/` y archivos locales del editor cuando corresponda.
- No publicar datos reales de clientes; los ejemplos del README son ficticios.
- Si una contraseña ya fue publicada en Git, cambiarla y revisar el historial del repositorio: añadirla a `.gitignore` no elimina versiones anteriores.

**Ejemplo de `.gitignore` adicional:**

```gitignore
.env
.env.*
!.env.example
target/
*.log
```

## Estado de las pruebas

Se documentaron mediante Postman nueve operaciones exitosas: creación de oficina, vehículo, cliente, alquiler y mantenimiento; activación de alquiler; consulta del vehículo alquilado; finalización de alquiler; y actualización de mantenimiento. Esto **no equivale** a una verificación exhaustiva de todas las rutas ni de escenarios de error.
