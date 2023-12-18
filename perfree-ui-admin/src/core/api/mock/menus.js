export default [
  {
    id: "elastic",
    name: "弹性计算",
    leaf: false,
    children: [
      {
        id: "ecs",
        name: "云服务器 ECS",
        leaf: true,
        page: "/ecs",
      }
    ],
    icon: "cpu",
    version: "0.1.0",
    host: "http://127.0.0.1/modules"
  }
];
