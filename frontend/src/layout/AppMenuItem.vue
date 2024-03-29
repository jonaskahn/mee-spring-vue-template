<script setup>
import { onBeforeMount, ref, watch } from "vue"
import { useRoute } from "vue-router"
import { useLayout } from "@/layout/composables/layout"
import AppMenuBadge from "@/layout/AppMenuBadge.vue"
import { LocalStorageManager } from "@/helper"
import { containsAny } from "@/utils/arrays"

const route = useRoute()

const { layoutConfig, layoutState, setActiveMenuItem, onMenuToggle } = useLayout()

const props = defineProps({
  item: {
    type: Object,
    default: () => ({})
  },
  index: {
    type: Number,
    default: 0
  },
  root: {
    type: Boolean,
    default: true
  },
  parentItemKey: {
    type: String,
    default: null
  }
})

const isActiveMenu = ref(false)
const itemKey = ref(null)

onBeforeMount(() => {
  itemKey.value = props.parentItemKey
    ? props.parentItemKey + "-" + props.index
    : String(props.index)

  const activeItem = layoutState.activeMenuItem

  isActiveMenu.value =
    activeItem === itemKey.value || activeItem ? activeItem.startsWith(itemKey.value + "-") : false
})

watch(
  () => layoutConfig.activeMenuItem.value,
  (newVal) => {
    isActiveMenu.value = newVal === itemKey.value || newVal.startsWith(itemKey.value + "-")
  }
)
const itemClick = (event, item) => {
  if (item.disabled) {
    event.preventDefault()
    return
  }

  const { overlayMenuActive, staticMenuMobileActive } = layoutState

  if ((item.to || item.url) && (staticMenuMobileActive.value || overlayMenuActive.value)) {
    onMenuToggle()
  }

  if (item.command) {
    item.command({ originalEvent: event, item: item })
  }

  const foundItemKey = item.items
    ? isActiveMenu.value
      ? props.parentItemKey
      : itemKey
    : itemKey.value

  setActiveMenuItem(foundItemKey)
}

const checkActiveRoute = (item) => {
  return route.path === item.to
}
</script>

<template>
  <li
    v-if="item.hasPermissionVisibility"
    :class="{ 'layout-root-menuitem': root, 'active-menuitem': isActiveMenu }"
  >
    <div v-if="root && item.visible !== false" class="layout-menuitem-root-text">
      {{ $t(item.label) }}
    </div>
    <a
      v-if="(!item.to || item.items) && item.visible !== false"
      :class="item.class"
      :href="item.url"
      :target="item.target"
      tabindex="0"
      @click="itemClick($event, item, index)"
    >
      <i :class="item.icon" class="layout-menuitem-icon"></i>
      <span class="layout-menuitem-text">{{ $t(item.label) }}</span>
      <app-menu-badge :badge="item.badge"></app-menu-badge>
      <i v-if="item.items" class="pi pi-fw pi-angle-down layout-submenu-toggler"> </i>
    </a>
    <router-link
      v-if="item.to && !item.items && item.visible !== false"
      :class="[item.class, { 'active-route': checkActiveRoute(item) }]"
      :to="item.to"
      tabindex="0"
      @click="itemClick($event, item, index)"
    >
      <i :class="item.icon" class="layout-menuitem-icon"></i>
      <span class="layout-menuitem-text">{{ $t(item.label) }}</span>
      <app-menu-badge :badge="item.badge"></app-menu-badge>
      <i v-if="item.items" class="pi pi-fw pi-angle-down layout-submenu-toggler"></i>
    </router-link>
    <Transition v-if="item.items && item.visible !== false" name="layout-submenu">
      <ul v-show="root ? true : isActiveMenu" class="layout-submenu">
        <template v-for="(child, i) in item.items" :key="child">
          <app-menu-item
            :index="i"
            :item="child"
            :parentItemKey="itemKey"
            :root="false"
          ></app-menu-item>
        </template>
      </ul>
    </Transition>
  </li>
</template>

<style lang="scss" scoped></style>
