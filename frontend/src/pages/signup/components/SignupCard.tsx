import { Paper } from '@mui/material';
import AuthSocialButtons from 'pages/login/components/AuthSocialButtons.tsx';
import AuthDivider from 'pages/login/components/AuthDivider.tsx';
import SignupForm from './SignupForm.tsx';

export default function SignupCard() {
  return (
    <Paper elevation={3} sx={{ borderRadius: 4, p: 4, maxWidth: 480, width: '100%' }}>
      <AuthSocialButtons />
      <AuthDivider />
      <SignupForm />
    </Paper>
  );
}
