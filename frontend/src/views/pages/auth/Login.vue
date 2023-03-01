<script setup>
import { useLayout } from '@/layout/composables/layout';
import { computed, ref } from 'vue';
import {AuthService} from "@/service/AuthService";

const { layoutConfig, contextPath } = useLayout();
const email = ref('');
const password = ref('');
const checked = ref(false);

const service = new AuthService();

const logoUrl = computed(() => {
    return `${contextPath}layout/images/${layoutConfig.darkTheme.value ? 'logo-white' : 'logo-dark'}.svg`;
});

function submit() {
  service.login({
    email: email.value
  })
}
</script>

<template>
    <div class="surface-ground flex align-items-center justify-content-center min-h-screen min-w-screen overflow-hidden">
        <div class="flex flex-column align-items-center justify-content-center">
            <img :src="logoUrl" alt="Sakai logo" class="mb-5 w-6rem flex-shrink-0" />
            <div>
                <form>
                    <div class="w-full surface-card py-8 px-5 sm:px-8" style="border-radius: 53px">
                        <div class="text-center mb-5">
                            <div class="text-900 text-3xl font-medium mb-3">Sign in to continue</div>
                        </div>

                        <div>
                            <label class="block text-900 text-xl font-medium mb-2" for="email1">Email</label>
                            <InputText id="email1" v-model="email" class="w-full md:w-30rem mb-5" placeholder="sample@tuyendev.github.io" style="padding: 1rem" type="text" />
                            <label class="block text-900 font-medium text-xl mb-2" for="password1">Password</label>
                            <Password id="password1" v-model="password" :feedback="false" :toggleMask="true" class="w-full mb-3" inputClass="w-full" inputStyle="padding:1rem" placeholder="********"></Password>

                            <div class="flex align-items-center justify-content-between mb-5 gap-5">
                                <div class="flex align-items-center">
                                    <Checkbox id="rememberme1" v-model="checked" binary class="mr-2"></Checkbox>
                                    <label for="rememberme1">Remember me</label>
                                </div>
                                <a class="font-medium no-underline ml-2 text-right cursor-pointer" style="color: var(--primary-color)">Forgot password?</a>
                            </div>
                            <Button class="w-full p-3 text-xl" label="Sign In" @click.prevent="submit"></Button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</template>

<style scoped>
.pi-eye {
    transform: scale(1.6);
    margin-right: 1rem;
}

.pi-eye-slash {
    transform: scale(1.6);
    margin-right: 1rem;
}
</style>