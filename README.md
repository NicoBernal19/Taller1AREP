# Servidor Web hecho en Java

Servidor web que soporta múlltiples solicitudes seguidas no concurrentes. El servidor lee los archivos del disco local y retorna todos los archivos solicitados, incluyendo páginas html, archivos java script, css e imágenes.
Incluye en la aplicación la comunicación asíncrona con unos servicios REST, NO se usan frameworks web como Spark o Spring, solo se uso Java y las librerías para manejo de la red.

## Instrucciones

Sigue estas instrucciones para poder configurar y ejecutar el proyecto en tu máquina local.

### Prerrequisitos a tener en cuenta

Para la instalacion y ejecucion del proyecto se requiere de lo siguiente:

```
- Java 8 o superior
- Apache Maven
- Una terminal
- Git
```

### Instalación

Clona este repositorio y navega al directorio donde clonaste el proyecto:

```
git clone https://github.com/NicoBernal19/Taller1AREP.git
```

Compila y construye el proyecto con Maven:

```
mvn clean install
```

Ejecuta el servidor:

```
mvn exec:java -Dexec.mainClass="co.edu.eci.arep.HttpServer"
```

El servidor esta hecho para que inicie en el puerto `35000`, una vez ejecutado, ya esta listo para que puedas usarlo y probarlo.

## Pruebas

### Pruebas Unitarias

Este proyecto incluye pruebas unitarias con JUnit 5. Para ejecutarlas, usa:

```
mvn test
```

### Pruebas extremo a extremo

Puedes probar el servidor abriendo un navegador y accediendo a:

```
http://localhost:35000
```
### Pruebas archivos estáticos

Prueba a acceder a los siguientes archivos:

```
http://localhost:35000/index.html
http://localhost:35000/script.js
http://localhost:35000/styles.css
http://localhost:35000/images/logo.png
```

## Diseño del sistema

El código está estructurado en diferentes archivos para no tener una sola clase llena de codigo, ademas de que es una buena practica.

Entre las funcionalidades que se ofrecen encontramos las siguientes:

- **Manejo de solicitudes HTTP (Servicios REST)**: Soporta los métodos `GET` y `POST`.
- **Manejo de archivos estáticos**: Permite servir HTML, CSS, JS e imágenes.

## Construido con

* Java - Lenguaje principal utilizado
* Maven - Para la gestión de dependencias y automatización
* Biblioteca estándar de Java - Para manejo de red y archivos
* JUnit - Para pruebas automatizadas

## Autor

* **Nicolas Bernal** - [GitHub Personal](https://github.com/NicoBernal19)

## Agradecimientos

* Inspiración de implementaciones de servidores web minimalistas
* Comunidad de código abierto por las mejores prácticas
