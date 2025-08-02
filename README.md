AssetFlow - Backend 🏢


📝 Overview
This repository contains the backend REST API for AssetFlow, a full-stack corporate resource management system. It's designed to solve the common office problem of inefficiently managing shared assets like meeting rooms, vehicles, and equipment by providing a secure, centralized, and real-time booking platform.

The application is built with Spring Boot and features a robust, role-based security model using JWT to serve a modern React frontend.

✨ Key Features
🔑 JWT-Based Security: End-to-end secure authentication using JSON Web Tokens.

🛡️ Role-Based Authorization: Strict access control distinguishing between ADMIN and EMPLOYEE user roles.

🔄 Full CRUD Operations: Complete Create, Read, Update, and Delete functionality for all major entities (Assets, Users, Bookings).

✅ Admin Approval Workflow: A full booking lifecycle where employees make requests, and admins can APPROVE or REJECT them.

💥 Booking Conflict Detection: The system intelligently prevents any asset from being double-booked for overlapping times.

💾 Persistent Local Database: Configured with a file-based H2 database for easy local development and data persistence across restarts.

🛠️ Technology Stack
Framework: Spring Boot 3.x

Language: Java 21

Security: Spring Security 6.x (with JWT)

Database: Spring Data JPA (Hibernate) & H2 Database

Build Tool: Maven

🚀 Getting Started
Prerequisites
☕ Java Development Kit (JDK) 21 or later

🪶 Apache Maven

📬 An API client like Postman

Running the Application
Clone the repository:

git clone https://github.com/your-username/assetflow-backend.git
cd assetflow-backend

Run with Maven:
Use the included Maven wrapper to start the server.

./mvnw spring-boot:run

The server will be live at http://localhost:8080.

Accessing the Database
You can view the H2 database through its web console:

URL: http://localhost:8080/h2-console

JDBC URL: jdbc:h2:file:./data/assetflowdb

Username: sa

Password: (leave blank)

📖 API Guide
All protected endpoints require an Authorization header with a JWT obtained from the /login endpoint.

Header Format: Authorization: Bearer <your_jwt_token>

Authentication Endpoints (Public)
POST /api/auth/register

Registers a new user. The role can be ROLE_EMPLOYEE or ROLE_ADMIN.

POST /api/auth/login

Authenticates a user and returns a JWT access token.

User Endpoints (Authenticated)
GET /api/assets

Fetches a list of all company assets.

GET /api/assets/{id}

Fetches details for a single asset.

POST /api/bookings

Creates a new booking request with a PENDING status.

GET /api/users/{userId}/bookings

Fetches all bookings for a specific user.

Admin Endpoints (ROLE_ADMIN only)
POST /api/assets

Creates a new asset.

PUT /api/assets/{id}

Updates an existing asset's details.

DELETE /api/assets/{id}

Deletes an asset.

GET /api/users

Fetches a list of all users in the system.

DELETE /api/users/{id}

Deletes a user.

PUT /api/bookings/{id}/status?status=<STATUS>

Updates a booking's status. Replace <STATUS> with APPROVED or REJECTED.

