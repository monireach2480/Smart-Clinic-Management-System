## Architecture Summary

This Spring Boot application follows a three-tier architecture consisting of the Presentation, Application, and Data tiers. The Presentation Tier includes Thymeleaf-based dashboards for admin and doctor users and REST APIs for modules like appointments, patient records, and dashboards.

The Application Tier is powered by Spring Boot and handles the core logic. Controllers—both MVC and REST—handle incoming requests and forward them to the Service Layer. This Service Layer applies business rules and workflows and then communicates with the Repository Layer for data access.

The Data Tier involves two databases: MySQL for structured data (like Patient, Doctor, Appointment, and Admin) and MongoDB for flexible, document-based data (like Prescriptions). MySQL uses JPA entities for ORM, while MongoDB uses document models with Spring Data MongoDB. This setup ensures the system is scalable, maintainable, and ready for CI/CD and cloud-native deployments.
