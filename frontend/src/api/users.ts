import { UsersApi as API } from './generated';
import type { AuthUser } from '../models/users';
import { config } from 'api/config.ts';

const api = new API(config);

export const UsersApi = {
  getLoggedInUser: async (): Promise<AuthUser> => {
    return (await api.getLoggedInUser()) as Promise<AuthUser>;
  },
};
