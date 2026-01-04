import { Paper } from '@mui/material';
import AuthSocialButtons from './AuthSocialButtons.tsx';
import AuthDivider from './AuthDivider.tsx';
import AuthForm from './AuthForm.tsx';

export default function AuthCard() {
  return (
    <Paper elevation={3} sx={{ borderRadius: 4, p: 4, maxWidth: 480, width: '100%' }}>
      <AuthSocialButtons />
      <AuthDivider />
      <AuthForm />
    </Paper>
  );
}
