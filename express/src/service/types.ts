export type FilterFields = (string | { name: string, field: string });
export type NewUser = {
  name: string, 
  birthdate: string, 
  username: string, 
  email: string, 
  password: string
}