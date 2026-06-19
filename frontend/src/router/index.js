import { createRouter, createWebHistory } from 'vue-router'
import { useAuthStore } from '../stores/auth'
import { login } from '../services/keycloak'
import MainLayout from '../layouts/MainLayout.vue'

const routes = [
  {
    path: '/register',
    name: 'Register',
    component: () => import('../views/RegisterView.vue')
  },
  {
    path: '/login',
    name: 'Login',
    component: {
      template: '<div style="display: flex; justify-content: center; align-items: center; height: 100vh;"><h3>Redirecting to login...</h3></div>',
      mounted() {
        login()
      }
    }
  },
  {
    path: '/',
    component: MainLayout,
    children: [
      {
        path: '',
        name: 'Dashboard',
        component: () => import('../views/DashboardView.vue'),
        meta: { requiresAuth: true }
      },
      // Employee Routes
      {
        path: 'cars',
        name: 'Cars',
        component: () => import('../views/CarsView.vue'),
        meta: { requiresAuth: true, role: 'EMPLOYEE' }
      },
      {
        path: 'my-bookings',
        name: 'MyBookings',
        component: () => import('../views/MyBookingsView.vue'),
        meta: { requiresAuth: true, role: 'EMPLOYEE' }
      },
      // Manager Routes
      {
        path: 'department-bookings',
        name: 'DepartmentBookings',
        component: () => import('../views/DepartmentBookingsView.vue'),
        meta: { requiresAuth: true, role: 'MANAGER' }
      },
      {
        path: 'department-stats',
        name: 'DepartmentStats',
        component: () => import('../views/DepartmentStatsView.vue'),
        meta: { requiresAuth: true, role: 'MANAGER' }
      },
      // Admin Routes
      {
        path: 'partners',
        name: 'Partners',
        component: () => import('../views/PartnersView.vue'),
        meta: { requiresAuth: true, role: 'ADMIN' }
      },
      {
        path: 'logs',
        name: 'Logs',
        component: () => import('../views/LogsView.vue'),
        meta: { requiresAuth: true, role: 'ADMIN' }
      },
      {
        path: 'statistics',
        name: 'Statistics',
        component: () => import('../views/StatisticsView.vue'),
        meta: { requiresAuth: true, role: 'ADMIN' }
      }
    ]
  },
  {
    path: '/:pathMatch(.*)*',
    redirect: '/'
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  const authStore = useAuthStore()

  if (to.meta.requiresAuth && !authStore.isAuthenticated) {
    next('/register') // Redirect to register if not logged in
  } else if (to.meta.role && !authStore.hasRole(to.meta.role)) {
    next({ name: 'Dashboard' })
  } else {
    next()
  }
})

export default router
