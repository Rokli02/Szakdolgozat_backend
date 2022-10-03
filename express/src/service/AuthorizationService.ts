import { Role } from '../entity/Role';
import { User } from '../entity/User';
import { NewUser } from './types';

export interface iAuthorizationService {
  findAllRole(): Promise<Role[]>;
  login(usernameOrEmail: string, rawPassword: string): Promise<string>;
  signup(newUser: NewUser): Promise<boolean>;
  verifyUserToken(token: string): Promise<User>;
  hasRight(user: User, nameOfRight: string): boolean;
}