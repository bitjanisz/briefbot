import { Box, Chip, Divider, Paper, Stack, Typography } from '@mui/material';
import { useParams } from 'react-router-dom';

// Admin service overview page
export function ServiceOverviewPage() {
  const { serviceId } = useParams();

  // Mocked detail; replace with API call based on serviceId
  const detail = {
    id: serviceId,
    name: 'Google Ads Campaign',
    category: 'Performance',
    price: '5,000 - 20,000 PLN',
    duration: '1 miesiąc',
    upsell: '2 usług',
    status: 'Aktywny',
    description: 'Kampanie Google Ads nastawione na wzrost konwersji i optymalizację kosztów.',
  };

  return (
    <Box>
      <Stack direction="row" alignItems="center" justifyContent="space-between" mb={2}>
        <Box>
          <Typography variant="h5" fontWeight={700}>
            {detail.name}
          </Typography>
          <Typography variant="body2" color="text.secondary">
            Przegląd usługi
          </Typography>
        </Box>
        <Chip
          label={detail.status}
          color={detail.status === 'Aktywny' ? 'success' : 'default'}
          variant="outlined"
        />
      </Stack>
      <Paper variant="outlined" sx={{ borderRadius: 3, p: 3 }}>
        <Stack spacing={2}>
          <Typography variant="subtitle2" color="text.secondary">
            Kategoria
          </Typography>
          <Typography>{detail.category}</Typography>
          <Divider />
          <Typography variant="subtitle2" color="text.secondary">
            Cennik
          </Typography>
          <Typography>{detail.price}</Typography>
          <Divider />
          <Typography variant="subtitle2" color="text.secondary">
            Czas trwania
          </Typography>
          <Typography>{detail.duration}</Typography>
          <Divider />
          <Typography variant="subtitle2" color="text.secondary">
            Upsell
          </Typography>
          <Typography>{detail.upsell}</Typography>
          <Divider />
          <Typography variant="subtitle2" color="text.secondary">
            Opis
          </Typography>
          <Typography>{detail.description}</Typography>
        </Stack>
      </Paper>
    </Box>
  );
}
