DROP TABLE IF EXISTS shopping_cart_item;
DROP TABLE IF EXISTS shopping_cart;

CREATE TABLE shopping_cart(
    id SERIAL PRIMARY KEY
);

CREATE TABLE shopping_cart_item(
    id SERIAL PRIMARY KEY,
    offer_id INTEGER,
    tier_id INTEGER,
    description TEXT,
    cart_id INTEGER,
    CONSTRAINT FK_CART FOREIGN KEY (cart_id) REFERENCES shopping_cart(id)
);
