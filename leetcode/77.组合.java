import java.util.ArrayList;
import java.util.List;

/*
 * @lc app=leetcode.cn id=77 lang=java
 *
 * [77] 组合
 */

// @lc code=start
class Solution {
    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> result = new ArrayList<>();

        this.fun(result, n, k, new ArrayList<>());

        return result;
    }

    private void fun(List<List<Integer>> result, int cur, int len, List<Integer> list) {
        if (cur > 0 && len <= cur) {
            list.add(cur);
            if (len == 1) {
                result.add(new ArrayList<>(list));
            } else {
                this.fun(result, cur - 1, len - 1, list);
            }
            list.remove(list.size() - 1);

            this.fun(result, cur - 1, len, list);
        }
    }
}
// @lc code=end
