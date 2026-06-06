<template>
  <div class="cars-view">
    <h2>Available Cars</h2>
    
    <el-form :inline="true" :model="filters" class="filters">
      <el-form-item label="Date From">
        <el-date-picker v-model="filters.dateFrom" type="date" placeholder="Select date" value-format="YYYY-MM-DD" />
      </el-form-item>
      <el-form-item label="Date To">
        <el-date-picker v-model="filters.dateTo" type="date" placeholder="Select date" value-format="YYYY-MM-DD" />
      </el-form-item>
      <el-form-item label="Type">
        <el-select v-model="filters.carType" placeholder="Any" clearable>
          <el-option label="Sedan" value="SEDAN" />
          <el-option label="SUV" value="SUV" />
          <el-option label="Minivan" value="MINIVAN" />
        </el-select>
      </el-form-item>
      <el-form-item label="City">
        <el-input v-model="filters.city" placeholder="City" clearable />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="fetchCars">Search</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="loading" :data="cars" style="width: 100%; margin-top: 20px">
      <el-table-column prop="brand" label="Brand" />
      <el-table-column prop="model" label="Model" />
      <el-table-column prop="registrationNumber" label="Reg. Number" />
      <el-table-column prop="pricePerDay" label="Price/Day" />
      <el-table-column prop="type" label="Type" />
      <el-table-column label="Actions">
        <template #default="scope">
          <el-button size="small" type="success" @click="openBookDialog(scope.row)">Book</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- Booking Dialog -->
    <el-dialog v-model="dialogVisible" title="Book Car" width="50%">
      <el-form ref="bookFormRef" :model="bookForm" label-width="120px">
        <el-form-item label="Car">
          <el-input :value="selectedCar ? `${selectedCar.brand} ${selectedCar.model}` : ''" disabled />
        </el-form-item>
        <el-form-item label="Date From" required>
          <el-date-picker v-model="bookForm.dateFrom" type="date" placeholder="Start Date" value-format="YYYY-MM-DD" style="width: 100%" />
        </el-form-item>
        <el-form-item label="Date To" required>
          <el-date-picker v-model="bookForm.dateTo" type="date" placeholder="End Date" value-format="YYYY-MM-DD" style="width: 100%" />
        </el-form-item>
        <el-form-item label="Purpose" required>
          <el-input v-model="bookForm.purpose" type="textarea" :rows="3" placeholder="Purpose of trip" />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">Cancel</el-button>
          <el-button type="primary" :loading="bookingLoading" @click="submitBooking">Confirm</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import api from '../services/api'

const loading = ref(false)
const cars = ref([])
const filters = ref({
  dateFrom: '',
  dateTo: '',
  carType: '',
  city: ''
})

const dialogVisible = ref(false)
const selectedCar = ref(null)
const bookingLoading = ref(false)
const bookForm = ref({
  dateFrom: '',
  dateTo: '',
  purpose: ''
})

const fetchCars = async () => {
  loading.value = true
  try {
    const params = {}
    if (filters.value.dateFrom) params.dateFrom = filters.value.dateFrom
    if (filters.value.dateTo) params.dateTo = filters.value.dateTo
    if (filters.value.carType) params.type = filters.value.carType
    if (filters.value.city) params.city = filters.value.city

    const response = await api.get('/cars', { params })
    cars.value = response.data.content || response.data || []
  } catch (error) {
    ElMessage.error('Failed to fetch cars')
    console.error(error)
  } finally {
    loading.value = false
  }
}

const openBookDialog = (car) => {
  selectedCar.value = car
  bookForm.value.dateFrom = filters.value.dateFrom || ''
  bookForm.value.dateTo = filters.value.dateTo || ''
  bookForm.value.purpose = ''
  dialogVisible.value = true
}

const submitBooking = async () => {
  if (!bookForm.value.dateFrom || !bookForm.value.dateTo || !bookForm.value.purpose) {
    ElMessage.warning('Please fill all fields')
    return
  }

  bookingLoading.value = true
  try {
    await api.post('/applications', {
      carId: selectedCar.value.id,
      startDate: bookForm.value.dateFrom,
      endDate: bookForm.value.dateTo,
      purpose: bookForm.value.purpose
    })
    ElMessage.success('Booking application submitted successfully')
    dialogVisible.value = false
  } catch (error) {
    ElMessage.error('Failed to submit booking application')
    console.error(error)
  } finally {
    bookingLoading.value = false
  }
}

onMounted(() => {
  fetchCars()
})
</script>

<style scoped>
.cars-view {
  padding: 20px;
}
.filters {
  margin-bottom: 20px;
}
</style>
