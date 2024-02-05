export default [
  {
    path: "/",
    component: () => import("../layout/Index"),
    name: 'frame',
    children: [
      {
        path: "/",
        redirect: "/admin"
      }
    ]
  },
  {
    path: "/login",
    component: () => import("../views/login"),
    name: 'login'
  },
  //404页面捕获
  {
    path: '/:pathMatch(.*)*',
    name: 'NotFound',
    component: () => import('../views/404')
  },
];
