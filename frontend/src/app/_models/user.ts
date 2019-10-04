export class User {
  private _id: number;
  private _username: string;
  private _password: string;
  private _firstName: string;
  private _lastName: string;
  private _phoneNumber: string;
  private _userType: string;
  private _email: string;


  get id(): number {
    return this._id;
  }

  set id(value: number) {
    this._id = value;
  }

  get username(): string {
    return this._username;
  }

  set username(value: string) {
    this._username = value;
  }

  get password(): string {
    return this._password;
  }

  set password(value: string) {
    this._password = value;
  }

  get firstName(): string {
    return this._firstName;
  }

  set firstName(value: string) {
    this._firstName = value;
  }

  get lastName(): string {
    return this._lastName;
  }

  set lastName(value: string) {
    this._lastName = value;
  }

  get phoneNumber(): string {
    return this._phoneNumber;
  }

  set phoneNumber(value: string) {
    this._phoneNumber = value;
  }

  get userType(): string {
    return this._userType;
  }

  set userType(value: string) {
    this._userType = value;
  }

  get email(): string {
    return this._email;
  }

  set email(value: string) {
    this._email = value;
  }
}
