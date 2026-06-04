document.addEventListener("DOMContentLoaded", function () {
    loadAllItems();

    document.getElementById("categoryFilter").addEventListener("change", function () {
        const category = this.value;
        if (category) {
            loadByCategory(category);
        } else {
            loadAllItems();
        }
    });

    document.getElementById("searchBtn").addEventListener("click", function () {
        const keyword = document.getElementById("searchInput").value.trim();
        if (keyword) {
            doSearch(keyword);
        } else {
            loadAllItems();
        }
    });
});

async function loadAllItems() {
    try {
        const items = await getAllMenuItems();
        renderItems(items);
    } catch (err) { showError(err); }
}

async function loadByCategory(category) {
    try {
        const items = await getMenuByCategory(category);
        renderItems(items);
    } catch (err) { showError(err); }
}

async function doSearch(keyword) {
    try {
        const items = await searchMenu(keyword);
        renderItems(items);
    } catch (err) { showError(err); }
}

// ------- Render menu items with counter or Add button ----------------
function renderItems(items) {
    const container = document.getElementById("menuItems");

    if (!items || items.length === 0) {
        container.innerHTML = `<p class="text-muted">No items found.</p>`;
        return;
    }

    const cart = getCart();

    container.innerHTML = items.map(item => {
        const stock = item.stockQuantity == null ? 0 : item.stockQuantity;
        const isOutOfStock = stock === 0;
        const isLowStock = stock > 0 && stock < 5;
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
                                    border-radius: 6px; font-weight: 700; font-size: 1rem;
                                    letter-spacing: 0.5px; text-transform: uppercase;
                                    box-shadow: 0 4px 12px rgba(0,0,0,0.3);">
                            ❌ Out of Stock
                        </div>` : ''}
                </div>
                <div class="card-body">
                    <div class="d-flex justify-content-between align-items-start mb-2">
                        <h5 class="card-title">${item.name}</h5>
                        <div>
                            ${item.bestseller ? '<span class="badge badge-bestseller">Bestseller</span>' : ''}
                            ${item.newLaunch ? '<span class="badge badge-new">New</span>' : ''}
                        </div>
                    </div>
                    <p class="card-text small text-muted">${item.description || ''}</p>
                    <p class="small"><strong>Category:</strong> ${item.category}</p>
                    ${isLowStock ? `<p class="small text-warning fw-bold mb-2">⚠️ Only ${stock} left in stock!</p>` : ''}
                    <div class="d-flex justify-content-between align-items-center">
                        <span class="price">₹${item.price}</span>
                        <div id="control-${item.id}">
                            ${renderControl(item, cartQty, isOutOfStock)}
                        </div>
                    </div>
                </div>
            </div>
        </div>`;
    }).join("");
}

// ------- Render the button OR the counter (− 1 +) --------------------
function renderControl(item, cartQty, isOutOfStock) {
    if (isOutOfStock) {
        return `<button class="btn btn-secondary btn-sm" disabled>Unavailable</button>`;
    }
    if (cartQty === 0) {
        // Show Add to Cart button (first click)
        return `<button class="btn btn-primary btn-sm"
                        onclick='handleAddToCart(${JSON.stringify(item)})'>
                    Add to Cart 🛒
                </button>`;
    }
    // Show counter after first click
    return `
        <div class="d-flex align-items-center gap-2"
             style="border: 1.5px solid #dc2626; border-radius: 8px; padding: 3px; background: #fef2f2;">
            <button class="btn btn-sm btn-light fw-bold"
                    style="width: 28px; height: 28px; padding: 0;"
                    onclick='handleDecrease(${item.id})'>−</button>
            <span class="fw-bold text-danger" style="min-width: 22px; text-align: center;">${cartQty}</span>
            <button class="btn btn-sm btn-light fw-bold"
                    style="width: 28px; height: 28px; padding: 0;"
                    onclick='handleIncrease(${JSON.stringify(item)})'>+</button>
        </div>`;
}

// ------- First click: Add to cart with counter -----------------------
async function handleAddToCart(item) {
    // Fetch LATEST stock from database
    const liveItem = await getLiveItem(item.id);
    if (!liveItem) {
        showAlert("Could not check stock", "danger");
        return;
    }
    const liveStock = liveItem.stockQuantity == null ? 0 : liveItem.stockQuantity;

    if (liveStock === 0) {
        showAlert(`❌ ${item.name} is Out of Stock`, "danger");
        loadAllItems();   // refresh to show out of stock
        return;
    }

    addToCart(item);
    showAlert("Added to cart: " + item.name, "success");
    updateBadgeAndControl(item, 1);
}

// ------- Increase quantity with LIVE stock check ---------------------
async function handleIncrease(item) {
    const cart = getCart();
    const cartItem = cart.find(c => c.menuItemId === item.id);
    const currentQty = cartItem ? cartItem.quantity : 0;



    // LIVE stock check from backend
    const liveItem = await getLiveItem(item.id);
    if (!liveItem) {
        showAlert("Could not check stock", "danger");
        return;
    }
    const liveStock = liveItem.stockQuantity == null ? 0 : liveItem.stockQuantity;

    if (currentQty >= liveStock) {
        showStockToast(`❌ Out of stock! Only ${liveStock} available`);
        return;
    }

    updateCartQuantity(item.id, currentQty + 1);
    updateBadgeAndControl(item, currentQty + 1);
}

// ------- Decrease quantity -------------------------------------------
function handleDecrease(menuItemId) {
    const cart = getCart();
    const cartItem = cart.find(c => c.menuItemId === menuItemId);
    if (!cartItem) return;

    const newQty = cartItem.quantity - 1;
    updateCartQuantity(menuItemId, newQty);

    // Need the full item to re-render
    const itemDom = document.getElementById("control-" + menuItemId);
    if (newQty === 0) {
        // Re-fetch item to render the Add to Cart button properly
        getLiveItem(menuItemId).then(item => {
            if (item) {
                itemDom.innerHTML = renderControl(item, 0, false);
            }
        });
    } else {
        // Just update the number
        const span = itemDom.querySelector("span");
        if (span) span.textContent = newQty;
    }
    updateCartBadge();
}

// ------- Update badge in navbar and counter in card ------------------
function updateBadgeAndControl(item, newQty) {
    updateCartBadge();
    const dom = document.getElementById("control-" + item.id);
    if (dom) {
        dom.innerHTML = renderControl(item, newQty, false);
    }
}

function updateCartBadge() {
    document.querySelectorAll("#navLinks .badge")
        .forEach(b => b.textContent = getCartCount());
}

// ------- Get fresh item details from database ------------------------
async function getLiveItem(itemId) {
    try {
        const allItems = await getAllMenuItems();
        return allItems.find(m => m.id === itemId);
    } catch (err) {
        console.error("Could not fetch live stock:", err);
        return null;
    }
}

function showError(err) {
    document.getElementById("menuItems").innerHTML = `
        <div class="alert alert-warning">
            Could not load menu. Make sure your backend is running at
            <code>http://localhost:9090</code>.
        </div>`;
    console.error(err);
}

function showStockToast(msg) {
    // Remove any existing toast
    const existing = document.querySelector('.stock-toast');
    if (existing) existing.remove();

    const toast = document.createElement('div');
    toast.className = 'stock-toast';
    toast.style.cssText = `
        position: fixed;
        bottom: 30px;
        right: 30px;
        background: linear-gradient(135deg, #dc2626, #f97316);
        color: white;
        padding: 16px 28px;
        border-radius: 50px;
        font-weight: 700;
        font-size: 1rem;
        z-index: 9999;
        box-shadow: 0 15px 35px rgba(220, 38, 38, 0.4),
                    0 0 30px rgba(220, 38, 38, 0.3);
        font-family: 'Plus Jakarta Sans', sans-serif;
        animation: slideUp 0.4s ease-out;
    `;
    toast.textContent = msg;
    document.body.appendChild(toast);

    setTimeout(() => {
        toast.style.opacity = '0';
        toast.style.transition = 'opacity 0.5s';
    }, 2500);
    setTimeout(() => toast.remove(), 3000);
}
