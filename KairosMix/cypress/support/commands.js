/* global Cypress, cy */

const testUsers = {
  admin: {
    id: 1,
    email: 'admin@kairosmix.com',
    name: 'Administrador',
    role: 'ADMIN',
  },
  user: {
    id: 2,
    email: 'usuario@kairosmix.com',
    name: 'Usuario',
    role: 'USER',
  },
};

Cypress.Commands.add('seedAuthSession', (role = 'admin') => {
  const user = testUsers[role];

  cy.visit('/login', {
    onBeforeLoad(win) {
      win.localStorage.removeItem('currentUser');
      win.localStorage.removeItem('viewMode');
      win.localStorage.setItem('currentUser', JSON.stringify(user));
      win.localStorage.setItem('viewMode', user.role === 'USER' ? 'client' : 'admin');
    },
  });
});

Cypress.Commands.add('visitWithAuth', (path, role = 'admin') => {
  const user = testUsers[role];

  cy.visit(path, {
    onBeforeLoad(win) {
      win.localStorage.removeItem('currentUser');
      win.localStorage.removeItem('viewMode');
      win.localStorage.setItem('currentUser', JSON.stringify(user));
      win.localStorage.setItem('viewMode', user.role === 'USER' ? 'client' : 'admin');
    },
  });
});

Cypress.Commands.add('seedAppFixtures', () => {
  cy.intercept('GET', '**/api/v1/products', { fixture: 'products.json' }).as('getProducts');
  cy.intercept('GET', '**/api/v1/clients', { fixture: 'clients.json' }).as('getClients');
  cy.intercept('GET', '**/api/v1/orders', { fixture: 'orders.json' }).as('getOrders');

  cy.intercept('POST', '**/api/v1/products', (req) => {
    const body = req.body || {};

    req.reply({
      statusCode: 201,
      body: {
        id: 999,
        ...body,
        stock: body.stock ?? body.initialStock ?? 0,
        initialStock: body.initialStock ?? body.stock ?? 0,
        currentStock: body.currentStock ?? body.initialStock ?? body.stock ?? 0,
      },
    });
  }).as('createProduct');
});
