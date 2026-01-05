import { createContext, type PropsWithChildren, use, useEffect, useMemo, useState } from 'react';
import type { AuthUser } from 'models/users.ts';
import { UsersApi } from 'api/users.ts';

// Dependency contract: API layer the provider/service will use
export interface AuthApi {
  getCurrentUser(): Promise<AuthUser>;
  // login(credentials: AuthCredentials): Promise<AuthUser>;
  // logout(): Promise<void>;
}

// UI contract: what components consume from context
interface AuthContextValue {
  user: AuthUser | null;
  loading: boolean;
  // error: string | null;
  // login: (credentials?: Partial<AuthCredentials>) => Promise<void>;
  // logout: () => Promise<void>;
  // refresh: () => Promise<void>;
}

export function AuthProvider({ children }: PropsWithChildren) {
  const [user, setUser] = useState<AuthUser | null>(null);
  const [loading, setLoading] = useState<boolean>(true);

  const refresh = async () => {
    setLoading(true);

    try {
      await new Promise((resolve) => setTimeout(resolve, 2000));
      const current = await UsersApi.getLoggedInUser();
      setUser(current ?? null);
    } catch (e) {
      console.log('eeee', e);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    void refresh();
  }, []);

  const value = useMemo<AuthContextValue>(() => ({ user, loading, refresh }), [user, loading]);

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}

// eslint-disable-next-line react-refresh/only-export-components
export const AuthContext = createContext<AuthContextValue | undefined>(undefined);

// eslint-disable-next-line react-refresh/only-export-components
export function useAuth() {
  const ctx = use(AuthContext);

  if (!ctx) throw new Error('useAuth must be used within AuthProvider');

  return ctx;
}
