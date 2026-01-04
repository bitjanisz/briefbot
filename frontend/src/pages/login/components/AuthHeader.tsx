import { Avatar, Box, Typography } from '@mui/material';

export const AuthHeader = () => {
  return (
    <Box display="flex" flexDirection="column" alignItems="center" mb={1}>
      <Avatar sx={{ bgcolor: '#1976d2', width: 64, height: 64, fontSize: 32 }}>B</Avatar>
      <Typography variant="h4" fontWeight={400}>
        Witaj w Brifin
      </Typography>
      <Typography variant="subtitle1" fontWeight={300}>
        Zaloguj się lub zarejestruj
      </Typography>
    </Box>
  );
};
