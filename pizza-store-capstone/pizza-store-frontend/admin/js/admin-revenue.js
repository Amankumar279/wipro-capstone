

document.addEventListener("DOMContentLoaded", function () {
    if (!getToken()) {
        window.location.href = "login.html";
        return;
    }

    document.getElementById("revenueForm").addEventListener("submit", async function (e) {
        e.preventDefault();

        const year = parseInt(document.getElementById("year").value);
        const month = parseInt(document.getElementById("month").value);

        try {
            const revenue = await adminGetRevenue(year, month);
            document.getElementById("revenueResult").innerHTML = `
                <div class="card border-0 shadow-sm overflow-hidden" style="border-radius: var(--radius-md); background: #ffffff;">
                    <div class="card-header py-3" style="background: rgba(225, 29, 72, 0.02); border-bottom: 1px solid rgba(0, 0, 0, 0.05);">
                        <h5 class="fw-bold mb-0 text-main">📊 Revenue Report Summary</h5>
                    </div>
                    <div class="card-body p-4">
                        <div class="row align-items-center">
                            <div class="col-md-6 mb-3 mb-md-0">
                                <h6 class="text-muted small text-uppercase fw-bold mb-2">Billing Period</h6>
                                <h4 class="fw-bold text-dark mb-3">🗓️ ${getMonthName(revenue.month)} ${revenue.year}</h4>
                                <p class="mb-0 text-muted">Total orders successfully processed: <strong class="text-dark">${revenue.totalOrders} orders</strong></p>
                            </div>
                            <div class="col-md-6 text-md-end">
                                <h6 class="text-muted small text-uppercase fw-bold mb-1">Total Earned Revenue</h6>
                                <h1 class="fw-extrabold text-success mb-0" style="font-size: 3rem;">₹${revenue.totalRevenue.toFixed(2)}</h1>
                            </div>
                        </div>
                    </div>
                </div>
            `;
        } catch (err) {
            showAlert("Failed: " + err.message, "danger");
        }
    });
});

function getMonthName(m) {
    return ["", "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"][m];
}
