// Centralized route definitions and helpers for absolute/relative paths

const ROUTES = {
  home: '/',
  login: '/login',
  signup: '/signup',
  testPage: '/test-page',
  testSubpage: '/test-page/subpage',
  other: '/other',
} as const;

export const RouteHelper = {
  mainPath: {
    abs: () => ROUTES.home,
    param: () => ROUTES.home,
  },
  loginPath: {
    abs: () => ROUTES.login,
    param: () => ROUTES.login,
  },
  signupPath: {
    abs: () => ROUTES.signup,
    param: () => ROUTES.signup,
  },
  testPagePath: {
    abs: () => ROUTES.testPage,
    param: () => ROUTES.testPage,
  },
  testSubpagePath: {
    abs: () => ROUTES.testSubpage,
    param: () => ROUTES.testSubpage,
  },
  otherPath: {
    abs: () => ROUTES.other,
    param: () => ROUTES.other,
  },
};
