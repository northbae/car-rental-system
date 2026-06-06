<template>
  <div class="register-container">
    <el-card class="register-card">
      <template #header>
        <div class="card-header">
          <h2>Create Account</h2>
          <p>Join the Car Rental System</p>
        </div>
      </template>

      <el-form :model="form" :rules="rules" ref="registerForm" label-position="top">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="Username" prop="username">
              <el-input v-model="form.username" placeholder="johndoe"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Email" prop="email">
              <el-input v-model="form.email" placeholder="john@example.com"></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-form-item label="Password" prop="password">
          <el-input v-model="form.password" type="password" show-password></el-input>
        </el-form-item>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="First Name" prop="firstName">
              <el-input v-model="form.firstName"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Last Name" prop="lastName">
              <el-input v-model="form.lastName"></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="Role" prop="role">
              <el-select v-model="form.role" placeholder="Select role" style="width: 100%">
                <el-option label="Employee" value="EMPLOYEE"></el-option>
                <el-option label="Manager" value="MANAGER"></el-option>
                <el-option label="Admin" value="ADMIN"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Department" prop="departmentId">
              <el-input v-model="form.departmentId" placeholder="e.g. IT, HR"></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <div class="actions">
          <el-button type="primary" @click="handleRegister" :loading="loading" block>Register Now</el-button>
        </div>

        <div class="login-link">
          Already have an account? <router-link to="/login">Login here</router-link>
        </div>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import axios from 'axios'

const router = useRouter()
const registerForm = ref(null)
const loading = ref(false)

const form = reactive({
  username: '',
  password: '',
  email: '',
  firstName: '',
  lastName: '',
  role: 'EMPLOYEE',
  departmentId: 'GENERAL'
})

const rules = {
  username: [{ required: true, message: 'Please input username', trigger: 'blur' }],
  password: [{ required: true, message: 'Please input password', trigger: 'blur' }, { min: 6, message: 'Min 6 characters', trigger: 'blur' }],
  email: [{ required: true, message: 'Please input email', trigger: 'blur' }, { type: 'email', message: 'Invalid email', trigger: 'blur' }],
  firstName: [{ required: true, message: 'Please input first name', trigger: 'blur' }],
  lastName: [{ required: true, message: 'Please input last name', trigger: 'blur' }],
  role: [{ required: true, message: 'Please select role', trigger: 'change' }],
  departmentId: [{ required: false, trigger: 'blur' }]
}

const handleRegister = async () => {
  if (!registerForm.value) return

  await registerForm.value.validate(async (valid) => {
    if (valid) {
      loading.value = true
      try {
        // Calling our auth-service via Gateway (permitted in SecurityConfig)
        await axios.post('/api/v1/users', form)
        ElMessage.success('Registration successful! Please login.')
        router.push('/login')
      } catch (error) {
        console.error(error)
        ElMessage.error(error.response?.data?.message || 'Registration failed')
      } finally {
        loading.value = false
      }
    }
  })
}
</script>

<style scoped>
.register-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background-color: #f5f7fa;
}
.register-card {
  width: 100%;
  max-width: 600px;
}
.card-header {
  text-align: center;
}
.card-header h2 {
  margin: 0;
  color: #409EFF;
}
.card-header p {
  margin: 5px 0 0;
  color: #909399;
}
.actions {
  margin-top: 20px;
}
.actions .el-button {
  width: 100%;
}
.login-link {
  margin-top: 20px;
  text-align: center;
  font-size: 14px;
  color: #606266;
}
</style>
