// In dev, prefer same-origin calls (via Vite proxy) with default '/api/v1'.
// In prod, set VITE_API_BASE_URL to your deployed backend, e.g. 'https://api.example.com/api/v1'.
const BASE_URL = import.meta.env.VITE_API_BASE_URL || '/api/v1';

export const api = {
    // Products
    getProducts: async () => {
        const res = await fetch(`${BASE_URL}/products`);
        return res.ok ? res.json() : [];
    },
    createProduct: async (product) => {
        const res = await fetch(`${BASE_URL}/products`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(product)
        });
        return res.json();
    },
    updateProduct: async (id, product) => {
        const res = await fetch(`${BASE_URL}/products/${id}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(product)
        });
        return res.json();
    },
    deleteProduct: async (id) => {
        await fetch(`${BASE_URL}/products/${id}`, { method: 'DELETE' });
    },

    // Clients
    getClients: async () => {
        const res = await fetch(`${BASE_URL}/clients`);
        return res.ok ? res.json() : [];
    },
    createClient: async (client) => {
        const res = await fetch(`${BASE_URL}/clients`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(client)
        });
        return res.json();
    },
    deleteClient: async (id) => {
        await fetch(`${BASE_URL}/clients/${id}`, { method: 'DELETE' });
    },

    // Orders
    getOrders: async () => {
        const res = await fetch(`${BASE_URL}/orders`);
        return res.ok ? res.json() : [];
    },
    createOrder: async (order) => {
        const res = await fetch(`${BASE_URL}/orders`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(order)
        });
        return res.json();
    },
    updateOrderStatus: async (id, status) => {
        // Backend expects request param name 'status' (not 'newStatus') and no body.
        const res = await fetch(`${BASE_URL}/orders/${id}/status?status=${encodeURIComponent(status)}`, {
            method: 'PATCH'
        });
        return res.json();
    },
    deleteOrder: async (id) => {
        await fetch(`${BASE_URL}/orders/${id}`, { method: 'DELETE' });
    },

    // Custom Mixes
    getCustomMixes: async () => {
        const res = await fetch(`${BASE_URL}/custom-mixes`);
        return res.ok ? res.json() : [];
    },
    createCustomMix: async (mix) => {
        const res = await fetch(`${BASE_URL}/custom-mixes`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(mix)
        });
        return res.json();
    },
    deleteCustomMix: async (id) => {
        await fetch(`${BASE_URL}/custom-mixes/${id}`, { method: 'DELETE' });
    }
};
