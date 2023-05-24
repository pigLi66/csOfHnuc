module.exports = {
  root: true,

  env: {
    node: true,
    "vue/setup-compiler-macros": true,
  },

  extends: [
    "plugin:vue/vue3-essential",

    "eslint:recommended",

    "@vue/typescript/recommended",
    "@vue/standard",
  ],

  parserOptions: {
    ecmaVersion: 2020,
  },

  rules: {
    quotes: "off",
    "comma-dangle": "off",
    "no-extra-semi": "off",
    semi: 0,
    'no-array-constructor': "warn",
    "@typescript-eslint/no-extra-semi": "error",
    "no-unused-vars": "off",
    "@typescript-eslint/no-unused-vars": [2],
    "space-before-function-paren": ["error", "never"],
    "vue/no-mutating-props": "off",
    "vue/multi-word-component-names": "off",
    "no-console": process.env.NODE_ENV === "production" ? "warn" : "off",

    "no-debugger": process.env.NODE_ENV === "production" ? "warn" : "off",

    "no-empty-function": "off",
    "@typescript-eslint/no-empty-function": "off",

    "@typescript-eslint/no-var-requires": 0,

    "@typescript-eslint/explicit-module-boundary-types": ["off"],

    "@typescript-eslint/no-explicit-any": ["off"],

    // "no-empty-pattern": true,
  },
};
