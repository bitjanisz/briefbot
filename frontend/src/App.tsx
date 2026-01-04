import { Navigate, Route, Routes } from 'react-router-dom';
import {AuthProvider} from "./app/providers/AuthContext";
import ProtectedRoute from "./app/routing/ProtectedRoute";
import {RouteHelper} from "./app/routing/routes";
import {DashboardPage} from "./pages/DashboardPage";
import {LoginPage} from "./pages/login/LoginPage";
import { OtherPage } from './pages/OtherPage';
import { OtherChildPage } from './pages/OtherChildPage';


function App() {
  return (
    <AuthProvider>
      <Routes>
        <Route element={<ProtectedRoute />}>
          <Route path={RouteHelper.mainPath.param()} element={<DashboardPage />} />
        </Route>
        <Route path={RouteHelper.loginPath.param()} element={<LoginPage />} />
        <Route path={RouteHelper.otherPath.param()} element={<OtherPage />} />
        <Route path={RouteHelper.otherChildPath.param()} element={<OtherChildPage />} />
        <Route path="*" element={<Navigate to={RouteHelper.mainPath.param()} replace />} />
      </Routes>
    </AuthProvider>
  );
}

export default App;
