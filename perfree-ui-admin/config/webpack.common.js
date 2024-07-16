const webpack = require("webpack");
// Vue编译用
const VueLoaderPlugin = require("vue-loader/dist/plugin").default;
// 路径获取
const resolve = require("./utils").resolve;

const path = require("path")
const requireContext = require("./utils").requireContext

const getManifest = dir => {
  let fileName
  const manifestList = requireContext(
    path.resolve(__dirname, `../static/dll/${dir}`),
    false,
    /\.manifest\.json$/
  )
  Object.keys(manifestList).forEach(name => {
    fileName = name
  })
  return path.resolve(__dirname, `../static/dll/${dir}/${fileName}.json`)
}

module.exports = {
  output: {
    filename: "[name].bundle.[chunkhash].js",
    path: resolve("dist/frame")
  },
  performance: {
    hints:false
  },
  resolve: {
    extensions: ["*", ".js", ".vue", ".json"],
    alias: {
      vue$: "vue",
      "@": resolve("src"),
      "@core": resolve("src/core"),
      "@frames": resolve("src/frames"),
      "@pages": resolve("src/modules")
    }
  },
  module: {
    rules: [
      {
        test: /\.vue$/,
        use: [
          {
            loader: "vue-loader",
            options: { hotReload: false }
          },
        ],
      },
      {
        test: /\.js$/,
        loader: "babel-loader",
        exclude: /node_modules/
      },
      {
        test: /\.css$/,
        use: ["vue-style-loader", "css-loader"],
      },
      {
        test: /\.less$/,
        use: ["vue-style-loader", "css-loader", "less-loader"],
      },
      {
        test: /\.(png|svg|jpg|gif)$/,
        use: [
          {
            loader: "file-loader",
            options: { outputPath: "img", esModule: false }
          }
        ]
      },
     /* {
        test: /\.(woff|woff2|eot|ttf|otf)$/,
        use: [
          {
            loader: "file-loader",
            options: {
              name: '[name].[ext]',
              limit: 100000,
              outputPath: "fonts/"
            },
          }
        ]
      }*/
    ]
  },
  plugins: [
    new VueLoaderPlugin(),
    // dll
    new webpack.DllReferencePlugin({
      manifest: require(getManifest("core"))
    }),
    new webpack.DllReferencePlugin({
      manifest: require(getManifest("perfree"))
    })
  ]
};
