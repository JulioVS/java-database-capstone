/*
  This script handles the admin dashboard functionality for managing doctors:
  - Loads all doctor cards
  - Filters doctors by name, time, or specialty
  - Adds a new doctor via modal form


  Attach a click listener to the "Add Doctor" button ✅
  When clicked, it opens a modal form using openModal('addDoctor') ✅


  When the DOM is fully loaded:
    - Call loadDoctorCards() to fetch and display all doctors ✅


  Function: loadDoctorCards ✅
  Purpose: Fetch all doctors and display them as cards ✅

    Call getDoctors() from the service layer ✅
    Clear the current content area ✅
    For each doctor returned:
    - Create a doctor card using createDoctorCard() ✅
    - Append it to the content div ✅

    Handle any fetch errors by logging them ✅


  Attach 'input' and 'change' event listeners to the search bar and filter dropdowns
  On any input change, call filterDoctorsOnChange() ✅


  Function: filterDoctorsOnChange ✅
  Purpose: Filter doctors based on name, available time, and specialty ✅

    Read values from the search bar and filters ✅
    Normalize empty values to null ✅
    Call filterDoctors(name, time, specialty) from the service ✅

    If doctors are found:
    - Render them using createDoctorCard() ✅
    If no doctors match the filter:
    - Show a message: "No doctors found with the given filters." ✅

    Catch and display any errors with an alert ✅


  Function: renderDoctorCards ✅
  Purpose: A helper function to render a list of doctors passed to it ✅

    Clear the content area ✅
    Loop through the doctors and append each card to the content area ✅


  Function: adminAddDoctor ✅
  Purpose: Collect form data and add a new doctor to the system ✅

    Collect input values from the modal form
    - Includes name, email, phone, password, specialty, and available times ✅

    Retrieve the authentication token from localStorage
    - If no token is found, show an alert and stop execution ✅

    Build a doctor object with the form values ✅

    Call saveDoctor(doctor, token) from the service ✅

    If save is successful:
    - Show a success message ✅
    - Close the modal and reload the page ✅

    If saving fails, show an error message ✅
*/

import { openModal, closeModal } from "./components/modals.js";
import { createDoctorCard } from "./components/doctorCard.js";

import {
  getDoctors,
  filterDoctors,
  saveDoctor,
} from "./services/doctorServices.js";

document.getElementById("addDoctorBtn").addEventListener("click", () => {
  openModal("addDoctor");
});

document.addEventListener("DOMContentLoaded", () => {
  loadDoctorCards();

  // Attach event listeners for filtering
  document
    .getElementById("searchBar")
    .addEventListener("input", filterDoctorsOnChange);
  document
    .getElementById("timeFilter")
    .addEventListener("change", filterDoctorsOnChange);
  document
    .getElementById("specialtyFilter")
    .addEventListener("change", filterDoctorsOnChange);
});

function loadDoctorCards() {
  getDoctors()
    .then((doctors) => {
      const contentDiv = document.getElementById("content");
      contentDiv.innerHTML = ""; // Clear existing content
      doctors.forEach((doctor) => {
        const card = createDoctorCard(doctor);
        contentDiv.appendChild(card);
      });
    })
    .catch((error) => {
      console.error("Error fetching doctors:", error);
    });
}

function filterDoctorsOnChange() {
  const name = document.getElementById("searchBar").value.trim() || null;
  const time = document.getElementById("timeFilter").value || null;
  const specialty = document.getElementById("specialtyFilter").value || null;

  filterDoctors(name, time, specialty)
    .then((doctors) => {
      if (doctors.length > 0) {
        renderDoctorCards(doctors);
      } else {
        document.getElementById("content").innerHTML =
          "<p>No doctors found with the given filters.</p>";
      }
    })
    .catch((error) => {
      console.error("Error filtering doctors:", error);
      alert("An error occurred while filtering doctors. Please try again.");
    });
}

function renderDoctorCards(doctors) {
  const contentDiv = document.getElementById("content");
  contentDiv.innerHTML = ""; // Clear existing content
  doctors.forEach((doctor) => {
    const card = createDoctorCard(doctor);
    contentDiv.appendChild(card);
  });
}

function adminAddDoctor() {
  const name = document.getElementById("doctorName").value.trim();
  const email = document.getElementById("doctorEmail").value.trim();
  const phone = document.getElementById("doctorPhone").value.trim();
  const password = document.getElementById("doctorPassword").value;
  const specialty = document.getElementById("doctorSpecialty").value;
  const availableTimes = Array.from(
    document.querySelectorAll(".available-time:checked"),
  ).map((input) => input.value);

  const token = localStorage.getItem("authToken");
  if (!token) {
    alert("You must be logged in to add a doctor.");
    return;
  }

  const doctor = {
    name,
    email,
    phone,
    password,
    specialty,
    availableTimes,
  };

  saveDoctor(doctor, token)
    .then((response) => {
      if (response.success) {
        alert("Doctor added successfully!");
        closeModal("addDoctor");
        loadDoctorCards(); // Reload the doctor cards to show the new doctor
      } else {
        alert("Failed to add doctor: " + response.message);
      }
    })
    .catch((error) => {
      console.error("Error adding doctor:", error);
      alert("An error occurred while adding the doctor. Please try again.");
    });
}
