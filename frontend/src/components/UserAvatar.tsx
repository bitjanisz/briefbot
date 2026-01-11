import { Avatar } from '@mui/material';
import type { CSSProperties } from 'react';
import { useAuth } from '../providers/AuthContext';

function computeInitials(givenName?: string, familyName?: string, email?: string): string {
  const g = (givenName || '').trim();
  const f = (familyName || '').trim();
  if (g || f) {
    const first = g ? g.charAt(0) : '';
    const last = f ? f.charAt(0) : '';
    const res = `${first}${last}`.toUpperCase();
    return res || g.slice(0, 2).toUpperCase();
  }
  const username = (email || '').split('@')[0];
  if (!username) return 'NA';
  return username.slice(0, 2).toUpperCase();
}

export function UserAvatar({ style }: { style?: CSSProperties }) {
  const { user } = useAuth();
  const initials = computeInitials(user?.givenName, user?.familyName, user?.email);

  return (
    <Avatar sx={{ bgcolor: 'primary.main' }} style={style}>
      {initials}
    </Avatar>
  );
}
