const { merge } = require("webpack-merge");
const common = require("./webpack.common.js");
// HTML 模板
const HtmlWebpackPlugin = require("html-webpack-plugin");
// 静态资源复制
const CopyWebpackPlugin = require("copy-webpack-plugin");

// 路径获取
const { resolve, getDlls } = require("./utils");

module.exports = merge(common, {
  mode: "development",
  entry: {
    app: "./src/core/"
  },
  output: {
    publicPath: "/"
  },
  devtool: "inline-source-map",
  // 信息打印
  stats: "errors-only",
  devServer: {
    port: 80,
    // 热重载
    hot: true,
    client: {
      // 错误显示在页面中
      overlay: false,
    },
    // 自动打开
    open: false,
    // 代理
    proxy: {
      "/api": "http://localhost:8080"
    },
    // history 模式
    historyApiFallback: true
  },
  plugins: [
    // HTML 模板
    new HtmlWebpackPlugin({
      basePath: "",
      template: "public/index.html",
      dlls: getDlls()
    }),
    // 静态资源复制
    new CopyWebpackPlugin({
      patterns: [
        {
          from: resolve("static"),
          to: "static",
          globOptions: {
            ignore: [".*"]
          }
        },
        {
          from: resolve("public/static/"),
          to: resolve("static/")
        },
        {
          from: resolve("public/favicon.ico"),
          to: "favicon.ico"
        }
      ]
    })
  ]
});
