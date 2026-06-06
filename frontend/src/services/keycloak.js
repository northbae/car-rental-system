import Keycloak from 'keycloak-js'

const keycloakConfig = {
  url: '/auth',
  realm: 'car-rental',
  clientId: 'rental-client'
}

const keycloak = new Keycloak(keycloakConfig)

export const initKeycloak = (onResult) => {
  // Use check-sso so the app can mount even if not logged in (to show Register page)
  keycloak.init({ onLoad: 'check-sso', pkceMethod: 'S256' })
    .then((authenticated) => {
        onResult(authenticated, keycloak)
    })
    .catch((error) => {
      console.error('Keycloak init failed', error)
      onResult(false, null)
    })
}

export const login = () => keycloak.login({ redirectUri: window.location.origin })
export const getToken = () => keycloak.token
export const updateToken = (minValidity = 5) => keycloak.updateToken(minValidity)
export const logout = () => keycloak.logout()
export const getUserProfile = () => keycloak.loadUserProfile()

export default keycloak
