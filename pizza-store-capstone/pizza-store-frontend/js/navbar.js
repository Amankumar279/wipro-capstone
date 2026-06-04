


document.addEventListener("DOMContentLoaded", function () {
    const navLinks = document.getElementById("navLinks");
    if (!navLinks) return;

    const user = getUser();   // from api.js
    const cartCount = getCartCount();

    const isInsideAdmin = window.location.pathname.includes("/admin/");
    const isInsideUser = window.location.pathname.includes("/user/");

    let rootPrefix = "";
    let adminPrefix = "admin/";
    let userPrefix = "user/";

    if (isInsideAdmin || isInsideUser) {
        rootPrefix = "../";
        adminPrefix = isInsideAdmin ? "" : "../admin/";
        userPrefix = isInsideUser ? "" : "../user/";
    }

    let html = `
        <li class="nav-item"><a class="nav-link" href="${rootPrefix}index.html">Home</a></li>
        <li class="nav-item"><a class="nav-link" href="${rootPrefix}menu.html">Menu</a></li>
        <li class="nav-item">
            <a class="nav-link" href="${userPrefix}cart.html">Cart 🛒
                <span class="badge bg-warning text-dark">${cartCount}</span>
            </a>
        </li>
    `;

    if (user) {
        // Logged-in customer: show order history and profile
        html += `
            <li class="nav-item"><a class="nav-link" href="${userPrefix}orders.html">My Orders</a></li>
            <li class="nav-item"><a class="nav-link" href="${userPrefix}profile.html">👤 ${user.fullName || user.email}</a></li>
            <li class="nav-item"><a class="nav-link" href="#" onclick="logout(); return false;">Logout</a></li>
        `;
    } else {
        // Not logged in: show login and register
        html += `
            <li class="nav-item"><a class="nav-link" href="${rootPrefix}login.html">Login</a></li>
            <li class="nav-item"><a class="nav-link" href="${rootPrefix}register.html">Register</a></li>
            <li class="nav-item"><a class="nav-link" href="${adminPrefix}login.html">Admin</a></li>
        `;
    }

    navLinks.innerHTML = html;

    // Highlight current page active link
    const currentPath = window.location.pathname.split("/").pop() || "index.html";
    const links = navLinks.querySelectorAll(".nav-link");
    links.forEach(link => {
        const href = link.getAttribute("href");
        const hrefBasename = href ? href.split("/").pop() : "";
        if (hrefBasename === currentPath) {
            link.classList.add("active");
        }
    });
});
