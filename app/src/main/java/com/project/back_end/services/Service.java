package com.project.back_end.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.project.back_end.DTO.Login;
import com.project.back_end.models.Admin;
import com.project.back_end.models.Appointment;
import com.project.back_end.models.Doctor;
import com.project.back_end.models.Patient;
import com.project.back_end.repo.AdminRepository;
import com.project.back_end.repo.DoctorRepository;
import com.project.back_end.repo.PatientRepository;

@org.springframework.stereotype.Service
public class Service {

    // 1. **@Service Annotation**
    // The @Service annotation marks this class as a service component in Spring.
    // This allows Spring to automatically detect it through component scanning
    // and manage its lifecycle, enabling it to be injected into controllers or
    // other services using @Autowired or constructor injection.

    // 2. **Constructor Injection for Dependencies**
    // The constructor injects all required dependencies (TokenService,
    // Repositories, and other Services). This approach promotes loose coupling,
    // improves testability,
    // and ensures that all required dependencies are provided at object creation
    // time.
    private final TokenService tokenService;
    private final AdminRepository adminRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final DoctorService doctorService;
    private final PatientService patientService;

    public Service(TokenService tokenService, AdminRepository adminRepository,
            DoctorRepository doctorRepository, PatientRepository patientRepository,
            DoctorService doctorService, PatientService patientService) {
        this.tokenService = tokenService;
        this.adminRepository = adminRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
        this.doctorService = doctorService;
        this.patientService = patientService;
    }

    // 3. **validateToken Method**
    // This method checks if the provided JWT token is valid for a specific user. It
    // uses the TokenService to perform the validation.
    // If the token is invalid or expired, it returns a 401 Unauthorized response
    // with an appropriate error message. This ensures security by preventing
    // unauthorized access to protected resources.
    public ResponseEntity<Map<String, String>> validateToken(String token, String user) {

        if (!tokenService.validateToken(token, user)) {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid or expired token"));
        }

        return ResponseEntity.ok(Map.of("message", "Token is valid"));

    }

    // 4. **validateAdmin Method**
    // This method validates the login credentials for an admin user.
    // - It first searches the admin repository using the provided username.
    // - If an admin is found, it checks if the password matches.
    // - If the password is correct, it generates and returns a JWT token (using the
    // admin’s username) with a 200 OK status.
    // - If the password is incorrect, it returns a 401 Unauthorized status with an
    // error message.
    // - If no admin is found, it also returns a 401 Unauthorized.
    // - If any unexpected error occurs during the process, a 500 Internal Server
    // Error response is returned.
    // This method ensures that only valid admin users can access secured parts of
    // the system.
    public ResponseEntity<Map<String, String>> validateAdmin(Admin receivedAdmin) {

        try {

            Admin admin = adminRepository.findByUsername(receivedAdmin.getUsername());

            if (admin != null) {
                if (admin.getPassword().equals(receivedAdmin.getPassword())) {
                    String token = tokenService.generateToken(admin.getUsername());
                    return ResponseEntity.ok(Map.of("token", token));
                } else {
                    return ResponseEntity.status(401).body(Map.of("error", "Invalid password"));
                }
            } else {
                return ResponseEntity.status(401).body(Map.of("error", "Admin not found"));
            }

        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "An error occurred: " + e.getMessage()));
        }

    }

    // 5. **filterDoctor Method**
    // This method provides filtering functionality for doctors based on name,
    // specialty, and available time slots.
    // - It supports various combinations of the three filters.
    // - If none of the filters are provided, it returns all available doctors.
    // This flexible filtering mechanism allows the frontend or consumers of the API
    // to search and narrow down doctors based on user criteria.
    public Map<String, Object> filterDoctor(String name, String time, String specialty) {

        if (name == null && time == null && specialty == null) {
            return Map.of("doctors", doctorService.getDoctors());
        }

        if (name != null && time == null && specialty == null) {
            return doctorService.findDoctorByName(name);
        }

        if (name == null && time != null && specialty == null) {
            return doctorService.filterDoctorsByTime(time);
        }

        if (name == null && time == null && specialty != null) {
            return doctorService.filterDoctorBySpecialty(specialty);
        }

        if (name != null && time != null && specialty == null) {
            return doctorService.filterDoctorByNameAndTime(name, time);
        }

        if (name != null && time == null && specialty != null) {
            return doctorService.filterDoctorByNameAndSpecialty(name, specialty);
        }

        if (name == null && time != null && specialty != null) {
            return doctorService.filterDoctorByTimeAndSpecialty(time, specialty);
        }

        if (name != null && time != null && specialty != null) {
            return doctorService.filterDoctorsByNameSpecialtyAndTime(name, specialty, time);
        }

        return Map.of("doctors", null); // Placeholder for actual filtering logic

    }

    // 6. **validateAppointment Method**
    // This method validates if the requested appointment time for a doctor is
    // available.
    // - It first checks if the doctor exists in the repository.
    // - Then, it retrieves the list of available time slots for the doctor on the
    // specified date.
    // - It compares the requested appointment time with the start times of these
    // slots.
    // - If a match is found, it returns 1 (valid appointment time).
    // - If no matching time slot is found, it returns 0 (invalid).
    // - If the doctor doesn’t exist, it returns -1.
    // This logic prevents overlapping or invalid appointment bookings.
    public int validateAppointment(Appointment appointment) {

        // Check if doctor exists
        Long doctorId = appointment.getDoctor().getId();

        if (doctorId == null) {
            return -1; // Doctor ID is missing
        }

        Doctor doctor = doctorRepository.findById(doctorId).orElse(null);

        if (doctor == null) {
            return -1; // Doctor doesn't exist
        }

        // Check available time slots and validate appointment

        // Extract the date and time from the requested appointment
        LocalDate appointmentDate = appointment.getAppointmentTime().toLocalDate();
        LocalTime appointmentTime = appointment.getAppointmentTime().toLocalTime();

        if (appointmentDate == null || appointmentTime == null) {
            return 0; // Invalid appointment date or time
        }

        // Get available slots for the doctor on the requested date
        List<String> availableSlots = doctorService.getDoctorAvailability(doctorId, appointmentDate);

        // Check if the requested appointment time matches any available slot
        if (availableSlots != null) {
            for (String slot : availableSlots) {
                if (slot.startsWith(appointmentTime.toString())) {
                    return 1; // Valid appointment time
                }
            }
        }

        return 0; // Invalid appointment time

    }

    // 7. **validatePatient Method**
    // This method checks whether a patient with the same email or phone number
    // already exists in the system.
    // - If a match is found, it returns false (indicating the patient is not valid
    // for new registration).
    // - If no match is found, it returns true.
    // This helps enforce uniqueness constraints on patient records and prevent
    // duplicate entries.
    public boolean validatePatient(Patient patient) {

        String email = patient.getEmail();
        String phoneNumber = patient.getPhone();

        if (email == null || phoneNumber == null) {
            return false; // Email and phone number are required
        }

        if (patientRepository.findByEmailOrPhone(email, phoneNumber) != null) {
            return false; // Email or phone number already exists
        }

        return true; // Patient is valid for registration

    }

    // 8. **validatePatientLogin Method**
    // This method handles login validation for patient users.
    // - It looks up the patient by email.
    // - If found, it checks whether the provided password matches the stored one.
    // - On successful validation, it generates a JWT token and returns it with a
    // 200 OK status.
    // - If the password is incorrect or the patient doesn't exist, it returns a 401
    // Unauthorized with a relevant error.
    // - If an exception occurs, it returns a 500 Internal Server Error.
    // This method ensures only legitimate patients can log in and access their data
    // securely.
    public ResponseEntity<Map<String, String>> validatePatientLogin(Login login) {

        try {

            Patient patient = patientRepository.findByEmail(login.getEmail());

            if (patient != null) {
                if (patient.getPassword().equals(login.getPassword())) {
                    String token = tokenService.generateToken(patient.getEmail());
                    return ResponseEntity.ok(Map.of("token", token));
                } else {
                    return ResponseEntity.status(401).body(Map.of("error", "Invalid password"));
                }
            } else {
                return ResponseEntity.status(401).body(Map.of("error", "Patient not found"));
            }

        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "An error occurred: " + e.getMessage()));
        }

    }

    // 9. **filterPatient Method**
    // This method filters a patient's appointment history based on condition and
    // doctor name.
    // - It extracts the email from the JWT token to identify the patient.
    // - Depending on which filters (condition, doctor name) are provided, it
    // delegates the filtering logic to PatientService.
    // - If no filters are provided, it retrieves all appointments for the patient.
    // This flexible method supports patient-specific querying and enhances user
    // experience on the client side.
    public ResponseEntity<Map<String, Object>> filterPatient(String condition, String name, String token) {

        // Extract email from token to identify the patient
        String email = tokenService.extractEmail(token);

        if (email == null) {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid token"));
        }

        // Find the patient by email
        Patient patient = patientRepository.findByEmail(email);

        if (patient == null) {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid token"));
        }

        // Get the patient ID for filtering appointments
        Long patientId = patient.getId();

        if (patientId == null) {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid token"));
        }

        // Filter appointments based on provided condition and doctor name
        if (condition != null && name == null) {
            return patientService.filterByCondition(condition, patientId);
        }

        if (condition == null && name != null) {
            return patientService.filterByDoctor(name, patientId);
        }

        if (condition != null && name != null) {
            return patientService.filterByDoctorAndCondition(condition, name, patientId);
        }

        if (condition == null && name == null) {
            return patientService.getPatientAppointments(patientId, token);
        }

        return ResponseEntity.ok(Map.of("appointments", null)); // Placeholder for actual filtering logic

    }

}
