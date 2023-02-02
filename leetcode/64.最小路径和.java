/*
 * @lc app=leetcode.cn id=64 lang=java
 *
 * [64] 最小路径和
 */

// @lc code=start
class Solution {
    public int minPathSum(int[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (i == 0 || j == 0) {
                    if (i == 0 && j == 0) {
                        continue;
                    } else if (i == 0) {
                        grid[i][j] += grid[i][j - 1];
                    } else {
                        grid[i][j] += grid[i - 1][j];
                    }
                } else {
                    grid[i][j] += Math.min(grid[i][j - 1], grid[i - 1][j]);
                }
            }
        }

        return grid[grid.length - 1][grid[grid.length - 1].length - 1];
    }
}
// @lc code=end
