// =====================================================================
// admin-login.js — Admin login
// =====================================================================

document.getElementById("adminLoginForm").addEventListener("submit", async function (e) {
    e.preventDefault();

    const email    = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    try {
        // Call admin-service login
        const response = await loginAdmin(email, password);

        // Save token + admin info
        saveToken(response.token, {
            userId:   response.adminId,
            email:    response.email,
            fullName: response.name,
            role:     response.role
        });

        showAlert("Login successful! Redirecting to dashboard...", "success");
        setTimeout(() => window.location.href = "dashboard.html", 1000);

    } catch (err) {
        showAlert("Login failed: " + err.message, "danger");
    }
});
