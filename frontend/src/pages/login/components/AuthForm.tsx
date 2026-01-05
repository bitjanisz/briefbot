import { useState } from 'react';
import { Box, Button, InputAdornment, TextField } from '@mui/material';
import EmailIcon from '@mui/icons-material/Email';
import LockIcon from '@mui/icons-material/Lock';

export default function AuthForm() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  return (
    <Box component="form" noValidate autoComplete="off">
      <TextField
        disabled={true}
        fullWidth
        margin="normal"
        label="Adres email"
        placeholder="nazwa@firma.pl"
        value={email}
        onChange={(e) => setEmail(e.target.value)}
        InputProps={{
          startAdornment: (
            <InputAdornment position="start">
              <EmailIcon sx={{ color: '#aaa' }} />
            </InputAdornment>
          ),
        }}
      />
      <TextField
        disabled={true}
        fullWidth
        margin="normal"
        label="Hasło"
        placeholder="Wprowadź hasło"
        type="password"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
        InputProps={{
          startAdornment: (
            <InputAdornment position="start">
              <LockIcon sx={{ color: '#aaa' }} />
            </InputAdornment>
          ),
        }}
      />
      {/*<Grid container alignItems="center" justifyContent="space-between" sx={{ mt: 1, mb: 2 }}>*/}
      {/*  <Grid>*/}
      {/*    <FormControlLabel*/}
      {/*      control={*/}
      {/*        <Checkbox*/}
      {/*          disabled={true}*/}
      {/*          checked={remember}*/}
      {/*          onChange={(e) => setRemember(e.target.checked)}*/}
      {/*        />*/}
      {/*      }*/}
      {/*      label="Zapamiętaj mnie"*/}
      {/*    />*/}
      {/*  </Grid>*/}
      {/*  <Grid>*/}
      {/*    <Link href="#" underline="hover" color="text.secondary" fontSize={14}>*/}
      {/*      Zapomniałeś hasła?*/}
      {/*    </Link>*/}
      {/*  </Grid>*/}
      {/*</Grid>*/}
      <Button
        sx={{ mt: 2, mb: 1 }}
        type="submit"
        fullWidth
        disabled={true}
        variant="contained"
        onClick={() => {
          alert('Jeszcze nie działam');
        }}
        // size="large"
        // startIcon={<LockIcon />}
      >
        Kontynuuj
      </Button>
    </Box>
  );
}
