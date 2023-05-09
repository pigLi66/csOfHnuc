export interface LoginForm {
    email: string
    password: string
}

export class LoginData implements LoginForm{
    password = "";
    email = "";
}