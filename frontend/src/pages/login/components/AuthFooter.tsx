import { Box, Link, Typography } from '@mui/material';

export default function AuthFooter() {
  return (
    <Box textAlign="center" mt={4}>
      <Typography color="text.secondary">
        Nie masz konta?{' '}
        <Link href="#" underline="hover" fontWeight={500}>
          Skontaktuj się z administratorem
        </Link>
      </Typography>
    </Box>
  );
}
