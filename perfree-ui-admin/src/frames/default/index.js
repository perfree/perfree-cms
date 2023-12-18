import RouterStatic from "./scripts/routerStatic";
import Store from "./scripts/store";

// 样式
import "minireset.css/minireset.min.css";
// import "./style/base.less";

export default myRuntimePublicPath => {
  __webpack_public_path__ = myRuntimePublicPath;
  return {
    routerStatic: RouterStatic,
    store: Store
  };
};
