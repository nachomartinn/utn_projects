# 🌋 SismosApp

**SismosApp** es una aplicación de escritorio desarrollada en **JavaFX** para la **gestión y revisión de eventos sísmicos**.  
Combina una interfaz simple con persistencia en base de datos mediante **Hibernate ORM**, y un flujo de estados que modela el ciclo de vida de los eventos sísmicos.

---

## ⚙️ Requisitos

- **JDK 21**
- **Maven 3.9+**

---

## ▶️ Ejecución

1. Abrir una terminal en la raíz del proyecto (donde está `pom.xml`).
2. Ejecutar el siguiente comando:

```terminal
mvn javafx:run

## 🚀 Características principales

- Interfaz gráfica en **JavaFX (FXML + CSS)**.
- Persistencia de datos con **Hibernate ORM 6** y **JPA (Jakarta Persistence)**.
- **Base de datos H2 embebida** para desarrollo y pruebas.
- **Arquitectura en capas**: `controllers`, `service`, `repository`, `models`, `dto`.
- Implementación del **Patrón State** para representar los distintos estados de un evento sísmico.
- Flujo de **revisión manual de eventos** con detección automática, validación por expertos y confirmación final.
- Soporte para **muestras sísmicas** por estación sismológica y análisis detallado desde la interfaz.

---

## 🧱 Tecnologías

| Tecnología | Versión / Uso |
|-------------|----------------|
| **Java** | 21 |
| **JavaFX** | 21 (`controls`, `fxml`, `graphics`) |
| **Maven** | Gestión de dependencias y build |
| **Hibernate ORM** | 6.x |
| **H2 Database** | Base de datos embebida (modo desarrollo) |
| **Jakarta Persistence API** | Mapeo JPA para entidades |
| **Lombok** | Reducción de boilerplate en entidades y DTOs |

---

## 🧩 Patrón de diseño

El proyecto aplica el **Patrón State** para los eventos sísmicos, definidos en:

Estados implementados:
- `EstadoAutodetectado`
- `EstadoEnRevisionExperto`
- `EstadoConfirmado`
- `EstadoRechazado`
- `EstadoBloqueado`

Cada estado define su propio comportamiento en respuesta a acciones del sistema o del usuario (por ejemplo, bloqueo, revisión o confirmación del evento).

---

## 🏗️ Arquitectura del proyecto

Estructura basada en capas para mejorar la mantenibilidad y escalabilidad:
📦 src/main/java
├─ application/ → Punto de entrada (App.java)
├─ controllers/ → Controladores JavaFX
├─ service/ → Lógica de negocio
├─ repository/ → Acceso a datos (Hibernate / JPA)
│ └─ db/ → Contexto de base de datos (DbContext.java)
├─ models/ → Entidades JPA y clases de dominio
├─ dto/ → Data Transfer Objects
└─ seeder/ → Inicialización de datos (DataSeeder.java)

---

## 🗂️ Archivos clave

| Propósito | Archivo |
|------------|----------|
| **Punto de entrada** | `src/main/java/application/App.java` |
| **UI principal** | `src/main/resources/PantallaRevisionManual.fxml` |
| **Estilos** | `src/main/resources/styles.css` |
| **Configuración JPA** | `src/main/resources/META-INF/persistence.xml` |
| **Contexto DB (Hibernate)** | `src/main/java/repository/db/DbContext.java` |
| **Seeder de datos iniciales** | `src/main/java/seeder/DataSeeder.java` |

---