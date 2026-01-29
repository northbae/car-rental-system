## OAuth2 Authorization

### Формулировка

* Для авторизации используется OpenID Connect, в роли Identity Provider - стороннее решение.
* На Identity Provider настроено
  использование [Resource Owner Password flow](https://auth0.com/docs/authorization/flows/resource-owner-password-flow)
  (в одном запросе передается `clientId`, `clientSecret`, `username`, `password`).
* Все методы `/api/**` (кроме `/api/v1/authorize` и `/api/v1/callback`) на всех сервисах закрыты token-based
  авторизацией.
* В качестве токена использован [JWT](https://jwt.io/introduction), для валидации токена
  использован [JWKs](https://auth0.com/docs/security/tokens/json-web-tokens/json-web-key-sets).
* JWT токен пробрасывается между сервисами, при получении запроса валидацию токена так же реализована через JWKs.
* Если авторизация некорректная (отсутствие токена, ошибка валидации JWT токена, закончилось время жизни токена
  (поле `exp` в payload)), то отдается 401 ошибку.
