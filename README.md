# Servidor Web hecho en Java

Servidor web que soporta múlltiples solicitudes seguidas no concurrentes. El servidor lee los archivos del disco local y retorna todos los archivos solicitados, incluyendo páginas html, archivos java script, css e imágenes.
Incluye en la aplicación la comunicación asíncrona con unos servicios REST, NO se usan frameworks web como Spark o Spring, solo se uso Java y las librerías para manejo de la red.

## Autor

* **Nicolas Bernal** - [GitHub Personal](https://github.com/NicoBernal19)

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

## Diseño del sistema

El código está estructurado en diferentes archivos para no tener una sola clase llena de codigo, ademas de que es una buena practica y permite que todo este mas ordenado y compacto, hace que sea mas facil extender a posterior el codigo.

Entre las funcionalidades que se ofrecen encontramos las siguientes:

- Soporta los métodos `GET` y `POST`.
- Permite servir HTML, CSS, JS e imágenes.

## Pruebas

### Pruebas Unitarias

Este proyecto incluye pruebas unitarias con JUnit. Ejecutalas con el siguiente comando:

```
mvn test
```

### Pruebas en tiempo real

Puedes probar el servidor abriendo un navegador y accediendo a:

```
http://localhost:35000
```

Una vez abierto el servidor web puedes navegar entre la pagina de inicio (index.html) o la pagina en donde podras probar los metodos `GET` y `POST` (testREST.html).

### Pruebas archivos estáticos

Prueba a acceder a los siguientes archivos:

```
http://localhost:35000/index.html
http://localhost:35000/script.js
http://localhost:35000/styles.css
http://localhost:35000/images/logo.png
```

## Construido con

* Java - Lenguaje principal utilizado
* Maven - Para la gestión de dependencias y automatización
* Biblioteca estándar de Java - Para manejo de red y archivos
* JUnit - Para pruebas automatizadas

## Agradecimientos

* Inspiración de implementaciones de servidores web minimalistas
* Comunidad de código abierto por las mejores prácticas
