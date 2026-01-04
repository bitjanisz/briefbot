import { Outlet } from 'react-router-dom';

export default function ProtectedRoute() {
  // const { user, loading } = useAuth();
  // const location = useLocation();

  // if (loading) {
  //   return (
  //     <Box minHeight="100vh" display="flex" alignItems="center" justifyContent="center">
  //       <CircularProgress />
  //     </Box>
  //   );
  // }

  // if (!user) {
  //   return <Navigate to="/login" state={{ from: location }} replace />;
  // }

  return <Outlet />;
}
