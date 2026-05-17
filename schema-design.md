## MySQL Database Design

### Table: admin

- id: INT, Primary Key, Auto Increment
- username: VARCHAR(16), Not Null, Unique
- password: VARCHAR(16), Not Null

### Table: doctors

- id: INT, Primary Key, Auto Increment
- name: VARCHAR(100), Not Null
- specialty: VARCHAR(30), Not Null
- email: VARCHAR(30), Not Null, Unique
- password: VARCHAR(16), Not Null
- phone: CHAR(12)
- available_times, VARCHAR(100)

### Table: patients

- id: INT, Primary Key, Auto Increment
- name: VARCHAR(100), Not Null
- email: VARCHAR(30), Not Null, Unique
- password: VARCHAR(16), Not Null
- address: VARCHAR(100)
- phone: CHAR(12)

### Table: appointments

- id: INT, Primary Key, Auto Increment
- doctor_id: INT, Foreign Key → doctors(id)
- patient_id: INT, Foreign Key → patients(id)
- appointment_time: DATETIME, Not Null
- status: INT (0 = Scheduled, 1 = Completed, 2 = Cancelled)


## MongoDB Collection Design

### Collection: prescriptions

```json
{
  "_id": "ObjectId('64abc123456')",
  "patientName": "John Smith",
  "appointmentId": 51,
  "medication": "Paracetamol",
  "dosage": "500mg",
  "doctorNotes": "Take 1 tablet every 6 hours.",
  "refillCount": 2,
  "pharmacy": {
    "name": "Walgreens SF",
    "location": "Market Street"
  }
}
