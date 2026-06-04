# 🍕 Pizza Store — Frontend

Simple HTML + CSS + JavaScript frontend that connects to your Spring Boot
microservices backend. Uses **Bootstrap 5** for clean UI.

## How to run

You just need to open the HTML files in a browser. But to avoid
CORS issues, use a simple HTTP server.

### Option 1 — VS Code Live Server (easiest)
1. Open the `pizza-store-frontend` folder in VS Code
2. Install the **Live Server** extension (by Ritwick Dey)
3. Right-click `index.html` → **Open with Live Server**
4. Browser opens at `http://127.0.0.1:5500/`

### Option 2 — Python (if installed)
```
cd pizza-store-frontend
python -m http.server 8000
```
Open browser at `http://localhost:8000`

### Option 3 — Just double-click index.html
Most things will work, but some browsers block local fetch calls.
The first two options are better.

---

## Folder structure

```
pizza-store-frontend/
├── index.html              ← Landing page (shows bestsellers)
├── register.html           ← Customer signup
├── login.html              ← Customer login
├── menu.html               ← Browse menu + add to cart
├── cart.html               ← Cart + place order
├── orders.html             ← My orders + cancel
├── profile.html            ← View/edit my profile
│
├── admin/
│   ├── login.html          ← Admin login
│   ├── dashboard.html      ← Admin home
│   ├── menu.html           ← Add/delete menu items
│   ├── orders.html         ← Accept/reject orders
│   └── revenue.html        ← Monthly revenue
│
├── css/
│   └── style.css           ← Custom pizza-themed styling
│
└── js/
    ├── api.js              ← ⭐ Shared file — all backend API calls
    ├── navbar.js           ← Builds nav bar based on login state
    ├── home.js             ← Home page logic
    ├── register.js         ← Registration form handler
    ├── login.js            ← Customer login handler
    ├── menu.js             ← Menu browsing logic
    ├── cart.js             ← Cart + checkout logic
    ├── orders.js           ← Order history logic
    ├── profile.js          ← Profile page logic
    ├── admin-login.js      ← Admin login handler
    ├── admin-menu.js       ← Admin menu management
    ├── admin-orders.js     ← Admin orders management
    └── admin-revenue.js    ← Revenue page logic
```

## Backend Connection

All API calls go to `http://localhost:9090` (your API Gateway).
This is set in `js/api.js` at the very top:

```javascript
const API_BASE = "http://localhost:9090";
```

If your gateway runs on a different port, just change this one line.

---

## Important: CORS

Your `api-gateway` already allows CORS from any origin
(`allowedOriginPatterns=*`), so the frontend can call it from any port.

---

## Default Logins

**Customer:** create a new one via the Register page
**Admin:** `admin@pizzastore.com` / `admin123` (auto-created by backend)

---

## Flow to test

1. Start the backend (all 8 services)
2. Open `index.html`
3. Click **Register** → create a customer account
4. Click **Menu** → browse items
5. Add items to cart → click **Cart** → place order
6. Go to **My Orders** → see your order
7. Open new tab → go to **Admin Login** → use default credentials
8. Open **Manage Menu** → add a new pizza
9. Open **Orders** → accept/reject the customer order
10. Open **Revenue** → see monthly revenue
