import { UsersApi as API } from './generated';
import type { UserResponseOverride } from '../models/users';

const api = new API();

export const UsersApi = {
  getLoggedInUser: async (): Promise<UserResponseOverride> => {
    return (await api.getLoggedInUser()) as Promise<UserResponseOverride>;
  },
};
