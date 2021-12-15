// const CompressionPlugin = require("compression-webpack-plugin")
// module.exports = {
//   // sourceType: 'unambiguous',
//     // 关闭代码检查
//     lintOnSave: true,
//     configureWebpack: config => {
//       if (process.env.NODE_ENV === 'production') {
//           return {
//               plugins: [
                
//                   new CompressionPlugin({
//                       test: /\.js$|\.html$|\.css/,
//                       threshold: 1024,
//                       deleteOriginalAssets: false
//                   })
//               ]
//           }
//       }
//   },
//     // build: {
//     //   env: require('./prod.env'),
//     //   index: path.resolve(__dirname, '../dist/index.html'),
//     //   assetsRoot: path.resolve(__dirname, '../dist'),
//     //   assetsSubDirectory: 'static',
//     //   assetsPublicPath: '/',
//     //   productionSourceMap: true
//     // }

// }
const CompressionPlugin = require("compression-webpack-plugin")
module.exports = {
      lintOnSave: true,
    // devServer: {
    //     host: 'localhost',
    //     port: 8080,
    //     proxy: proxyObj
    // },
    configureWebpack: config => {
        if (process.env.NODE_ENV === 'production') {
            return {
                plugins: [
                    new CompressionPlugin({
                        test: /\.js$|\.html$|\.css/,
                        threshold: 1024,
                        deleteOriginalAssets: false
                    })
                ]
            }
        }
    }
}