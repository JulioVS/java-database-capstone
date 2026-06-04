# TEST THE APPLICATION

# Move to App folder
cd app 

# Install dependencies and run the application
mvn clean install
mvn spring-boot:run

# Test the endpoints using curl

# Get all doctors
curl http://localhost:8080/doctor

# Sign up a new patient
curl -X POST http://localhost:8080/patient \
-H "Content-Type: application/json" \
-d '{
    "name": "Karin Venders", 
    "email": "karin@me.com", 
    "phone": "555-111-9090", 
    "password": "iloveyou", 
    "address": "123 Main St"
    }'

# Log in as the patient
curl -X POST http://localhost:8080/patient/login \
-H "Content-Type: application/json" \
-d '{ 
    "email": "karin@me.com", 
    "password": "iloveyou" 
    }'

# Use the token from the login response to access the patient's appointments
curl -i -X GET http://localhost:8080/patient/1/patient/eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJrYXJpbkBtZS5jb20iLCJpYXQiOjE3ODA1MzY5NjcsImV4cCI6MTc4MTE0MTc2N30.8E7m96D6gQ2cbbNUdrVmscc_ifSrzI0qgVD2mBpxGrg \
-H "Accept: application/json" 

# Filter doctors by time and specialty
curl -X GET http://localhost:8080/doctor/filter/null/AM/Cardiologist

# End of 'curl' tests
