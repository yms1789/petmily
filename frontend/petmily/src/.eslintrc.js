module.exports = {
  env: {
    browser: true,
    es6: true,
    node: true,
  },
  extends: ['eslint:recommended', 'airbnb', 'plugin:prettier/recommended'],
  rules: {
    'react/react-in-jsx-scope': 'off',
    'react/jsx-filename-extension': ['error', { extensions: ['.js', '.jsx'] }],
    'no-console': 0,

    'linebreak-style': [
      'error',
      // eslint-disable-next-line global-require
      require('os').EOL === '\r\n' ? 'windows' : 'unix',
    ],
    'prettier/prettier': ['error', { endOfLine: 'auto' }],
    'react/require-default-props': 'off',
  },
};
