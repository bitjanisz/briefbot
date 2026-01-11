import { Navigate, Route, Routes, Outlet } from 'react-router-dom';
import ProtectedRoute from './routing/ProtectedRoute';
import { LoginPage } from 'pages/login/LoginPage.tsx';
import { RouteHelper } from './routing/routes.ts';
import { DashboardPage } from 'pages/DashboardPage.tsx';
import { TestPage } from 'pages/TestPage';
import { TestSubpage } from 'pages/TestSubpage';
import { OtherPage } from 'pages/OtherPage';
import { SignupPage } from 'pages/signup/SignupPage.tsx';
import { AppLayout } from './components/AppLayout';
import { ServicesPage } from 'pages/services/ServicesPage';
import { ServiceOverviewPage } from 'pages/services/ServiceOverviewPage';

function App() {
  return (
    <Routes>
      {/*Protected routes*/}
      <Route element={<ProtectedRoute />}>
        <Route
          element={
            <AppLayout>
              <Outlet />
            </AppLayout>
          }
        >
          <Route path={RouteHelper.mainPath.param()} element={<DashboardPage />} />
          <Route path={RouteHelper.testPagePath.param()} element={<TestPage />} />
          <Route path={RouteHelper.testSubpagePath.param()} element={<TestSubpage />} />
          <Route path={RouteHelper.otherPath.param()} element={<OtherPage />} />
          <Route path={RouteHelper.servicesPath.param()} element={<ServicesPage />} />
          <Route path={RouteHelper.serviceOverviewPath.param()} element={<ServiceOverviewPage />} />
        </Route>
      </Route>

      {/*Public routes*/}
      <Route path={RouteHelper.loginPath.param()} element={<LoginPage />} />
      <Route path={RouteHelper.signupPath.param()} element={<SignupPage />} />
      <Route path="*" element={<Navigate to={RouteHelper.mainPath.param()} replace />} />
    </Routes>
  );
}

export default App;
