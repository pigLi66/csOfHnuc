import java.util.ArrayList;
import java.util.LinkedList;

/*
 * @lc app=leetcode.cn id=40 lang=java
 *
 * [40] 组合总和 II
 */

// @lc code=start
class Solution {
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {

    }

    private void fun(List<List<Integer>> result, int[] candidates, int target, int index, List<Integer> cur) {
        if (index < candidates.length) {
            if (target == candidates[index]) {
                cur = new ArrayList<>(cur);
                cur.add(candidates[index]);
                result.add(cur);
            } else {
                this.fun(result, candidates, target, index + 1, cur);
                cur.add(candidates[index]);
                this.fun(result, candidates, target - candidates[index], index + 1, cur);
            }
        }
    }

}
// @lc code=end
