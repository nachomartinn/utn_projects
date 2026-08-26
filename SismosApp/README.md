# 🌋 SismosApp

**SismosApp** es una aplicación de escritorio desarrollada en **JavaFX** para la **gestión y revisión de eventos sísmicos**.  
Combina una interfaz simple con persistencia en base de datos mediante Hibernate ORM, y un flujo de estados que modela el ciclo de vida de los eventos sísmicos.

---

## ⚙️ Requisitos

- **JDK 21**
- **Maven 3.9+**

---

## ▶️ Ejecución

1. Abrir una terminal en la raíz del proyecto (donde está `pom.xml`).
2. Ejecutar el siguiente comando:

```bash
mvn javafx:run
```

---

## 🚀 Características principales

- Interfaz gráfica en **JavaFX (FXML + CSS)**.
- Persistencia de datos con **Hibernate ORM 6.4** y **JPA (Jakarta Persistence 3.1)**.
- **Base de datos H2 embebida** para desarrollo y pruebas (se puebla automáticamente mediante `DataSeeder` si está vacía).
- **Arquitectura en capas**: `controllers`, `service`, `repository`, `models`, `dto`, `utilities`.
- Implementación del **Patrón State** para representar los distintos estados de un evento sísmico.
- Flujo de **revisión manual de eventos** con detección automática, validación por expertos y confirmación o rechazo final.
- Soporte para **muestras sísmicas** por estación sismológica y análisis detallado de sismogramas desde la interfaz.

---

## 🧱 Tecnologías

| Tecnología                  | Versión / Uso                                |
|-----------------------------|----------------------------------------------|
| **Java**                    | 21                                           |
| **JavaFX**                  | 21.0.3 (`controls`, `fxml`, `graphics`)      |
| **Maven**                   | Gestión de dependencias y build              |
| **Hibernate ORM**           | 6.4.4.Final                                  |
| **H2 Database**             | 2.3.232 (Base de datos embebida)             |
| **Jakarta Persistence API** | 3.1.0 (Mapeo JPA para entidades)             |
| **Lombok**                  | 1.18.30 (Reducción de boilerplate)           |

---

## 🧩 Patrón de diseño

El proyecto aplica el **Patrón State** para gestionar el ciclo de vida y las transiciones de estado de un evento sísmico.

- **Clase Base Abstracta:** `EstadoEventoSismico`
- **Estados Concretos Implementados:**
  - `EstadoAutodetectado`: Estado inicial tras la detección del evento.
  - `EstadoEnRevisionExperto`: Evento bajo evaluación por parte de un analista.
  - `EstadoConfirmado`: Evento validado y confirmado.
  - `EstadoRechazado`: Evento descartado o invalidadas sus lecturas.
  - `EstadoBloqueado`: Evento bloqueado temporalmente durante su procesamiento.

Cada estado define las transiciones válidas e invalida acciones no permitidas en su fase actual mediante excepciones de tipo `IllegalStateException`.

---

## 🏗️ Arquitectura del proyecto

```text
src/
└── main/
    ├── java/
    │   ├── application/        # Punto de entrada JavaFX (App.java)
    │   ├── controllers/        # Controladores de vista y gestor de flujo (GestorRevisionManual, PantallaRevisionManual)
    │   ├── dto/                # Data Transfer Objects (EventoSismicoDTO, EstacionSismologicaDTO, DatosSismicosDTO)
    │   ├── models/             # Entidades JPA y modelo de dominio
    │   │   └── estados/        # Implementación del Patrón State (EstadoEventoSismico y derivados)
    │   ├── repository/         # Acceso a datos con JPA/Hibernate (EventoSismicoRepository, etc.)
    │   │   └── db/             # Contexto de base de datos y EntityManager (DbContext.java)
    │   ├── seeder/             # Inicialización y poblado de datos (DataSeeder.java)
    │   ├── service/            # Lógica de negocio (EventoService, SesionService)
    │   └── utilities/          # Constantes y utilidades de rutas (Paths.java)
    └── resources/
        ├── META-INF/           # Configuración de JPA (persistence.xml)
        ├── PantallaRevisionManual.fxml  # Vista principal FXML
        └── styles.css          # Estilos CSS de la interfaz
```

---

## 🗂️ Archivos clave

| Propósito                     | Archivo                                          |
|-------------------------------|--------------------------------------------------|
| **Punto de entrada**          | `src/main/java/application/App.java`             |
| **Gestor de flujo**           | `src/main/java/controllers/GestorRevisionManual.java` |
| **Controlador de vista**      | `src/main/java/controllers/PantallaRevisionManual.java` |
| **Servicio de negocio**       | `src/main/java/service/EventoService.java`       |
| **UI principal (FXML)**       | `src/main/resources/PantallaRevisionManual.fxml` |
| **Estilos**                   | `src/main/resources/styles.css`                  |
| **Configuración JPA**         | `src/main/resources/META-INF/persistence.xml`    |
| **Contexto DB (Hibernate)**   | `src/main/java/repository/db/DbContext.java`     |
| **Seeder de datos iniciales** | `src/main/java/seeder/DataSeeder.java`           |

---
