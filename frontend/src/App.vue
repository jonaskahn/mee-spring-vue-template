<script setup>
import { getCurrentLocale, switchLanguage } from "@/helper"
import { onBeforeMount } from "vue"

onBeforeMount(() => {
  switchLanguage(getCurrentLocale())
})
</script>

<template>
  <router-view v-slot="{ Component }">
    <transition :duration="500" mode="out-in" name="slide-fade">
      <component :is="Component" />
    </transition>
  </router-view>

  <Toast
    draggable="false"
    error-icon="pi pi-times-circle"
    group="desktop"
    info-icon="pi pi-info-circle"
    position="top-right"
    success-icon="pi pi-check-circle"
    warn-icon="pi pi-exclamation-circle"
  />
  <Toast
    draggable="false"
    error-icon="pi pi-times-circle"
    group="mobile"
    info-icon="pi pi-info-circle"
    position="top-center"
    success-icon="pi pi-check-circle"
    warn-icon="pi pi-exclamation-circle"
  />
</template>

<style scoped></style>
