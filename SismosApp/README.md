SismosApp
Aplicación de escritorio JavaFX para gestionar y revisar eventos sísmicos. Incluye UI en FXML, persistencia con Hibernate y una base de datos H2 para desarrollo. Implementa flujo de estados para los eventos, con servicios y repositorios que separan la lógica de negocio y acceso a datos.
Tecnologías
Java 21
JavaFX 21 (controls, fxml, graphics)
Maven
Hibernate ORM 6
H2 Database
Jakarta Persistence API
Lombok
Patrón de diseño
Patrón State aplicado a eventos sísmicos en src/main/java/models/estados con estados como EstadoAutodetectado, EstadoEnRevisionExperto, EstadoConfirmado, EstadoRechazado y EstadoBloqueado.
Arquitectura por capas: controllers, service, repository, models y dto.
Estructura y archivos clave
Punto de entrada: src/main/java/application/App.java
UI: src/main/resources/PantallaRevisionManual.fxml y src/main/resources/styles.css
Persistencia JPA: src/main/resources/META-INF/persistence.xml
Contexto DB: src/main/java/repository/db/DbContext.java
Seeder: src/main/java/seeder/DataSeeder.java
Requisitos
JDK 21
Maven 3.9+
Ejecución
Ubicarse en la raíz del proyecto donde está pom.xml.
Ejecutar:
mvn javafx:run