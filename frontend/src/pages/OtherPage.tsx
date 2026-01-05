import { Box, Stack, Typography } from '@mui/material';
import { Link } from 'react-router-dom';
import { RouteHelper } from '../routing/routes';

export function OtherPage() {
  return (
    <Box p={2}>
      <Stack spacing={2}>
        <Typography variant="h6">Other Page</Typography>
        <Link to={RouteHelper.mainPath.abs()}>Go to /</Link>
      </Stack>
    </Box>
  );
}
