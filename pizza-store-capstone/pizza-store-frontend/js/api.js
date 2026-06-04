
const API_BASE = "http://localhost:9090";

// ----------- Token storage (saves JWT in browser's localStorage) ------

function saveToken(token, user) {
    localStorage.setItem("token", token);
    localStorage.setItem("user", JSON.stringify(user));
}

function getToken() {
    return localStorage.getItem("token");
}

function getUser() {
    const u = localStorage.getItem("user");
    return u ? JSON.parse(u) : null;
}

function logout(redirectTo) {
    localStorage.removeItem("token");
    localStorage.removeItem("user");
    if (redirectTo) {
        window.location.href = redirectTo;
    } else {
        const path = window.location.pathname;
        if (path.includes("/admin/") || path.includes("/user/")) {
            window.location.href = "../index.html";
        } else {
            window.location.href = "index.html";
        }
    }
}

// ----------- Headers helper -------------------------------------------

function getHeaders(withAuth) {
    const headers = { "Content-Type": "application/json" };
    if (withAuth) {
        const token = getToken();
        if (token) headers["Authorization"] = "Bearer " + token;
    }
    return headers;
}

// ----------- Generic API call function --------------------------------
// All other functions use this. It does:
//   1. Build the URL
//   2. Add JWT token in header if needed
//   3. Send request to backend
//   4. Return JSON response

async function apiCall(method, path, body, withAuth) {

    // request configuration
    const options = {
        method: method,
        headers: getHeaders(withAuth)
    };
    if (body) options.body = JSON.stringify(body);

    const response = await fetch(API_BASE + path, options);
    const data = await response.json().catch(() => ({}));

    if (!response.ok) {
        throw new Error(data.message || "Request failed");
    }
    return data;
}


// USER (CUSTOMER) APIs


async function registerUser(fullName, email, password, phone, address) {
    return apiCall("POST", "/api/users/register", {
        fullName, email, password, phone, address
    }, false);
}

async function loginUser(email, password) {
    return apiCall("POST", "/api/users/login", { email, password }, false);
}

async function getUserProfile() {
    return apiCall("GET", "/api/users/profile", null, true);
}

async function updateUserProfile(fullName, phone, address) {
    return apiCall("PUT", "/api/users/profile", { fullName, phone, address }, true);
}

// =====================================================================
// ADMIN APIs
// =====================================================================

async function loginAdmin(email, password) {
    return apiCall("POST", "/api/admins/login", { email, password }, false);
}

async function adminCreateMenuItem(item) {
    return apiCall("POST", "/api/admins/menu/items", item, true);
}

async function adminUpdateMenuItem(id, item) {
    return apiCall("PUT", "/api/admins/menu/items/" + id, item, true);
}

async function adminDeleteMenuItem(id) {
    return apiCall("DELETE", "/api/admins/menu/items/" + id, null, true);
}

async function adminGetAllOrders(status) {
    const q = status ? "?status=" + status : "";
    return apiCall("GET", "/api/admins/orders" + q, null, true);
}

async function adminAcceptOrder(id) {
    return apiCall("PUT", "/api/admins/orders/" + id + "/accept", null, true);
}

async function adminRejectOrder(id) {
    return apiCall("PUT", "/api/admins/orders/" + id + "/reject", null, true);
}

async function adminUpdateOrderStatus(id, status) {
    return apiCall("PUT", "/api/admins/orders/" + id + "/status", { status }, true);
}

async function adminGetRevenue(year, month) {
    return apiCall("GET", "/api/admins/revenue?year=" + year + "&month=" + month, null, true);
}

async function adminGetUserBill(userId) {
    return apiCall("GET", "/api/admins/users/" + userId + "/bill", null, true);
}

async function adminSendMessage(userId, orderId, email, message) {
    return apiCall("POST", "/api/admins/notify", { userId, orderId, email, message }, true);
}

// =====================================================================
// MENU APIs (public - no token needed for browsing)
// =====================================================================

async function getAllMenuItems() {
    return apiCall("GET", "/api/menu/items", null, false);
}

async function getMenuByCategory(category) {
    return apiCall("GET", "/api/menu/items/category/" + category, null, false);
}

async function getBestsellers() {
    return apiCall("GET", "/api/menu/bestsellers", null, false);
}

async function getNewLaunches() {
    return apiCall("GET", "/api/menu/new-launches", null, false);
}

async function searchMenu(keyword) {
    return apiCall("GET", "/api/menu/search?keyword=" + encodeURIComponent(keyword), null, false);
}

async function getCategories() {
    return apiCall("GET", "/api/menu/categories", null, false);
}

// =====================================================================
// ORDER APIs (needs token)
// =====================================================================

async function placeOrder(items, paymentMode, deliveryMode, deliveryAddress) {
    return apiCall("POST", "/api/orders", {
        items, paymentMode, deliveryMode, deliveryAddress
    }, true);
}

async function cancelOrder(orderId) {
    return apiCall("PUT", "/api/orders/" + orderId + "/cancel", null, true);
}

async function getMyOrders() {
    const user = getUser();
    return apiCall("GET", "/api/orders/user/" + user.userId, null, true);
}

async function getMyBill() {
    const user = getUser();
    return apiCall("GET", "/api/orders/user/" + user.userId + "/bill", null, true);
}

async function getOrderById(orderId) {
    return apiCall("GET", "/api/orders/" + orderId, null, true);
}

// =====================================================================
// PAYMENT APIs
// =====================================================================

async function getPaymentModes() {
    return apiCall("GET", "/api/payments/modes", null, false);
}

// =====================================================================
// NOTIFICATION APIs
// =====================================================================

async function getMyNotifications() {
    const user = getUser();
    return apiCall("GET", "/api/notifications/user/" + user.userId, null, true);
}

// =====================================================================
// HELPER — show alert messages
// =====================================================================

function showAlert(message, type) {
    type = type || "success";   // success / danger / warning / info
    const alertBox = document.getElementById("alertBox");
    if (alertBox) {
        alertBox.innerHTML = `
            <div class="alert alert-${type} alert-dismissible fade show" role="alert">
                ${message}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>`;
        setTimeout(() => { alertBox.innerHTML = ""; }, 4000);
    } else {
        alert(message);
    }
}

// =====================================================================
// CART HELPERS (cart is saved in browser's localStorage)
// =====================================================================

function getCart() {
    const c = localStorage.getItem("cart");
    return c ? JSON.parse(c) : [];
}

function saveCart(cart) {
    localStorage.setItem("cart", JSON.stringify(cart));
}

function addToCart(menuItem) {
    const cart = getCart();
    const existing = cart.find(c => c.menuItemId === menuItem.id);
    if (existing) {
        existing.quantity += 1;
    } else {
        cart.push({
            menuItemId: menuItem.id,
            itemName: menuItem.name,
            price: menuItem.price,
            quantity: 1
        });
    }
    saveCart(cart);
}

function removeFromCart(menuItemId) {
    let cart = getCart();
    cart = cart.filter(c => c.menuItemId !== menuItemId);
    saveCart(cart);
}

function clearCart() {
    localStorage.removeItem("cart");
}

function getCartTotal() {
    return getCart().reduce((sum, c) => sum + (c.price * c.quantity), 0);
}

function getCartCount() {
    return getCart().reduce((sum, c) => sum + c.quantity, 0);
}

// =====================================================================
// Stock & quantity helpers
// =====================================================================

const MAX_QTY_PER_ITEM = 10;

/** Update quantity of an item already in cart. */
function updateCartQuantity(menuItemId, newQuantity) {
    const cart = getCart();
    const item = cart.find(c => c.menuItemId === menuItemId);
    if (item) {
        if (newQuantity <= 0) {
            removeFromCart(menuItemId);
        } else {
            item.quantity = newQuantity;
            saveCart(cart);
        }
    }
}
