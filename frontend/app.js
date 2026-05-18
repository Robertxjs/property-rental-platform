const API_BASE_URL = "http://localhost:8081/api";

// ==========================================
// 1. MANAGEMENTUL AUTENTIFICĂRII (Pasul 4)
// ==========================================

// Formular Înregistrare
const registerForm = document.getElementById("registerForm");
if (registerForm) {
    registerForm.addEventListener("submit", async (e) => {
        e.preventDefault();
        
        const payload = {
            fullName: document.getElementById("regFullName").value,
            email: document.getElementById("regEmail").value,
            password: document.getElementById("regPassword").value,
            phone: document.getElementById("regPhone").value,
            role: document.getElementById("regRole").value
        };

        try {
            const response = await fetch(`${API_BASE_URL}/auth/register`, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(payload)
            });

            const message = await response.text();
            const msgDiv = document.getElementById("registerMessage");
            
            if (response.ok) {
                msgDiv.style.color = "green";
                msgDiv.innerText = message;
                setTimeout(() => window.location.href = "login.html", 2000);
            } else {
                msgDiv.style.color = "red";
                msgDiv.innerText = message;
            }
        } catch (error) {
            console.error("Eroare la rețea:", error);
        }
    });
}

// Formular Login
const loginForm = document.getElementById("loginForm");
if (loginForm) {
    loginForm.addEventListener("submit", async (e) => {
        e.preventDefault();

        const payload = {
            email: document.getElementById("loginEmail").value,
            password: document.getElementById("loginPassword").value
        };

        try {
            const response = await fetch(`${API_BASE_URL}/auth/login`, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(payload)
            });

            if (response.ok) {
                const userData = await response.json();
                // Salvăm utilizatorul în memoria browserului ca sesiune locală
                localStorage.setItem("user", JSON.stringify(userData));
                window.location.href = "dashboard.html";
            } else {
                const errorText = await response.text();
                const msgDiv = document.getElementById("loginMessage");
                msgDiv.style.color = "red";
                msgDiv.innerText = errorText;
            }
        } catch (error) {
            console.error("Eroare la rețea:", error);
        }
    });
}

function logout() {
    localStorage.removeItem("user");
    window.location.href = "login.html";
}


// ==========================================
// 2. MANAGEMENTUL APARTAMENTELOR (Pasul 5)
// ==========================================

const apartmentForm = document.getElementById("apartmentForm");
if (apartmentForm) {
    apartmentForm.addEventListener("submit", async (e) => {
        e.preventDefault();

        const user = JSON.parse(localStorage.getItem("user"));
        const apartmentId = document.getElementById("apartmentId").value;
        
        const payload = {
            landlordId: user.id,
            address: document.getElementById("aptAddress").value,
            building: document.getElementById("aptBuilding").value,
            floor: document.getElementById("aptFloor").value,
            rooms: parseInt(document.getElementById("aptRooms").value),
            size: parseFloat(document.getElementById("aptSize").value),
            monthlyRent: parseFloat(document.getElementById("aptRent").value),
            deposit: parseFloat(document.getElementById("aptDeposit").value),
            status: document.getElementById("aptStatus").value
        };

        // Dacă avem id, facem PUT (Editare), altfel fac POST (Creare)
        const isEdit = apartmentId !== "";
        const url = isEdit ? `${API_BASE_URL}/apartments/${apartmentId}` : `${API_BASE_URL}/apartments`;
        const method = isEdit ? "PUT" : "POST";

        try {
            const response = await fetch(url, {
                method: method,
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(payload)
            });

            if (response.ok) {
                alert(isEdit ? "Apartament actualizat!" : "Apartament adăugat cu succes!");
                resetForm();
                loadApartments();
            } else {
                alert("Eroare la salvarea apartamentului.");
            }
        } catch (error) {
            console.error("Eroare:", error);
        }
    });
}

// Funcție care încarcă apartamentele din baza de date
async function loadApartments() {
    const tableBody = document.getElementById("apartmentsTableBody");
    if (!tableBody) return;

    try {
        const response = await fetch(`${API_BASE_URL}/apartments`);
        const apartments = await response.json();
        
        const currentUser = JSON.parse(localStorage.getItem("user"));
        tableBody.innerHTML = "";

        // Regula de business: Filtrăm ca Landlord-ul să își vadă doar apartamentele sale
        const ownApartments = apartments.filter(apt => apt.landlord && apt.landlord.id === currentUser.id);

        if (ownApartments.length === 0) {
            tableBody.innerHTML = `<tr><td colspan="7" style="text-align:center;">Nu ai adăugat niciun apartament încă.</td></tr>`;
            return;
        }

        ownApartments.forEach(apt => {
            tableBody.innerHTML += `
                <tr>
                    <td>${apt.id}</td>
                    <td>${apt.address}</td>
                    <td>Bl. ${apt.building}, Et. ${apt.floor}</td>
                    <td>${apt.rooms} cam (${apt.size} mp)</td>
                    <td><strong>${apt.monthlyRent} €</strong></td>
                    <td><span class="badge">${apt.status}</span></td>
                    <td>
                        <button class="btn-sm" style="background-color: #ffc107; color: black;" onclick="editApartment(${JSON.stringify(apt).clientX /* Evităm probleme de parsing */})">Editează</button>
                        <button class="btn-sm" style="background-color: #dc3545;" onclick="deleteApartment(${apt.id})">Șterge</button>
                    </td>
                </tr>
            `;
        });
    } catch (error) {
        console.error("Eroare la încărcarea apartamentelor:", error);
    }
}

// Pregătire formular pentru editare
function editApartment(id, address, building, floor, rooms, size, rent, deposit, status) {
    // Funcția va fi mapată direct din eveniment
    document.getElementById("formTitle").innerText = "Editează Apartamentul ID: " + id;
    document.getElementById("apartmentId").value = id;
    document.getElementById("aptAddress").value = address;
    document.getElementById("aptBuilding").value = building;
    document.getElementById("aptFloor").value = floor;
    document.getElementById("aptRooms").value = rooms;
    document.getElementById("aptSize").value = size;
    document.getElementById("aptRent").value = rent;
    document.getElementById("aptDeposit").value = deposit;
    document.getElementById("aptStatus").value = status;

    document.getElementById("cancelEditBtn").style.display = "block";
    document.getElementById("saveAptBtn").innerText = "Actualizează Modificările";
}

// Suport pentru butoanele din tabel prin re-mapare curată globală
window.editApartment = function(apt) {
    editApartment(apt.id, apt.address, apt.building, apt.floor, apt.rooms, apt.size, apt.monthlyRent, apt.deposit, apt.status);
};

window.deleteApartment = async function(id) {
    if (!confirm("Sigur vrei să ștergi acest apartament?")) return;

    try {
        const response = await fetch(`${API_BASE_URL}/apartments/${id}`, { method: "DELETE" });
        if (response.ok) {
            alert("Apartament șters!");
            loadApartments();
        } else {
            alert("Eroare la ștergere.");
        }
    } catch (error) {
        console.error("Eroare:", error);
    }
};

const cancelEditBtn = document.getElementById("cancelEditBtn");
if (cancelEditBtn) {
    cancelEditBtn.addEventListener("click", resetForm);
}

function resetForm() {
    document.getElementById("apartmentForm").reset();
    document.getElementById("apartmentId").value = "";
    document.getElementById("formTitle").innerText = "Adaugă un Apartament Nou";
    document.getElementById("saveAptBtn").innerText = "Salvează Apartamentul";
    if (cancelEditBtn) cancelEditBtn.style.display = "none";
}