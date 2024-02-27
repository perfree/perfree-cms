const app = {
  state: {
    options: {},
    menus: [],
    frameChildRouter: []
  },
  mutations: {
    SET_OPTIONS: (state, options) => (state.options = options),
    SET_MENUS: (state, menus) => (state.menus = menus),
    SET_FRAME_CHILD_ROUTER: (state, frameChildRouter) => (state.frameChildRouter = frameChildRouter),
  },
  getters: {
    options: state => state.options,
    menus: state => state.menus,
    frameChildRouter: state => state.frameChildRouter,
  }
};
export default app;
