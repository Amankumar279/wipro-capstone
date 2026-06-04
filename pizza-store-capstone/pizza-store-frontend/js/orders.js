

document.addEventListener("DOMContentLoaded", async function () {
    // 1. Make sure user is logged in
    if (!getToken()) {
        showAlert("Please login first", "warning");
        setTimeout(() => window.location.href = "../login.html", 1500);
        return;
    }

    await loadMyOrders();
    await loadMyBill();
});

// ------- Fetch and display all orders ----------------------------------
async function loadMyOrders() {
    try {
        const orders = await getMyOrders();    // from api.js
        const container = document.getElementById("ordersList");

        if (orders.length === 0) {
            container.innerHTML = `
                <div class="alert alert-info">
                    You have no orders yet. <a href="../menu.html">Order something!</a>
                </div>`;
            return;
        }

        container.innerHTML = orders.map(o => renderOrderCard(o)).join("");
    } catch (err) {
        showAlert("Failed to load orders: " + err.message, "danger");
    }
}

// ------- Total spent --------------------------------------------------
async function loadMyBill() {
    try {
        const bill = await getMyBill();
        document.getElementById("grandTotal").textContent = bill.grandTotal;
        document.getElementById("orderCount").textContent = bill.totalOrders;
    } catch (err) {
        console.warn("Could not load bill:", err);
    }
}

// ------- Build the HTML card for one order -----------------------------
function renderOrderCard(o) {

    // Shows item name, quantity badge, unit price and subtotal
    const itemsHtml = o.items.map(i =>
        `<li class="mb-1">
            🍕 <span class="fw-semibold text-dark">${i.itemName}</span>
            <span class="badge bg-secondary ms-1">Qty: ${i.quantity}</span>
            <span class="text-muted small"> × ₹${i.unitPrice} each = </span>
            <strong class="text-primary">₹${i.subtotal.toFixed(2)}</strong>
        </li>`
    ).join("");

    // Can only cancel if not delivered/out for delivery/cancelled/rejected
    const cancellable = !["DELIVERED", "OUT_FOR_DELIVERY", "CANCELLED", "REJECTED"]
        .includes(o.status);

    return `
        <div class="card mb-4 border-0 shadow-sm overflow-hidden" style="border-radius: var(--radius-md); background: #ffffff;">
            <div class="card-header d-flex justify-content-between align-items-center py-3" style="background: rgba(225, 29, 72, 0.02); border-bottom: 1px solid rgba(0, 0, 0, 0.05);">
                <span class="fw-bold text-main">🛍️ Order #${o.id}</span>
                <span class="status-${o.status}">${o.status}</span>
            </div>
            <div class="card-body p-4">
                <div class="row">
                    <div class="col-md-7">
                        <p class="small text-muted mb-3">
                            📅 Placed: ${new Date(o.createdAt).toLocaleString()}
                        </p>
                        <h6 class="mb-2 text-main fw-bold">Items Ordered:</h6>
                        <ul class="mb-0 px-2" style="list-style-type: none;">
                            ${itemsHtml}
                        </ul>
                    </div>
                    <div class="col-md-5 text-md-end d-flex flex-column justify-content-between align-items-md-end mt-3 mt-md-0">
                        <div class="mb-2">
                            <p class="text-muted small mb-0">Total Paid/Amount</p>
                            <h3 class="fw-extrabold text-primary mb-2">₹${o.totalAmount.toFixed(2)}</h3>
                        </div>
                        <div class="small mb-3 text-muted">
                            <span class="d-block">💳 Payment: <strong>${o.paymentMode}</strong> (${o.paymentStatus})</span>
                            <span class="d-block">🛵 Delivery: <strong>${o.deliveryMode.replace('_', ' ')}</strong></span>
                        </div>
                        ${cancellable
            ? `<button class="btn btn-sm btn-danger px-4 py-2"
                                    onclick="cancelMyOrder(${o.id})">❌ Cancel Order</button>`
            : ''}
                    </div>
                </div>
            </div>
        </div>
    `;
}

async function cancelMyOrder(id) {
    if (!confirm("Cancel order #" + id + "?")) return;
    try {
        await cancelOrder(id);                  // from api.js
        showAlert("Order cancelled successfully", "success");
        loadMyOrders();
    } catch (err) {
        showAlert("Failed: " + err.message, "danger");
    }
}