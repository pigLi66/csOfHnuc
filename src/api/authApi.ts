import { service } from "@/api/index";
import { LoginForm, LoginResp } from "@/type/api/Auth";

export async function login(data: LoginForm): Promise<LoginResp> {
  return service({
    url: "/auth",
    method: "POST",
    data: JSON.stringify(data),
  }).then((resp) => {
    const data = resp.data;
    return {
      id: data.id,
      name: data.name,
      email: data.email,
      avatar: data.avatar,
      token: data.token,
    };
  });
}

export function loginByGithub(code: string): Promise<LoginResp> {
  return service({
    url: "/auth/github",
    method: "POST",
    data: JSON.stringify({
      code: code,
    }),
  }).then((resp) => {
    const data = resp.data;
    return {
      id: data.id,
      name: data.name,
      email: data.email,
      avatar: data.avatar,
      token: data.token,
    };
  });
}

export async function logout() {
  return service({
    url: "/auth",
    method: "DELETE",
  }).then((resp) => {
    localStorage.removeItem("userId");
    localStorage.removeItem("username");
    localStorage.removeItem("email");
    localStorage.removeItem("avatar");
    localStorage.removeItem("token");
    location.reload()
    return resp;
  });
}
