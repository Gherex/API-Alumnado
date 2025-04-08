# 🎓 Alumnado - Sistema de Gestión de Alumnos

¡Bienvenido a **Alumnado**, una aplicación fullstack para la gestión de alumnos, materias, profesores e inscripciones!

## 📖 Descripción
Este proyecto fue desarrollado con el stack **Java + Spring Boot** para el backend y **React** para el frontend. Permite realizar operaciones CRUD sobre diferentes entidades relacionadas al entorno educativo.

El objetivo de este proyecto es seguir afianzando conocimientos tanto en frontend como en backend, así como poner en práctica conceptos como autenticación con JWT, control de roles, rutas protegidas y consumo de APIs REST.

## 🌐 Sitio en producción
- 🖥️ Frontend: [alumnado-de-gherex.netlify.app](https://alumnado-de-gherex.netlify.app)
- 🔗 Backend: [app-alumnado-latest.onrender.com](https://app-alumnado-latest.onrender.com/alumnado/api/v1/)

## 🧩 Características principales
- Gestión de alumnos, profesores, materias e inscripciones
- Operaciones CRUD completas
- Control de acceso mediante JWT
- Roles: admin (control total) e invitado (solo lectura)
- Autenticación con login y acceso como invitado
- Frontend responsivo con React + React Router
- Backend robusto con Spring Boot y conexión a base de datos MySQL

## 🛠️ Tecnologías utilizadas

### 🧠 Backend
- Java 21
- Spring Boot
- Spring Security + JWT
- JPA + Hibernate
- MySQL
- Maven

### 💻 Frontend
- React
- React Router DOM
- Sass
- Fetch API

## 🗃️ Modelo de Base de Datos

El sistema cuenta con las siguientes entidades:
- `persona`
- `alumno`
- `profesor`
- `materia`
- `inscripcion`

Cada una con sus respectivas relaciones. Se usa `snake_case` en todas las tablas y columnas.

![Diagrama ER](images/diagrama-ER.png)

## 📷 Capturas de pantalla

### Login
![Login](images/login.png)

### Vista principal (invitado)
![Vista principal](images/visualizacion-tablas.png)

### Panel de administrador
![Alta alumno](images/admin-panel.png)

## 🚀 Cómo ejecutar el proyecto

### 🔙 Backend
1. Clonar el repositorio:
   ```sh
   git clone https://github.com/Gherex/API-Alumnado.git
   ```
2. Importar el proyecto en tu IDE favorito (IntelliJ / Eclipse / NetBeans).
3. Configurar las credenciales de la base de datos en src/main/resources/application.properties.
4. Crear la base de datos alumnado en MySQL y asegurarse de que esté corriendo.

### 🔜 Frontend
1. Clonar el repositorio:
   ```sh
   git clone https://github.com/Gherex/frontend-Alumnado.git
   ```
2. Navegar al directorio del proyecto: cd frontend-Alumnado 
3. Instalar las dependencias: npm install  
4. Ejecutar en modo desarrollo: npm run dev 

⚠️ Asegurate de que la URL del backend esté bien configurada en los archivos de entorno o dentro del código para que el frontend pueda comunicarse con la API correctamente.

🔐 Acceso
- Invitado: puede realizar operaciones GET.
- Administrador: puede realizar POST, PUT, DELETE y GET.
- Para acceder como invitado, simplemente hacé clic en el botón "Acceder como invitado".
- Para acceder como admin, ingresá usuario y contraseña válidos. La verificación se hace en el backend comparando el hash.

## ✍️ Autor
**Germán Lagger**  
- [GitHub](https://github.com/Gherex)
- [LinkedIn](https://www.linkedin.com/in/germanlagger/)
