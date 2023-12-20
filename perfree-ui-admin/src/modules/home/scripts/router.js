export default function(menusRouter, moduleName) {
  let router = [];
  // 动态路由
  for (let item of menusRouter)
    router.push({
      name: item.componentName,
      path: item.path,
      component: () =>
        import(
          /* webpackChunkName: "views/[request]" */
          `../views/${item.component}`
        ),
      meta: {
        moduleName: moduleName,
        name: item.name,
        resource: item.resource ? item.resource : []
      }
    });

  return router;
}
