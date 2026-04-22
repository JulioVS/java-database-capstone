## MySQL Database Design

### Table: patients

- id: INT, Primary Key, Auto Increment
- name: VARCHAR(30), Not Null
- address: VARCHAR(100), Not Null
- phone: VARCHAR(15), Not Null
- email: VARCHAR(30), Not Null
- username: VARCHAR(8), Not Null, Unique
- password: VARCHAR(15), Not Null
- birth_date: DATETIME, Not Null
- ssn: VARCHAR(9), Not Null, Unique
- status: INT (0 = Inactive, 1 = Active)

### Table: doctors

- id: INT, Primary Key, Auto Increment
- name: VARCHAR(30), Not Null
- address: VARCHAR(100), Not Null
- phone: VARCHAR(15), Not Null
- email: VARCHAR(30), Not Null
- username: VARCHAR(8), Not Null, Unique
- password: VARCHAR(15), Not Null
- license: VARCHAR(15), Not Null, Unique
- specialization: VARCHAR(30), Not Null
- status: INT (0 = Inactive, 1 = Active 2 = Retired)

### Table: appointments

- id: INT, Primary Key, Auto Increment
- doctor_id: INT, Foreign Key → doctors(id)
- patient_id: INT, Foreign Key → patients(id)
- appointment_time: DATETIME, Not Null
- status: INT (0 = Scheduled, 1 = Completed, 2 = Cancelled)

### Table: admin

- id: INT, Primary Key, Auto Increment
- name: VARCHAR(30), Not Null
- email: VARCHAR(30), Not Null
- username: VARCHAR(8), Not Null, Unique
- password: VARCHAR(15), Not Null
- status: INT (0 = Inactive, 1 = Active)

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
```

### Collection: feedback

```json
{
  "_id": "ObjectId('13xyz123456')",
  "patientName": "Joan De Arc",
  "appointmentId": 15,
  "opinion": "Positive",
  "review": "Excellent attention.",
  "ratings": {
    "attention": "5",
    "punctuality": "3",
    "facilities": "4"
  }
}
```

### Collection: logs

```json
{
  "_id": "ObjectId('55yyz123456')",
  "timestamp": 2026-01-15T11:05:33.1234,
  "username": "drhouse@yahoo.com",
  "activity": "Logging into the system",
  "warnings": "None",
  "errors": "None"
}
```

### Collection: messages

```json
{
  "_id": "ObjectId('98aaa123456')",
  "timestamp": 2026-01-15T11:00:00,
  "from": "drhouse@yahoo.com",
  "to": [
    "admin@smartclinic.com",
    "laura@smartclinic.com",
    "steve@smartclinic.com"
    ],
  "message": "Drinks after shift, guys?",
  "priority": "Normal"
}
```
