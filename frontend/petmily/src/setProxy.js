const { createProxyMiddleware } = require('http-proxy-middleware');

module.exports = function proxyMiddleware(app) {
  app.use(
    '/api',
    createProxyMiddleware({
      target: '3.34.187.150:8080', // 서버 URL or localhost:설정한포트번호
      changeOrigin: true,
      pathRewrite: {
        '^/api': '',
      },
    }),
  );
};
