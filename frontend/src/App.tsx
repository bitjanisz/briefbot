import { Navigate, Route, Routes, Outlet, Link } from 'react-router-dom';
import ProtectedRoute from 'app/routing/ProtectedRoute';
import { AuthProvider } from 'app/providers/AuthContext';
import { LoginPage } from 'pages/login/LoginPage.tsx';
import { RouteHelper } from 'app/routing/routes.ts';
import { DashboardPage } from 'pages/DashboardPage.tsx';
import { TestPage } from 'pages/TestPage';
import { TestSubpage } from 'pages/TestSubpage';
import { OtherPage } from 'pages/OtherPage';

function App() {
  return (
    <AuthProvider>
      <Routes>
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
        <Route path={RouteHelper.loginPath.param()} element={<LoginPage />} />
        <Route path="*" element={<Navigate to={RouteHelper.mainPath.param()} replace />} />
      </Routes>
    </AuthProvider>
  );
}

export default App;
