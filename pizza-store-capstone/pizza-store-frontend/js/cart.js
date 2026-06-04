
document.addEventListener("DOMContentLoaded", async function () {
    await loadCartWithStock();

    document.getElementById("deliveryMode").addEventListener("change", function () {
        const addressBox = document.getElementById("addressBox");
        addressBox.style.display = (this.value === "TAKEAWAY") ? "none" : "block";
    });

    document.getElementById("placeOrderBtn").addEventListener("click", placeOrderNow);
});

// ------- Load cart and fetch fresh stock for all items ----------------
async function loadCartWithStock() {
    const cart = getCart();
    if (cart.length === 0) {
        renderCart([]);
        return;
    }

    try {
        const allMenuItems = await getAllMenuItems();
        const cartWithStock = cart.map(c => {
            const menuItem = allMenuItems.find(m => m.id === c.menuItemId);
            return {
                ...c,
                totalStock: menuItem ? (menuItem.stockQuantity || 0) : 0
            };
        });
        renderCart(cartWithStock);
    } catch (err) {
        console.warn("Could not fetch stock", err);
        renderCart(cart.map(c => ({ ...c, totalStock: '?' })));
    }
}

// ------- Render cart with REMAINING available stock -------------------
function renderCart(cartWithStock) {
    const container = document.getElementById("cartItems");

    if (!cartWithStock || cartWithStock.length === 0) {
        container.innerHTML = `
            <div class="alert alert-info">
                Your cart is empty. <a href="../menu.html">Browse menu</a>
            </div>`;
        document.getElementById("totalAmount").textContent = "0";
        document.getElementById("placeOrderBtn").disabled = true;
        return;
    }

    container.innerHTML = cartWithStock.map(c => {
        // Calculate REMAINING available (total stock minus cart quantity)
        const remaining = c.totalStock === '?' ? '?' : (c.totalStock - c.quantity);

        let stockText;
        if (remaining === '?') {
            stockText = `<small class="text-muted">Stock: checking...</small>`;
        } else if (remaining <= 0) {
            stockText = `<small style="color:#dc2626; font-weight:600;">❌ No more available — you have all ${c.totalStock} in cart</small>`;
        } else if (remaining < 5) {
            stockText = `<small style="color:#f59e0b; font-weight:600;">⚠️ Only ${remaining} more available</small>`;
        } else {
            stockText = `<small style="color:#10b981; font-weight:600;">✅ ${remaining} more available</small>`;
        }

        return `
        <div class="cart-item d-flex justify-content-between align-items-center mb-3 p-3"
             style="background: #fff; border-radius: 12px; box-shadow: 0 2px 6px rgba(0,0,0,0.06);">
            <div>
                <h6 class="cart-item-title mb-1">🍕 ${c.itemName}</h6>
                <small class="text-muted">₹${c.price} each</small><br>
                ${stockText}
            </div>
            <div class="d-flex align-items-center gap-3 flex-wrap">
                <div class="d-flex align-items-center gap-2"
                     style="border: 1.5px solid #dc2626; border-radius: 8px; padding: 3px; background: #fef2f2;">
                    <button class="btn btn-sm btn-light fw-bold"
                            style="width: 32px; height: 32px;"
                            onclick="cartDecrease(${c.menuItemId})">−</button>
                    <span class="fw-bold text-danger" style="min-width: 24px; text-align: center;">${c.quantity}</span>
                    <button class="btn btn-sm btn-light fw-bold"
                            style="width: 32px; height: 32px;"
                            onclick="cartIncrease(${c.menuItemId})">+</button>
                </div>
                <span><strong class="fs-5 text-primary">₹${(c.price * c.quantity).toFixed(2)}</strong></span>
                <button class="btn btn-sm btn-outline-danger"
                        onclick="cartRemove(${c.menuItemId})">🗑️</button>
            </div>
        </div>`;
    }).join("");

    document.getElementById("totalAmount").textContent = getCartTotal().toFixed(2);
}

async function cartIncrease(menuItemId) {
    const cart = getCart();
    const item = cart.find(c => c.menuItemId === menuItemId);
    if (!item) return;

    try {
        const allItems = await getAllMenuItems();
        const menuItem = allItems.find(m => m.id === menuItemId);
        const stock = menuItem && menuItem.stockQuantity != null ? menuItem.stockQuantity : 0;

        if (item.quantity >= stock) {
            showAlert(`❌ Out of stock! Only ${stock} available`, "danger");
            return;
        }
    } catch (err) {
        console.warn("Could not check stock", err);
    }

    updateCartQuantity(menuItemId, item.quantity + 1);
    await loadCartWithStock();
    updateCartBadge();
}

async function cartDecrease(menuItemId) {
    const cart = getCart();
    const item = cart.find(c => c.menuItemId === menuItemId);
    if (!item) return;
    updateCartQuantity(menuItemId, item.quantity - 1);
    await loadCartWithStock();
    updateCartBadge();
}

async function cartRemove(menuItemId) {
    removeFromCart(menuItemId);
    await loadCartWithStock();
    updateCartBadge();
}

function updateCartBadge() {
    document.querySelectorAll("#navLinks .badge")
        .forEach(b => b.textContent = getCartCount());
}

async function placeOrderNow() {
    if (!getToken()) {
        showAlert("Please login first to place an order", "warning");
        setTimeout(() => window.location.href = "../login.html", 1500);
        return;
    }

    const cart = getCart();
    if (cart.length === 0) {
        showAlert("Cart is empty", "warning");
        return;
    }

    const paymentMode = document.getElementById("paymentMode").value;
    const deliveryMode = document.getElementById("deliveryMode").value;
    const deliveryAddress = document.getElementById("address").value.trim();

    if (deliveryMode === "HOME_DELIVERY") {
        if (!deliveryAddress) {
            showAlert("⚠️ Please enter delivery address for Home Delivery", "warning");
            const addressInput = document.getElementById("address");
            addressInput.style.border = "2px solid #dc2626";
            addressInput.focus();
            setTimeout(() => addressInput.style.border = "", 3000);
            return;
        }
        if (deliveryAddress.length < 10) {
            showAlert("⚠️ Please enter a complete address (at least 10 characters)", "warning");
            const addressInput = document.getElementById("address");
            addressInput.style.border = "2px solid #dc2626";
            addressInput.focus();
            setTimeout(() => addressInput.style.border = "", 3000);
            return;
        }
    }

    const items = cart.map(c => ({
        menuItemId: c.menuItemId,
        quantity: c.quantity
    }));

    try {
        const order = await placeOrder(items, paymentMode, deliveryMode, deliveryAddress);
        clearCart();
        showAlert("Order placed successfully! Order ID: " + order.id, "success");
        setTimeout(() => window.location.href = "orders.html", 1500);
    } catch (err) {
        showAlert("Failed to place order: " + err.message, "danger");
    }
}