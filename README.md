# Shopping cart microservice

[![CI/CD](https://github.com/ProgramowanieZespoloweIS2021/cart-service/actions/workflows/ci.yml/badge.svg)](https://github.com/ProgramowanieZespoloweIS2021/cart-service/actions/workflows/ci.yml)
[![codecov](https://codecov.io/gh/ProgramowanieZespoloweIS2021/cart-service/branch/main/graph/badge.svg?token=8ZTZXEZT6F)](https://codecov.io/gh/ProgramowanieZespoloweIS2021/cart-service)
[![GitHub release (latest by date)](https://img.shields.io/github/v/release/ProgramowanieZespoloweIS2021/cart-service)](https://github.com/ProgramowanieZespoloweIS2021/cart-service/releases)
[![Docker Image Version (latest by date)](https://img.shields.io/docker/v/arokasprz100/cart-service?label=dockerhub%20image)](https://hub.docker.com/r/arokasprz100/cart-service)

## API description
API will probably be changed, any suggestions on how to do so are very welcome. Supported operations:
 * getting empty (new) shopping cart (creates it in the database)
 * adding item to the cart
 * removing item from the cart
 * getting the cart
 * getting all items from the cart
 * getting single item from the cart
 * editing item in the cart
 * submitting cart (creating orders)
 

### Getting empty shopping cart

URL: `http://localhost:8081/carts` (method: GET)

Returns ID of created cart.


### Adding item to the cart

URL: `http://localhost:8081/carts/<cart_id>/items` (method: POST)

Example body:
```json
{
    "offerId": 1,
    "tierId": 2,
    "description": "Sample shopping cart item description."
}
```

Returns string message informing about success of the operation.


### Removing item from the cart

URL: `http://localhost:8081/carts/<cart_id>/items/<item_id>` (method: DELETE)

Returns string message informing about success of the operation.


### Getting the cart

URL: `http://localhost:8081/carts/<cart_id>` (method: GET)

Example response:
```json
{
    "id": 1,
    "totalPrice": 15.00,
    "items": [
        {
            "id": 1,
            "description": "test",
            "offerId": 1,
            "offerOwnerId": 1,
            "offerTitle": "Example offer 2",
            "tierId": 2,
            "tierTitle": "tier2",
            "tierPrice": 7.50
        },
        {
            "id": 2,
            "description": "test",
            "offerId": 1,
            "offerOwnerId": 1,
            "offerTitle": "Example offer 2",
            "tierId": 2,
            "tierTitle": "tier2",
            "tierPrice": 7.50
        }
    ]
}
```

### Getting all items from the cart

URL: `http://localhost:8082/carts/<cart_id>/items` (method: GET)

Example response:
```json
[
    {
        "id": 1,
        "description": "test",
        "offerId": 1,
        "offerOwnerId": 1,
        "offerTitle": "Example offer 2",
        "tierId": 2,
        "tierTitle": "tier2",
        "tierPrice": 7.50
    },
    {
        "id": 2,
        "description": "test",
        "offerId": 1,
        "offerOwnerId": 1,
        "offerTitle": "Example offer 2",
        "tierId": 2,
        "tierTitle": "tier2",
        "tierPrice": 7.50
    }
]
```


### Getting single item from the cart

URL: `http://localhost:8081/carts/<cart_id>/items/<item_id>` (method: GET)

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

URL: `http://localhost:8081/carts/<cart_id>/items/<item_id>` (method: POST)

Example body:
```json
{
    "offerId": 1,
    "tierId": 2,
    "description": "test after edit"
}
```

Returns string message informing about success of the operation.


### Submitting the cart

URL: `http://localhost:8081/carts/submission` (method: POST)

Example body
```json
{
    "cartId": 1,
    "buyerId": 1
}
```

Returns string with a message about success. Creates orders via the orders-service and payments via the payment-service.

