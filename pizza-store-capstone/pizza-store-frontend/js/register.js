

document.getElementById("registerForm").addEventListener("submit", async function (e) {
    e.preventDefault();   // stop the page from reloading

    // 1. Read values from the form
    const fullName = document.getElementById("fullName").value;
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;
    const phone = document.getElementById("phone").value;
    const address = document.getElementById("address").value;

    try {
        // 2. Call backend (user-service via API Gateway)
        const response = await registerUser(fullName, email, password, phone, address);

        // 3. Backend returns JWT token + user info — save it
        saveToken(response.token, {
            userId: response.userId,
            email: response.email,
            fullName: response.fullName,
            role: response.role
        });

        // 4. Show success and redirect to menu
        showAlert("Account created successfully! Redirecting...", "success");
        setTimeout(() => window.location.href = "menu.html", 1500);

    } catch (err) {
        showAlert("Registration failed: " + err.message, "danger");
    }
});
