const webpack = require("webpack");
const { merge } = require("webpack-merge");
const common = require("./webpack.common.js");
// 构建前清理
const { CleanWebpackPlugin } = require("clean-webpack-plugin");
// 路径获取
const resolve = require("./utils").resolve;

module.exports = function(type, perfree) {
  let entry = {};
  entry[perfree] = `./src/${type}/${perfree}/`;

  const webpackConfig = {
    mode: "production",
    entry,
    output: {
      path: resolve(`dist/${type}/${perfree}`),
      // publicPath: `./${type}/${puzzle}/`,
      filename: "[name].js",
      chunkFilename: "[name].[chunkhash].js",
      library: `${type}_${perfree}`,
      libraryTarget: "umd"
    },
    // devtool: "source-map",
    optimization: {
      chunkIds: 'named'
    },
    plugins: [
      new CleanWebpackPlugin(),
      // 解决动态模块导入打包重名的问题
      // new webpack.NamedChunksPlugin(chunk => puzzle + "/" + chunk.name)
    ]
  };

  return merge(common, webpackConfig);
};
