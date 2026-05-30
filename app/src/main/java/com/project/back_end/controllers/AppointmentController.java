package com.project.back_end.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.project.back_end.models.Appointment;
import com.project.back_end.services.AppointmentService;
import com.project.back_end.services.Service;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    // 1. Set Up the Controller Class:
    // - Annotate the class with `@RestController` to define it as a REST API
    // controller.
    // - Use `@RequestMapping("/appointments")` to set a base path for all
    // appointment-related endpoints.
    // - This centralizes all routes that deal with booking, updating, retrieving,
    // and canceling appointments.

    // 2. Autowire Dependencies:
    // - Inject `AppointmentService` for handling the business logic specific to
    // appointments.
    // - Inject the general `Service` class, which provides shared functionality
    // like token validation and appointment checks.
    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private Service service;

    // 3. Define the `getAppointments` Method:
    // - Handles HTTP GET requests to fetch appointments based on date and patient
    // name.
    // - Takes the appointment date, patient name, and token as path variables.
    // - First validates the token for role `"doctor"` using the `Service`.
    // - If the token is valid, returns appointments for the given patient on the
    // specified date.
    // - If the token is invalid or expired, responds with the appropriate message
    // and status code.
    @GetMapping("/{date}/{patientName}/{token}")
    public Map<String, Object> getAppointments(@PathVariable String date, @PathVariable String patientName,
            @PathVariable String token) {

        ResponseEntity<Map<String, String>> response = service.validateToken(token, "doctor");

        if (!response.getStatusCode().is2xxSuccessful()) {
            return Map.of("error", "Invalid token: " + response.getStatusCode().toString());
        }

        LocalDate appointmentDate;

        try {
            appointmentDate = LocalDate.parse(date);
        } catch (DateTimeParseException e) {
            return Map.of("error", "Invalid date format. Use YYYY-MM-DD.");
        }

        return appointmentService.getAppointments(patientName, appointmentDate, token);

    }

    // 4. Define the `bookAppointment` Method:
    // - Handles HTTP POST requests to create a new appointment.
    // - Accepts a validated `Appointment` object in the request body and a token as
    // a path variable.
    // - Validates the token for the `"patient"` role.
    // - Uses service logic to validate the appointment data (e.g., check for doctor
    // availability and time conflicts).
    // - Returns success if booked, or appropriate error messages if the doctor ID
    // is invalid or the slot is already taken.
    @PostMapping("/{token}")
    public ResponseEntity<Map<String, String>> bookAppointment(@RequestBody @NonNull Appointment appointment,
            @PathVariable String token) {

        ResponseEntity<Map<String, String>> response = service.validateToken(token, "patient");

        if (!response.getStatusCode().is2xxSuccessful()) {
            return response;
        }

        if (!(service.validateAppointment(appointment) == 1)) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Invalid appointment data. Please check doctor ID, date, and time."));
        }

        if (appointmentService.bookAppointment(appointment) == 1) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("message", "Appointment booked successfully."));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to book appointment. Please try again."));
        }

    }

    // 5. Define the `updateAppointment` Method:
    // - Handles HTTP PUT requests to modify an existing appointment.
    // - Accepts a validated `Appointment` object and a token as input.
    // - Validates the token for `"patient"` role.
    // - Delegates the update logic to the `AppointmentService`.
    // - Returns an appropriate success or failure response based on the update
    // result.
    @PutMapping("/{token}")
    public ResponseEntity<Map<String, String>> updateAppointment(@RequestBody @NonNull Appointment appointment,
            @PathVariable String token) {

        ResponseEntity<Map<String, String>> response = service.validateToken(token, "patient");

        if (!response.getStatusCode().is2xxSuccessful()) {
            return response;
        }

        if (!(service.validateAppointment(appointment) == 1)) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Invalid appointment data. Please check doctor ID, date, and time."));
        }

        return appointmentService.updateAppointment(appointment);

    }

    // 6. Define the `cancelAppointment` Method:
    // - Handles HTTP DELETE requests to cancel a specific appointment.
    // - Accepts the appointment ID and a token as path variables.
    // - Validates the token for `"patient"` role to ensure the user is authorized
    // to cancel the appointment.
    // - Calls `AppointmentService` to handle the cancellation process and returns
    // the result.
    @DeleteMapping("/{id}/{token}")
    public ResponseEntity<Map<String, String>> cancelAppointment(@PathVariable @NonNull Long id,
            @PathVariable String token) {

        ResponseEntity<Map<String, String>> response = service.validateToken(token, "patient");

        if (!response.getStatusCode().is2xxSuccessful()) {
            return response;
        }

        return appointmentService.cancelAppointment(id, token);

    }

}
