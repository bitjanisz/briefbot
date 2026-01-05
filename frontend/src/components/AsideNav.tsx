import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import Avatar from '@mui/material/Avatar';
import Button from '@mui/material/Button';
import Divider from '@mui/material/Divider';
import DashboardIcon from '@mui/icons-material/Dashboard';
import DescriptionIcon from '@mui/icons-material/Description';
import LocalOfferIcon from '@mui/icons-material/LocalOffer';
import LocalShippingIcon from '@mui/icons-material/LocalShipping';
import WorkIcon from '@mui/icons-material/Work';
import FolderIcon from '@mui/icons-material/Folder';
import SettingsIcon from '@mui/icons-material/Settings';
import Drawer from '@mui/material/Drawer';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';

const navItems = [
  { label: 'Dashboard', icon: <DashboardIcon />, href: '/dashboard' },
  { label: 'Briefy', icon: <DescriptionIcon />, href: '/briefy' },
  { label: 'Oferty', icon: <LocalOfferIcon />, href: '/oferty' },
  { label: 'Wysyłki', icon: <LocalShippingIcon />, href: '/wysylki' },
  { label: 'Usługi', icon: <WorkIcon />, href: '/uslugi' },
  { label: 'Case Studies', icon: <FolderIcon />, href: '/case-studies' },
  { label: 'Ustawienia', icon: <SettingsIcon />, href: '/ustawienia' },
];

export function AsideNav({
  user,
  // onLogin,
  // onLogout,
  activePath,
}: {
  user: any;
  // onLogin: () => void;
  // onLogout: () => void;
  activePath: string;
}) {
  return (
    <Drawer
      variant="permanent"
      PaperProps={{
        sx: {
          width: 260,
          boxSizing: 'border-box',
          borderRight: '1px solid #eee',
          pt: 3,
          pb: 0,
          display: 'flex',
          flexDirection: 'column',
          justifyContent: 'space-between',
        },
      }}
    >
      <Box>
        <Typography variant="h5" fontWeight={700} px={3} mb={3}>
          Brifin
        </Typography>
        <List>
          {navItems.map((item) => (
            <ListItem key={item.label} disablePadding sx={{ mb: 1 }}>
              <ListItemButton
                // component={Link}
                href={item.href}
                selected={activePath === item.href}
                sx={{
                  borderRadius: 3,
                  mx: 1.5,
                  bgcolor: activePath === item.href ? '#171717' : 'transparent',
                  color: activePath === item.href ? '#fff' : '#222',
                  '&:hover': { bgcolor: activePath === item.href ? '#171717' : '#f5f5f5' },
                }}
              >
                <ListItemIcon sx={{ color: activePath === item.href ? '#fff' : '#888' }}>
                  {item.icon}
                </ListItemIcon>
                <ListItemText primary={item.label} primaryTypographyProps={{ fontWeight: 500 }} />
              </ListItemButton>
            </ListItem>
          ))}
        </List>
      </Box>
      <Box px={3} pb={3}>
        <Divider sx={{ mb: 2 }} />
        {user ? (
          <Box display="flex" alignItems="center" gap={2}>
            <Avatar sx={{ bgcolor: '#e0e0e0', width: 40, height: 40 }}>A</Avatar>
            <Box>
              <Typography fontWeight={500}>Anna Kowalska</Typography>
              <Typography color="text.secondary" fontSize={15}>
                anna@brifin.pl
              </Typography>
              <Button
                size="small"
                color="inherit"
                // onClick={onLogout}
                sx={{ mt: 0.5, textTransform: 'none' }}
              >
                Wyloguj się
              </Button>
            </Box>
          </Box>
        ) : (
          <Button fullWidth variant="outlined" sx={{ textTransform: 'none' }}>
            Zaloguj się
          </Button>
        )}
      </Box>
    </Drawer>
  );
}
