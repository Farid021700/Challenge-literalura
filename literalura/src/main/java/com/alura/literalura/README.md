# 📚 Literalura

Literalura es una aplicación de consola desarrollada con **Java + Spring Boot** que permite buscar libros desde una API pública, almacenarlos en una base de datos PostgreSQL y consultarlos mediante un menú interactivo.

Este proyecto fue desarrollado como práctica de **Spring Boot, JPA/Hibernate y consumo de APIs externas**, aplicando buenas prácticas de arquitectura y persistencia de datos.

---

# 🚀 Funcionalidades

La aplicación permite:

✔ Buscar libro por título (desde API externa)  
✔ Guardar libro y autor en base de datos  
✔ Listar libros registrados  
✔ Listar autores registrados  
✔ Listar autores vivos en un determinado año  
✔ Listar libros por idioma  
✔ Mostrar cantidad de libros por idioma  
✔ Persistencia automática con Hibernate

---

# 🛠️ Tecnologías utilizadas

- ☕ Java 17+
- 🌱 Spring Boot
- 📦 Spring Data JPA
- 🔄 Hibernate
- 🐘 PostgreSQL
- 📡 Consumo de API REST
- 🧰 Maven

---


---

# 🗄️ Modelo de Datos

## 📘 Libro

- id (Long)
- titulo (String)
- idioma (String)
- numeroDescargas (Integer)
- autor (ManyToOne)

## 👤 Autor

- id (Long)
- nombre (String)
- nacimiento (Integer)
- fallecimiento (Integer)
- libros (OneToMany)

### Relación

Un **Autor** puede tener muchos **Libros**  
Un **Libro** pertenece a un **Autor**

---

# ⚙️ Configuración de Base de Datos

## 1️⃣ Crear la base de datos

En PostgreSQL ejecutar:

```sql
CREATE DATABASE literalura;

========= LITERALURA =========

1 - Buscar libro por título
2 - Listar libros registrados
3 - Listar autores registrados
4 - Listar autores vivos en determinado año
5 - Listar libros por idioma
6 - Cantidad de libros por idioma

0 - Salir
