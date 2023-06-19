export interface RegisterFormReq {
  name: string;
  email: string;
  password: string;
}

export interface RegisterFormResp {
  id: number;
  name: string;
  email: string;
  avatar: string;
  token: string;
}
