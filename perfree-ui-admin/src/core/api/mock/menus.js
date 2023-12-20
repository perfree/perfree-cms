export default [
  {
    module: "home",
    name: "首页",
    icon: "cpu",
    version: "0.1.0",
    path: "/home",
    componentName: "home",
    component: "home"
  },
  {
    module: "elastic",
    name: "弹性计算",
    icon: "cpu",
    version: "0.1.0",
    path: "",
    componentName: "",
    component: "",
    children: [
      {
        module: "elastic",
        componentName: "ecs",
        name: "云服务器 ECS",
        path: "/ecs",
        icon: "cpu",
        component: "ecs",
        version: "0.1.0"
      }
    ]
  },
  {
    module: "database",
    name: "数据库",
    icon: "cpu",
    version: "0.1.0",
    path: "",
    componentName: "",
    component: "",
    children: [
      {
        module: "database",
        componentName: "polarDB",
        name: "polarDB",
        path: "/polarDB",
        icon: "cpu",
        component: "polarDB",
        version: "0.1.0"
      }
    ]
  },
  {
    module: "test",
    name: "数据库2",
    icon: "cpu",
    version: "0.1.0",
    path: "",
    componentName: "",
    component: "",
    children: [
      {
        module: "test",
        componentName: "test",
        name: "test",
        path: "/test",
        icon: "cpu",
        component: "test",
        version: "0.1.0"
      }
    ]
  }
];
