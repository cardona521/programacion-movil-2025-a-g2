# HU-01: Entender el negocio de canchas sintéticas

## Descripción
**Como** equipo de desarrollo.

**Quiero** comprender el negocio de la gestión de reservas de canchas sintéticas.

**Para** definir correctamente la arquitectura y los requisitos técnicos de la aplicación móvil.

### Criterios de Aceptación
1. **Registro y Autenticación**: Como usuario, quiero registrarme e iniciar sesión en la aplicación para poder acceder de manera segura a las funciones de reserva y gestión de canchas.
2. **Realizar Reserva**: Como usuario, quiero poder seleccionar una cancha disponible y confirmar mi reserva para que la cancha quede apartada para mi uso y nadie más pueda ocuparla en ese horario.
3. **Editar o Cancelar Reserva**: Como usuario, quiero modificar o cancelar una reserva que ya realicé para poder ajustar cambios de último minuto sin perder la oportunidad de jugar.
4. **Ver Mis Reservas**: Como usuario, quiero acceder a un historial de mis reservas para poder revisar qué canchas he reservado, en qué fechas y a qué horas.
5. **Reportes de Uso**: Como administrador, quiero obtener reportes del uso de las canchas para poder analizar la demanda y optimizar la disponibilidad de los espacios.

### Prioridad y Estimación
📌 **Prioridad**: Alta  
🕒 **Estimación**: 5 días


# Documentación del Diagrama de Base de Datos para la Gestión de Canchas Sintéticas

### 1. Descripción General
Este diagrama representa la estructura de una base de datos diseñada para la gestión de canchas sintéticas. Permite manejar usuarios, reservas, pagos, horarios de disponibilidad y reseñas, proporcionando una solución completa para la administración de estos espacios.

### 2. Descripción de las Tablas

#### 2.1 Usuario
Almacena la información de los usuarios del sistema.
- **id_usuario** (INTEGER): Identificador único del usuario.
- **nombre** (VARCHAR(100)): Nombre completo del usuario.
- **correo** (VARCHAR(100)): Correo electrónico del usuario.
- **telefono** (VARCHAR(20)): Número de teléfono.
- **contrasena** (VARCHAR(255)): Contraseña encriptada del usuario.
- **rol** (ENUM): Rol del usuario (Ejemplo: Cliente, Administrador).
- **fecha_registro** (TIMESTAMP): Fecha de registro del usuario.

#### 2.2 Administrador
Relaciona a los usuarios que tienen privilegios administrativos.
- **id_admin** (INTEGER): Identificador del administrador.
- **id_usuario** (INTEGER): Referencia a la tabla Usuario.
- **cargo** (VARCHAR(100)): Cargo o función del administrador.

#### 2.3 Cancha
Guarda información sobre las canchas disponibles.
- **id_cancha** (INTEGER): Identificador único de la cancha.
- **nombre** (VARCHAR(100)): Nombre de la cancha.
- **ubicacion** (VARCHAR(255)): Ubicación física de la cancha.
- **tipo** (ENUM): Tipo de cancha (Ejemplo: Fútbol, Tenis, etc.).
- **estado** (ENUM): Estado de la cancha (Ejemplo: Disponible, Mantenimiento).
- **precio_por_hora** (DECIMAL(10,2)): Costo por hora de la cancha.
- **descripcion** (TEXT): Descripción adicional.
- **capacidad** (INTEGER): Capacidad máxima de personas.

#### 2.4 HorarioDisponibilidad
Define los horarios en los que cada cancha está disponible.
- **id_horario** (INTEGER): Identificador único.
- **id_cancha** (INTEGER): Referencia a la tabla Cancha.
- **dia_semana** (ENUM): Día de la semana.
- **hora_apertura** (TIME): Hora de apertura.
- **hora_cierre** (TIME): Hora de cierre.

#### 2.5 Reserva
Registra las reservas realizadas por los usuarios.
- **id_reserva** (INTEGER): Identificador de la reserva.
- **id_usuario** (INTEGER): Referencia a la tabla Usuario.
- **id_cancha** (INTEGER): Referencia a la tabla Cancha.
- **fecha_reserva** (DATE): Fecha de la reserva.
- **hora_inicio** (TIME): Hora de inicio.
- **hora_fin** (TIME): Hora de finalización.
- **estado** (ENUM): Estado de la reserva (Ejemplo: Confirmada, Cancelada).
- **costo_total** (DECIMAL(10,2)): Costo total de la reserva.

#### 2.6 Pago
Administra los pagos de las reservas realizadas.
- **id_pago** (INTEGER): Identificador único del pago.
- **id_reserva** (INTEGER): Referencia a la tabla Reserva.
- **metodo_pago** (ENUM): Método de pago (Ejemplo: Tarjeta, Efectivo, Transferencia).
- **monto** (DECIMAL(10,2)): Monto total pagado.
- **fecha_pago** (TIMESTAMP): Fecha y hora del pago.
- **estado_pago** (ENUM): Estado del pago (Ejemplo: Pagado, Pendiente, Rechazado).
- **comprobante_pago** (TEXT): Referencia al comprobante de pago.

#### 2.7 Factura
Registra las facturas emitidas por los pagos realizados.
- **id_factura** (INTEGER): Identificador de la factura.
- **id_pago** (INTEGER): Referencia a la tabla Pago.
- **numero_factura** (VARCHAR(50)): Número único de factura.
- **fecha_emision** (TIMESTAMP): Fecha de emisión de la factura.

#### 2.8 Resena
Permite a los usuarios dejar reseñas sobre las canchas utilizadas.
- **id_resena** (INTEGER): Identificador de la reseña.
- **id_usuario** (INTEGER): Referencia a la tabla Usuario.
- **id_cancha** (INTEGER): Referencia a la tabla Cancha.
- **calificacion** (INTEGER): Puntuación de 1 a 5.
- **comentario** (TEXT): Comentario adicional.
- **fecha_resena** (TIMESTAMP): Fecha y hora de la reseña.

### 3. Relaciones entre Tablas
- Un **Usuario** puede hacer múltiples **Reservas**.
- Un **Usuario** puede dejar múltiples **Reseñas** sobre las **Canchas**.
- Una **Cancha** puede tener múltiples **Horarios de Disponibilidad**.
- Una **Reserva** genera un **Pago**, y este a su vez puede generar una **Factura**.
- Un **Administrador** está vinculado a un **Usuario**.

### 4. Consideraciones
- Se recomienda usar claves foráneas para mantener la integridad referencial.
- Se puede agregar un sistema de auditoría para rastrear cambios en reservas y pagos.
- Se podría incluir una funcionalidad para que los usuarios puedan modificar sus reservas bajo ciertas condiciones.

### 5. Conclusión
Este diseño de base de datos proporciona una solución estructurada y eficiente para la gestión de canchas sintéticas, permitiendo un control detallado de usuarios, reservas, pagos y reseñas.


# Documentación del Diagrama de Caso de Uso

### 1. Descripción General
El diagrama representa los casos de uso del Sistema de Gestión de Canchas, identificando los actores involucrados y las funcionalidades disponibles para cada uno. Los actores principales son el **Usuario** y el **Administrador**, quienes interactúan con los diferentes módulos del sistema. Además, el sistema se conecta a una **Base de Datos** para almacenar la información relevante.

### 2. Actores
- **Usuario**: Persona que utiliza el sistema para gestionar sus reservas de canchas.
- **Administrador**: Responsable de la administración del sistema, incluyendo la gestión de canchas, disponibilidad y generación de reportes.
- **Base de Datos**: Entidad que almacena la información del sistema.

### 3. Casos de Uso

#### **Autenticación**
- **Registrarse**: Permite al usuario crear una cuenta en el sistema.
- **Iniciar Sesión**: Permite al usuario autenticarse en el sistema. *(Incluye "Registrarse" porque un usuario debe estar registrado para iniciar sesión).*

#### **Reservas**
- **Ver Historial de Reservas**: Permite a los usuarios consultar sus reservas anteriores.
- **Realizar Reserva**: Permite a los usuarios reservar una cancha. *(Extiende "Cancelar Reservas" y "Editar Reservas", ya que después de reservar pueden surgir cambios o cancelaciones).*
- **Cancelar Reservas**: Permite a los usuarios cancelar una reserva existente. *(Extiende "Realizar Reserva").*
- **Editar Reservas**: Permite modificar los datos de una reserva realizada. *(Extiende "Realizar Reserva").*

#### **Opiniones**
- **Escribir Reseña**: Permite a los usuarios compartir su opinión sobre las canchas o el servicio recibido.

#### **Administración**
- **Administrar Canchas**: Permite al administrador gestionar las canchas disponibles en el sistema.
- **Configurar Disponibilidad**: Permite modificar la disponibilidad de las canchas.
- **Generar Reportes**: Permite al administrador obtener información estadística del uso de las canchas.

#### **Pagos y Facturación**
- **Realizar Pago**: Permite a los usuarios efectuar pagos por sus reservas. *(Incluye "Generar Factura", ya que cada pago genera una factura).*
- **Generar Factura**: Emite una factura tras la realización de un pago.

### 4. Relaciones Entre Casos de Uso
- **"Include" (<<include>>)**: Indica que un caso de uso siempre incluye otro. Ejemplo: *"Iniciar Sesión" incluye "Registrarse".*
- **"Extend" (<<extend>>)**: Indica que un caso de uso puede ampliar opcionalmente otro. Ejemplo: *"Cancelar Reservas" extiende "Realizar Reserva".*

### 5. Conexión con la Base de Datos
El sistema interactúa con una base de datos para almacenar información sobre usuarios, reservas, pagos y reportes administrativos.

# Documentación del Diagrama de Clases

### 1. Descripción General
Este diagrama de clases representa la estructura del **Sistema de Gestión de Canchas**, detallando las entidades principales, sus atributos y las relaciones entre ellas. El sistema maneja usuarios, administradores, canchas, reservas, pagos y reseñas.


### 2. Clases y Atributos

#### **Usuario**
Representa a los usuarios que utilizan el sistema.
- `id_usuario: int` → Identificador único del usuario.
- `nombre: String` → Nombre del usuario.
- `correo: String` → Dirección de correo electrónico.
- `telefono: String` → Número de teléfono.
- `contrasena: String` → Contraseña de acceso.
- `rol: String` → Determina si es usuario o administrador.
- `fecha_registro: Timestamp` → Fecha y hora del registro.

**Relaciones:**
- Un usuario puede ser un administrador.
- Un usuario realiza reservas.
- Un usuario escribe reseñas.

#### **Administrador**
Representa a los administradores que gestionan el sistema.
- `id_admin: int` → Identificador único del administrador.
- `cargo: String` → Cargo del administrador dentro del sistema.

**Relaciones:**
- Un administrador administra varias canchas.


#### **Cancha**
Representa las canchas deportivas disponibles en el sistema.
- `id_cancha: int` → Identificador único de la cancha.
- `nombre: String` → Nombre de la cancha.
- `ubicacion: String` → Dirección o ubicación de la cancha.
- `tipo: String` → Tipo de cancha (fútbol, tenis, etc.).
- `estado: String` → Estado actual (disponible, en mantenimiento, etc.).
- `precio_por_hora: double` → Tarifa de alquiler por hora.
- `descripcion: String` → Descripción de la cancha.
- `capacidad: int` → Capacidad máxima de personas.

**Relaciones:**
- Una cancha tiene un horario de disponibilidad.
- Una cancha es reservada por múltiples usuarios.


#### **HorarioDisponibilidad**
Define el horario de apertura y cierre de cada cancha.
- `id_horario: int` → Identificador único del horario.
- `dia_semana: String` → Día de la semana correspondiente.
- `hora_apertura: Time` → Hora de apertura.
- `hora_cierre: Time` → Hora de cierre.

**Relaciones:**
- Un horario pertenece a una cancha.


#### **Reserva**
Registra las reservas realizadas por los usuarios.
- `id_reserva: int` → Identificador único de la reserva.
- `fecha_reserva: Date` → Fecha en la que se realizó la reserva.
- `hora_inicio: Time` → Hora de inicio de la reserva.
- `hora_fin: Time` → Hora de finalización de la reserva.
- `estado: String` → Estado de la reserva (pendiente, confirmada, cancelada, etc.).
- `costo_total: double` → Costo total de la reserva.

**Relaciones:**
- Una reserva es realizada por un usuario.
- Una reserva se genera para una cancha.
- Una reserva genera un pago.


#### **Pago**
Gestiona los pagos de las reservas realizadas.
- `id_pago: int` → Identificador único del pago.
- `metodo_pago: String` → Método de pago utilizado.
- `monto: double` → Monto total pagado.
- `fecha_pago: Timestamp` → Fecha y hora del pago.
- `estado_pago: String` → Estado del pago (completado, pendiente, rechazado, etc.).
- `comprobante_pago: String` → Código o imagen del comprobante de pago.

**Relaciones:**
- Un pago está asociado a una reserva.
- Un pago emite una factura.


#### **Factura**
Registra la emisión de facturas por los pagos realizados.
- `id_factura: int` → Identificador único de la factura.
- `numero_factura: String` → Número de factura asignado.
- `fecha_emision: Timestamp` → Fecha y hora de emisión de la factura.

**Relaciones:**
- Una factura es emitida tras un pago.


#### **Reseña**
Permite a los usuarios calificar y dejar comentarios sobre las canchas.
- `id_resena: int` → Identificador único de la reseña.
- `calificacion: int` → Puntuación dada a la cancha.
- `comentario: String` → Opinión del usuario.
- `fecha_resena: Timestamp` → Fecha y hora de la reseña.

**Relaciones:**
- Una reseña es escrita por un usuario.
- Una reseña se asigna a una cancha reservada.


### 3. Relaciones Clave
- Un usuario puede ser un administrador.
- Un usuario puede realizar múltiples reservas.
- Cada reserva se asocia a una sola cancha.
- Cada reserva genera un pago.
- Cada pago emite una factura.
- Los usuarios pueden escribir reseñas sobre sus reservas.
- Cada cancha tiene un horario de disponibilidad.

# Documentación del Diagrama de Secuencia

### Sistema de Gestión de Canchas

### 1. Descripción General
Este diagrama de secuencia describe el flujo de interacción entre el usuario, la aplicación móvil (App Móvil), el sistema de gestión, y los módulos de canchas, reservas y pagos. Representa el proceso de autenticación, reserva de una cancha y pago correspondiente.

### 2. Actores Involucrados
- **Usuario**: Persona que realiza la reserva de la cancha.
- **App Móvil**: Plataforma a través de la cual el usuario interactúa con el sistema.
- **Sistema de Gestión**: Responsable de procesar las solicitudes del usuario.
- **Cancha**: Módulo encargado de verificar la disponibilidad de las canchas.
- **Reserva**: Módulo encargado de registrar las reservas.
- **Pago**: Módulo encargado de gestionar los pagos.
- **POS**: Punto de venta físico donde se puede registrar un pago (si aplica).

### 3. Descripción de Secuencia de Eventos

#### Paso 1: Inicio de Sesión
1. El usuario inicia sesión en la App Móvil.
2. La App Móvil envía las credenciales al Sistema de Gestión.
3. El Sistema de Gestión verifica las credenciales y devuelve el acceso concedido.

#### Paso 2: Selección de Cancha y Horario
4. El usuario selecciona la cancha y el horario en la App Móvil.
5. La App Móvil consulta la disponibilidad en el Sistema de Gestión.
6. El Sistema de Gestión envía la consulta al módulo de Cancha.
7. El módulo de Cancha retorna la disponibilidad.

#### Paso 3: Confirmación de Reserva
8. El usuario confirma la reserva en la App Móvil.
9. La App Móvil envía la confirmación al Sistema de Gestión.
10. El Sistema de Gestión registra la reserva en el módulo de Reserva.
11. El módulo de Reserva guarda la reserva y envía la confirmación.

#### Paso 4: Realización del Pago
12. El usuario realiza el pago en la App Móvil.
13. La App Móvil envía los detalles del pago al Sistema de Gestión.
14. El Sistema de Gestión procesa el pago a través del módulo de Pago.
15. El módulo de Pago confirma el pago exitoso.
16. Se actualiza el estado de la reserva a "Confirmada".
17. Si aplica, se registra un pago físico en el POS.
18. El POS confirma el pago.

#### Paso 5: Confirmación de Reserva Exitosa
19. El usuario recibe la confirmación del pago y la reserva en la App Móvil.
20. Se envía una notificación de reserva exitosa al usuario.

### 4. Resumen del Proceso
El sistema de gestión de canchas permite a los usuarios autenticarse, seleccionar una cancha disponible, confirmar la reserva y realizar el pago de manera eficiente. La integración entre los módulos garantiza una correcta sincronización de la información y confirma al usuario cuando la reserva y el pago han sido procesados correctamente.

Este flujo asegura la correcta gestión de disponibilidad, pagos y confirmaciones en la plataforma, optimizando la experiencia del usuario.