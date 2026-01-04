import Box from '@mui/material/Box';
import Paper from '@mui/material/Paper';
import Stack from '@mui/material/Stack';
import Typography from '@mui/material/Typography';

export const OtherChildPage = () => {
  return (
    <Box minHeight="100vh" display="flex" alignItems="center" justifyContent="center" bgcolor="background.default">
      <Paper elevation={3} sx={{ p: 4, maxWidth: 420, width: '100%', textAlign: 'center' }}>
        <Stack spacing={3}>
          <Typography variant="h4" fontWeight={700} color="primary.main">
            Other Child Page
          </Typography>
          <Typography color="text.secondary">This is the /other/child view.</Typography>
        </Stack>
      </Paper>
    </Box>
  );
};

