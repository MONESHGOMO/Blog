# Dev16 Blog Platform

> A cloud-native blog platform built to scale — Spring Boot · JWT · MySQL · Docker · Firebase · Render

---

## What This Is

Dev16 Blog is a full-stack content management system built from scratch as a personal project to publish tech content and apply production-grade backend engineering practices.

Not a tutorial follow-along. A real deployed system — with a real performance problem found and fixed post-launch.

---

## 🔗 Project Access

- **Frontend:**  
  [![Firebase Frontend](https://img.shields.io/badge/Firebase-Frontend-FFCA28?style=for-the-badge&logo=firebase)](https://dev16-blog.web.app)

- **Backend:**  
  [![Render Backend](https://img.shields.io/badge/Render-Backend-46E3B7?style=for-the-badge&logo=render)](https://blog-1fcl.onrender.com)  
  ⚙️ *Note:* Backend is on Render's free tier — may take a few seconds to wake up on first request (cold start).

- **Backend Server Status:**  
  👉 [Check Server Status](https://blog-1fcl.onrender.com/)

- **Admin Panel:**  
  👉 [Open Admin Panel](https://gomodevblogs.netlify.app/)

---

## Architecture

```
[User] ⇄ [Firebase Frontend] ⇄ [Render REST API] ⇄ [Aiven MySQL]
```

| Layer      | Technology                          |
|------------|-------------------------------------|
| Backend    | Spring Boot 3, Spring Security      |
| Auth       | JWT (JSON Web Tokens)               |
| Database   | MySQL via Aiven Cloud               |
| Frontend   | HTML · CSS · JavaScript             |
| Hosting    | Firebase (frontend) · Render (API)  |
| Container  | Docker                              |

---

## Core Features

### Administration
- JWT-based authentication and authorization
- Role-Based Access Control (RBAC)
- Admin dashboard — CRUD for posts and users
- Automated email notifications on user registration

### User Experience
- Responsive, mobile-first UI
- Clean, accessible blog interface
- Pagination (5 blogs per page)
- Real-time feedback and error handling

### Infrastructure
- Decoupled frontend and backend for independent scaling
- Managed MySQL database with automated backups (Aiven)
- Environment variable management for secure configuration

---

## 🖥️ Screenshots

### Server Status Page
<img width="1600" height="1300" alt="Server" src="https://github.com/user-attachments/assets/3e0eab54-702d-4d17-9559-01dd8bce84a9" />

> Ensures the API is live and connected before any frontend requests are processed.

---

### Home UI
<img width="1600" height="1300" alt="Home" src="https://github.com/user-attachments/assets/1efc2011-ff7e-4f31-8e28-43f9760f3fbf" />

> Clean and responsive frontend UI for readers to explore posts.

---

### Blog Display Page
<img width="1600" height="1300" alt="App" src="https://github.com/user-attachments/assets/0ff051d4-861b-4a0a-8c31-c678e7d7c5b7" />

> All published blogs listed with pagination for smooth navigation.

---

### Admin Dashboard
<img width="1600" height="1300" alt="Admin" src="https://github.com/user-attachments/assets/9f5244eb-81c6-477e-91ce-f09af2f7fe3a" />

> Secured admin panel — login, CRUD operations, and analytics dashboard.

---

## 🛠️ Performance Optimization

### The Problem

After launch, blog pages with images were taking **~300 seconds** to load.

**Root cause:** Images were stored as BLOBs directly in MySQL — causing massive API payloads on every request.

### What I Did

| Fix | Detail |
|-----|--------|
| Migrated images | MySQL BLOBs → cloud object storage |
| Database change | Stores only image URLs, not raw data |
| Added pagination | 5 blogs per page to reduce payload |
| Refactored APIs | Fetch only required fields per request |

### Result

| Metric | Before | After | Improvement |
|--------|--------|-------|-------------|
| Page load time | ~300s | ~6s | ~98% |

### How It Works Now

1. Frontend requests blog data → backend returns JSON with image URLs
2. Frontend uses `<img>` tags to load images directly from cloud storage
3. Pagination ensures only 5 blogs per page — smooth and fast

### Remaining Latency

The ~6s residual latency is an **infrastructure constraint**, not a code issue:  
Render backend (Oregon) ↔ UpCloud database (Singapore) — cross-region network delay.

---

## Running Locally

```bash
# Clone the repo
git clone https://github.com/moneshgomo/Blog.git

# Configure environment variables
application.properties
# Fill in DB credentials, JWT secret, cloud storage config

# Run with Docker
docker build -t dev16-blog .
docker run -p 8089:8089 dev16-blog

# Or run directly
./mvnw spring-boot:run
```

---

## What I Learned

- How BLOB storage in relational DBs kills API performance at scale
- Identifying bottlenecks post-launch and applying targeted architectural fixes
- FinOps  every infrastructure decision justified by cost and use case
- Cross-region latency as an infrastructure constraint vs code-level inefficiency
- End-to-end ownership: architecture → code → deployment → optimization

---

## Author

**Monesh D** · [moneshgomo](https://moneshgomo.netlify.app)
