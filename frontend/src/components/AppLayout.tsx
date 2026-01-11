import type { PropsWithChildren } from 'react';
import {
  AppBar,
  Box,
  Divider,
  Drawer,
  List,
  ListItem,
  ListItemButton,
  ListItemIcon,
  ListItemText,
  ListSubheader,
  Toolbar,
  Typography,
} from '@mui/material';
import { alpha } from '@mui/material/styles';
import DashboardIcon from '@mui/icons-material/Dashboard';
import DescriptionIcon from '@mui/icons-material/Description';
import LibraryBooksIcon from '@mui/icons-material/LibraryBooks';
import FolderCopyIcon from '@mui/icons-material/FolderCopy';
import SettingsIcon from '@mui/icons-material/Settings';
import { Link, NavLink } from 'react-router-dom';
import { RouteHelper } from '../routing/routes';
import { UserAvatar } from './UserAvatar';

const drawerWidth = 240;

export function AppLayout({ children }: PropsWithChildren) {
  // helper to render nav items with active styling
  const renderNavItem = (to: string, icon: React.ReactNode, label: string) => (
    <ListItem disablePadding>
      <ListItemButton
        component={NavLink}
        to={to}
        // use NavLink's isActive to control selected state
        sx={{
          '&.active': {
            bgcolor: (t) => alpha(t.palette.primary.main, 0.08),
            color: 'primary.main',
            // '& .MuiListItemIcon-root': { color: 'primary.main' },
          },
        }}
      >
        <ListItemIcon>{icon}</ListItemIcon>
        <ListItemText primary={label} />
      </ListItemButton>
    </ListItem>
  );

  return (
    <Box sx={{ display: 'flex' }}>
      {/* Top AppBar */}
      <AppBar
        position="fixed"
        color="inherit"
        elevation={0}
        sx={{
          zIndex: (t) => t.zIndex.drawer + 1,
          borderBottom: 1,
          borderColor: 'divider',
        }}
      >
        <Toolbar sx={{ gap: 2 }}>
          {/* Brand */}
          <Typography
            variant="h6"
            component={Link}
            to={RouteHelper.mainPath.abs()}
            sx={{ textDecoration: 'none', color: 'inherit', fontWeight: 700, mr: 1 }}
          >
            Brifin
          </Typography>
          {/* Actions */}
          <Box sx={{ ml: 'auto', display: 'flex', alignItems: 'center', gap: 1.5 }}>
            {/*<Button*/}
            {/*  variant="contained"*/}
            {/*  color="primary"*/}
            {/*  startIcon={<AddIcon />}*/}
            {/*  sx={{ borderRadius: 2, textTransform: 'none' }}*/}
            {/*>*/}
            {/*  Dodaj*/}
            {/*</Button>*/}
            {/*<Tooltip title="Pomoc">*/}
            {/*  <IconButton color="default">*/}
            {/*    <HelpOutlineIcon />*/}
            {/*  </IconButton>*/}
            {/*</Tooltip>*/}
            {/*<Tooltip title="Powiadomienia">*/}
            {/*  <IconButton color="default">*/}
            {/*    <Badge badgeContent={3} color="primary">*/}
            {/*      <NotificationsNoneIcon />*/}
            {/*    </Badge>*/}
            {/*  </IconButton>*/}
            {/*</Tooltip>*/}
            <UserAvatar />
          </Box>
        </Toolbar>
      </AppBar>

      {/* Sidebar Drawer */}
      <Drawer
        variant="permanent"
        sx={{
          width: drawerWidth,
          flexShrink: 0,
          [`& .MuiDrawer-paper`]: { width: drawerWidth, boxSizing: 'border-box' },
        }}
      >
        <Toolbar />
        <Box sx={{ overflow: 'auto', position: 'relative', height: '100%' }}>
          <List>
            {renderNavItem(RouteHelper.mainPath.abs(), <DashboardIcon />, 'Dashboard')}
            {renderNavItem(RouteHelper.testPagePath.abs(), <DescriptionIcon />, 'Briefy')}
          </List>

          <Divider />

          {/*<List*/}
          {/*  subheader={*/}
          {/*    <ListSubheader disableSticky sx={{ lineHeight: '32px' }}>*/}
          {/*      OFERTY*/}
          {/*    </ListSubheader>*/}
          {/*  }*/}
          {/*>*/}
          {/*  {renderNavItem(RouteHelper.otherPath.abs(), <WorkOutlineIcon />, 'Wszystkie oferty')}*/}
          {/*  {renderNavItem(RouteHelper.testSubpagePath.abs(), <DraftsIcon />, 'Drafty')}*/}
          {/*  {renderNavItem(RouteHelper.otherPath.abs(), <FactCheckIcon />, 'Do akceptacji')}*/}
          {/*  {renderNavItem(RouteHelper.otherPath.abs(), <SendIcon />, 'Wysłane')}*/}
          {/*</List>*/}

          <Divider />

          <List
            subheader={
              <ListSubheader disableSticky sx={{ lineHeight: '32px' }}>
                ZASOBY
              </ListSubheader>
            }
          >
            {renderNavItem(RouteHelper.servicesPath.abs(), <LibraryBooksIcon />, 'Usługi')}
            {renderNavItem(RouteHelper.otherPath.abs(), <FolderCopyIcon />, 'Case Studies')}
          </List>

          {/* Bottom settings */}
          <Box sx={{ position: 'absolute', bottom: 0, left: 0, right: 0 }}>
            <Divider />
            <List>
              <ListItem disablePadding>
                <ListItemButton
                  sx={(t) => ({
                    mx: 1,
                    mb: 1,
                    borderRadius: 2,
                    bgcolor: alpha(t.palette.primary.main, 0.08),
                    color: t.palette.primary.main,
                    '&:hover': { bgcolor: alpha(t.palette.primary.main, 0.12) },
                  })}
                >
                  <ListItemIcon sx={{ color: 'inherit' }}>
                    <SettingsIcon />
                  </ListItemIcon>
                  <ListItemText primary="Ustawienia" />
                </ListItemButton>
              </ListItem>
            </List>
          </Box>
        </Box>
      </Drawer>

      {/* Content area */}
      <Box component="main" sx={{ flexGrow: 1, p: 3 }}>
        <Toolbar />
        {children}
      </Box>
    </Box>
  );
}
