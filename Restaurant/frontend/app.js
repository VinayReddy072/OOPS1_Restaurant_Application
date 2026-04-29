// Global Configuration
const BASE_URL = "http://localhost:8080"; 

// Global State
let menuData = [];
let selectedDish = "";

// --- IMAGE MAPPING ---
// Maps exact dish names to Unsplash/Brave Image URLs (As requested)
const IMAGE_MAP = {
    "Breakfast Burrito": "https://images.unsplash.com/photo-1626700051175-6818013e1d4f?auto=format&fit=crop&w=400&q=80",
    "Pancakes": "https://images.unsplash.com/photo-1567620905732-2d1ec7ab7445?auto=format&fit=crop&w=400&q=80",
    "Avocado Toast": "https://images.unsplash.com/photo-1504674900247-0877df9cc836?auto=format&fit=crop&w=400&q=80",
    "Eggs Benedict": "https://images.unsplash.com/photo-1608039829572-78524f79c4c7?auto=format&fit=crop&w=400&q=80",
    "Caesar Salad": "https://imgs.search.brave.com/7jM0LKcL3px7uN-E5slaUMuCsxH-6F-ezAXeRJIa3Bs/rs:fit:500:0:1:0/g:ce/aHR0cHM6Ly90aHVt/YnMuZHJlYW1zdGlt/ZS5jb20vYi9jYWVz/YXItc2FsYWQtZ3Jp/bGxlZC1jaGlja2Vu/LWNyaXNwLXJvbWFp/bmUtY3JvdXRvbnMt/cGFybWVzYW4tY3Jl/YW15LWRyZXNzaW5n/LXNlcnZlZC1zaWRl/LWFuY2hvdmllcy0z/NDYzODk0NDguanBn?auto=format&fit=crop&w=400&q=80",
    "Grilled Chicken Salad": "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?auto=format&fit=crop&w=400&q=80",
    "Veggie Burger": "https://images.unsplash.com/photo-1550547660-d9450f859349?auto=format&fit=crop&w=400&q=80",
    "Burger": "https://images.unsplash.com/photo-1568901346375-23c9450c58cd?auto=format&fit=crop&w=400&q=80",
    "Chicken Sandwich": "https://images.unsplash.com/photo-1606755962773-d324e0a13086?auto=format&fit=crop&w=400&q=80",
    "Steak": "https://images.unsplash.com/photo-1600891964092-4316c288032e?auto=format&fit=crop&w=400&q=80",
    "Pasta": "https://imgs.search.brave.com/C-9OmWWXcDEOC0_rBGhychvN6V-S3eQPBVVPlZS3zOs/rs:fit:500:0:1:0/g:ce/aHR0cHM6Ly9tZWRp/YS5nZXR0eWltYWdl/cy5jb20vaWQvMTM1/Mjg1NjQ4MC9waG90/by9pdGFsaWFuLWRp/c2hlcy1mb3ItZmFt/aWx5LWRpbm5lci5q/cGc_cz02MTJ4NjEy/Jnc9MCZrPTIwJmM9/TGgtaGNZbkhHT0lv/TTlRMW5QWGlWSHVF/VnNRVnBzeGV6TFlm/el9GTGJ5MD0?auto=format&fit=crop&w=400&q=80",
    "Salmon Fillet": "https://imgs.search.brave.com/LseU_lmobghXzZG4JmG3gmQcNsclb0TTHme35GfHnJw/rs:fit:500:0:1:0/g:ce/aHR0cHM6Ly9pbWcu/ZnJlZXBpay5jb20v/cHJlbWl1bS1waG90/by9yYXctc2FsbW9u/LWZpbGxldC13aXRo/LXJvc2VtYXJ5LWJs/YWNrLXN0b25lLXBs/YXRlLWRhcmstYmFj/a2dyb3VuZC1mbGF0/LWxheV8xODcxNjYt/NTMzNjAuanBnP3Nl/bXQ9YWlzX2luY29t/aW5nJnc9NzQwJnE9/ODA?auto=format&fit=crop&w=400&q=80",
    "Ribeye Steak": "https://imgs.search.brave.com/P9r2rXl85P6IO3pdGY53k_hc_TFmhPC_zW4ybDEgkxo/rs:fit:500:0:1:0/g:ce/aHR0cHM6Ly9tZWRp/YS5pc3RvY2twaG90/by5jb20vaWQvMTQ0/NTY5OTU3Ni9waG90/by9ncmlsbGVkLXJp/YmV5ZS1iZWVmLXN0/ZWFrLmpwZz9zPTYx/Mng2MTImdz0wJms9/MjAmYz03R3RJYXUz/NndqLUZhZnBBRnZr/Tnc0dnVvNUZjS2px/bkVQTklLOFAyMnlj/PQ?auto=format&fit=crop&w=400&q=80",
    "Grilled Lobster": "https://imgs.search.brave.com/jim1OD8eajJpS31nnr46hr05fByM57qdWRfH88vzF0A/rs:fit:500:0:1:0/g:ce/aHR0cHM6Ly9zdGF0/aWMudmVjdGVlenku/Y29tL3N5c3RlbS9y/ZXNvdXJjZXMvdGh1/bWJuYWlscy8wMzAv/NDUzLzI0Mi9zbWFs/bC9kZWxpY2lvdXMt/Z3JpbGxlZC1sb2Jz/dGVyLXJlYWR5LXRv/LWVhdC1nZW5lcmF0/aXZlLWFpLXBob3Rv/LmpwZw?auto=format&fit=crop&w=400&q=80",
    "Ice Cream": "https://imgs.search.brave.com/RyKgYPQA4MY09Syi_zQG2ym6IBqXY1khTPxfDt8ydqU/rs:fit:500:0:1:0/g:ce/aHR0cHM6Ly9tZWRp/YS5pc3RvY2twaG90/by5jb20vaWQvOTM2/MjA1ODUyL3Bob3Rv/L2Nob2NvbGF0ZS1p/Y2UtY3JlYW0taW4t/YS1nbGFzcy1jdXAu/anBnP3M9NjEyeDYx/MiZ3PTAmaz0yMCZj/PWdjUFRyb0RWUE5T/bENTNDVCa3FFRy02/LVJFbktGRXpib0Jw/dVg4TzNsWTQ9?auto=format&fit=crop&w=400&q=80",
    "Chocolate Cake": "https://images.unsplash.com/photo-1578985545062-69928b1d9587?auto=format&fit=crop&w=400&q=80",
    "Cheesecake": "https://images.unsplash.com/photo-1524351199678-941a58a3df50?auto=format&fit=crop&w=400&q=80",
    "Milkshake": "https://imgs.search.brave.com/HLpCE7RL88F1kbQtUdS10-sww2vOywhHDGRQR0t8pCs/rs:fit:500:0:1:0/g:ce/aHR0cHM6Ly93d3cu/c2h1dHRlcnN0b2Nr/LmNvbS9pbWFnZS1w/aG90by9taWxrc2hh/a2VzLWRpZmZlcmVu/dC1mbGF2b3JzLW9u/LWRhcmstMjYwbnct/MjE3MzY3MjAyNy5q/cGc?auto=format&fit=crop&w=400&q=80",
    "Brownie Sundae": "https://imgs.search.brave.com/M1KN38lWTnKEUKxWgJWRyYr06QTNkorRTSR0DxU9XVc/rs:fit:500:0:1:0/g:ce/aHR0cHM6Ly93d3cu/dGFzdGVvZmhvbWUu/Y29tL3dwLWNvbnRl/bnQvdXBsb2Fkcy8y/MDI0LzA2L0Jyb3du/aWUtU3VuZGFlX1RP/SEQyNF8zMDI3OV9K/dWxpYUhhcnRiZWNr/XzgtMS5qcGc_Zml0/PTcwMCwxMDI0?auto=format&fit=crop&w=400&q=80"
};

// Fallback image if name doesn't match
const DEFAULT_IMG = "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?auto=format&fit=crop&w=400&q=80";

// Initialization
document.addEventListener('DOMContentLoaded', () => {
    navigate('home');
    loadMenu();
});

// --- NAVIGATION ---
function navigate(view) {
    document.querySelectorAll('.view').forEach(el => el.classList.add('hidden'));
    
    // Handle specific display types (receipt needs flex to center)
    const target = document.getElementById(`view-${view}`);
    if(target) {
        target.classList.remove('hidden');
        if(view === 'receipt') target.style.display = 'flex';
        else target.style.display = ''; 
    }
    
    document.querySelectorAll('.nav-links a').forEach(el => el.classList.remove('active'));
    const activeLink = document.getElementById(`nav-${view}`);
    if(activeLink) activeLink.classList.add('active');

    if(view === 'orders') fetchOrders();
}

// --- MENU LOGIC ---
async function loadMenu() {
    try {
        const res = await fetch(`${BASE_URL}/menu`);
        if (!res.ok) throw new Error("Failed to fetch menu");
        
        menuData = await res.json();
        renderMenu(menuData);
    } catch (e) {
        console.error("Connection failed", e);
        document.getElementById('menu-grid').innerHTML = 
            '<p style="grid-column: 1/-1; text-align:center; color:var(--red); font-weight:bold;">' +
            '⚠️ Error connecting to Backend.<br>Make sure RestaurantApp.java is running.</p>';
    }
}

function renderMenu(items) {
    const grid = document.getElementById('menu-grid');
    if(items.length === 0) {
        grid.innerHTML = '<p>No menu items found.</p>';
        return;
    }
    
    grid.innerHTML = items.map(item => {
        // Look up image, use default if not found
        const imgSrc = IMAGE_MAP[item.name] || DEFAULT_IMG;
        
        return `
        <div class="menu-card">
            <div class="menu-img-container">
                <img src="${imgSrc}" alt="${item.name}" class="menu-img">
            </div>
            <div class="menu-content">
                <div class="card-header">
                    <span class="dish-name">${item.name}</span>
                    <span class="dish-price">$${item.price.toFixed(2)}</span>
                </div>
                <span class="dish-type">${item.mealType}</span>
                <div style="margin-top:auto">
                    <button class="btn btn-primary full-width" onclick="openModal('${item.name}')">
                        Add to Order
                    </button>
                </div>
            </div>
        </div>
    `}).join('');
}

function filterMenu(type) {
    document.querySelectorAll('.filter-btn').forEach(btn => btn.classList.remove('active'));
    document.querySelector(`.filter-btn[data-type="${type}"]`).classList.add('active');

    if (type === 'ALL') renderMenu(menuData);
    else renderMenu(menuData.filter(item => item.mealType === type));
}

// --- MODAL & ORDERING ---
function openModal(dish) {
    selectedDish = dish;
    document.getElementById('modal-dish-display').innerText = dish;
    document.getElementById('order-modal').style.display = 'flex';
    // Focus on input for better UX
    document.getElementById('input-customer').focus();
}

function closeModal() {
    document.getElementById('order-modal').style.display = 'none';
    // Clear error styles if any
    const input = document.getElementById('input-customer');
    input.style.borderColor = '';
}

async function submitOrder() {
    const customerInput = document.getElementById('input-customer');
    const customer = customerInput.value.trim();
    
    // --- VALIDATION: REQUIRED FIELDS ---
    if (!customer) {
        alert("Name is required to place an order.");
        customerInput.style.borderColor = "var(--red)";
        customerInput.focus();
        return; // Stop execution if invalid
    }

    const qty = document.getElementById('input-qty').value;
    const promo = document.getElementById('input-promo').value;

    const payload = {
        customer: customer,
        dish: selectedDish,
        qty: parseInt(qty),
        promoCode: promo
    };

    try {
        const res = await fetch(`${BASE_URL}/order`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(payload)
        });

        if (res.ok) {
            const receiptData = await res.json();
            closeModal();
            
            document.getElementById('input-customer').value = "";
            document.getElementById('input-qty').value = "1";
            document.getElementById('input-promo').value = "";
            customerInput.style.borderColor = "";
            
            showReceipt(receiptData, payload);
        } else {
            const err = await res.text();
            alert("Failed: " + err);
        }
    } catch (e) {
        alert("Server Error: Check console for details");
    }
}
// --- RECEIPT LOGIC ---
function showReceipt(serverData, orderData) {
    const paper = document.querySelector('.receipt-paper');
    if (!paper) return;

    // Retrieve Customer Name (Default to "Guest" if missing)
    const customerName = orderData.customer || "Guest";
    const shortId = serverData.orderId.split('-')[1] || "???";
    
    // Current Date & Time
    const now = new Date();
    const dateStr = formatDate(now);
    const timeStr = formatTime(now);

    // Calculate Tax (Mock 8% from backend or calculate)
    const estimatedTax = (serverData.finalTotal - serverData.subtotal);
    const taxDisplay = estimatedTax > 0 ? `$${estimatedTax.toFixed(2)}` : "$0.00";

    // Start constructing the receipt content
    paper.innerHTML = `
        <div style="text-align:center; color:#000; font-family: 'Courier New', Courier, monospace; font-size: 0.9rem;">
            <h2 style="margin:0; font-size:1.4rem; font-weight:800; text-transform:uppercase; letter-spacing:1px;">Java Bistro</h2>
            <p style="margin:5px 0 15px 0; font-size:0.8rem; line-height:1.4;">
                2424 Java Avenue, Web City<br>
                Spring Boot, JVM 20009<br>
                +1 202-555-0199
            </p>
            
            <div style="border-bottom: 1px solid #000; margin-bottom: 10px;"></div>
            
            <div style="display:flex; justify-content:space-between; font-size:0.85rem; margin-bottom:10px;">
                <span>Trans#: ${serverData.orderId}</span>
                <span>Table: Q1</span>
            </div>
            
            <div style="display:flex; justify-content:space-between; font-size:0.85rem; margin-bottom:10px;">
                <span style="font-weight:bold; text-transform:uppercase;">Name: ${customerName}</span>
                <span># Cust: 1</span>
            </div>
            
            <div style="border-bottom: 1px solid #000; margin-bottom: 10px;"></div>
            
            <div style="display:flex; justify-content:space-between; font-size:0.85rem; font-weight:600; margin-bottom:10px;">
                <span>Date: ${dateStr}</span>
                <span>Time: ${timeStr}</span>
            </div>
            
            <div style="border-bottom: 1px solid #000; margin-bottom: 10px;"></div>
            
            <div style="display:flex; justify-content:space-between; font-weight:800; font-size:0.9rem; margin-bottom:10px;">
                <span>Quan Descript</span>
                <span>Cost</span>
            </div>
            
            <div style="display:flex; justify-content:space-between; font-size:0.9rem; margin-bottom:5px;">
                <span>${orderData.dish} <span style="font-size:0.8em; color:#555;">x${orderData.qty}</span></span>
                <span>$${(serverData.subtotal * orderData.qty).toFixed(2)}</span>

            </div>
            
            ${serverData.discountApplied !== "None" ? `
            <div style="display:flex; justify-content:space-between; font-size:0.85rem; color:#444; margin-bottom:5px;">
                <span>Disc. (${serverData.discountApplied})</span>
                <span>-${(serverData.subtotal - serverData.finalTotal).toFixed(2)}</span>
            </div>` : ''}
            
            <div style="border-bottom: 1px dashed #000; margin: 15px 0;"></div>
            
            <div style="display:flex; justify-content:space-between; font-size:0.9rem; margin-bottom:5px;">
                <span>Sub-Total</span>
                <span>$${(serverData.subtotal * orderData.qty).toFixed(2)}</span>
            </div>
            <div style="display:flex; justify-content:space-between; font-size:0.9rem; margin-bottom:5px;">
                <span>Tax</span>
                <span>${taxDisplay}</span>
            </div>
            
            <div style="border-bottom: 1px dashed #000; margin: 20px 0;"></div>

            <div style="display:flex; justify-content:space-between; font-weight:800; font-size:1.2rem; margin-top:10px;">
                <span>TOTAL</span>
                <span>$${(serverData.finalTotal * orderData.qty).toFixed(2)}</span>

            </div>
            
            <div style="display:flex; justify-content:space-between; font-size:0.9rem; margin-top:5px;">
                <span>Amount Due</span>
                <span>$${(serverData.finalTotal * orderData.qty).toFixed(2)}</span>
            </div>

            <div style="border-bottom: 1px dashed #000; margin: 20px 0;"></div>
            
            <div style="text-align:center; margin-bottom: 20px;">
                <p style="font-weight:700; margin:0;">THANK YOU</p>
                <p style="font-size:0.8rem; margin:5px 0;">PLEASE VISIT AGAIN</p>
            </div>
            

            <div style="height: 50px; width: 80%; margin: 0 auto; background: repeating-linear-gradient(
                to right,
                #000 0px, #000 2px,
                transparent 2px, transparent 4px,
                #000 4px, #000 8px,
                transparent 8px, transparent 9px,
                #000 9px, #000 12px,
                transparent 12px, transparent 15px
            );"></div>
            <p style="font-size:0.7rem; margin-top:5px; letter-spacing:2px; text-align:center;">${serverData.orderId}</p>

            <div style="display:flex; justify-content:center; gap:10px; margin-top:30px;">
                <button class="btn btn-primary" id="done-btn">Done</button>
                <button class="btn btn-ghost" id="track-order-btn">Track Order</button>
            </div>
        </div>
    `;

    // Add event listeners for buttons
    document.getElementById('done-btn').addEventListener('click', () => navigate('home'));
    document.getElementById('track-order-btn').addEventListener('click', () => navigate('orders'));
    
    // Navigate to receipt view
    navigate('receipt');
}

// Format date as "Month Day, Year" (e.g., "November 26, 2025")
function formatDate(date) {
    return date.toLocaleDateString('en-US', { 
        day: 'numeric', 
        month: 'long', 
        year: 'numeric' 
    });
}

// Format time as "Hour:Minute AM/PM" (e.g., "12:01 PM")
function formatTime(date) {
    return date.toLocaleTimeString('en-US', { 
        hour: 'numeric', 
        minute: '2-digit' 
    });
}

// --- ORDER HISTORY LOGIC ---
async function fetchOrders() {
    const tbody = document.getElementById('orders-body');
    tbody.innerHTML = "<tr><td colspan='5' style='text-align:center; padding:2rem;'>Loading Data...</td></tr>";

    try {
        const res = await fetch(`${BASE_URL}/orders`);
        if (!res.ok) throw new Error(`Server returned ${res.status} ${res.statusText}`);

        const orders = await res.json();

        if (!Array.isArray(orders) || orders.length === 0) {
            tbody.innerHTML = "<tr><td colspan='5' style='text-align:center; padding:2rem;'>No active orders.</td></tr>";
            return;
        }

        const rows = await Promise.all(
            orders.map(async (record) => {

                // --- STATUS ---
                let status = "PENDING";
                try {
                    const sRes = await fetch(`${BASE_URL}/order-status?orderId=${record.orderId}`);
                    if (sRes.ok) {
                        const sData = await sRes.json();
                        status = sData.status;
                    }
                } catch { /* ignore */ }

                // --- NORMALIZE ORDER ITEM ---
                // Backend usually returns: dish, qty, subtotal, finalTotal
                const dishName = record.dish || (record.items?.[0]?.name) || "Unknown Dish";
                const qty = record.qty || (record.items?.[0]?.qty) || 1;

                // finalTotal already includes qty — DO NOT MULTIPLY AGAIN
                const total = Number(record.finalTotal || record.subtotal || 0);

                const shortId = record.orderId
                    ? record.orderId.split('-')[1]
                    : "???";

                return `
                    <tr>
                        <td style="font-family:monospace; color:var(--blue); font-weight:bold;">#${shortId}</td>
                        <td>${record.customerName || record.customer || "Guest"}</td>
                        <td>
                            <span style="font-weight:600;">${dishName}</span>
                            <span style="color:var(--text-muted); font-size:0.85em; margin-left:5px;">x${qty}</span>
                        </td>
                        <td style="color:var(--green); font-weight:bold;">$${total.toFixed(2)}</td>
                        <td><span class="status-${status}">${status}</span></td>
                    </tr>
                `;
            })
        );

        tbody.innerHTML = rows.join("");

    } catch (e) {
        console.error("Fetch Orders Error:", e);

        let msg = e.message.includes("Failed to fetch")
            ? "Error connecting to Java Backend.<br>Is the server running on port 8080?"
            : e.message;

        tbody.innerHTML = `
            <tr>
                <td colspan='5' style='text-align:center; color:var(--red); padding:1rem;'>
                    <strong>Error Loading History</strong><br>
                    <span style="font-size:0.9em">${msg}</span>
                </td>
            </tr>`;
    }
}
