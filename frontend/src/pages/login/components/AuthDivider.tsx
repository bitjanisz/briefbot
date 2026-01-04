import { Box, Divider, Typography } from '@mui/material';

export default function AuthDivider() {
  return (
    <Box display="flex" alignItems="center" my={2}>
      <Divider sx={{ flex: 1 }} />
      <Typography color="text.secondary" mx={2} fontWeight={500}>
        LUB
      </Typography>
      <Divider sx={{ flex: 1 }} />
    </Box>
  );
}
