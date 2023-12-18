export default [
  {
    path: "/",
    component: () => import("../layout/Index"),
    name: '111',
    children: [
      {
        path: "/",
        redirect: "/home"
      },
      {
        path: "/home",
        component: () => import("../views/Home")
      }
    ]
  }
];
