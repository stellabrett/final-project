<script setup>
import {useCourseStore} from "@/stores/CourseStore.js";
import {onMounted, ref, computed, onBeforeMount} from "vue";
import {useRouter} from "vue-router";
import CourseCard from "@/components/CourseCard.vue";
import jwtDecode from "jwt-decode";

const courseStore = useCourseStore()
const router = useRouter(); // router
const isVisible = ref(false);
const courses = computed(() => courseStore.courses);
const userCourses = computed(() => courseStore.userCourses);
const userId = jwtDecode(localStorage.getItem("accessToken")).userId;
const err = false;

onBeforeMount(() => {
  const userId = jwtDecode(localStorage.getItem("accessToken")).userId;
  
});

onMounted(() => {
  const userId = jwtDecode(localStorage.getItem("accessToken")).userId;
  
  ShowCourses();
  ShowUserCourses(userId);

});

async function ShowCourses() {
  await courseStore.showCourses();
}

ShowCourses();

async function ShowUserCourses() {
  await courseStore.showUserCourses(userId);
}

ShowUserCourses();
</script>

<template>
  <div>
    <h2 style="text-align: center;" class="mx-auto text-h4 py-10" color="secondary">Deine gebuchten Kurse </h2>
    <v-row class="d-flex ma-2 ">
      <v-col v-for="courses in userCourses" :key="courses.courseId" cols="12" sm="6" md="4" lg="3">
        <CourseCard
            :courseTitle="courses.courseTitle"
            :startDate="courses.startDate"
            :description="courses.description"
            :courseId="courses.courseId"
            :teacher="courses.teacher"
            :price="courses.price"
            :image="courses.image"
        />
      </v-col>
    </v-row>
  </div>
  <v-sheet color="secondary" class="mt-3">
    <h2  class="mx-auto text-h4 py-10 text-black text-center ">Unsere aktuellen Kurse</h2>
    <v-row class="d-flex ma-2 ">
      <v-col v-for="course in courses" :key="course.courseId" cols="12" sm="6" md="4" lg="3">
        <CourseCard
            :courseTitle="course.courseTitle"
            :startDate="course.startDate"
            :description="course.description"
            :courseId="course.courseId"
            :teacher="course.teacher"
            :price="course.price"
            :image="course.image"
        />
      </v-col>
    </v-row>
  </v-sheet>

</template>