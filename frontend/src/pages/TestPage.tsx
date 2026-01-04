import { Link, Outlet } from 'react-router-dom';
import { Box, Stack, Typography } from '@mui/material';
import { RouteHelper } from 'app/routing/routes';

export function TestPage() {
  return (
    <Box p={2}>
      <Stack spacing={2}>
        <Typography variant="h5">Test Page</Typography>
        <Stack direction="row" spacing={2}>
          <Link to={RouteHelper.loginPath.abs()}>/login</Link>
          <Link to={RouteHelper.testPagePath.abs()}>/test-page</Link>
          <Link to={RouteHelper.testSubpagePath.abs()}>/test-page/subpage</Link>
          <Link to={RouteHelper.otherPath.abs()}>/other</Link>
        </Stack>
        <Link to={RouteHelper.mainPath.abs()}>Go to /</Link>
        <Outlet />
      </Stack>
    </Box>
  );
}
