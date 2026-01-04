import { Button } from '@mui/material';
import GoogleIcon from '@mui/icons-material/Google';
import FacebookIcon from '@mui/icons-material/Facebook';
import InstagramIcon from '@mui/icons-material/Instagram';
import RedditIcon from '@mui/icons-material/Reddit';
import { GOOGLE_LOGIN_URL } from 'utils/constants.ts';

const socialButtons = [
  {
    key: 'google',
    text: 'GOOGLE',
    icon: <GoogleIcon sx={{ color: '#4285F4' }} />,
    disabled: false,
  },
  {
    key: 'facebook',
    text: 'FACEBOOK',
    icon: <FacebookIcon sx={{ color: '#fff' }} />,
    disabled: true,
  },
  {
    key: 'instagram',
    text: 'INSTAGRAM',
    icon: <InstagramIcon sx={{ color: '#fff' }} />,
    disabled: true,
  },
  {
    key: 'reddit',
    text: 'REDDIT',
    icon: <RedditIcon sx={{ color: '#fff' }} />,
    disabled: true,
  },
];

export default function AuthSocialButtons() {
  const handleGoogleClick = async () => {
    window.location.href = GOOGLE_LOGIN_URL;
  };

  return (
    <>
      {socialButtons.map(({ key, text, icon, disabled }) => (
        <Button
          key={text}
          fullWidth
          startIcon={icon}
          disabled={disabled}
          variant="contained"
          sx={{ mb: 2 }}
          onClick={key === 'google' ? handleGoogleClick : () => alert('Jeszcze nie działam')}
        >
          {text}
        </Button>
      ))}
    </>
  );
}
