import { createRouter, createWebHistory } from 'vue-router'
import Layout from "../layout/Layout.vue";

const routes =[
    {
      path: '/',
      name: 'layout',
      component: Layout,
      children: [
        {
          path: '/',
          name: 'index',
          component: () => import('@/views/Index.vue')
        },
        {
          path: '/count',
          name: 'count',
          component: () => import('@/views/Count.vue')
        },
        {
          path: '/count2',
          name: 'count2',
          component: () => import('@/views/Count2.vue')
        },
        {
          path: '/count3',
          name: 'count3',
          component: () => import('@/views/Count3.vue')
        },
        {
          path: '/user',
          name: 'user',
          component: () => import('@/views/User.vue')
        },
        {
          path: '/person',
          name: 'person',
          component: () => import('@/views/Person.vue')
        },
        {
          path: '/password',
          name: 'password',
          component: () => import('@/views/Password.vue')
        },
        {
          path: '/faculty',
          name: 'faculty',
          component: () => import('@/views/Faculty.vue')
        },
        {
          path: '/taro',
          name: 'taro',
          component: () => import('@/views/Taro.vue')
        },
        {
          path: '/major',
          name: 'major',
          component: () => import('@/views/Major.vue')
        },
        {
          path: '/class',
          name: 'class',
          component: () => import('@/views/Class.vue')
        },
        {
          path: '/course',
          name: 'course',
          component: () => import('@/views/Course.vue')
        },
        {
          path: '/student',
          name: 'student',
          component: () => import('@/views/Student.vue')
        },
        {
          path: '/teacher',
          name: 'teacher',
          component: () => import('@/views/Teacher.vue')
        },
        {
          path: '/learning',
          name: 'learning',
          component: () => import('@/views/Learning.vue')
        },
        {
          path: '/teaching',
          name: 'teaching',
          component: () => import('@/views/Teaching.vue')
        },
        {
          path: '/teaching2',
          name: 'teaching2',
          component: () => import('@/views/Teaching2.vue')
        },
        {
          path: '/teachStu',
          name: 'teachStu',
          component: () => import('@/views/TeachStu.vue')
        },
        {
          path: '/learnTeacher',
          name: 'learnTeacher',
          component: () => import('@/views/LearnTeacher.vue')
        }
      ]
    },
    {
      path: '/register',
      name: 'register',
      component: () => import('@/views/Register.vue')
    },
    {
      path: '/login',
      name: 'login',
      component: () => import('@/views/Login.vue')
    }
  ]

const router = createRouter({
  history: createWebHistory(),
  routes,
});

router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token');
  if (to.name !== 'login' && to.name !== 'register' && !token) {
    next({ name: 'login' });
  } else {
    next();
  }
});

export default router
