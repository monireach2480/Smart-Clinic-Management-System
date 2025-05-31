## Flow of Data and Control

1. **User Interface Layer:** Users access the application either through Thymeleaf-based dashboards (Admin/Doctor) or via REST API clients like mobile apps or browser modules (e.g., Appointment or PatientDashboard).

2. **Controller Layer:** The request is routed to a specific controller. Thymeleaf controllers return rendered HTML templates, while REST controllers process the request and return JSON responses.

3. **Service Layer:** Controllers pass the request to the Service Layer, where business logic is applied—such as checking doctor availability or validating input data.

4. **Repository Layer:** The service calls the appropriate repository to perform database operations. MySQL repositories use Spring Data JPA, while MongoDB uses Spring Data MongoDB.

5. **Database Access:** Repositories interact directly with the databases. MySQL stores relational data with constraints, and MongoDB stores flexible prescription documents.

6. **Model Binding:** Retrieved data is bound to model classes. JPA entities represent MySQL records, and MongoDB documents are mapped to @Document-annotated classes.

7. **Response Handling:** The data models are returned to the controller. In MVC flows, they’re passed to Thymeleaf for HTML rendering. In REST flows, they’re converted to JSON and sent as a response to the client.
