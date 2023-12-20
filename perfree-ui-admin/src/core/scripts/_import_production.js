var loadjs = require("loadjs");

export default (type, name, version) =>
  new Promise((resolve, reject) => {
    loadjs(`/${type}/${name}/${name}.js?v=${version}`, {
      success: () => {
        window.PERFREE_SUCCESS = window.PERFREE_SUCCESS || [];
        window.PERFREE_SUCCESS.push(name);
        resolve({ default: window[type + "_" + name].default });
      },
      error: err => {
        window.PUZZLE_FAILURE = window.PUZZLE_FAILURE || [];
        window.PUZZLE_FAILURE.push(name);
        reject(err);
      }
    });
  });
