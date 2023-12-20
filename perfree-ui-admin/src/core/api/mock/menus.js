export default [
  {
    id: 1,
    module: "home",
    name: "首页",
    icon: "House",
    version: "0.1.0",
    path: "/home",
    componentName: "home",
    component: "home"
  },
  {
    id: 2,
    module: "elastic",
    name: "elastic模块",
    icon: "Wallet",
    version: "0.1.0",
    path: "",
    componentName: "",
    component: "",
    children: [
      {
        id: 3,
        module: "elastic",
        componentName: "",
        name: "多级菜单",
        path: "",
        icon: "cpu",
        component: "",
        version: "0.1.0",
        children: [
          {
            id: 9,
            module: "elastic",
            componentName: "ecs",
            name: "ecs",
            path: "/ecs",
            icon: "cpu",
            component: "ecs",
            version: "0.1.0",
          },
          {
            id: 10,
            module: "elastic",
            componentName: "page",
            name: "page",
            path: "/page",
            icon: "cpu",
            component: "page",
            version: "0.1.0",
          }
        ]
      }
    ]
  },
  {
    id: 4,
    module: "database",
    name: "数据库",
    icon: "Coin",
    version: "0.1.0",
    path: "",
    componentName: "",
    component: "",
    children: [
      {
        id: 5,
        module: "database",
        componentName: "polarDB",
        name: "polarDB",
        path: "/polarDB",
        icon: "cpu",
        component: "polarDB",
        version: "0.1.0",
      }
    ]
  },
  {
    id: 6,
    module: "test",
    name: "数据库2",
    icon: "cpu",
    version: "0.1.0",
    path: "",
    componentName: "",
    component: "",
    children: [
      {
        id: 7,
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
