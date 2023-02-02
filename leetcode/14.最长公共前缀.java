/*
 * @lc app=leetcode.cn id=14 lang=java
 *
 * [14] 最长公共前缀
 */

// @lc code=start
class Solution {
    public String longestCommonPrefix(String[] strs) {
        int i = 0;

        for (boolean isRun = true; isRun; i++) {
            char c = '\0';

            for (String str : strs) {
                if (str.length() <= i) {
                    isRun = false;
                    break;
                } else if (c == '\0') {
                    c = str.charAt(i);
                } else if (c != str.charAt(i)) {
                    isRun = false;
                    break;
                }
            }
        }

        return i <= 1 ? "" : strs[0].substring(0, i - 1);
    }
}
// @lc code=end
