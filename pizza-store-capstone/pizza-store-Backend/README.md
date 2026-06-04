# 🍕 Pizza Store — Capstone Backend (Spring Boot Microservices)

A complete, beginner-friendly backend for an online pizza store. Built with
**Spring Boot 3.3 + Java 17** in a **microservices architecture** using
**MySQL**, **Eureka**, **API Gateway**, **JWT security**, and **OpenFeign**.

Every requirement from the capstone brief is implemented:
- Customer: register, login, browse menu, place/cancel order, view bill, get status
- Admin: login, manage menu (CRUD), accept/reject orders, send messages, monthly revenue
- Microservice architecture with Eureka discovery and API Gateway
- Each service has its own MySQL database
- Separate Swagger UI per service
- JWT-based security in every protected service
- Centralized global exception handling in every service
- OpenFeign for inter-service communication

---

## 1. The 8 services

| # | Service | Port | What it does |
|---|---|---|---|
| 1 | **eureka-server** | 8761 | The "phone book" — every service registers here |
| 2 | **api-gateway** | 8080 | Single entry door — routes URLs to the right service |
| 3 | **user-service** | 8081 | Customer register / login / profile |
| 4 | **admin-service** | 8082 | Admin login + manage menu / orders / send messages |
| 5 | **menu-service** | 8083 | Menu CRUD + browsing (categories, bestsellers, search) |
| 6 | **order-service** | 8084 | Place / cancel orders, billing, monthly revenue |
| 7 | **payment-service** | 8085 | Process payments + list payment modes |
| 8 | **notification-service** | 8086 | Order-status messages + email (simulated) |

---

## 2. Prerequisites

- **JDK 17** or higher
- **Maven 3.9+**
- **MySQL 8** running on `localhost:3306`
- An IDE like **IntelliJ IDEA** or **Spring Tool Suite** (recommended)

### MySQL setup

You DO NOT need to create the databases by hand. The connection URL contains
`createDatabaseIfNotExist=true` — each service creates its own DB on first run.

But you DO need to make sure MySQL is running and the username/password match.
By default this project uses:

```
spring.datasource.username=root
spring.datasource.password=root
```

If your MySQL password is different (very common), open the
`src/main/resources/application.properties` file in each of these 6 services
and change `spring.datasource.password` to your password:
- user-service, admin-service, menu-service, order-service, payment-service, notification-service

The databases that will get auto-created:
- pizza_user_db
- pizza_admin_db
- pizza_menu_db
- pizza_order_db
- pizza_payment_db
- pizza_notification_db

---

## 3. How to run (very important — order matters)

Open 8 terminal windows (or 8 tabs in IntelliJ). In each one, go to the
service folder and run `mvn spring-boot:run`. Wait for one to fully start
before launching the next.

```
Order:
1. eureka-server   →  wait ~30 seconds, open http://localhost:8761
2. api-gateway     →  wait ~15 seconds
3. user-service    →  wait ~15 seconds
4. admin-service
5. menu-service
6. order-service
7. payment-service
8. notification-service
```

Services 3-8 can be started in any order, but eureka and api-gateway MUST come first.

Check the Eureka dashboard at `http://localhost:8761` — you should see all 6
business services listed (gateway + the 5 others — admin/menu/order/payment/notification + user).

---

## 4. Default admin login

A default admin is created automatically when admin-service starts the first time:

```
Email:    admin@pizzastore.com
Password: admin123
```

---

## 5. Swagger UI per service

Each service has its own Swagger page. Open them in a browser:

- user-service:          http://localhost:8081/swagger-ui.html
- admin-service:         http://localhost:8082/swagger-ui.html
- menu-service:          http://localhost:8083/swagger-ui.html
- order-service:         http://localhost:8084/swagger-ui.html
- payment-service:       http://localhost:8085/swagger-ui.html
- notification-service:  http://localhost:8086/swagger-ui.html

To test secured endpoints in Swagger:
1. Login first → copy the JWT `token` from the response
2. Click the green **Authorize** button at the top right
3. Paste the token (Swagger will add "Bearer " automatically)
4. Now you can call any endpoint

---

## 6. API endpoints (through the gateway at http://localhost:8080)

### Public — no token needed
| Method | URL | What it does |
|---|---|---|
| POST | `/api/users/register` | Customer signup |
| POST | `/api/users/login` | Customer login → JWT |
| POST | `/api/admins/login` | Admin login → JWT |
| GET | `/api/menu/items` | All menu items |
| GET | `/api/menu/categories` | List categories |
| GET | `/api/menu/items/category/PIZZA` | Items by category |
| GET | `/api/menu/bestsellers` | Bestsellers |
| GET | `/api/menu/new-launches` | New launches |
| GET | `/api/menu/search?keyword=paneer` | Search |
| GET | `/api/payments/modes` | List payment modes |

### Customer (needs Bearer token from login)
| Method | URL | What it does |
|---|---|---|
| GET | `/api/users/profile` | My profile |
| PUT | `/api/users/profile` | Update my profile |
| POST | `/api/orders` | Place an order |
| PUT | `/api/orders/{id}/cancel` | Cancel my order |
| GET | `/api/orders/user/{userId}` | My order history |
| GET | `/api/orders/user/{userId}/bill` | My bill |
| GET | `/api/notifications/user/{userId}` | My order status messages |

### Admin (needs admin Bearer token)
| Method | URL | What it does |
|---|---|---|
| POST | `/api/admins/menu/items` | Add menu item |
| PUT | `/api/admins/menu/items/{id}` | Edit menu item |
| DELETE | `/api/admins/menu/items/{id}` | Delete menu item |
| GET | `/api/admins/orders` | All orders |
| PUT | `/api/admins/orders/{id}/accept` | Accept an order |
| PUT | `/api/admins/orders/{id}/reject` | Reject an order |
| GET | `/api/admins/users/{userId}/bill` | Generate a user's bill |
| GET | `/api/admins/revenue?year=2026&month=5` | Monthly revenue |
| POST | `/api/admins/notify` | Send message to a user |

---

## 7. Sample full flow (curl)

```bash
# Register customer
curl -X POST http://localhost:8080/api/users/register \
  -H "Content-Type: application/json" \
  -d '{"fullName":"Aman","email":"aman@test.com","password":"secret123",
       "phone":"9999999999","address":"Rohtak"}'

# Login customer
curl -X POST http://localhost:8080/api/users/login \
  -H "Content-Type: application/json" \
  -d '{"email":"aman@test.com","password":"secret123"}'
# COPY the "token" from the response

# Browse menu (public)
curl http://localhost:8080/api/menu/items

# Place order (replace TOKEN with the customer token)
curl -X POST http://localhost:8080/api/orders \
  -H "Authorization: Bearer TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"items":[{"menuItemId":1,"quantity":2}],
       "paymentMode":"UPI","deliveryMode":"HOME_DELIVERY",
       "deliveryAddress":"Rohtak, Haryana"}'

# Admin login
curl -X POST http://localhost:8080/api/admins/login \
  -H "Content-Type: application/json" \
  -d '{"email":"admin@pizzastore.com","password":"admin123"}'

# Admin accepts order 1 (replace ADMIN_TOKEN)
curl -X PUT http://localhost:8080/api/admins/orders/1/accept \
  -H "Authorization: Bearer ADMIN_TOKEN"

# Admin views revenue
curl "http://localhost:8080/api/admins/revenue?year=2026&month=5" \
  -H "Authorization: Bearer ADMIN_TOKEN"
```

---

## 8. Where to read more

- `FOLDER_STRUCTURE.md` — full file tree
- `EXPLAIN_TO_MAAM.md` — module-by-module talking points (read this before submission!)

---

## 9. Notes on what's simulated

- **Payment** is simulated: online modes always return SUCCESS with a generated transaction id. COD stays PENDING until delivery.
- **Email notifications** are logged to the console, not actually sent. Swap the `log.info(...)` call in `NotificationService` for a real `JavaMailSender` to send real email.
