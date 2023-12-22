const webpack = require("webpack");
const path = require("path");

// 构建前清理
const { CleanWebpackPlugin } = require("clean-webpack-plugin");
const NProgress = require("nprogress");

module.exports = {
  entry: {
    // 基本依赖
    core: [
      // Vue 全家桶
      "vue",
      "vue-router",
      "vuex",
      // 第三方依赖
      "axios",
      "loadjs",
    ],
    // 模块公共依赖 / 防止模块打包重复代码
    perfree: ["moment", 'screenfull'],
    element: ['element-plus', '@element-plus/icons-vue'],
    NProgress: ['nprogress']
  },
  performance: {
    hints:false
  },
  output: {
    filename: "[name]/[name].[chunkhash].dll.js",
    path: path.join(__dirname, "../static/dll"),
    library: "[name]_perfreeDll",
    libraryTarget: "umd"
  },
  plugins: [
    new CleanWebpackPlugin(),
    new webpack.DllPlugin({
      path: path.join(
        __dirname,
        "../static/dll/[name]",
        "[name].[chunkhash].manifest.json"
      ),
      name: "[name]_perfreeDll"
    })

  ]
};
