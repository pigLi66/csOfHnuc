import java.util.ArrayList;
import java.util.List;

/*
 * @lc app=leetcode.cn id=39 lang=java
 *
 * [39] 组合总和
 */

// @lc code=start
class Solution {
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        // 设置长度，防止自动扩展，导致时间加长
        List<List<Integer>> result = new ArrayList<>(target + 1);

        this.fun(result, target, candidates, candidates.length - 1, new ArrayList<>());

        return result;
    }

    private void fun(List<List<Integer>> result, int target, int[] arr, int index, List<Integer> last) {
        int curInt = arr[index];

        if (index == 0 || target == 0) {
            if (target % curInt == 0) {
                List<Integer> list = new ArrayList<>(last);
                for (int i = target / curInt; i > 0; i--) {
                    list.add(curInt);
                }
                result.add(list);
            }
        } else if (target > 0) {
            fun(result, target, arr, index - 1, last);

            for (int i = curInt; i <= target; i += curInt) {
                last.add(curInt);
                fun(result, target - i, arr, index - 1, last);
            }

            last.removeIf((item) -> item == curInt);
        }
    }
}
// @lc code=end
