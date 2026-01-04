'use client';

import { createTheme } from '@mui/material/styles';

const theme = createTheme({
  typography: {
    fontFamily: 'var(--font-roboto)',
  },
  palette: {
    primary: {
      main: '#9719d2',
    },
  },
  cssVariables: true,
});

export default theme;
