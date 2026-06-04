
document.getElementById("loginForm").addEventListener("submit", async function (e) {
    e.preventDefault();

    // 1. Read email and password from form
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    try {
        // 2. Call backend - user-service login API
        const response = await loginUser(email, password);

        // 3. Save JWT token to localStorage so it persists across pages
        saveToken(response.token, {
            userId: response.userId,
            email: response.email,
            fullName: response.fullName,
            role: response.role
        });

        // 4. Redirect to menu
        showAlert("Login successful! Redirecting...", "success");
        setTimeout(() => window.location.href = "menu.html", 1000);

    } catch (err) {
        showAlert("Login failed: " + err.message, "danger");
    }
});
