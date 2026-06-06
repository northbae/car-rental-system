<template>
  <div class="dept-stats">
    <h2>Department Statistics</h2>

    <el-card class="box-card" v-loading="loading">
      <template #header>
        <div class="card-header">
          <span>Search Department</span>
        </div>
      </template>
      <el-form :inline="true" @submit.prevent="fetchData">
        <el-form-item label="Department ID">
          <el-input v-model="deptId" placeholder="Enter Dept ID (e.g. 1)" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" native-type="submit">Load Data</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <div v-if="stats || quotas" class="results-container">
      <el-row :gutter="20">
        <el-col :span="12">
          <el-card>
            <template #header>
              <div class="card-header">
                <span>Statistics</span>
              </div>
            </template>
            <div v-if="stats">
               <p><strong>Total Bookings:</strong> {{ stats.totalBookings || 0 }}</p>
               <p><strong>Approved:</strong> {{ stats.approvedBookings || 0 }}</p>
               <p><strong>Rejected:</strong> {{ stats.rejectedBookings || 0 }}</p>
               <p><strong>Total Spending:</strong> {{ stats.totalSpending || 0 }}</p>
            </div>
            <el-empty v-else description="No statistics found"></el-empty>
          </el-card>
        </el-col>
        <el-col :span="12">
          <el-card>
            <template #header>
              <div class="card-header">
                <span>Quotas</span>
              </div>
            </template>
            <div v-if="quotas">
               <p><strong>Monthly Limit:</strong> {{ quotas.monthlyLimit || 0 }}</p>
               <p><strong>Used Amount:</strong> {{ quotas.usedAmount || 0 }}</p>
               <p><strong>Remaining:</strong> {{ (quotas.monthlyLimit || 0) - (quotas.usedAmount || 0) }}</p>
            </div>
            <el-empty v-else description="No quotas found"></el-empty>
          </el-card>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { ElMessage } from 'element-plus'
import api from '../services/api'

const deptId = ref('')
const loading = ref(false)
const stats = ref(null)
const quotas = ref(null)

const fetchData = async () => {
  if (!deptId.value) {
    ElMessage.warning('Please enter a Department ID')
    return
  }

  loading.value = true
  stats.value = null
  quotas.value = null

  try {
    const [statsRes, quotasRes] = await Promise.all([
      api.get(`/departments/${deptId.value}/statistics`),
      api.get(`/departments/${deptId.value}/quotas`)
    ])
    
    stats.value = statsRes.data
    quotas.value = quotasRes.data
  } catch (error) {
    ElMessage.error('Failed to fetch department data. Make sure ID is correct.')
    console.error(error)
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.dept-stats {
  padding: 20px;
}
.box-card {
  margin-bottom: 20px;
}
.results-container {
  margin-top: 20px;
}
</style>
