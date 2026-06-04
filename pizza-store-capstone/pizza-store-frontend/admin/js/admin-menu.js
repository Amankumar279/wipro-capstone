// =====================================================================
// admin-menu.js — Admin can add, view, edit, delete menu items
// =====================================================================

let editModal;

document.addEventListener("DOMContentLoaded", function () {


    if (!getToken()) {
        window.location.href = "login.html";
        return;
    }

    loadItems();

    document.getElementById("name").addEventListener("blur", async function () {

        const typedName = this.value.trim().toLowerCase();
        if (!typedName) return;

        try {
            const items = await getAllMenuItems();

            const exists = items.some(
                i => i.name.toLowerCase() === typedName
            );

            if (exists) {
                this.style.border = "2px solid red";
                showAlert(
                    "⚠️ An item with this name already exists!",
                    "warning"
                );
            } else {
                this.style.border = "2px solid green";
            }

        } catch (err) {
            console.warn(err);
        }
    });

    document.getElementById("name").addEventListener("focus", function () {
        this.style.border = "";
    });

    document.getElementById("addItemForm")
        .addEventListener("submit", async function (e) {

            e.preventDefault();

            const item = {
                name: document.getElementById("name").value,
                description: document.getElementById("description").value,
                price: parseFloat(document.getElementById("price").value),
                category: document.getElementById("category").value,
                imageUrl: document.getElementById("imageUrl").value,
                available: true,
                bestseller: document.getElementById("bestseller").checked,
                newLaunch: document.getElementById("newLaunch").checked,
                stockQuantity:
                    parseInt(
                        document.getElementById("stockQuantity").value
                    ) || 0
            };

            try {

                await adminCreateMenuItem(item);

                showAlert(
                    "✅ Item added successfully!",
                    "success"
                );

                document.getElementById("addItemForm").reset();

                document.getElementById(
                    "stockQuantity"
                ).value = 50;

                loadItems();

            } catch (err) {

                showAlert(
                    "Failed to add item: " + err.message,
                    "danger"
                );
            }
        });


});

async function loadItems() {


    try {

        const items = await getAllMenuItems();

        const tbody =
            document.getElementById("itemsTable");

        if (items.length === 0) {

            tbody.innerHTML =
                `<tr>
                <td colspan="7"
                    class="text-center text-muted">
                    No items yet.
                </td>
             </tr>`;

            return;
        }

        tbody.innerHTML = items.map(i => {

            const stock =
                i.stockQuantity == null
                    ? 0
                    : i.stockQuantity;

            let stockBadge;

            if (stock === 0) {

                stockBadge =
                    `<span class="badge bg-danger">
                    Out of Stock
                 </span>`;

            } else if (stock < 5) {

                stockBadge =
                    `<span class="badge bg-warning text-dark">
                    ${stock} (Low)
                 </span>`;

            } else {

                stockBadge =
                    `<span class="badge bg-success">
                    ${stock}
                 </span>`;
            }

            return `
            <tr>

                <td>${i.id}</td>

                <td>${i.name}</td>

                <td>${i.category}</td>

                <td>₹${i.price}</td>

                <td>${stockBadge}</td>

                <td>
                    ${i.bestseller
                    ? '<span class="badge badge-bestseller">Bestseller</span>'
                    : ''
                }

                    ${i.newLaunch
                    ? '<span class="badge badge-new">New</span>'
                    : ''
                }
                </td>

                <td>

                    <button
                        class="btn btn-sm btn-warning mb-1"
                        onclick='openEditModal(${JSON.stringify(i)})'>
                        ✏️ Edit
                    </button>

                    <button
                        class="btn btn-sm btn-danger mb-1"
                        onclick="removeItem(${i.id})">
                        🗑️ Delete
                    </button>

                </td>

            </tr>
        `;

        }).join("");

    } catch (err) {

        showAlert(
            "Failed to load: " + err.message,
            "danger"
        );
    }


}

function openEditModal(item) {


    document.getElementById("editId").value =
        item.id;

    document.getElementById("editName").value =
        item.name;

    document.getElementById("editPrice").value =
        item.price;

    document.getElementById("editCategory").value =
        item.category;

    document.getElementById("editStock").value =
        item.stockQuantity || 0;

    document.getElementById("editImageUrl").value =
        item.imageUrl || "";

    document.getElementById("editDescription").value =
        item.description || "";

    document.getElementById("editBestseller").checked =
        item.bestseller || false;

    document.getElementById("editNewLaunch").checked =
        item.newLaunch || false;

    if (!editModal) {

        editModal =
            new bootstrap.Modal(
                document.getElementById("editItemModal")
            );
    }

    editModal.show();


}

async function updateItem() {


    const stock =
        parseInt(
            document.getElementById("editStock").value
        ) || 0;

    const updatedItem = {

        name:
            document.getElementById("editName").value,

        description:
            document.getElementById("editDescription").value,

        price:
            parseFloat(
                document.getElementById("editPrice").value
            ),

        category:
            document.getElementById("editCategory").value,

        imageUrl:
            document.getElementById("editImageUrl").value,

        available: stock > 0,

        bestseller:
            document.getElementById("editBestseller").checked,

        newLaunch:
            document.getElementById("editNewLaunch").checked,

        stockQuantity: stock
    };

    const id =
        document.getElementById("editId").value;

    try {

        await adminUpdateMenuItem(
            id,
            updatedItem
        );

        showAlert(
            "✅ Item updated successfully!",
            "success"
        );

        editModal.hide();

        loadItems();

    } catch (err) {

        showAlert(
            "Failed to update item: " + err.message,
            "danger"
        );
    }


}

async function removeItem(id) {


    if (!confirm("Delete item #" + id + "?"))
        return;

    try {

        await adminDeleteMenuItem(id);

        showAlert(
            "Item deleted",
            "success"
        );

        loadItems();

    } catch (err) {

        showAlert(
            "Failed: " + err.message,
            "danger"
        );
    }


}
