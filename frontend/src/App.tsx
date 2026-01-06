import { Navigate, Route, Routes, Outlet, Link } from 'react-router-dom';
import ProtectedRoute from './routing/ProtectedRoute';
import { LoginPage } from 'pages/login/LoginPage.tsx';
import { RouteHelper } from './routing/routes.ts';
import { DashboardPage } from 'pages/DashboardPage.tsx';
import { TestPage } from 'pages/TestPage';
import { TestSubpage } from 'pages/TestSubpage';
import { OtherPage } from 'pages/OtherPage';
import { SignupPage } from 'pages/signup/SignupPage.tsx';

function App() {
  return (
    <Routes>
      {/*Protected routes*/}
      <Route element={<ProtectedRoute />}>
        <Route element={<Outlet />}>
          <Route
            path={RouteHelper.mainPath.param()}
            element={
              <div style={{ padding: 16 }}>
                <nav style={{ display: 'flex', gap: 12 }}>
                  <Link to={RouteHelper.loginPath.abs()}>/login test</Link>
                  <Link to={RouteHelper.testPagePath.abs()}>/test-page</Link>
                  <Link to={RouteHelper.otherPath.abs()}>/other</Link>
                </nav>
                <DashboardPage />
              </div>
            }
          />
          <Route path={RouteHelper.testPagePath.param()} element={<TestPage />} />
          <Route path={RouteHelper.testSubpagePath.param()} element={<TestSubpage />} />
          <Route path={RouteHelper.otherPath.param()} element={<OtherPage />} />
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
