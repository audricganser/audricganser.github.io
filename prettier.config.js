//  @ts-check

/** @type {import('prettier').Config} */
const config = {
  semi: false,
  singleQuote: true,
  trailingComma: 'all',
  tabWidth: 2,
  singleAttributePerLine: true,
  printWidth: 80,
  overrides: [
    {
      files: ['*.scss', '*.css'],
      options: {
        parser: 'scss',
      },
    },
  ],
}

export default config;
