import {
  Box,
  Button,
  Chip,
  Paper,
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableRow,
  Typography,
} from '@mui/material';
import AddIcon from '@mui/icons-material/Add';
import MoreHorizIcon from '@mui/icons-material/MoreHoriz';
import { useNavigate } from 'react-router-dom';
import { RouteHelper } from '../../routing/routes';

// Admin services list page
export function ServicesPage() {
  const navigate = useNavigate();

  // Mocked data; replace later with API
  const rows = [
    {
      id: 'cms',
      name: 'Content Marketing Strategy',
      category: 'Strategia',
      price: '8,000 - 15,000 PLN',
      duration: '2-3 tygodnie',
      upsell: '3 usług',
      status: 'Aktywny',
    },
    {
      id: 'gads',
      name: 'Google Ads Campaign',
      category: 'Performance',
      price: '5,000 - 20,000 PLN',
      duration: '1 miesiąc',
      upsell: '2 usług',
      status: 'Aktywny',
    },
    {
      id: 'seo',
      name: 'SEO Optimization',
      category: 'SEO',
      price: '6,000 - 12,000 PLN',
      duration: '3 miesiące',
      upsell: '4 usług',
      status: 'Aktywny',
    },
    {
      id: 'smm',
      name: 'Social Media Management',
      category: 'Social Media',
      price: '4,000 - 8,000 PLN',
      duration: '1 miesiąc',
      upsell: '2 usług',
      status: 'Aktywny',
    },
    {
      id: 'ema',
      name: 'Email Marketing Automation',
      category: 'Email Marketing',
      price: '3,000 - 7,000 PLN',
      duration: '2 tygodnie',
      upsell: '1 usług',
      status: 'Wyłączony',
    },
  ];

  const goToOverview = (id: string) => navigate(RouteHelper.serviceOverviewPath.abs(id));

  return (
    <Box>
      <Box display="flex" alignItems="center" justifyContent="space-between" mb={2}>
        <Box>
          <Typography variant="h5" fontWeight={700}>
            Katalog usług
          </Typography>
          <Typography variant="body2" color="text.secondary">
            Zarządzaj ofertą usług agencji
          </Typography>
        </Box>
        <Button
          variant="contained"
          startIcon={<AddIcon />}
          sx={{ borderRadius: 2, textTransform: 'none' }}
        >
          Dodaj usługę
        </Button>
      </Box>
      <Paper variant="outlined" sx={{ borderRadius: 3 }}>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>Nazwa usługi</TableCell>
              <TableCell>Kategoria</TableCell>
              <TableCell>Cena</TableCell>
              <TableCell>Czas trwania</TableCell>
              <TableCell>Upsell</TableCell>
              <TableCell>Status</TableCell>
              <TableCell align="right">Akcje</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {rows.map((r) => (
              <TableRow
                key={r.id}
                hover
                sx={{ cursor: 'pointer' }}
                onClick={() => goToOverview(r.id)}
              >
                <TableCell>{r.name}</TableCell>
                <TableCell>{r.category}</TableCell>
                <TableCell>{r.price}</TableCell>
                <TableCell>{r.duration}</TableCell>
                <TableCell>{r.upsell}</TableCell>
                <TableCell>
                  <Chip
                    label={r.status}
                    color={r.status === 'Aktywny' ? 'success' : 'default'}
                    variant="outlined"
                  />
                </TableCell>
                <TableCell align="right">
                  <MoreHorizIcon />
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </Paper>
    </Box>
  );
}
