

document.addEventListener("DOMContentLoaded", async function () {
    const container = document.getElementById("bestsellers");

    try {
        const items = await getBestsellers();   // from api.js

        if (!items || items.length === 0) {
            container.innerHTML = `
                <p class="text-muted">
                    No bestsellers yet. Admin can mark items as bestsellers.
                </p>`;
            return;
        }

        const cart = getCart();

        container.innerHTML = items.map(item => {
            const stock = item.stockQuantity == null ? 0 : item.stockQuantity;
            const isOutOfStock = stock === 0;
            const inCart = cart.find(c => c.menuItemId === item.id);
            const cartQty = inCart ? inCart.quantity : 0;

            return `
            <div class="col-md-4">
                <div class="card menu-card ${isOutOfStock ? 'out-of-stock' : ''}">
                    <div style="position: relative;">
                        <img src="${item.imageUrl || 'https://placehold.co/400x200?text=Pizza'}"
                             class="card-img-top" alt="${item.name}"
                             style="${isOutOfStock ? 'opacity: 0.5; filter: grayscale(70%);' : ''}">
                        ${isOutOfStock ? `
                            <div style="position: absolute; top: 50%; left: 50%; transform: translate(-50%, -50%);
                                        background: rgba(220, 38, 38, 0.95); color: white; padding: 8px 20px;
                                        border-radius: 6px; font-weight: 700;">
                                ❌ Out of Stock
                            </div>` : ''}
                    </div>
                    <div class="card-body">
                        <h5 class="card-title">${item.name}</h5>
                        <p class="card-text small">${item.description || ''}</p>
                        <div class="d-flex justify-content-between align-items-center">
                            <span class="price">₹${item.price}</span>
                            <div id="home-control-${item.id}">
                                ${renderHomeControl(item, cartQty, isOutOfStock)}
                            </div>
                        </div>
                    </div>
                </div>
            </div>`;
        }).join("");

    } catch (err) {
        container.innerHTML = `
            <div class="alert alert-warning">
                Could not load menu. Make sure the backend is running on
                http://localhost:9090 (API Gateway).
            </div>`;
        console.error(err);
    }
});

function renderHomeControl(item, cartQty, isOutOfStock) {
    if (isOutOfStock) {
        return `<button class="btn btn-secondary btn-sm" disabled>Unavailable</button>`;
    }
    if (cartQty === 0) {
        return `<button class="btn btn-primary btn-sm"
                        onclick='homeAddToCart(${JSON.stringify(item)})'>
                    Add to Cart
                </button>`;
    }
    return `
        <div class="d-flex align-items-center gap-2"
             style="border: 1.5px solid #dc2626; border-radius: 8px; padding: 3px; background: #fef2f2;">
            <button class="btn btn-sm btn-light fw-bold"
                    style="width: 28px; height: 28px; padding: 0;"
                    onclick='homeDecrease(${item.id})'>−</button>
            <span class="fw-bold text-danger" style="min-width: 22px; text-align: center;">${cartQty}</span>
            <button class="btn btn-sm btn-light fw-bold"
                    style="width: 28px; height: 28px; padding: 0;"
                    onclick='homeIncrease(${JSON.stringify(item)})'>+</button>
        </div>`;
}

async function homeAddToCart(item) {
    const liveItem = await getHomeLiveItem(item.id);
    if (!liveItem) {
        alert("Could not check stock");
        return;
    }
    const liveStock = liveItem.stockQuantity == null ? 0 : liveItem.stockQuantity;

    if (liveStock === 0) {
        alert("❌ " + item.name + " is Out of Stock");
        location.reload();
        return;
    }

    addToCart(item);
    showHomeToast("✅ Added to cart: " + item.name);
    updateHomeBadgeAndControl(item, 1);
}

async function homeIncrease(item) {
    const cart = getCart();
    const cartItem = cart.find(c => c.menuItemId === item.id);
    const currentQty = cartItem ? cartItem.quantity : 0;


    const liveItem = await getHomeLiveItem(item.id);
    if (!liveItem) return;
    const liveStock = liveItem.stockQuantity == null ? 0 : liveItem.stockQuantity;

    if (currentQty >= liveStock) {
        showHomeToast("❌ Out of stock! Only " + liveStock + " available");
        return;
    }

    updateCartQuantity(item.id, currentQty + 1);
    updateHomeBadgeAndControl(item, currentQty + 1);
}

function homeDecrease(menuItemId) {
    const cart = getCart();
    const cartItem = cart.find(c => c.menuItemId === menuItemId);
    if (!cartItem) return;

    const newQty = cartItem.quantity - 1;
    updateCartQuantity(menuItemId, newQty);

    const dom = document.getElementById("home-control-" + menuItemId);
    if (newQty === 0) {
        getHomeLiveItem(menuItemId).then(item => {
            if (item) dom.innerHTML = renderHomeControl(item, 0, false);
        });
    } else {
        const span = dom.querySelector("span");
        if (span) span.textContent = newQty;
    }
    document.querySelectorAll("#navLinks .badge").forEach(b => b.textContent = getCartCount());
}

function updateHomeBadgeAndControl(item, newQty) {
    document.querySelectorAll("#navLinks .badge").forEach(b => b.textContent = getCartCount());
    const dom = document.getElementById("home-control-" + item.id);
    if (dom) dom.innerHTML = renderHomeControl(item, newQty, false);
}

async function getHomeLiveItem(itemId) {
    try {
        const items = await getAllMenuItems();
        return items.find(m => m.id === itemId);
    } catch (err) {
        return null;
    }
}

function showHomeToast(msg) {
    const toast = document.createElement('div');
    toast.style.cssText = 'position:fixed;bottom:30px;right:30px;background:linear-gradient(135deg,#e11d48,#f97316);color:white;padding:16px 28px;border-radius:16px;font-weight:700;font-size:0.95rem;z-index:9999;box-shadow:0 15px 35px rgba(225,29,72,0.3);font-family:Plus Jakarta Sans,sans-serif;';
    toast.textContent = msg;
    document.body.appendChild(toast);
    setTimeout(() => { toast.style.opacity = '0'; toast.style.transition = 'opacity 0.4s'; }, 2000);
    setTimeout(() => toast.remove(), 2500);
}
