![alt text](https://github.com/Dankosik/stock-api/blob/master/Untitled%20Diagram.drawio.png)<br/><br/><br/>
https://github.com/Dankosik/moex-stock-api<br/><br/>
https://github.com/Dankosik/yahoo-stock-api<br/><br/>
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
`[GET] https://dankos.com/stock-api/stocks/price` <br/><br/>
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
{
    "stocks": [
        {
            "ticker": "YNDX",
            "stockPrice": {
                "value": 1894,
                "minorUnits": 100,
                "currency": "USD"
            },
            "time": "18:08:22.604756537"
        },
        {
            "ticker": "YNDX",
            "stockPrice": {
                "value": 190300,
                "minorUnits": 100,
                "currency": "RUR"
            },
            "time": "18:49:00"
        }
    ]
}
```
### Request<br/>
`[GET] https://dankos.com/stock-api/stocks/YNDX/baseInfo` <br/><br/>

### Responnse<br/>
```js
{
    "companies": [
        {
            "ticker": "YNDX",
            "companyName": "Yandex N.V.",
            "exchange": "NASDAQ"
        },
        {
            "ticker": "YNDX",
            "companyName": "PLLC Yandex N.V. class A shs",
            "exchange": "MOEX"
        }
    ]
}
```

## run
run with default profile
```
docker run -p 8081:8080 dankosik/stock-api
```
run with kuber profile
```
docker run -p 8081:8080 -e "SPRING_PROFILES_ACTIVE=kuber" dankosik/stock-api
