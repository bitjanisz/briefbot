import { Box, Stack, Typography } from '@mui/material';
import { Link } from 'react-router-dom';
import { RouteHelper } from 'app/routing/routes';

export function TestSubpage() {
  return (
    <Box p={2}>
      <Stack spacing={2}>
        <Typography variant="h6">Test Subpage</Typography>
        <Link to={RouteHelper.mainPath.abs()}>Go to /</Link>
      </Stack>
    </Box>
  );
}

