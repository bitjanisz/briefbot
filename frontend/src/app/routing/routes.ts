// Centralized route definitions and helpers for absolute/relative paths

const ROUTES = {
  home: '/',
  login: '/login',
  other: '/other',
  otherChild: '/other/child',
} as const;

export type RouteKey = keyof typeof ROUTES;

export const RouteHelper = {
  mainPath: {
    abs: () => ROUTES.home,
    param: () => ROUTES.home,
  },
  loginPath: {
    abs: () => ROUTES.login,
    param: () => ROUTES.login,
  },
  otherPath: {
    abs: () => ROUTES.other,
    param: () => ROUTES.other,
  },
  otherChildPath: {
    abs: () => ROUTES.otherChild,
    param: () => ROUTES.otherChild,
  },
};
