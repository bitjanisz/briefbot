import { Navigate, Outlet } from 'react-router-dom';
import { useAuth } from '../providers/AuthContext.tsx';
import { Box, CircularProgress } from '@mui/material';
import { RouteHelper } from './routes.ts';

export default function ProtectedRoute() {
  const { user, loading } = useAuth();

  if (loading) {
    return (
      <Box
        minHeight="100vh"
        width="100vw"
        display="flex"
        alignItems="center"
        justifyContent="center"
      >
        <CircularProgress />
      </Box>
    );
  }

  if (!user) {
    return <Navigate to={RouteHelper.loginPath.abs()} replace />;
  }

  return <Outlet />;
}
