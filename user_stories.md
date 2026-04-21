# User Story Template

**Title:**
_As a [user role], I want [feature/goal], so that [reason]._

**Acceptance Criteria:**
1. [Criteria 1]
2. [Criteria 2]
3. [Criteria 3]

**Priority:** [High/Medium/Low]
**Story Points:** [Estimated Effort in Points]
**Notes:**
- [Additional information or edge cases]

## Admin User Stories

**Title:**
_As an Admin, I want to log into the portal with my username and password, so that I can manage the platform securely._

**Acceptance Criteria:**
1. Given a login page
2. When I enter my credentials
3. Then I should be redirected to my Admin Dashboard

**Priority:** High
**Story Points:** 5
**Notes:**
- None

**Title:**
_As an Admin, I want to log out of the portal, so that I can protect system access._

**Acceptance Criteria:**
1. Given a logout page
2. When I click the logout button
3. Then I should safely exit the portal

**Priority:** High
**Story Points:** 3
**Notes:**
- The admin must be already logged in

**Title:**
_As an Admin, I want to add doctors to the portal, so that they can start accepting appointments._

**Acceptance Criteria:**
1. Given my Admin Dashboard
2. When I click the option to add a doctor
3. Then I should be able to enter the doctor details and save them on the system

**Priority:** High
**Story Points:** 8
**Notes:**
- None

**Title:**
_As an Admin, I want to delete doctor's profile from the portal, so that they no longer can receive appointments._

**Acceptance Criteria:**
1. Given my Admin Dashboard
2. When I click the option to remove a doctor
3. Then I should be able to delete him from the system

**Priority:** Medium
**Story Points:** 3
**Notes:**
- The doctor must already be created on the system

**Title:**
_As an Admin, I want to run a stored procedure in MySQL CLI, so that I can get the number of appointments per month and track usage statistics._

**Acceptance Criteria:**
1. Given my Admin Dashboard
2. When I click the option to run statistic reports
3. Then I should get a report about appointment and usage statistics

**Priority:** Low
**Story Points:** 5
**Notes:**
- There must already be activity info to run the report on

## Patient User Stories

**Title:**
_As a Patient, I want to view a list of doctors without logging in, so that I can explore options before registering._

**Acceptance Criteria:**
1. Given a system homepage
2. When I click the View Doctors section
3. Then should get a list of all available doctors in the system

**Priority:** High
**Story Points:** 5
**Notes:**
- None

**Title:**
_As a Patient, I want to sign up using my email and password, so that I can book appointments._

**Acceptance Criteria:**
1. Given a Sign Up link or button
2. When I follow or press it
3. Then I should be able to register on the system with my email and password

**Priority:** High
**Story Points:** 8
**Notes:**
- The patient must not be already registered

**Title:**
_As a Patient, I want to log into the portal, so that I can manage my bookings._

**Acceptance Criteria:**
1. Given a Log In link or button
2. When I follow or press it
3. Then I should be able to enter the system and manage my bookings

**Priority:** Medium
**Story Points:** 8
**Notes:**
- The patient must already be registered on the system and have bookings

**Title:**
_As a Patient, I want to log out of the portal, so that I can secure my account._

**Acceptance Criteria:**
1. Given a Log Out link or button
2. When I follow it or press it
3. Then I should safely exit the system

**Priority:** High
**Story Points:** 3
**Notes:**
- The patient must already be logged into the system

**Title:**
_As a Patient, I want to log in and book an hour-long appointment, so that I can consult with a doctor._

**Acceptance Criteria:**
1. Given a successful log in into the system
2. When I go the Doctors listing page and choose an available one
3. Then I should be able to book an hour-long appointment with him

**Priority:** Medium
**Story Points:** 5
**Notes:**
- The doctor needs to have a lot of availability to accept hour-long appointments

**Title:**
_As a Patient, I want to view my upcoming appointments, so that I can prepare accordingly._

**Acceptance Criteria:**
1. Given a successful log in into the system
2. When I go to My Appointments section
3. Then I should see a list of all my upcoming appointments

**Priority:** Medium
**Story Points:** 5
**Notes:**
- The patient must already have appointments

## Doctor User Stories

**Title:**
_As a Doctor, I want to log into the portal, so that I can manage my appointments._

**Acceptance Criteria:**
1. Given a login page
2. When I enter my credentials
3. Then I should be redirected to my Doctor Dashboard and see my appointments

**Priority:** High
**Story Points:** 5
**Notes:**
- The doctor must already be registered on the system

**Title:**
_As a Doctor, I want to log out of the portal, so that I protect my data._

**Acceptance Criteria:**
1. Given a logout link or button
2. When I follow it or press it
3. Then I should safely exit the system

**Priority:** High
**Story Points:** 3
**Notes:**
- The doctor must already be logged into the system

**Title:**
_As a Doctor, I want to view my appointment calendar, so that I can stay organized._

**Acceptance Criteria:**
1. Given a successful login into the system 
2. When I click into My Appointments section
3. Then I should see my appointment calendar

**Priority:** High
**Story Points:** 5
**Notes:**
- The doctor must already be logged in and have scheduled appointments

**Title:**
_As a Doctor, I want to be able to mark my unavailability, so that patients are only shown the available slots._

**Acceptance Criteria:**
1. Given my calendar schedule on the system
2. When I select a target time slot
3. Then I should be able to mark it as unavailable, as to not receive any appointments for it

**Priority:** Medium
**Story Points:** 5
**Notes:**
- None

**Title:**
_As a Doctor, I want to be able to update my profile with specialization and contact information, so that patients have up-to-date information._

**Acceptance Criteria:**
1. Given my Doctor Dashboard on the system
2. When I click into My Profile section
3. Then I want to be able to edit and update my specialization and contact information

**Priority:** Medium
**Story Points:** 5
**Notes:**
- None

**Title:**
_As a Doctor, I want to be able to view the patient details for upcoming appointments, so that I can be prepared._

**Acceptance Criteria:**
1. Given my Doctor Dashboard on the system
2. When I click into My Appointments section
3. Then I want to be able to see my patient details

**Priority:** Medium
**Story Points:** 5
**Notes:**
- None
