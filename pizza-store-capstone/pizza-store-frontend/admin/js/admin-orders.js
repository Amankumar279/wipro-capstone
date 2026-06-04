// =====================================================================
// admin-orders.js — Admin views all orders, accepts/rejects, sees address
// =====================================================================

document.addEventListener("DOMContentLoaded", function () {
    if (!getToken()) {
        window.location.href = "login.html";
        return;
    }

    loadOrders("");

    document.getElementById("statusFilter").addEventListener("change", function () {
        loadOrders(this.value);
    });
});

async function loadOrders(status) {
    try {
        const orders = (await adminGetAllOrders(status));
        const container = document.getElementById("ordersList");

        if (!orders || orders.length === 0) {
            container.innerHTML = `<p class="text-muted">No orders found.</p>`;
            return;
        }

        container.innerHTML = orders.map(o => {
            // Format delivery info nicely
            const deliveryHtml = o.deliveryMode === 'HOME_DELIVERY'
                ? `<div class="alert alert-info mt-2 mb-3 p-2 small" style="border-radius:8px;">
                       <strong>📍 Delivery Address:</strong><br>
                       <span class="text-dark">${o.deliveryAddress || 'No address provided'}</span>
                   </div>`
                : `<div class="alert alert-warning mt-2 mb-3 p-2 small" style="border-radius:8px;">
                       <strong>🛍️ Takeaway:</strong> Customer will pick up
                   </div>`;

            return `
            <div class="card mb-4 border-0 shadow-sm overflow-hidden" style="border-radius: var(--radius-md); background: #ffffff;">
                <div class="card-header d-flex justify-content-between align-items-center py-3" style="background: rgba(225, 29, 72, 0.02); border-bottom: 1px solid rgba(0, 0, 0, 0.05);">
                    <span class="fw-bold text-main">🛍️ Order #${o.id} — User ${o.userId}</span>
                    <span class="status-${o.status}">${o.status}</span>
                </div>
                <div class="card-body p-4">
                    <div class="row">
                        <div class="col-md-7">
                            <p class="small text-muted mb-2">
                                📧 Email: <strong>${o.customerEmail || 'N/A'}</strong>
                            </p>
                            <p class="small text-muted mb-3">
                                📅 Placed: ${new Date(o.createdAt).toLocaleString()}
                            </p>
                            ${deliveryHtml}
                            <h6 class="mb-2 text-main fw-bold">Items Ordered:</h6>
                            <ul class="mb-0 px-2" style="list-style-type: none;">
                                ${o.items.map(i => `<li class="mb-1">🍕 <span class="fw-semibold text-dark">${i.itemName}</span> <span class="text-muted">× ${i.quantity}</span> = <strong class="text-primary">₹${i.subtotal.toFixed(2)}</strong></li>`).join("")}
                            </ul>
                        </div>
                        <div class="col-md-5 text-md-end d-flex flex-column justify-content-between align-items-md-end mt-3 mt-md-0">
                            <div class="mb-2">
                                <p class="text-muted small mb-0">Total Amount</p>
                                <h3 class="fw-extrabold text-primary mb-2">₹${o.totalAmount.toFixed(2)}</h3>
                            </div>
                            <div class="small mb-3 text-muted">
                                <span class="d-block">💳 Payment: <strong>${o.paymentMode}</strong> (${o.paymentStatus})</span>
                                <span class="d-block">🛵 Delivery: <strong>${o.deliveryMode.replace('_', ' ')}</strong></span>
                            </div>
                            <div class="d-flex gap-2 flex-wrap justify-content-md-end w-100">
                                ${o.status === 'PLACED' ? `
                                    <button class="btn btn-sm btn-success px-3 py-2 fw-bold" onclick="acceptOrder(${o.id})">✓ Accept</button>
                                    <button class="btn btn-sm btn-danger px-3 py-2 fw-bold" onclick="rejectOrder(${o.id})">✗ Reject</button>
                                ` : ''}
                                ${(o.status === 'ACCEPTED' || o.status === 'PREPARING' || o.status === 'OUT_FOR_DELIVERY')
                    ? `<select class="form-select form-select-sm w-auto py-2 px-3 fw-bold"
                                               onchange="updateStatus(${o.id}, this.value)" style="border-radius: var(--radius-sm);">
                                        <option value="">⚙️ Update Status...</option>
                                        <option value="PREPARING">Preparing</option>
                                        ${o.deliveryMode === 'HOME_DELIVERY'
                        ? `<option value="OUT_FOR_DELIVERY">Out for Delivery</option>`
                        : ''}
                                        <option value="DELIVERED">Delivered</option>
                                      </select>`
                    : ''}
                            </div>
                        </div>
                    </div>
                </div>
            </div>`;
        }).join("");
    } catch (err) {
        showAlert("Failed to load: " + err.message, "danger");
    }
}

async function acceptOrder(id) {
    try {
        await adminAcceptOrder(id);
        showAlert("Order #" + id + " accepted ✓", "success");
        loadOrders(document.getElementById("statusFilter").value);
    } catch (err) {
        showAlert("Failed: " + err.message, "danger");
    }
}

async function rejectOrder(id) {
    if (!confirm("Reject order #" + id + "?")) return;
    try {
        await adminRejectOrder(id);
        showAlert("Order #" + id + " rejected", "warning");
        loadOrders(document.getElementById("statusFilter").value);
    } catch (err) {
        showAlert("Failed: " + err.message, "danger");
    }
}

async function updateStatus(id, status) {
    if (!status) return;
    try {
        await adminUpdateOrderStatus(id, status);
        showAlert("Status updated to " + status, "success");
        loadOrders(document.getElementById("statusFilter").value);
    } catch (err) {
        showAlert("Failed: " + err.message, "danger");
    }
}
