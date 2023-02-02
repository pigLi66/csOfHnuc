export interface LoginForm {
    username: string
    password: string
}

export class LoginData implements LoginForm{
    password = "";
    username = "";
}