import Box from '@mui/material/Box';
import Paper from '@mui/material/Paper';
import Stack from '@mui/material/Stack';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
import { useState } from 'react';

export const DashboardPage = () => {
  const [count, setCount] = useState(0);

  return (
    <Box
      minHeight="100vh"
      display="flex"
      alignItems="center"
      justifyContent="center"
      bgcolor="background.default"
    >
      <Paper elevation={3} sx={{ p: 4, maxWidth: 420, width: '100%', textAlign: 'center' }}>
        <Stack spacing={3}>
          <Typography variant="h4" fontWeight={700} color="primary.main">
            Dashboard
          </Typography>
          <Typography color="text.secondary">Protected home page content.</Typography>
          <Button variant="contained" size="large" onClick={() => setCount((prev) => prev + 1)}>
            Count is {count}
          </Button>
        </Stack>
      </Paper>
    </Box>
  );
};
