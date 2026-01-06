import { useState } from 'react';
import { Box, Button, InputAdornment, TextField } from '@mui/material';
import EmailIcon from '@mui/icons-material/Email';
import LockIcon from '@mui/icons-material/Lock';
import { useForm } from 'react-hook-form';
import { z } from 'zod';
import { zodResolver } from '@hookform/resolvers/zod';
import { AuthApi } from 'api/auth.ts';
import type { UserCredentials } from 'models/auth.ts';
import { HTTP_STATUS } from 'utils/httpStatus.ts';
import { ResponseError } from 'api/generated';

const loginSchema = z.object({
  email: z.string().min(1, 'Email nie może być pusty'),
  password: z.string().min(1, 'Hasło nie może być puste'),
});

type LoginFormValues = z.infer<typeof loginSchema>;

export default function AuthForm() {
  const [submitting, setSubmitting] = useState(false);
  const {
    register,
    handleSubmit,
    setError,
    formState: { errors },
  } = useForm<LoginFormValues>({
    resolver: zodResolver(loginSchema),
    defaultValues: { email: '', password: '' },
    mode: 'onSubmit',
  });

  const onSubmit = async (values: LoginFormValues) => {
    try {
      setSubmitting(true);
      const creds: UserCredentials = {
        email: values.email,
        password: values.password,
      } as UserCredentials;
      await AuthApi.login(creds);
      window.location.reload();
    } catch (e: unknown) {
      if (e instanceof ResponseError) {
        if (e.response.status === HTTP_STATUS.BAD_REQUEST) {
          const errorBody: { email?: string } = await e.response.json();

          if (errorBody.email) {
            setError('email', { type: 'server', message: errorBody.email }, { shouldFocus: true });
          }
        }
      }

      // const rest =
      // if (body) {

      // }
      // const r = body().then((s) => console.log('Login failedTESTR json', s));
      // Extract common shapes (API response or axios-like error)
      // const err: any = e;
      // let status: number | string | undefined;
      // let email: string | undefined;

      // if (err?.response?.data) {
      //   status = err.response.status ?? err.response.data.status;
      //   email = err.response.data.email ?? err.response.data?.errors?.email;
      // } else if (err?.status || err?.email) {
      //   status = err.status;
      //   email = err.email;
      // } else if (typeof err === 'object') {
      //   status = (err as any)?.status;
      //   email = (err as any)?.email;
      // }
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <Box component="form" noValidate autoComplete="off" onSubmit={handleSubmit(onSubmit)}>
      <TextField
        fullWidth
        margin="normal"
        label="Adres email"
        placeholder="nazwa@firma.pl"
        {...register('email')}
        error={!!errors.email}
        helperText={errors.email?.message}
        InputProps={{
          startAdornment: (
            <InputAdornment position="start">
              <EmailIcon sx={{ color: '#aaa' }} />
            </InputAdornment>
          ),
        }}
      />
      <TextField
        fullWidth
        margin="normal"
        label="Hasło"
        placeholder="Wprowadź hasło"
        type="password"
        {...register('password')}
        error={!!errors.password}
        helperText={errors.password?.message}
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
        variant="contained"
        disabled={submitting}
      >
        Kontynuuj
      </Button>
    </Box>
  );
}
