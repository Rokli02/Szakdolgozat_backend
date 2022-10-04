import { } from 'ts-jest';

const testConnection = 'testConnection'

describe('UsersRepository', () => {
  let usersRepository: UsersRepository

  beforeEach(async () => {
    const connection = await createConnection({
      type: 'sqlite',
      database: ':memory:',
      dropSchema: true,
      entities: [User],
      synchronize: true,
      logging: false,
      name: testConnection
    })

    usersRepository = connection.getCustomRepository(UsersRepository)
  })

  afterEach(async () => {
    await getConnection(testConnection).close()
  })

  describe('findByEmail', () => {
    it(`should return the user when the user exists in database.`, async () => {
      await usersRepository.createAndSave(testUser)
      const fetchedUser = await usersRepository.findByEmail(testUser.email)
      expect(fetchedUser.email).toEqual(testUser.email)
    })
  })
})