# Tokohobby Blogs Service 🚀

A high-performance **Spring Boot** backend service for the Tokohobby Blog platform. It manages blog posts, drafts, and media linking (YouTube & Images).

## 🛠️ Tech Stack
- **Java 17**
- **Spring Boot 3.x**
- **Spring Data JPA** (Repository Pattern)
- **H2 Database** (Development) / PostgreSQL (Production ready)
- **Lombok** (Boilerplate reduction)

## ✨ Key Features
- **RESTful API**: Endpoints for Public feed and Admin management.
- **Optimized Queries**: Uses JPA Projections for high-performance feed fetching.
- **Media Support**: Built-in fields for `youtubeLink` and `imagePath`.
- **Soft Delete**: Implemented ensuring data safety.

## 🏃‍♂️ How to Run

### Prerequisites
- JDK 17+
- Maven (wrapper included)

### Run Locally
```bash
./mvnw spring-boot:run
```
The server will start at `http://localhost:8084`.

## 🔌 API Endpoints

### Public
- `GET /blogs`: Fetch paginated, optimized blog feed (supports `keyword` search).
- `GET /blogs/{slug}`: Fetch full blog detail.

### Admin
- `POST /admin/blogs`: Create a new blog/draft.
- `GET /admin/blogs`: Fetch all blogs (including drafts).
- `PUT /admin/blogs/{id}`: Update blog.
- `DELETE /admin/blogs/{id}`: Soft delete blog.
