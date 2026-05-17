# User Story Template

**Title:**
_As a [user role], I want [feature/goal], so that [reason]._

**Acceptance Criteria:**
1. Given [Criteria 1]
2. When [Criteria 2]
3. Then [Criteria 3]

**Priority:** [High/Medium/Low]
**Story Points:** [Estimated Effort in Points]
**Notes:**
- [Additional information or edge cases]

## Admin User Stories

**Log into the portal:**
_As an Admin, I want to log into the portal with my username and password, so that I can manage the platform securely._

**Acceptance Criteria:**
1. Given an Admin dashboard,
2. When I enter my credentials,
3. Then I should be able to manage my information securely.

**Priority:** High
**Story Points:** 5
**Notes:**
- Admin should first register in the system.


**Log out of the portal:**
_As an Admin, I want to log out of the portal, so that I can protect system access._

**Acceptance Criteria:**
1. Given an Admin dashboard,
2. When I press the Log Out button,
3. Then I should safely exit my profile.

**Priority:** High
**Story Points:** 3
**Notes:**
- Admin should be already logged into the system.


**Add doctors to the portal:**
_As an Admin, I want to add doctors to the portal, so that they can accept patient appointments._

**Acceptance Criteria:**
1. Given an Admin dashboard,
2. When I press the Add Doctor button,
3. Then I should be able to register a doctor on the system.

**Priority:** High
**Story Points:** 5
**Notes:**
- Admin should be already logged into the system.


**Delete doctor's profile from the portal:**
_As an Admin, I want to delete a doctor's profile from the portal, so that they can no longer accept appointments._

**Acceptance Criteria:**
1. Given an Admin dashboard,
2. When I press the Delete Doctor button,
3. Then I should be able to remove a doctor's profile from the system.

**Priority:** Medium
**Story Points:** 3
**Notes:**
- Admin should be already logged in and doctor must already exist on the system.


**Run a stored procedure in MySQL CLI:**
_As an Admin, I want to run a stored procedure in MySQL CLI, so that I can get the number of appointments per month and track usage statistics._

**Acceptance Criteria:**
1. Given an Admin dashboard,
2. When I run a MySQL stored procedure,
3. Then I should be get the number of appointments per month and track usage statistics.

**Priority:** Medium
**Story Points:** 5
**Notes:**
- Admin should already be logged in and doctor must already exist on the system.


## Patient User Stories

**View a list of doctors:**
_As a Patient, I want to view a list of doctors without logging in, so that I can explore options before registering._

**Acceptance Criteria:**
1. Given a landing page,
2. When I go to the Doctors option,
3. Then I should see a list of available doctors.

**Priority:** Medium
**Story Points:** 5
**Notes:**
- There should be already registered doctors in the system.


**Sign up using email and password:**
_As a Patient, I want to sign up using my email and password, so that I can book appointments._

**Acceptance Criteria:**
1. Given a Sign Up option,
2. When I enter my email and password,
3. Then I should be able to book appointments.

**Priority:** High
**Story Points:** 8
**Notes:**
- None.


**Log into the portal:**
_As a Patient, I want to log into the portal with my credentials, so that I can manage my bookings._

**Acceptance Criteria:**
1. Given a Patient dashboard,
2. When I enter my credentials,
3. Then I should be able to manage my bookings.

**Priority:** High
**Story Points:** 5
**Notes:**
- Patient should first register in the system.


**Log out of the portal:**
_As a Patient, I want to log out of the portal, so that I can secure my account._

**Acceptance Criteria:**
1. Given a Patient dashboard,
2. When I press the Log Out button,
3. Then I should safely exit my profile.

**Priority:** High
**Story Points:** 3
**Notes:**
- Patient should already be logged into the system.


**Log in and book appointment:**
_As a Patient, I want to log in and book an hour-long appointment, so that I can consult with a doctor._

**Acceptance Criteria:**
1. Given a Patient dashboard,
2. When I enter my credentials,
3. Then I should be able book hour-long appointments.

**Priority:** High
**Story Points:** 5
**Notes:**
- None.


**View my upcoming appointments:**
_As a Patient, I want to view my upcoming appointments, so that I can prepare accordingly._

**Acceptance Criteria:**
1. Given a Patient dashboard,
2. When I look at the main panel,
3. Then I should see my upcoming appointments.

**Priority:** Medium
**Story Points:** 5
**Notes:**
- There should already be scheduled appointments.


## Doctor User Stories

**Log into the portal:**
_As a Doctor, I want to log into the portal, so that I can manage my appointments._

**Acceptance Criteria:**
1. Given a Doctor dashboard,
2. When I enter my credentials,
3. Then I should be able to manage my appointments.

**Priority:** High
**Story Points:** 5
**Notes:**
- Doctor should first register into the system.


**Log out of the portal:**
_As a Doctor, I want to log out of the portal, so that I can protect my data._

**Acceptance Criteria:**
1. Given a Doctor dashboard,
2. When I press the Log Out button,
3. Then I should safely exit my profile.

**Priority:** High
**Story Points:** 3
**Notes:**
- Doctor should already be logged into the system.


**View my appointment calendar:**
_As a Doctor, I want to view my appointment calendar, so that I can stay organized._

**Acceptance Criteria:**
1. Given a Doctor dashboard,
2. When I click the Calendar option,
3. Then I should see my appointment calendar.

**Priority:** Medium
**Story Points:** 8
**Notes:**
- There should be already scheduled appointments.


**Mark my unavailability:**
_As a Doctor, I want to mark my unavailability, so that I can inform patients only the available slots._

**Acceptance Criteria:**
1. Given a Doctor dashboard,
2. When I unmark a time slot as available,
3. Then it should not be visible for patient appointments anymore.

**Priority:** Medium
**Story Points:** 5
**Notes:**
- None.


**Update my profile:**
_As a Doctor, I want to update my profile with specialization and contact information, so that patients have up-to-date information._

**Acceptance Criteria:**
1. Given a Doctor dashboard,
2. When I click the Update Profile option,
3. Then I should be abe to update my profile with specialization and contact information.

**Priority:** Low
**Story Points:** 3
**Notes:**
- None.


**View upcoming appointments:**
_As a Doctor, I want to view patient details for upcoming appointments, so that I can be prepared._

**Acceptance Criteria:**
1. Given a Doctor dashboard,
2. When I click into Upcoming Appointments,
3. Then I should see my patients details.

**Priority:** Medium
**Story Points:** 5
**Notes:**
- There should already be scheduled appointments.
