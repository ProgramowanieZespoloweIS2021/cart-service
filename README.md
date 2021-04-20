# Shopping cart microservice

[![codecov](https://codecov.io/gh/ProgramowanieZespoloweIS2021/cart-service/branch/main/graph/badge.svg?token=8ZTZXEZT6F)](https://codecov.io/gh/ProgramowanieZespoloweIS2021/cart-service)

![CI/CD](https://github.com/ProgramowanieZespoloweIS2021/cart-service/actions/workflows/ci.yml/badge.svg)


## API description
API will probably be changed, any suggestions on how to do so are very welcome. Supported operations:
 * getting empty (new) shopping cart (creates it in the database)
 * adding item to the cart
 * removing item from the cart
 * getting the cart
 * getting single item from the cart
 * editing item in the cart
 * submitting cart (creating orders)
 

### Getting empty shopping cart

URL: `http://localhost:8081/carts` (method: GET)

Returns ID of created cart.


### Adding item to the cart

URL: `http://localhost:8081/carts/<cart_id>` (method: POST)

Example body:
```json
{
    "offerId": 1,
    "tierId": 2,
    "description": "test"
}
```

Returns ID of the new item added to the cart.


### Removing item from the cart

URL: `http://localhost:8081/carts/<cart_id>/<item_id>` (method: DELETE)

Returns ID of removed item.


### Getting the cart

URL: `http://localhost:8081/carts/<cart_id>` (method: GET)

Example response:
```json
{
    "id": 1,
    "items": [
        {
            "id": 1,
            "description": "test after edit",
            "offerId": 1,
            "offerOwnerId": 0,
            "offerTitle": "Example offer 2",
            "tierId": 2,
            "tierTitle": "tier2",
            "tierPrice": 7.50
        },
        {
            "id": 2,
            "description": "test",
            "offerId": 1,
            "offerOwnerId": 0,
            "offerTitle": "Example offer 2",
            "tierId": 2,
            "tierTitle": "tier2",
            "tierPrice": 7.50
        }
    ]
}
```


### Getting single item from the cart

URL: `http://localhost:8081/carts/<cart_id>/<item_id>` (method: GET)

Example response:
```json
{
    "id": 2,
    "description": "test",
    "offerId": 1,
    "offerOwnerId": 0,
    "offerTitle": "Example offer 2",
    "tierId": 2,
    "tierTitle": "tier2",
    "tierPrice": 7.50
}
```


### Editing item in the cart

URL: `http://localhost:8081/carts/<cart_id>/<item_id>` (method: POST)

Example body:
```json
{
    "offerId": 1,
    "tierId": 2,
    "description": "test after edit"
}
```

Returns ID of edited item.


### Submitting the cart

URL: `http://localhost:8081/carts/submission` (method: POST)

Example body
```json
{
    "cartId": 1,
    "buyerId": 1
}
```

Returns string with a message about success. Creates orders via the orders-service.

