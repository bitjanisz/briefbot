import { AuthHeader } from 'pages/login/components/AuthHeader.tsx';
import AuthCard from 'pages/login/components/AuthCard.tsx';
import { useAuth } from 'app/providers/useAuth';
import { Navigate, useLocation } from 'react-router-dom';

export function LoginPage() {
  const { user } = useAuth();
  const location = useLocation();
  if (user) {
    const from = (location.state as any)?.from?.pathname || '/';
    return <Navigate to={from} replace />;
  }
  return (
    <div
      style={{
        minHeight: '100vh',
        background: '#fafbfc',
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
        justifyContent: 'center',
      }}
    >
      <AuthHeader />
      <div style={{ margin: '32px 0', width: '100%', display: 'flex', justifyContent: 'center' }}>
        <AuthCard />
      </div>
    </div>
  );
}
