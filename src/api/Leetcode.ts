import { GetQuestionOfTodayResp } from "../type/Leetcode";
import { service } from "@/api/index";

export function getQuestionOfToday(): Promise<GetQuestionOfTodayResp> {
  return service({
    url: "/leetcode/questionOfToday",
    method: "GET",
  }).then((resp) => {
    return { question: resp.data.question, state: resp.data.state };
  });
}
