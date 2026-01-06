import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';
import path from 'node:path';

// https://vite.dev/config/
export default defineConfig({
  plugins: [react()],
  resolve: {
    alias: {
      api: path.resolve(__dirname, 'src/api'),
      app: path.resolve(__dirname, 'src/app'),
      pages: path.resolve(__dirname, 'src/pages'),
      utils: path.resolve(__dirname, 'src/utils'),
      models: path.resolve(__dirname, 'src/models'),
      routing: path.resolve(__dirname, 'src/routing'),
    },
  },
  server: {
    port: 3000,
  },
  preview: {
    port: 3000,
  },
});
