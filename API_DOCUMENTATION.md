# 📝 TokoHobby Blog API - Postman Collection

Complete API documentation dengan contoh request untuk testing di Postman.

**Base URL:** `http://localhost:8084` (local) atau `https://tokohobby.shop/api/blogs` (production)

---

## 🎯 Table of Contents

1. [Blogs API](#blogs-api)
2. [Categories API](#categories-api)
3. [Tags API](#tags-api)
4. [Authentication Notes](#authentication-notes)

---

## 📝 Blogs API

### 1. Get All Published Blogs (Public)

**GET** `/blogs`

**Query Parameters:**
- `page` (optional): Page number, default 0
- `size` (optional): Items per page, default 10
- `keyword` (optional): Search by title
- `categoryId` (optional): Filter by category

**Example Request:**
```http
GET http://localhost:8084/blogs?page=0&size=12&keyword=gaming&categoryId=2
```

**Success Response (200 OK):**
```json
{
  "content": [
    {
      "id": 1,
      "title": "Panduan Lengkap Gaming Setup untuk Pemula",
      "slug": "panduan-lengkap-gaming-setup-untuk-pemula",
      "content": "Ingin membangun gaming setup impian...",
      "status": "PUBLISHED",
      "authorId": "admin-user-001",
      "categoryName": "Gaming",
      "tags": ["Tutorial", "Gaming", "Pemula"],
      "imagePath": "/images/gaming-setup.jpg",
      "youtubeLink": null,
      "publishedAt": "2024-12-27T10:00:00",
      "createdAt": "2024-12-27T09:30:00",
      "updatedAt": "2024-12-27T09:30:00"
    }
  ],
  "pageNumber": 0,
  "pageSize": 12,
  "totalElements": 3,
  "totalPages": 1,
  "last": true
}
```

---

### 2. Get Blog by Slug (Public)

**GET** `/blogs/{slug}`

**Example Request:**
```http
GET http://localhost:8084/blogs/panduan-lengkap-gaming-setup-untuk-pemula
```

**Success Response (200 OK):**
```json
{
  "id": 1,
  "title": "Panduan Lengkap Gaming Setup untuk Pemula",
  "slug": "panduan-lengkap-gaming-setup-untuk-pemula",
  "content": "Full content here...",
  "status": "PUBLISHED",
  "authorId": "admin-user-001",
  "categoryId": 2,
  "categoryName": "Gaming",
  "tagIds": [1, 2, 9],
  "tags": ["Tutorial", "Gaming", "Pemula"],
  "imagePath": "/images/gaming-setup.jpg",
  "youtubeLink": null,
  "publishedAt": "2024-12-27T10:00:00",
  "createdAt": "2024-12-27T09:30:00",
  "updatedAt": "2024-12-27T09:30:00"
}
```

---

### 3. Create New Blog

**POST** `/blogs`

**Headers:**
```
Content-Type: application/json
```

**Request Body:**
```json
{
  "title": "Review Smartphone Gaming Terbaik 2024",
  "content": "Kami telah menguji berbagai smartphone gaming terbaru di tahun 2024. Dari performa, layar, baterai, hingga sistem pendingin...",
  "status": "PUBLISHED",
  "categoryId": 1,
  "tagIds": [3, 12, 13],
  "imagePath": "/images/smartphone-review.jpg",
  "youtubeLink": "https://www.youtube.com/watch?v=example"
}
```

**Field Descriptions:**
- `title` (required): Blog title
- `content` (required): Blog content (supports markdown/HTML)
- `status` (required): `DRAFT` or `PUBLISHED`
- `categoryId` (optional): Category ID
- `tagIds` (optional): Array of tag IDs
- `imagePath` (optional): Featured image path
- `youtubeLink` (optional): YouTube video URL

**Success Response (201 Created):**
```json
{
  "id": 4,
  "title": "Review Smartphone Gaming Terbaik 2024",
  "slug": "review-smartphone-gaming-terbaik-2024",
  "content": "Kami telah menguji...",
  "status": "PUBLISHED",
  "authorId": "default-author-001",
  "categoryId": 1,
  "categoryName": "Teknologi",
  "tagIds": [3, 12, 13],
  "tags": ["Review", "Premium", "Trending"],
  "imagePath": "/images/smartphone-review.jpg",
  "youtubeLink": "https://www.youtube.com/watch?v=example",
  "publishedAt": "2024-12-29T16:30:00",
  "createdAt": "2024-12-29T16:30:00",
  "updatedAt": "2024-12-29T16:30:00",
  "previewToken": null
}
```

**Create as Draft (with Preview Token):**
```json
{
  "title": "Draft Blog Post",
  "content": "This is a draft...",
  "status": "DRAFT",
  "categoryId": 2,
  "tagIds": [1, 2]
}
```

**Draft Response includes `previewToken`:**
```json
{
  "id": 5,
  "title": "Draft Blog Post",
  "slug": "draft-blog-post",
  "status": "DRAFT",
  "previewToken": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
  ...
}
```

---

### 4. Update Blog

**PUT** `/blogs/{id}`

**Headers:**
```
Content-Type: application/json
```

**Example Request:**
```http
PUT http://localhost:8084/blogs/4
```

**Request Body:**
```json
{
  "title": "Review Smartphone Gaming Terbaik 2024 (Updated)",
  "content": "Updated content with more details...",
  "status": "PUBLISHED",
  "categoryId": 1,
  "tagIds": [3, 12, 13, 14],
  "imagePath": "/images/smartphone-review-v2.jpg",
  "youtubeLink": "https://www.youtube.com/watch?v=updated"
}
```

**Success Response (200 OK):**
```json
{
  "id": 4,
  "title": "Review Smartphone Gaming Terbaik 2024 (Updated)",
  "slug": "review-smartphone-gaming-terbaik-2024",
  "content": "Updated content...",
  "status": "PUBLISHED",
  "updatedAt": "2024-12-29T17:00:00",
  ...
}
```

---

### 5. Delete Blog (Soft Delete)

**DELETE** `/blogs/{id}`

**Example Request:**
```http
DELETE http://localhost:8084/blogs/4
```

**Success Response (204 No Content)**

---

### 6. Preview Draft Blog

**GET** `/blogs/preview/{previewToken}`

**Example Request:**
```http
GET http://localhost:8084/blogs/preview/a1b2c3d4-e5f6-7890-abcd-ef1234567890
```

**Success Response (200 OK):**
```json
{
  "id": 5,
  "title": "Draft Blog Post",
  "slug": "draft-blog-post",
  "status": "DRAFT",
  "content": "Preview content...",
  "previewToken": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
  ...
}
```

---

### 7. Get All Blogs (Management - includes drafts)

**GET** `/blogs/manage`

**Query Parameters:**
- `page` (optional): Page number, default 0
- `size` (optional): Items per page, default 10

**Example Request:**
```http
GET http://localhost:8084/blogs/manage?page=0&size=10
```

**Success Response (200 OK):**
Returns all blogs including DRAFT status.

---

## 🏷️ Categories API

### 1. Get All Categories

**GET** `/categories`

**Example Request:**
```http
GET http://localhost:8084/categories
```

**Success Response (200 OK):**
```json
[
  {
    "id": 1,
    "name": "Technology",
    "slug": "technology"
  },
  {
    "id": 2,
    "name": "Gaming",
    "slug": "gaming"
  },
  {
    "id": 3,
    "name": "Reviews",
    "slug": "reviews"
  }
]
```

---

### 2. Get Category by ID

**GET** `/categories/{id}`

**Example Request:**
```http
GET http://localhost:8084/categories/2
```

**Success Response (200 OK):**
```json
{
  "id": 2,
  "name": "Gaming",
  "slug": "gaming"
}
```

---

### 3. Create Category

**POST** `/categories`

**Headers:**
```
Content-Type: application/json
```

**Request Body:**
```json
{
  "name": "Artificial Intelligence"
}
```

**Success Response (201 Created):**
```json
{
  "id": 11,
  "name": "Artificial Intelligence",
  "slug": "artificial-intelligence"
}
```

**Note:** Slug is auto-generated from name.

---

### 4. Update Category

**PUT** `/categories/{id}`

**Headers:**
```
Content-Type: application/json
```

**Example Request:**
```http
PUT http://localhost:8084/categories/11
```

**Request Body:**
```json
{
  "name": "AI & Machine Learning"
}
```

**Success Response (200 OK):**
```json
{
  "id": 11,
  "name": "AI & Machine Learning",
  "slug": "ai-machine-learning"
}
```

---

### 5. Delete Category

**DELETE** `/categories/{id}`

**Example Request:**
```http
DELETE http://localhost:8084/categories/11
```

**Success Response (204 No Content)**

**Note:** Tidak bisa delete category yang masih digunakan oleh blog.

---

## 🏷️ Tags API

### 1. Get All Tags

**GET** `/tags`

**Example Request:**
```http
GET http://localhost:8084/tags
```

**Success Response (200 OK):**
```json
[
  {
    "id": 1,
    "name": "DIY",
    "slug": "diy"
  },
  {
    "id": 2,
    "name": "Tutorial",
    "slug": "tutorial"
  },
  {
    "id": 3,
    "name": "Review",
    "slug": "review"
  }
]
```

---

### 2. Get Tag by ID

**GET** `/tags/{id}`

**Example Request:**
```http
GET http://localhost:8084/tags/3
```

**Success Response (200 OK):**
```json
{
  "id": 3,
  "name": "Review",
  "slug": "review"
}
```

---

### 3. Create Tag

**POST** `/tags`

**Headers:**
```
Content-Type: application/json
```

**Request Body:**
```json
{
  "name": "VR Technology"
}
```

**Success Response (201 Created):**
```json
{
  "id": 41,
  "name": "VR Technology",
  "slug": "vr-technology"
}
```

---

### 4. Update Tag

**PUT** `/tags/{id}`

**Headers:**
```
Content-Type: application/json
```

**Example Request:**
```http
PUT http://localhost:8084/tags/41
```

**Request Body:**
```json
{
  "name": "Virtual Reality"
}
```

**Success Response (200 OK):**
```json
{
  "id": 41,
  "name": "Virtual Reality",
  "slug": "virtual-reality"
}
```

---

### 5. Delete Tag

**DELETE** `/tags/{id}`

**Example Request:**
```http
DELETE http://localhost:8084/tags/41
```

**Success Response (204 No Content)**

---

## 🔐 Authentication Notes

### Current Implementation (Development)

Blog service saat ini menggunakan **fallback authentication** untuk development:

```java
// If no authentication, uses default author
authorId = "default-author-001"
```

### Production Implementation (TODO)

Untuk production, tambahkan headers:

```
Authorization: Bearer <jwt-token>
```

Authentication akan dihandle oleh `SecurityInterceptor` yang verify JWT token dari Accounts Service.

---

## 📊 Error Responses

### 400 Bad Request
```json
{
  "timestamp": "2024-12-29T16:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed: title is required"
}
```

### 404 Not Found
```json
{
  "timestamp": "2024-12-29T16:30:00",
  "status": 404,
  "error": "Not Found",
  "message": "Blog not found with slug: invalid-slug"
}
```

### 500 Internal Server Error
```json
{
  "timestamp": "2024-12-29T16:30:00",
  "status": 500,
  "error": "Internal Server Error",
  "message": "Failed to create blog: Database connection error"
}
```

---

## 🧪 Testing Tips

### 1. Import Postman Collection

Save API examples as JSON:

```json
{
  "info": {
    "name": "TokoHobby Blog API",
    "schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json"
  },
  "item": [...]
}
```

### 2. Environment Variables

Create Postman environment:

```json
{
  "base_url": "http://localhost:8084",
  "base_url_prod": "https://tokohobby.shop"
}
```

### 3. Test Workflow

1. **Get Categories & Tags** → Note IDs
2. **Create Draft Blog** → Get preview token
3. **Preview Draft** → Verify content
4. **Update to Published** → Change status
5. **Get Public Blogs** → Verify appears
6. **Delete Blog** → Cleanup

---

## 📝 Quick Test Examples

### Test 1: Create Simple Blog
```bash
curl -X POST http://localhost:8084/blogs \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Test Blog Post",
    "content": "This is a test blog content.",
    "status": "PUBLISHED",
    "categoryId": 1,
    "tagIds": [1, 2]
  }'
```

### Test 2: Search Blogs
```bash
curl "http://localhost:8084/blogs?keyword=gaming&page=0&size=10"
```

### Test 3: Get Categories
```bash
curl http://localhost:8084/categories
```

### Test 4: Create Tag
```bash
curl -X POST http://localhost:8084/tags \
  -H "Content-Type: application/json" \
  -d '{"name": "New Tag"}'
```

---

## ✅ Complete API Endpoint Summary

**Blogs:**
- `GET /blogs` - List published blogs (paginated, searchable)
- `GET /blogs/{slug}` - Get blog by slug
- `GET /blogs/manage` - List all blogs (including drafts)
- `GET /blogs/preview/{token}` - Preview draft blog
- `POST /blogs` - Create new blog
- `PUT /blogs/{id}` - Update blog
- `DELETE /blogs/{id}` - Soft delete blog

**Categories:**
- `GET /categories` - List all categories
- `GET /categories/{id}` - Get category by ID
- `POST /categories` - Create category
- `PUT /categories/{id}` - Update category
- `DELETE /categories/{id}` - Delete category

**Tags:**
- `GET /tags` - List all tags
- `GET /tags/{id}` - Get tag by ID
- `POST /tags` - Create tag
- `PUT /tags/{id}` - Update tag
- `DELETE /tags/{id}` - Delete tag

---

Happy Testing! 🚀
