package com.project.back_end.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.back_end.DTO.Login;
import com.project.back_end.models.Doctor;
import com.project.back_end.services.DoctorService;
import com.project.back_end.services.Service;

@RestController
@RequestMapping("${api.path}doctor")
public class DoctorController {

    // 1. Set Up the Controller Class:
    // - Annotate the class with `@RestController` to define it as a REST controller
    // that serves JSON responses.
    // - Use `@RequestMapping("${api.path}doctor")` to prefix all endpoints with a
    // configurable API path followed by "doctor".
    // - This class manages doctor-related functionalities such as registration,
    // login, updates, and availability.

    // 2. Autowire Dependencies:
    // - Inject `DoctorService` for handling the core logic related to doctors
    // (e.g., CRUD operations, authentication).
    // - Inject the shared `Service` class for general-purpose features like token
    // validation and filtering.
    @Autowired
    private DoctorService doctorService;

    @Autowired
    private Service service;

    // 3. Define the `getDoctorAvailability` Method:
    // - Handles HTTP GET requests to check a specific doctor’s availability on a
    // given date.
    // - Requires `user` type, `doctorId`, `date`, and `token` as path variables.
    // - First validates the token against the user type.
    // - If the token is invalid, returns an error response; otherwise, returns the
    // availability status for the doctor.
    @GetMapping("/availability/{user}/{doctorId}/{date}/{token}")
    public Map<String, Object> getDoctorAvailability(@PathVariable String user, @PathVariable @NonNull Long doctorId,
            @PathVariable String date, @PathVariable String token) {

        ResponseEntity<Map<String, String>> response = service.validateToken(token, user);

        if (!response.getStatusCode().is2xxSuccessful()) {
            return Map.of("error", "Invalid token: " + response.getStatusCode().toString());
        }

        LocalDate appointmentDate;

        try {
            appointmentDate = LocalDate.parse(date);
        } catch (DateTimeParseException e) {
            return Map.of("error", "Invalid date format. Use YYYY-MM-DD.");
        }

        if (appointmentDate.isBefore(LocalDate.now())) {
            return Map.of("error", "Appointment date cannot be in the past.");
        }

        return Map.of("availability", doctorService.getDoctorAvailability(doctorId, appointmentDate));

    }

    // 4. Define the `getDoctors` Method:
    // - Handles HTTP GET requests to retrieve a list of all doctors.
    // - Returns the list within a response map under the key `"doctors"` with HTTP
    // 200 OK status.
    @GetMapping
    public Map<String, Object> getDoctors() {
        return Map.of("doctors", doctorService.getDoctors());
    }

    // 5. Define the `saveDoctor` Method:
    // - Handles HTTP POST requests to register a new doctor.
    // - Accepts a validated `Doctor` object in the request body and a token for
    // authorization.
    // - Validates the token for the `"admin"` role before proceeding.
    // - If the doctor already exists, returns a conflict response; otherwise, adds
    // the doctor and returns a success message.
    @PostMapping("/{token}")
    public ResponseEntity<Map<String, String>> saveDoctor(@RequestBody Doctor doctor, @PathVariable String token) {

        ResponseEntity<Map<String, String>> response = service.validateToken(token, "admin");

        if (!response.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.status(response.getStatusCode())
                    .body(Map.of("error", "Invalid token: " + response.getStatusCode().toString()));
        }

        int result = doctorService.saveDoctor(doctor);

        if (result == 1) {
            return ResponseEntity.ok(Map.of("message", "Doctor registered successfully"));
        } else if (result == -1) {
            return ResponseEntity.status(409).body(Map.of("error", "Doctor already exists"));
        } else {
            return ResponseEntity.status(500).body(Map.of("error", "An unexpected error occurred"));
        }

    }

    // 6. Define the `doctorLogin` Method:
    // - Handles HTTP POST requests for doctor login.
    // - Accepts a validated `Login` DTO containing credentials.
    // - Delegates authentication to the `DoctorService` and returns login status
    // and token information.
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> doctorLogin(@RequestBody Login login) {
        return doctorService.validateDoctor(login);
    }

    // 7. Define the `updateDoctor` Method:
    // - Handles HTTP PUT requests to update an existing doctor's information.
    // - Accepts a validated `Doctor` object and a token for authorization.
    // - Token must belong to an `"admin"`.
    // - If the doctor exists, updates the record and returns success; otherwise,
    // returns not found or error messages.
    @PutMapping("/{token}")
    public ResponseEntity<Map<String, String>> updateDoctor(@RequestBody Doctor doctor, @PathVariable String token) {

        ResponseEntity<Map<String, String>> response = service.validateToken(token, "admin");

        if (!response.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.status(response.getStatusCode())
                    .body(Map.of("error", "Invalid token: " + response.getStatusCode().toString()));
        }

        int result = doctorService.updateDoctor(doctor);

        if (result == 1) {
            return ResponseEntity.ok(Map.of("message", "Doctor updated successfully"));
        } else if (result == -1) {
            return ResponseEntity.status(404).body(Map.of("error", "Doctor not found"));
        } else {
            return ResponseEntity.status(500).body(Map.of("error", "An unexpected error occurred"));
        }

    }

    // 8. Define the `deleteDoctor` Method:
    // - Handles HTTP DELETE requests to remove a doctor by ID.
    // - Requires both doctor ID and an admin token as path variables.
    // - If the doctor exists, deletes the record and returns a success message;
    // otherwise, responds with a not found or error message.
    @DeleteMapping("/{id}/{token}")
    public ResponseEntity<Map<String, String>> deleteDoctor(@PathVariable @NonNull Long id,
            @PathVariable String token) {

        ResponseEntity<Map<String, String>> response = service.validateToken(token, "admin");

        if (!response.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.status(response.getStatusCode())
                    .body(Map.of("error", "Invalid token: " + response.getStatusCode().toString()));
        }

        int result = doctorService.deleteDoctor(id);

        if (result == 1) {
            return ResponseEntity.ok(Map.of("message", "Doctor deleted successfully"));
        } else if (result == -1) {
            return ResponseEntity.status(404).body(Map.of("error", "Doctor not found"));
        } else {
            return ResponseEntity.status(500).body(Map.of("error", "An unexpected error occurred"));
        }

    }

    // 9. Define the `filter` Method:
    // - Handles HTTP GET requests to filter doctors based on name, time, and
    // specialty.
    // - Accepts `name`, `time`, and `specialty` as path variables.
    // - Calls the shared `Service` to perform filtering logic and returns matching
    // doctors in the response.
    @GetMapping("/filter/{name}/{time}/{specialty}")
    public Map<String, Object> filter(@PathVariable String name, @PathVariable String time,
            @PathVariable String specialty) {

        if (name.equals("null")) {
            name = null;
        }
        if (time.equals("null")) {
            time = null;
        }
        if (specialty.equals("null")) {
            specialty = null;
        }
        
        return service.filterDoctor(name, time, specialty);
    }

}
