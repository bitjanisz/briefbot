import { AuthenticationApi as API, type AuthResponse } from './generated';
import type { UserCredentials, UserRegistration } from 'models/auth.ts';
import { config } from 'api/config.ts';

const api = new API(config);

export const AuthApi = {
  login: (loginRequest: UserCredentials): Promise<AuthResponse> => {
    return api.login({ loginRequest });
  },
  register: (registerUserRequest: UserRegistration): Promise<AuthResponse> => {
    return api.register({ registerUserRequest });
  },
};
