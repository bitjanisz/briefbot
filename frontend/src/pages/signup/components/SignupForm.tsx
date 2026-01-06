import { Box, Button, Grid, InputAdornment, TextField, Typography } from '@mui/material';
import EmailIcon from '@mui/icons-material/Email';
import PersonIcon from '@mui/icons-material/Person';
import { useForm } from 'react-hook-form';
import { z } from 'zod';
import { zodResolver } from '@hookform/resolvers/zod';
import { AuthApi } from 'api/auth.ts';
import type { UserRegistration } from 'models/auth.ts';
import { useState } from 'react';
import { Link as RouterLink, useNavigate } from 'react-router-dom';
import { RouteHelper } from 'routing/routes.ts';
import { ResponseError } from 'api/generated';
import { HTTP_STATUS } from 'utils/httpStatus.ts';

const signupSchema = z.object({
  email: z.string().min(1, 'Email jest wymagany').email('Nieprawidłowy adres email'),
  givenName: z.string().min(1, 'Imię jest wymagane'),
  familyName: z.string().min(1, 'Nazwisko jest wymagane'),
});

type SignupFormValues = z.infer<typeof signupSchema>;

export default function SignupForm() {
  const navigate = useNavigate();
  const [submitting, setSubmitting] = useState(false);

  const {
    register,
    handleSubmit,
    setError,
    formState: { errors },
  } = useForm<SignupFormValues>({
    resolver: zodResolver(signupSchema),
    defaultValues: { email: '', givenName: '', familyName: '' },
  });

  const onSubmit = async (values: SignupFormValues) => {
    try {
      setSubmitting(true);
      const payload: UserRegistration = {
        email: values.email,
        givenName: values.givenName,
        familyName: values.familyName,
      };
      await AuthApi.register(payload);
      navigate(RouteHelper.loginPath.abs(), { replace: true });
    } catch (e) {
      if (e instanceof ResponseError) {
        if (e.response.status === HTTP_STATUS.BAD_REQUEST) {
          const errorBody: { detail?: string } = await e.response.json();

          if (errorBody.detail) {
            setError('email', { type: 'server', message: errorBody.detail }, { shouldFocus: true });
          }
        }
      }
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <Box component="form" noValidate autoComplete="off" onSubmit={handleSubmit(onSubmit)}>
      <Grid container spacing={2}>
        <Grid size={12}>
          <TextField
            fullWidth
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
        </Grid>
        <Grid size={12}>
          <TextField
            fullWidth
            label="Imię"
            placeholder="Jan"
            {...register('givenName')}
            error={!!errors.givenName}
            helperText={errors.givenName?.message}
            InputProps={{
              startAdornment: (
                <InputAdornment position="start">
                  <PersonIcon sx={{ color: '#aaa' }} />
                </InputAdornment>
              ),
            }}
          />
        </Grid>
        <Grid size={12}>
          <TextField
            fullWidth
            label="Nazwisko"
            placeholder="Kowalski"
            {...register('familyName')}
            error={!!errors.familyName}
            helperText={errors.familyName?.message}
            InputProps={{
              startAdornment: (
                <InputAdornment position="start">
                  <PersonIcon sx={{ color: '#aaa' }} />
                </InputAdornment>
              ),
            }}
          />
        </Grid>
      </Grid>

      <Button sx={{ mt: 2 }} type="submit" fullWidth variant="contained" disabled={submitting}>
        Zarejestruj się
      </Button>

      <Typography color="text.secondary" textAlign="center" mt={2}>
        Masz już konto?{' '}
        <Button
          component={RouterLink}
          to={RouteHelper.loginPath.abs()}
          variant="text"
          sx={{ p: 0, minWidth: 0 }}
        >
          Zaloguj się
        </Button>
      </Typography>
    </Box>
  );
}
