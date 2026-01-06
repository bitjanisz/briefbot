import { AuthHeader } from 'pages/login/components/AuthHeader.tsx';
import SignupCard from 'pages/signup/components/SignupCard.tsx';
import { useAuth } from '../../providers/AuthContext.tsx';
import { Navigate } from 'react-router-dom';
import { RouteHelper } from 'routing/routes.ts';

export function SignupPage() {
  const { user } = useAuth();

  if (user) {
    return <Navigate to={RouteHelper.mainPath.abs()} replace />;
  }

  return (
    <div
      style={{
        minHeight: '100vh',
        width: '100vw',
        background: '#fafbfc',
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
        justifyContent: 'center',
      }}
    >
      <AuthHeader />
      <div style={{ margin: '32px 0', width: '100%', display: 'flex', justifyContent: 'center' }}>
        <SignupCard />
      </div>
    </div>
  );
}
