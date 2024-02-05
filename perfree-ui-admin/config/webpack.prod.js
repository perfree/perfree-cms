const inquirer = require("inquirer");
const { getAllDirs, resolve, getDlls} = require("./utils");
const webpackPromise = require("./utils").webpackPromise;
const { merge } = require("webpack-merge");
const _common = require("./webpack.build.common.prod");
const {CleanWebpackPlugin} = require("clean-webpack-plugin");
const HtmlWebpackPlugin = require("html-webpack-plugin");
const CopyWebpackPlugin = require("copy-webpack-plugin");
const common = require("./webpack.common");

const promptList = [
  {
    type: "checkbox",
    message: "请选择需要打包的项目基座:",
    name: "frames",
    choices: getAllDirs("./src/frames"),
    validate: function(val) {
      var done = this.async();
      if (val.length === 0) {
        done("请至少选择一个！");
        return;
      }
      done(null, true);
    }
  },
  {
    type: "checkbox",
    message: "请选择需要打包的模块:",
    name: "modules",
    choices: getAllDirs("./src/modules"),
    validate: function(val) {
      var done = this.async();
      if (val.length === 0) {
        done("请至少选择一个！");
        return;
      }
      done(null, true);
    }
  }
];

(async function() {
  // 选择打包内容
  let answers = await inquirer.prompt(promptList);
  console.log(answers); // 返回的结果

  // 打包核心
  console.log("\n--------------- BUILD CORE ---------------");
  const webpackCoreConfig = {
    mode: "production",
    entry: {
      core: "./src/core/"
    },
    performance: {
      hints:false
    },
    output: {
      path: resolve("../perfree-server/src/main/resources/static/admin/core"),
      publicPath: "/static/admin/core/",
      filename: "[name].[chunkhash].js",
      // libraryTarget: "var"
      library: {
        type: "var",
        name: "[name]_perfree"
      }
    },
    // devtool: "source-map",
    plugins: [
      new CleanWebpackPlugin(),
      // 纯静态资源复制
      new CopyWebpackPlugin({
        patterns: [
          {
            from: resolve("static"),
            to: resolve("../perfree-server/src/main/resources/static/admin/static"),
            globOptions: {
              ignore: [".*"]
            }
          },
          {
            from: resolve("public/static/"),
            to: resolve("../perfree-server/src/main/resources/static/admin/static/")
          },
          {
            from: resolve("public/favicon.ico"),
            to: resolve("../perfree-server/src/main/resources/static/admin/favicon.ico")
          }
        ]
      }),
      // HTML 模板
      new HtmlWebpackPlugin({
        basePath: "/static/admin",
        template: "public/index.html",
        filename: "../index.html",
        dlls: getDlls(),
      })
    ]
  };
  await webpackPromise(merge(common, webpackCoreConfig));


  // 打包架构
  console.log("\n--------------- BUILD FRAMES ---------------");
  for (let item of answers.frames)
    await webpackPromise(_common("frames", item));

  // 打包模块
  console.log("\n--------------- BUILD modules ---------------");
  for (let item of answers.modules)
    await webpackPromise(_common("modules", item));
})().then(() => {
  console.log("\n--------------- ALL DONE ---------------\n");
});
