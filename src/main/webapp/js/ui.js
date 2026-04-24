window.addEventListener("load", function () {
    const loader = document.getElementById("loader");
    const btn = document.querySelector(".dark-toggle");

    if (loader) {
        setTimeout(() => {
            loader.classList.add("hidden");
        }, 450);
    }

    if (localStorage.getItem("theme") === "dark") {
        document.body.classList.add("dark-mode");
        if (btn) btn.innerText = "☀ Light";
    } else {
        if (btn) btn.innerText = "🌙 Dark";
    }
});

function toggleDarkMode() {
    const btn = document.querySelector(".dark-toggle");

    document.body.classList.toggle("dark-mode");

    if (document.body.classList.contains("dark-mode")) {
        localStorage.setItem("theme", "dark");
        if (btn) btn.innerText = "☀ Light";
    } else {
        localStorage.setItem("theme", "light");
        if (btn) btn.innerText = "🌙 Dark";
    }
}

function openArtModal(title, price, image, description, artworkId) {
    const modalTitle = document.getElementById("modalTitle");
    const modalPrice = document.getElementById("modalPrice");
    const modalImage = document.getElementById("modalImage");
    const modalDescription = document.getElementById("modalDescription");
    const modalArtworkId = document.getElementById("modalArtworkId");
    const modal = document.getElementById("artModal");

    if (modalTitle) modalTitle.innerText = title;
    if (modalPrice) modalPrice.innerText = price;
    if (modalImage) modalImage.src = image;
    if (modalDescription) modalDescription.innerText = description;
    if (modalArtworkId) modalArtworkId.value = artworkId;
    if (modal) modal.classList.add("show");
}

function showToast(message) {
    let toast = document.getElementById("toast");

    if (!toast) return;

    toast.innerText = message;
    toast.classList.add("show-toast");

    setTimeout(() => {
        toast.classList.remove("show-toast");
    }, 2000);
}

function closeArtModal() {
    const modal = document.getElementById("artModal");

    if (modal) {
        modal.classList.remove("show");
    }
}

window.addEventListener("click", function (event) {
    const modal = document.getElementById("artModal");

    if (modal && event.target === modal) {
        closeArtModal();
    }
});

function filterArtworks() {
    const selected = document.getElementById("categoryFilter").value;
    const cards = document.querySelectorAll(".art-card");

    cards.forEach(card => {
        const category = card.getAttribute("data-category");

        if (selected === "all" || selected === category) {
            card.classList.remove("hide-card");
        } else {
            card.classList.add("hide-card");
        }
    });
}

function increaseQty(id) {
    const qty = document.getElementById(id);

    if (!qty) return;

    let value = parseInt(qty.innerText);
    qty.innerText = value + 1;
}

function decreaseQty(id) {
    const qty = document.getElementById(id);

    if (!qty) return;

    let value = parseInt(qty.innerText);

    if (value > 1) {
        qty.innerText = value - 1;
    }
}