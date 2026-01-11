// Centralized route definitions and helpers for absolute/relative paths

const ROUTES = {
  home: '/',
  login: '/login',
  signup: '/signup',
  testPage: '/test-page',
  testSubpage: '/test-page/subpage',
  other: '/other',
  services: '/services',
  serviceOverview: '/services/:serviceId/overview',
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
  servicesPath: {
    abs: () => ROUTES.services,
    param: () => ROUTES.services,
  },
  serviceOverviewPath: {
    abs: (serviceId: string | number) => `/services/${serviceId}/overview`,
    param: () => ROUTES.serviceOverview,
  },
};
