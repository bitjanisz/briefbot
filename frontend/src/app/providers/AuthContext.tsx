import { createContext, useMemo, useState } from 'react';
import type { UserResponseOverride } from 'models/users';
import {UsersApi } from 'api/users';

interface AuthState {
  user: UserResponseOverride | null;
  // loading: boolean;
  // error: string | null;
  // refresh: () => Promise<void>;
  // logout: () => Promise<void>;
}

export const AuthContext = createContext<AuthState | undefined>(undefined);

export const AuthProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const [user] = useState<UserResponseOverride | null>(null);
  // const [loading, setLoading] = useState(true);
  // const [error, setError] = useState<string | null>(null);

  const loadUser = async () => {
   const u = await UsersApi.getLoggedInUser();
   console.log('usss',u)
  };

  // const logout = useCallback(async () => {
  //   try {
  //     await fetch('/logout', { method: 'POST', credentials: 'include' });
  //   } catch (e) {
  //     // ignore network errors on logout
  //   }
  //   setUser(null);
  // }, []);

  const value = useMemo<AuthState>(
    () => ({ user}),
    [user]
  );

  return (
    <AuthContext.Provider value={value}>
      <button
        onClick={() => {
          loadUser();
        }}
      >
        check me
      </button>
      {children}
    </AuthContext.Provider>
  );
};
