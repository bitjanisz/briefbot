import Box from '@mui/material/Box';
import Paper from '@mui/material/Paper';
import Stack from '@mui/material/Stack';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
import Alert from '@mui/material/Alert';
import CircularProgress from '@mui/material/CircularProgress';
import { useState } from 'react';

export const OtherPage = () => {
  const [loading, setLoading] = useState(false);
  const [result, setResult] = useState<string | null>(null);
  const [error, setError] = useState<string | null>(null);

  const callSample = async () => {
    setLoading(true);
    setError(null);
    setResult(null);
    try {
      const res = await fetch('/api/sample', {
        method: 'GET',
        credentials: 'include',
        headers: {
          'Accept': 'application/json',
        },
      });
      if (!res.ok) {
        const text = await res.text();
        throw new Error(text || `Request failed with status ${res.status}`);
      }
      const contentType = res.headers.get('content-type') || '';
      if (contentType.includes('application/json')) {
        const data = await res.json();
        setResult(JSON.stringify(data));
      } else {
        const text = await res.text();
        setResult(text);
      }
    } catch (e: unknown) {
      const msg = e instanceof Error ? e.message : 'Unknown error';
      setError(msg);
    } finally {
      setLoading(false);
    }
  };

  return (
    <Box minHeight="100vh" display="flex" alignItems="center" justifyContent="center" bgcolor="background.default">
      <Paper elevation={3} sx={{ p: 4, maxWidth: 560, width: '100%', textAlign: 'center' }}>
        <Stack spacing={3}>
          <Typography variant="h4" fontWeight={700} color="primary.main">
            Other Page test
          </Typography>
          <Typography color="text.secondary">This is the /other view.</Typography>
          <Button variant="contained" onClick={callSample} disabled={loading}>
            {loading ? <CircularProgress size={24} /> : 'Call /api/sample'}
          </Button>
          {error && <Alert severity="error">{error}</Alert>}
          {result && (
            <Paper variant="outlined" sx={{ p: 2, textAlign: 'left', whiteSpace: 'pre-wrap' }}>
              <Typography variant="subtitle2" color="text.secondary">Response:</Typography>
              <Typography variant="body2">{result}</Typography>
            </Paper>
          )}
        </Stack>
      </Paper>
    </Box>
  );
};
