/*
 * @lc app=leetcode.cn id=402 lang=java
 *
 * [402] 移掉 K 位数字
 */

// @lc code=start
class Solution {
    public String removeKdigits(String num, int k) {
        if (k == 0) {
            return num;
        }
        if (num.length() <= k) {
            return "0";
        }

        if (k > 1) {
            num = removeKdigits(num, k - 1);
        }

        for (int i = 1; i < num.length(); i++) {
            if (num.charAt(i) < num.charAt(i - 1) && (i != 1 || num.charAt(i) != '0')) {
                num = num.substring(0, i - 1) + num.substring(i);
                break;
            }
        }

        return num;
    }
}
// @lc code=end
