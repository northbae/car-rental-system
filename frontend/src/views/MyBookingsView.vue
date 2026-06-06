<template>
  <div class="my-bookings">
    <h2>My Bookings</h2>

    <el-table v-loading="loading" :data="bookings" style="width: 100%">
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="carInfo" label="Car">
        <template #default="scope">
           {{ scope.row.carBrand }} {{ scope.row.carModel }} ({{ scope.row.carRegNumber }})
        </template>
      </el-table-column>
      <el-table-column prop="startDate" label="Start Date" />
      <el-table-column prop="endDate" label="End Date" />
      <el-table-column prop="status" label="Status">
        <template #default="scope">
          <el-tag :type="getStatusType(scope.row.status)">{{ scope.row.status }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="purpose" label="Purpose" />
      <el-table-column label="Actions">
        <template #default="scope">
          <el-button
            v-if="scope.row.status === 'PENDING_APPROVAL' || scope.row.status === 'PENDING'"
            size="small"
            type="danger"
            @click="cancelBooking(scope.row.id)"
          >
            Cancel
          </el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import api from '../services/api'

const loading = ref(false)
const bookings = ref([])

const fetchBookings = async () => {
  loading.value = true
  try {
    const response = await api.get('/applications')
    bookings.value = response.data.content || response.data || []
  } catch (error) {
    ElMessage.error('Failed to fetch bookings')
    console.error(error)
  } finally {
    loading.value = false
  }
}

const getStatusType = (status) => {
  switch (status) {
    case 'APPROVED': return 'success'
    case 'REJECTED': return 'danger'
    case 'PENDING_APPROVAL':
    case 'PENDING': return 'warning'
    case 'CANCELED': return 'info'
    default: return ''
  }
}

const cancelBooking = async (id) => {
  try {
    await ElMessageBox.confirm('Are you sure you want to cancel this booking?', 'Warning', {
      confirmButtonText: 'Yes',
      cancelButtonText: 'No',
      type: 'warning',
    })
    
    await api.delete(`/applications/${id}`)
    ElMessage.success('Booking cancelled successfully')
    fetchBookings()
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('Failed to cancel booking')
      console.error(error)
    }
  }
}

onMounted(() => {
  fetchBookings()
})
</script>

<style scoped>
.my-bookings {
  padding: 20px;
}
</style>
