export interface LoginForm {
  email: string;
  password: string;
}

export class LoginData implements LoginForm {
  password = "";
  email = "";
}

export interface LoginResp {
  id: string;
  name: string;
  email: string;
  avatar: string;
  token: string;
}
