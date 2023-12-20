const app = {
  state: {
    user: {},
    menus: [],
    frameChildRouter: []
  },
  mutations: {
    SET_USER: (state, user) => (state.user = user),
    SET_MENUS: (state, menus) => (state.menus = menus),
    SET_FRAME_CHILD_ROUTER: (state, frameChildRouter) => (state.frameChildRouter = frameChildRouter),
  },
  getters: {
    user: state => state.user,
    menus: state => state.menus,
    frameChildRouter: state => state.frameChildRouter,
  }
};
export default app;
