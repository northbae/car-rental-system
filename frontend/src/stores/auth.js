import { defineStore } from 'pinia'
import keycloak, { logout } from '../services/keycloak'

export const useAuthStore = defineStore('auth', {
  state: () => ({
    user: null,
    roles: [],
    token: null,
  }),
  getters: {
    isAuthenticated: (state) => !!state.token,
    hasRole: (state) => (role) => state.roles.includes(role),
    isEmployee: (state) => state.roles.includes('EMPLOYEE'),
    isManager: (state) => state.roles.includes('MANAGER'),
    isAdmin: (state) => state.roles.includes('ADMIN'),
  },
  actions: {
    setUser(keycloakInstance) {
      this.token = keycloakInstance.token
      // Keycloak resource access roles or realm access roles
      this.roles = keycloakInstance.realmAccess?.roles || []
      this.user = {
        username: keycloakInstance.tokenParsed?.preferred_username,
        email: keycloakInstance.tokenParsed?.email,
        name: keycloakInstance.tokenParsed?.name,
      }
    },
    clearUser() {
      this.user = null
      this.roles = []
      this.token = null
    },
    logoutUser() {
      this.clearUser()
      logout()
    }
  }
})
