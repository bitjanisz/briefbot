import { Avatar, Box, Typography, Link as MUILink } from '@mui/material';
import { Link as RouterLink } from 'react-router-dom';
import { RouteHelper } from 'routing/routes.ts';

export const AuthHeader = () => {
  return (
    <Box display="flex" flexDirection="column" alignItems="center" mb={1}>
      <Avatar sx={{ bgcolor: '#1976d2', width: 64, height: 64, fontSize: 32 }}>B</Avatar>
      <Typography variant="h4" fontWeight={400}>
        Witaj w Brifin
      </Typography>
      <Typography variant="subtitle1" fontWeight={300}>
        Zaloguj się lub{' '}
        <MUILink component={RouterLink} to={RouteHelper.signupPath.abs()} underline="hover">
          zarejestruj
        </MUILink>
      </Typography>
    </Box>
  );
};
