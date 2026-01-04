// file: 'src/app/config/env.ts'
const { VITE_API_URL, VITE_GOOGLE_LOGIN_URL } = import.meta.env;

export const API_URL: string = VITE_API_URL;
export const GOOGLE_LOGIN_URL: string = VITE_GOOGLE_LOGIN_URL;
