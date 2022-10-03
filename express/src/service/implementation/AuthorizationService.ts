import { Repository } from 'typeorm';
import mysqlDataSource from '../../data-source';
import { Role } from '../../entity/Role';
import { User } from '../../entity/User';
import { NewUser } from '../types';
import { genSalt, hash, compare } from 'bcrypt';
import { sign, verify } from 'jsonwebtoken';
import { iAuthorizationService } from '../AuthorizationService';
import { throwError } from './utils';

export class AuthorizationSerivce implements iAuthorizationService {
  private repository: Repository<User>;
  private roleRepository: Repository<Role>;
  constructor(
    repository?: Repository<User>,
    roleRepository?: Repository<Role>
  ) {
    this.repository = repository
      ? repository
      : mysqlDataSource.getRepository(User);
    this.roleRepository = roleRepository
      ? roleRepository
      : mysqlDataSource.getRepository(Role);
  }

  findAllRole = async (): Promise<Role[]> => {
    return this.roleRepository.find();
  };

  login = async (
    usernameOrEmail: string,
    rawPassword: string
  ): Promise<string> => {
    const user = await this.repository.findOneBy([
      {
        email: usernameOrEmail,
        active: true,
      },
      {
        username: usernameOrEmail,
        active: true,
      },
    ]);
    if (!user) {
      throwError('404', 'There is no user with such email or username!');
    }

    //Jelszó összehasonlítás
    if (!await compare(rawPassword, user.password)) {
      throwError('400', 'Wrong password!');
    }

    //Token generálás
    const token = sign(
      { id: user.id, username: user.username, email: user.email },
      process.env.JWT_SECRET_KEY,
      { expiresIn: process.env.JWT_EXPIRATION }
    );

    return token;
  };

  signup = async (newUser: NewUser): Promise<boolean> => {
    //Ellenőrzés meg lett-e adva bármi is
    const createdUser = this.repository.create(newUser);
    if (!createdUser) {
      throwError('400', 'No user is given to save!');
    }
    createdUser.id = undefined;

    //Ellenőrzés létezik-e már hasonló user
    const similarUser = await this.repository.findOneBy([
      { email: createdUser?.email },
      { username: createdUser?.username },
    ]);
    if (similarUser) {
      throwError('400', 'There is already a user with such username or email!');
    }

    //Jelszó letitkosítás
    const salt = await genSalt();
    const password = await hash(createdUser?.password, salt);
    if (!password) {
      throwError('400', 'Couldn\'t set password, try again!');
    }

    //Egyéb tulajdonság beállítás
    createdUser.role = await this.roleRepository.findOneBy({ name: 'user' });
    createdUser.active = true;
    createdUser.password = password;

    //Hozzáadás
    const user = await this.repository.save(createdUser);
    if (!user) {
      throwError('400', 'Couldn\'t sign up new user!');
    }

    return true;
  };

  verifyUserToken = async (token: string): Promise<User> => {
    const tokenParts = token.split(' ');
    if(tokenParts[0] !== 'Bearer') {
      throwError('400', 'Invalid token!');
    }

    //Token kibontás - ERROR-t dobhat
    const decodedToken = verify(tokenParts[1], process.env.JWT_SECRET_KEY) as User;

    //Tokenből származó adat ellenőrzés
    const user = await this.repository.findOneBy({
      id: decodedToken.id,
      username: decodedToken.username,
      email: decodedToken.email,
      active: true
    });
    if(!user) {
      throwError('400', 'Token doesn\'t have a valid user!');
    }

    return user;
  };

  hasRight = (user: User, neededRight: string): boolean => {
    if(user.role.name !== neededRight) {
      return false;
    }

    return true;
  }
}
