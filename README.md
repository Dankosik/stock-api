![alt text](https://github.com/Dankosik/stock-api/blob/master/Untitled%20Diagram.drawio.png)<br/>
# Examples<br/>
### Request<br/>
`[GET] https://dankos.com/stock-api/stocks/AAPL/price`
### Responnse<br/>
```js
{
    "ticker": "AAPL",
    "stockPrice": {
        "value": 17210,
        "minorUnits": 100,
        "currency": "USD"
    },
    "time": "11:34:25.626183643"
}
```

### Request<br/>
`[GET] https://dankos.com/stock-api/stocks/SBER/price`
### Responnse<br/>
```js
{
    "ticker": "SBER",
    "stockPrice": {
        "value": 12488,
        "minorUnits": 100,
        "currency": "RUR"
    },
    "time": "18:49:55"
}
```

### Request<br/>
`[GET] https://dankos.com/stock-api/stocks/price`
Body
```js
{
    "tickers": [
        "AAPL",
        "SBER"
    ]
}
```
### Responnse<br/>
```js
[
    {
        "ticker": "AAPL",
        "stockPrice": {
            "value": 17210,
            "minorUnits": 100,
            "currency": "USD"
        },
        "time": "11:38:07.342512265"
    },
    {
        "ticker": "SBER",
        "stockPrice": {
            "value": 12488,
            "minorUnits": 100,
            "currency": "RUR"
        },
        "time": "18:49:55"
    }
]
```

## run
run with default profile
```
docker run -p 8081:8080 dankosik/stock-api
```
run with kuber profile
```
docker run -p 8081:8080 -e "SPRING_PROFILES_ACTIVE=kuber" dankosik/stock-api
