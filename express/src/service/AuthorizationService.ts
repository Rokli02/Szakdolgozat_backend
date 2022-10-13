import { Role } from '../entity/Role';
import { User } from '../entity/User';
import { LoginData, NewUser } from './types';

export interface iAuthorizationService {
  findAllRole(): Promise<Role[]>;
  /**
   * Ha a megadott paraméter adatokkal sikeresen be lehet jelentkezni, akkor visszatér egy tokennel és a user adataival, különben hibát dob!
   * @param usernameOrEmail Felhasználói név, vagy email cím
   * @param rawPassword Jelszó
   */
  login(usernameOrEmail: string, rawPassword: string): Promise<LoginData>;
  signup(newUser: NewUser): Promise<boolean>;
  /**
   * Megnézi, hogy érvényes-e a token, ha igen visszatér a tokent kiváltó user adataival, különben hibát dob!
   * @param token Token ami két részből áll, ahol a Bearer szó szóköz (' ') karakterrel van elválasztva a tényleges tokentől
   */
  verifyUserToken(token: string): Promise<User>;
  hasRight(user: User, nameOfRight: string): boolean;
}