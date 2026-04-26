/*
Import the overlay function for booking appointments from loggedPatient.js ✅

  Import the deleteDoctor API function to remove doctors (admin role) from docotrServices.js ✅

  Import function to fetch patient details (used during booking) from patientServices.js ✅

  Function to create and return a DOM element for a single doctor card
    Create the main container for the doctor card ✅
    Retrieve the current user role from localStorage ✅
    Create a div to hold doctor information ✅
    Create and set the doctor’s name ✅
    Create and set the doctor's specialization ✅
    Create and set the doctor's email ✅
    Create and list available appointment times ✅
    Append all info elements to the doctor info container ✅
    Create a container for card action buttons ✅
    === ADMIN ROLE ACTIONS ===
      Create a delete button ✅
      Add click handler for delete button ✅
     Get the admin token from localStorage ✅
        Call API to delete the doctor ✅
        Show result and remove card if successful ✅
      Add delete button to actions container ✅
   
    === PATIENT (NOT LOGGED-IN) ROLE ACTIONS ===
      Create a book now button ✅
      Alert patient to log in before booking ✅
      Add button to actions container ✅
  
    === LOGGED-IN PATIENT ROLE ACTIONS === 
      Create a book now button ✅
      Handle booking logic for logged-in patient   
        Redirect if token not available ✅
        Fetch patient data with token ✅
        Show booking overlay UI with doctor and patient info ✅
      Add button to actions container ✅
   
  Append doctor info and action buttons to the car ✅
  Return the complete doctor card element ✅
*/

import { showBookingOverlay } from "../loggedPatient.js";
import { deleteDoctor } from "../services/doctorServices.js";
import { getPatientDetails } from "../services/patientServices.js";

export function createDoctorCard(doctor) {
  const card = document.createElement("div");
  card.classList.add("doctor-card");

  const userRole = localStorage.getItem("userRole");

  const doctorInfo = document.createElement("div");
  doctorInfo.classList.add("doctor-info");

  const name = document.createElement("h3");
  name.textContent = doctor.name;

  const specialization = document.createElement("p");
  specialization.textContent = `Specialization: ${doctor.specialization}`;

  const email = document.createElement("p");
  email.textContent = `Email: ${doctor.email}`;

  const availability = document.createElement("p");
  availability.textContent = `Available Times: ${doctor.availableTimes.join(", ")}`;

  doctorInfo.appendChild(name);
  doctorInfo.appendChild(specialization);
  doctorInfo.appendChild(email);
  doctorInfo.appendChild(availability);

  const actions = document.createElement("div");
  actions.classList.add("card-actions");

  if (userRole === "ADMIN") {
    const deleteBtn = document.createElement("button");
    deleteBtn.textContent = "Delete";
    deleteBtn.classList.add("delete-btn");
    deleteBtn.addEventListener("click", async () => {
      const token = localStorage.getItem("token");
      if (!token) {
        alert("Admin token not found. Please log in again.");
        return;
      }
      try {
        await deleteDoctor(doctor.id, token);
        alert("Doctor deleted successfully.");
        card.remove();
      } catch (error) {
        console.error("Error deleting doctor:", error);
        alert("Failed to delete doctor. Please try again.");
      }
    });
    actions.appendChild(deleteBtn);
  } else if (userRole === "PATIENT") {
    const bookBtn = document.createElement("button");
    bookBtn.textContent = "Book Now";
    bookBtn.classList.add("book-btn");
    bookBtn.addEventListener("click", () => {
      alert("Please log in to book an appointment.");
    });
    actions.appendChild(bookBtn);
  } else if (userRole === "LOGGED_IN_PATIENT") {
    const bookBtn = document.createElement("button");
    bookBtn.textContent = "Book Now";
    bookBtn.classList.add("book-btn");
    bookBtn.addEventListener("click", async () => {
      const token = localStorage.getItem("token");
      if (!token) {
        alert("Token not found. Please log in again.");
        return;
      }
      try {
        const patientData = await getPatientDetails(token);
        showBookingOverlay(doctor, patientData);
      } catch (error) {
        console.error("Error fetching patient details:", error);
        alert("Failed to fetch patient details. Please try again.");
      }
    });
    actions.appendChild(bookBtn);
  }

  card.appendChild(doctorInfo);
  card.appendChild(actions);

  return card;
}
