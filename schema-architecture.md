# Section 1: Architecture summary

This is a Full Stack Java Application, featuring a 3-tiered architecture comprising Presentation, Application and Data layers.

The Spring Boot application features both Thymeleaf and REST controllers while the
backend features both SQL (MySQL) and No SQL (MongoDB) databases for structured
and non-structured data. 

# Section 2: Numbered flow of data and control

The application features the following flow sequence:

1. Admin and Doctor dashboards are served through dynamic HTML web pages, or, third party clients like mobile of web client access the app through exposed REST endpoints.

2. Either Thymeleaf or REST controllers are called upon, respectively.

3. The invoked controller calls the appropriate service layer to provide the needed business logic.

4. The service layer call the appropriate repository layer (SQL or No SQL) depending on the type of interaction and data being accessed or created. 

5. The repository layer directly interacts with the database (either MySQL for structured data or MongoDB for non-structured data) for storage needs.

6. The target data is mapped in and out of the database and into model classes with String Data using either JPA Entity binding (for structured data) or Mongo DB document binding (for non-structured data).

7. The resulting model data is flown back together Thymeleaf to be rendered on HTML templates for dashboard users, or, serialized JSONs for third party REST clients.
