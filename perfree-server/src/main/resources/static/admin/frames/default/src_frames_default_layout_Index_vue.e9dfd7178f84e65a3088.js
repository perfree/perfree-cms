(self.webpackChunkframes_default=self.webpackChunkframes_default||[]).push([["src_frames_default_layout_Index_vue"],{2401:function(e,n,t){"use strict";t.d(n,{t:function(){return o}});const o={STORAGE_USER_INFO:"user_info",STORAGE_TOKEN:"token_info"}},9067:function(e,n,t){"use strict";function o(){return axios.post("/api/captchaImage",{})}function a(e){return axios.post("/api/login",e)}function r(){return axios.get("/api/menuList")}function l(){return axios.get("/api/userInfo")}t.d(n,{bp:function(){return o},eY:function(){return l},sh:function(){return r},x4:function(){return a}})},8466:function(e,n,t){"use strict";t.r(n);var o=t(2650),a=t.n(o),r=t(196),l=t.n(r)()(a());l.push([e.id,".common-layout,\n.el-container,\n.el-aside {\n  height: 100%;\n}\n.el-menu {\n  height: calc(100% - 50px);\n}\n.el-header {\n  height: 50px;\n  padding: 0;\n}\n.el-menu {\n  border: none;\n}\n.header-logo {\n  height: 50px;\n  background: var(--side-background-color);\n}\n.side-container {\n  box-shadow: 2px 0 6px rgba(0, 21, 41, 0.35);\n  width: 200px;\n  transition: width 0.28s;\n  background: var(--side-background-color);\n}\n.side-menu {\n  max-width: 200px;\n}\n.header-logo-box {\n  text-align: center;\n  line-height: 50px;\n  display: flex;\n  align-items: center;\n  height: 50px;\n  width: 200px;\n}\n.side-container-small {\n  width: 64px;\n}\n.header-logo-img {\n  margin-left: 12px;\n  margin-right: 12px;\n  width: 40px;\n}\n.header-title {\n  text-align: left;\n  color: white;\n}\n/** 动画 */\n.fade-enter-active,\n.fade-leave-active {\n  transition: opacity 0.5s ease;\n}\n.fade-enter-from,\n.fade-leave-to {\n  opacity: 0;\n}\n",""]),n.default=l},5714:function(e,n,t){"use strict";t.r(n);var o=t(2650),a=t.n(o),r=t(196),l=t.n(r)()(a());l.push([e.id,".header {\n  height: 100%;\n  display: flex;\n  line-height: 50px;\n  box-shadow: 0 1px 4px rgba(0, 21, 41, 0.08);\n}\n.header-left {\n  color: var(--el-text-color-regular);\n  display: flex;\n}\n.menu-change-btn,\n.fullScreen-btn {\n  display: inline-block;\n  line-height: 50px;\n  width: 50px;\n  text-align: center;\n  cursor: pointer;\n  font-size: 16px;\n}\n.breadcrumb {\n  line-height: 50px;\n}\n.breadcrumb .el-breadcrumb__inner a,\n.breadcrumb .el-breadcrumb__inner.is-link {\n  font-weight: 400;\n}\n.breadcrumb .el-breadcrumb__separator {\n  font-weight: 400;\n}\n.header-right {\n  margin-left: auto;\n  display: flex;\n}\n.header-user-box {\n  height: 50px;\n  line-height: 50px;\n  cursor: pointer;\n  margin-right: 20px;\n}\n.header-user-box .el-dropdown-link {\n  display: flex;\n  align-items: center;\n}\n",""]),n.default=l},3319:function(e,n,t){e.exports=t.p+"img/bbceeeff56a7c8c7174f1fdf55b87d1e.png"},1567:function(e,n,t){"use strict";t.r(n),t.d(n,{default:function(){return w}});var o=t(520),a=t(3319),r=t.n(a),l=t(4593),c=t(5887),i={__name:"menuTree",props:{menuList:[]},setup(e){const n=e;return(e,t)=>{const a=(0,o.resolveComponent)("el-icon"),r=(0,o.resolveComponent)("menuTree",!0),l=(0,o.resolveComponent)("el-sub-menu"),c=(0,o.resolveComponent)("el-menu-item");return(0,o.openBlock)(!0),(0,o.createElementBlock)(o.Fragment,null,(0,o.renderList)(n.menuList,(e=>((0,o.openBlock)(),(0,o.createElementBlock)(o.Fragment,null,[e.children&&e.children.length>0?((0,o.openBlock)(),(0,o.createBlock)(l,{key:0,index:e.id},{title:(0,o.withCtx)((()=>[e.icon?((0,o.openBlock)(),(0,o.createBlock)(a,{key:0},{default:(0,o.withCtx)((()=>[((0,o.openBlock)(),(0,o.createBlock)((0,o.resolveDynamicComponent)(e.icon)))])),_:2},1024)):(0,o.createCommentVNode)("v-if",!0),(0,o.createElementVNode)("span",null,(0,o.toDisplayString)(e.name),1)])),default:(0,o.withCtx)((()=>[(0,o.createVNode)(r,{menuList:e.children},null,8,["menuList"])])),_:2},1032,["index"])):((0,o.openBlock)(),(0,o.createBlock)(c,{key:1,index:e.path},{title:(0,o.withCtx)((()=>[(0,o.createTextVNode)((0,o.toDisplayString)(e.name),1)])),default:(0,o.withCtx)((()=>[e.icon?((0,o.openBlock)(),(0,o.createBlock)(a,{key:0},{default:(0,o.withCtx)((()=>[((0,o.openBlock)(),(0,o.createBlock)((0,o.resolveDynamicComponent)(e.icon)))])),_:2},1024)):(0,o.createCommentVNode)("v-if",!0)])),_:2},1032,["index"]))],64)))),256)}}},s=t(2281),d=t(2401),u=t(9067);const p={class:"header"},m={class:"header-left"},f={key:0,class:"no-redirect"},h={key:1},x={class:"header-right"},g={class:"el-dropdown-link"};var v={__name:"myHeader",props:{menuIsCollapse:!1},emits:["update:menuIsCollapse"],setup(e,{emit:n}){const t=e,a=(0,l.useRouter)(),r=(0,o.ref)({}),c=n;let i=(0,o.ref)(null);function v(){t.menuIsCollapse?c("update:menuIsCollapse",!1):c("update:menuIsCollapse",!0)}function C(e){let n=e.matched.filter((e=>e.meta&&e.meta.name));(function(e){const n=e&&e.meta.name;return!!n&&"首页"===n.trim()})(n[0])||(n=[{path:"/home",meta:{name:"首页"}}].concat(n)),i.value=n.filter((e=>e.meta&&e.meta.name&&!1!==e.meta.breadcrumb))}function k(){if(!s.default.isEnabled)return!1;s.default.toggle()}function b(){localStorage.removeItem(d.t.STORAGE_TOKEN),a.replace("/login")}return(0,l.onBeforeRouteUpdate)((e=>{e.path.startsWith("/redirect/")||C(e)})),(0,u.eY)().then((e=>{r.value=e.data,localStorage.setItem(d.t.STORAGE_USER_INFO,JSON.stringify(e.data))})),C(a.currentRoute.value),(n,t)=>{const a=(0,o.resolveComponent)("Fold"),l=(0,o.resolveComponent)("el-icon"),c=(0,o.resolveComponent)("Expand"),s=(0,o.resolveComponent)("el-breadcrumb-item"),d=(0,o.resolveComponent)("el-breadcrumb"),u=(0,o.resolveComponent)("FullScreen"),C=(0,o.resolveComponent)("el-avatar"),_=(0,o.resolveComponent)("arrow-down"),w=(0,o.resolveComponent)("el-dropdown-item"),N=(0,o.resolveComponent)("el-dropdown-menu"),V=(0,o.resolveComponent)("el-dropdown");return(0,o.openBlock)(),(0,o.createElementBlock)("div",p,[(0,o.createElementVNode)("div",m,[(0,o.createElementVNode)("span",{onClick:v,class:"menu-change-btn"},[e.menuIsCollapse?((0,o.openBlock)(),(0,o.createBlock)(l,{key:1},{default:(0,o.withCtx)((()=>[(0,o.createVNode)(c)])),_:1})):((0,o.openBlock)(),(0,o.createBlock)(l,{key:0},{default:(0,o.withCtx)((()=>[(0,o.createVNode)(a)])),_:1}))]),(0,o.createVNode)(d,{class:"breadcrumb",separator:"/"},{default:(0,o.withCtx)((()=>[((0,o.openBlock)(!0),(0,o.createElementBlock)(o.Fragment,null,(0,o.renderList)((0,o.unref)(i),((e,n)=>((0,o.openBlock)(),(0,o.createBlock)(s,{to:{path:e.path}},{default:(0,o.withCtx)((()=>["noRedirect"===e.redirect||n===(0,o.unref)(i).length-1?((0,o.openBlock)(),(0,o.createElementBlock)("span",f,(0,o.toDisplayString)(e.meta.name),1)):((0,o.openBlock)(),(0,o.createElementBlock)("a",h,(0,o.toDisplayString)(e.meta.name),1))])),_:2},1032,["to"])))),256))])),_:1})]),(0,o.createElementVNode)("div",x,[(0,o.createElementVNode)("span",{onClick:k,class:"fullScreen-btn"},[(0,o.createVNode)(l,null,{default:(0,o.withCtx)((()=>[(0,o.createVNode)(u)])),_:1})]),(0,o.createElementVNode)("div",null,[(0,o.createVNode)(V,{trigger:"click",class:"header-user-box"},{dropdown:(0,o.withCtx)((()=>[(0,o.createVNode)(N,null,{default:(0,o.withCtx)((()=>[(0,o.createVNode)(w,null,{default:(0,o.withCtx)((()=>[(0,o.createTextVNode)("个人中心")])),_:1}),(0,o.createVNode)(w,{onClick:b},{default:(0,o.withCtx)((()=>[(0,o.createTextVNode)("退出登录")])),_:1})])),_:1})])),default:(0,o.withCtx)((()=>[(0,o.createElementVNode)("span",g,[(0,o.createVNode)(C,{src:"https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"}),(0,o.createElementVNode)("span",null,(0,o.toDisplayString)(r.value.userName),1),(0,o.createVNode)(l,{class:"el-icon--right"},{default:(0,o.withCtx)((()=>[(0,o.createVNode)(_)])),_:1})])])),_:1})])])])}}};t(2213);var C=v;const k={class:"common-layout"},b=(0,o.createElementVNode)("div",{class:"header-logo"},[(0,o.createElementVNode)("div",{class:"header-logo-box"},[(0,o.createElementVNode)("img",{src:r(),class:"header-logo-img"}),(0,o.createElementVNode)("div",{class:"header-title"},"xxx管理系统")])],-1);var _={__name:"Index",setup(e){const n=(0,c.useStore)(),t=(0,l.useRouter)();let a=(0,o.ref)(t.currentRoute.value.path);const r=n.getters.menus;let s=(0,o.ref)(!1);return(0,l.onBeforeRouteUpdate)((e=>{a.value=e.path})),(e,n)=>{const t=(0,o.resolveComponent)("el-menu"),l=(0,o.resolveComponent)("el-aside"),c=(0,o.resolveComponent)("el-header"),d=(0,o.resolveComponent)("router-view"),u=(0,o.resolveComponent)("el-main"),p=(0,o.resolveComponent)("el-container");return(0,o.openBlock)(),(0,o.createElementBlock)("div",k,[(0,o.createVNode)(p,null,{default:(0,o.withCtx)((()=>[(0,o.createVNode)(p,null,{default:(0,o.withCtx)((()=>[(0,o.createVNode)(l,{class:(0,o.normalizeClass)({"side-container":!0,"side-container-small":(0,o.unref)(s)})},{default:(0,o.withCtx)((()=>[b,(0,o.createVNode)(t,{"active-text-color":"var( --primary-color)","background-color":"var(--side-background-color)",class:"side-menu","text-color":"#fff","default-active":(0,o.unref)(a),router:"",collapse:(0,o.unref)(s),"collapse-transition":!1},{default:(0,o.withCtx)((()=>[(0,o.createVNode)(i,{"menu-list":(0,o.unref)(r)},null,8,["menu-list"])])),_:1},8,["default-active","collapse"])])),_:1},8,["class"]),(0,o.createVNode)(p,null,{default:(0,o.withCtx)((()=>[(0,o.createVNode)(c,null,{default:(0,o.withCtx)((()=>[(0,o.createVNode)(C,{"menu-is-collapse":(0,o.unref)(s),"onUpdate:menuIsCollapse":n[0]||(n[0]=e=>(0,o.isRef)(s)?s.value=e:s=e)},null,8,["menu-is-collapse"])])),_:1}),(0,o.createVNode)(u,{class:"app-main"},{default:(0,o.withCtx)((()=>[(0,o.createVNode)(o.Transition,{name:"fade"},{default:(0,o.withCtx)((()=>[(0,o.createVNode)(d)])),_:1})])),_:1})])),_:1})])),_:1})])),_:1})])}}};t(1838);var w=_},1838:function(e,n,t){var o=t(8466);o.__esModule&&(o=o.default),"string"==typeof o&&(o=[[e.id,o,""]]),o.locals&&(e.exports=o.locals),(0,t(950).Z)("0042d22e",o,!1,{})},2213:function(e,n,t){var o=t(5714);o.__esModule&&(o=o.default),"string"==typeof o&&(o=[[e.id,o,""]]),o.locals&&(e.exports=o.locals),(0,t(950).Z)("1191cfad",o,!1,{})},5887:function(e,n,t){e.exports=t(805)(267)},4593:function(e,n,t){e.exports=t(805)(5397)},520:function(e,n,t){e.exports=t(805)(6384)},2281:function(e,n,t){e.exports=t(968)(9624)}}]);