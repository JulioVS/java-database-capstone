# Architecture Design

## Section 1: Architecture Summary

The Smart Clinic Management System features a three tiered web architecture consisting
of Presentation, Application and Data Tiers. It uses both Thymeleaf dynamic web pages and
REST APIs for access, which go through a common service layer for business logic which then accesses both structured and unstructured data on MySQL and MongoDB repositories respectively.

## Section 2: Numbered Flow of Data and Control

1. The User Interface Layer provides access to the application via dashboard web pages and/or a REST API requests.
2. The Controller Layer uses the Thymeleaf controller to serve dynamic HTML responses and the REST controller to serve lightweight JSON data objects, respectively.
3. In both cases, the Service Layer gets called in order to provide business logic and necessary validations.
4. The Repository Layer is then used to fetch data about patients, doctors, appointments and so on.
5. The Database Access Layer then directly interacts with both structured and unstructured databases like MySQL and MongoDB to get or process the required information.
6. Model Binding is then automatically applied by Spring JPA and/or Spring MongoDB to map retrieved data to Java entities for use by the other layers.
7. Finally, Application Models are then passed as embedded entities on Thymeleaf templates or serialized JSON objects in HTTP responses.
