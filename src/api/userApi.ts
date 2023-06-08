import { service } from "@/api/index";
import { RegisterFormReq, RegisterFormResp } from "@/type/User";

export async function login(data: RegisterFormReq): Promise<RegisterFormResp> {
  return service({
    url: "/user",
    method: "PUT",
    data: JSON.stringify(data),
  }).then((resp) => {
    const data: any = resp.data;
    return {
      id: data.id,
      name: data.name,
      email: data.email,
      avatar: data.avatar,
      token: data.token,
    };
  });
}
