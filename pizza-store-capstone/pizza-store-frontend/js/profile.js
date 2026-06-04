

document.addEventListener("DOMContentLoaded", async function () {
    if (!getToken()) {
        window.location.href = "../login.html";
        return;
    }

    // Load profile from backend
    try {
        const profile = await getUserProfile();   // from api.js
        document.getElementById("fullName").value = profile.fullName || "";
        document.getElementById("email").value = profile.email || "";
        document.getElementById("phone").value = profile.phone || "";
        document.getElementById("address").value = profile.address || "";
    } catch (err) {
        showAlert("Failed to load profile: " + err.message, "danger");
    }

    // Handle update form
    document.getElementById("profileForm").addEventListener("submit", async function (e) {
        e.preventDefault();
        const fullName = document.getElementById("fullName").value;
        const phone = document.getElementById("phone").value;
        const address = document.getElementById("address").value;

        try {
            await updateUserProfile(fullName, phone, address);
            showAlert("Profile updated successfully", "success");
        } catch (err) {
            showAlert("Update failed: " + err.message, "danger");
        }
    });
});
