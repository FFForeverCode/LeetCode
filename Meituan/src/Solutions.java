import java.util.*;

/**
 * @author ForeverCode
 * &#064;date  2024/12/17
 */
public class Solutions {
    //2024-12-17

    /**
     * 复原IP地址
     */
    List<String> ans = new LinkedList<>();
    List<String> path = new ArrayList<>();
    /**
     * 求树的最大路径和
     */
    int ans1 = Integer.MIN_VALUE;
    /**
     * 删除倒数第N个节点
     * 递归实现
     *
     * @param head 头节点
     * @return 返回新的头节点
     */
    int index = 0;

    /**
     * 手撕快速排序
     * 快速排序
     * 1.找到中位节点,左边<中位<右边
     * 2.继续拆分为左右两部分 直至low==high
     * 本质为使得中位左右有序,然后继续递归拆分
     *
     * @param nums 未排序数组
     * @return 已排序数组
     */
    public int[] sortArray(int[] nums) {
        QuickSorted(nums, 0, nums.length - 1);
        return nums;
    }

    private void QuickSorted(int[] nums, int low, int high) {
        if (low < high) {
            int index = getIndex(nums, low, high);
            QuickSorted(nums, low, index - 1);
            QuickSorted(nums, index + 1, high);
        }
    }

    private int getIndex(int[] nums, int low, int high) {
        int i = low - 1;//小于中位的数组的最后一个元素的索引
        int flag = nums[high];
        for (int k = low; k < high; k++) {
            if (nums[k] < flag) {
                i++;
                swap(nums, k, i);
            }
        }
        swap(nums, i + 1, high);
        return i + 1;
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    /**
     * 堆排序
     * 堆排序 首先要建堆,从最后的根节点开始下沉,依次下沉,直至到根节点
     * 此时根节点为最大的元素.
     * 然后从根节点开始依次将根节点置于尾节点处,然后下沉.
     *
     * @param nums 待排序数组
     * @return 排序数组
     */
    public int[] HeapSorted(int[] nums) {
        int Size = nums.length;
        buildMaxHeap(nums, Size);
        for (int i = nums.length - 1; i >= 0; i--) {
            swap(nums, 0, i);
            Size--;
            MaxHeap(nums, 0, Size);
        }
        return nums;
    }

    private void buildMaxHeap(int[] nums, int Size) {
        for (int i = Size / 2 - 1; i >= 0; i--) {
            MaxHeap(nums, i, Size);
        }
    }


    //2024-12-18

    private void MaxHeap(int[] nums, int i, int Size) {
        int l = i * 2 + 1, r = i * 2 + 2, largest = i;
        if (l < Size && nums[l] > nums[largest]) {
            largest = l;
        }
        if (r < Size && nums[r] > nums[largest]) {
            largest = r;
        }
        if (i != largest) {
            swap(nums, i, largest);
            MaxHeap(nums, largest, Size);
        }
    }

    /**
     * 重排链表
     *
     * @param head 头节点
     */
    public void reorderList(ListNode head) {
        Map<Integer, ListNode> map = new HashMap<>();
        int index = 0;
        ListNode dummyNode = new ListNode();
        dummyNode.next = head;
        ListNode cur = head;
        while (cur != null) {
            map.put(index, cur);
            ListNode later = cur.next;
            cur.next = null;
            cur = later;
            index++;
        }
        ListNode pre = dummyNode;
        int start = 0, end = index - 1;
        while (start < end) {
            ListNode node1 = map.get(start);
            ListNode node2 = map.get(end);
            pre.next = node1;
            node1.next = node2;
            pre = node2;
            start++;
            end--;
        }
        if (start == end) {
            pre.next = map.get(start);
        }
        map.get(start).next = null;
    }

    public boolean hasCycle(ListNode head) {
        Set<ListNode> contain = new HashSet<>();
        while (head != null) {
            if (contain.contains(head)) {
                return true;
            }
            contain.add(head);
            head = head.next;
        }
        return false;
    }

    /**
     * 合并两个有序升序链表
     *
     * @param list1 链表1
     * @param list2 链表2
     * @return
     */
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        PriorityQueue<ListNode> queue = new PriorityQueue<>((o1, o2) -> {
            return o1.val - o2.val;
        });
        while (list1 != null) {
            queue.offer(list1);
            list1 = list1.next;
        }
        while (list2 != null) {
            queue.offer(list2);
            list2 = list2.next;
        }
        ListNode dummyNode = new ListNode();
        ListNode pre = dummyNode;
        while (!queue.isEmpty()) {
            pre.next = queue.poll();
            pre = pre.next;
        }
        return dummyNode.next;
    }

    /**
     * 最长 无重复子串
     *
     * @param s 字符串
     * @return 最长的长度
     */
    public int lengthOfLongestSubstring(String s) {
        if (s.length() == 0) return 0;
        int left = 0, right = 0;
        Set<Character> set = new HashSet<>();
        set.add(s.charAt(0));
        int ans = 1;
        for (right = 1; right < s.length(); right++) {
            char c = s.charAt(right);
            while (set.contains(c)) {
                set.remove(s.charAt(left));
                left++;
            }
            set.add(c);
            ans = Math.max(ans, right - left + 1);
        }
        return ans;
    }

    /**
     * 删除所有重复的节点
     *
     * @param head
     * @return
     */
    public ListNode deleteDuplicates(ListNode head) {
        ListNode dummyNode = new ListNode();
        dummyNode.next = head;
        ListNode pre = dummyNode;
        boolean flag = false;
        while (pre.next != null) {
            flag = false;
            ListNode later = pre.next.next;
            while (later != null && later.val == pre.next.val) {
                later = later.next;
                flag = true;
            }
            if (flag) {
                pre.next = later;
            } else {
                pre = pre.next;
            }
        }
        return dummyNode.next;
    }

    /**
     * 三数之和
     *
     * @param nums 数组
     * @return 返回结果
     */
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> ans = new LinkedList<>();
        Arrays.sort(nums);
        for (int i = 0; i < nums.length - 2; i++) {
            if (nums[i] > 0) {
                return ans;
            }
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            int target = -nums[i];
            int right = nums.length - 1;
            for (int left = i + 1; left < nums.length && left < right; left++) {
                if (left > i + 1 && nums[left] == nums[left - 1]) {
                    continue;
                }
                int sum = nums[left] + nums[right];
                while (left < right && sum > target) {
                    right--;
                    sum = nums[right] + nums[left];
                }
                if (left < right && sum == target) {
                    List<Integer> path = new ArrayList<>(List.of(nums[i], nums[left], nums[right]));
                    ans.add(path);
                }
            }
        }
        return ans;
    }

    public List<String> restoreIpAddresses(String s) {
        dfs(s, 0);
        return ans;
    }

    private void dfs(String s, int index) {
        if (path.size() == 4 && index == s.length()) {
            StringBuilder str = new StringBuilder();
            for (String t : path) {
                str.append(t);
                str.append('.');
            }
            str.deleteCharAt(str.length() - 1);
            ans.add(str.toString());
        }
        for (int i = index + 1; i <= s.length(); i++) {
            String sub = s.substring(index, i);
            if (check(sub)) {
                path.add(sub);
                dfs(s, i);
                path.removeLast();
            }
        }
    }

    private boolean check(String sub) {
        if (sub.length() > 1 && sub.charAt(0) == '0') return false;
        int sum = 0;
        for (int i = 0; i < sub.length(); i++) {
            char c = sub.charAt(i);
            if (!Character.isDigit(c)) {
                return false;
            }
            sum *= 10;
            sum += (c - '0');
        }
        return sum <= 255 && sum >= 0;
    }

    public int maxPathSum(TreeNode root) {
        getPath(root);
        return ans1;
    }

    private int getPath(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int left = getPath(root.left);
        int right = getPath(root.right);
        int sum = left + right + root.val;
        ans1 = Math.max(sum, ans1);
        int path = Math.max(left + root.val, right + root.val);
        if (path > 0) {
            return path;
        }
        return 0;
    }

    public ListNode detectCycle(ListNode head) {
        Set<ListNode> set = new HashSet<>();
        ListNode cur = head;
        while (cur != null) {
            if (set.contains(cur)) {
                return cur;
            }
            set.add(cur);
            cur = cur.next;
        }
        return null;
    }

    /**
     * 获取和最大的子串
     * 当字串sum<0时就舍去之前的子串,重新计数
     *
     * @param nums 数组
     * @return 返回结果
     */
    public int getMaxSum(int[] nums) {
        int ans = nums[0];
        int sum = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if (sum < 0) {
                sum = nums[i];
            } else {
                sum += nums[i];
            }
            ans = Math.max(ans, sum);
        }
        return ans;
    }

    /**
     * 有效的括号
     * 栈的使用
     *
     * @param s 字符串
     * @return true or false
     */
    public boolean isValid(String s) {
        List<Character> stack = new LinkedList<>();
        Map<Character, Character> map = new HashMap<>();
        map.put(')', '(');
        map.put('}', '{');
        map.put(']', '[');
        boolean flag = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (isLeft(c)) {
                stack.addLast(c);
            } else {
                if (stack.isEmpty()) {
                    return false;
                } else {
                    char left = stack.removeLast();
                    if (map.get(c) != left) {
                        return false;
                    }
                }
            }
        }
        return stack.isEmpty();
    }

    private boolean isLeft(char c) {
        return c == '(' || c == '{' || c == '[';
    }

    /**
     * 两数之和
     * 枚举:枚举左+遍历寻找右
     *
     * @param nums   数组
     * @param target 目标和
     * @return 返回索引·
     */
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int right = nums[i];
            int left = target - right;
            if (map.containsKey(left)) {
                return new int[]{map.get(left), i};
            }
            map.put(right, i);
        }
        return new int[]{-1, -1};
    }

    /**
     * 二分查找
     * 模板题目
     *
     * @param nums   有序数组
     * @param target 目标值
     * @return 返回索引
     */
    public int search(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = (right - left) / 2 + left;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] > target) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return -1;
    }

    public int myAtio(String s) {
        char[] num = s.trim().toCharArray();
        int res = 0, boundary = Integer.MAX_VALUE / 10;
        int sign = 1, i = 1;
        if (num.length == 0) return 0;
        if (num[0] == '-') sign = -1;
        else if (num[0] != '+') i = 0;
        for (int j = i; j < num.length; j++) {
            if (num[j] < '0' || num[j] > '9') break;
            //注意要提前判断 是否会超过2^32-1 因为超过后会溢出,导致数变小,无法再判断.
            // 例如:8位 11111111+10000000 = 01111111->溢出导致数变小
            if (res > boundary || res == boundary && num[j] > '7') {
                return sign == 1 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
            }
            res = res * 10 + num[j] - '0';
        }
        return sign * res;

    }

    /**
     * 最长回文子串
     *
     * @param s
     * @return
     */
    public String getMaxString(String s) {
        //转移方程:dp[i][j]:i->j的子串是否为回文串
        //dp[i][j]=dp[i+1][j-1]&&charAt[i] == charAt[j]
        //初始化:dp[i][i]=true; dp[i][i+1]=if([i]==[i+1])
        int len = s.length();
        boolean[][] dp = new boolean[len][len];
        for (int i = 0; i < len; i++) {
            dp[i][i] = true;
        }
        int maxLen = 1;
        int begin = 0;
        for (int Len = 2; Len <= len; Len++) {
            for (int left = 0; left < len; left++) {
                int right = left + Len - 1;
                if (right >= len) break;
                if (s.charAt(left) != s.charAt(right)) {
                    dp[left][right] = false;
                } else {
                    if (Len <= 2) {
                        dp[left][right] = true;
                    } else {
                        dp[left][right] = dp[left + 1][right - 1];
                    }
                }
                if (dp[left][right] && Len > maxLen) {
                    maxLen = Len;
                    begin = left;
                }
            }
        }
        return s.substring(begin, begin + maxLen);
    }

    /**
     * k个一组反转链表
     *
     * @param head 头节点
     * @param k    k个
     * @return 返回新的头节点
     */
    public ListNode reverseKthList(ListNode head, int k) {
        ListNode dummyNode = new ListNode();
        dummyNode.next = head;
        ListNode pre = dummyNode;
        ListNode left = head;
        ListNode right = head;
        int size = 1;
        while (right != null) {
            if (size == k) {
                ListNode later = right.next;
                right.next = null;
                pre.next = reverse(left);
                left.next = later;
                pre = left;
                left = later;
                right = later;
                size = 1;
            } else {
                size++;
                right = right.next;
            }
        }
        return dummyNode.next;


    }

    /**
     * 反转链表
     *
     * @param head 头节点
     * @return 返回新的头节点
     */
    private ListNode reverse(ListNode head) {
        ListNode pre = null;
        ListNode cur = head;
        while (cur != null) {
            ListNode later = cur.next;
            cur.next = pre;
            pre = cur;
            cur = later;
        }
        return pre;
    }

    public ListNode removeNthFromEnd(ListNode head, int n) {
        return removeHelp(head, n);
    }

    private ListNode removeHelp(ListNode cur, int n) {
        if (cur == null) {
            return null;
        }
        cur.next = removeHelp(cur.next, n);
        index++;
        if (index == n) {
            return cur.next;
        }
        return cur;
    }

    /**
     * 螺旋矩阵
     *
     * @param matrix 矩阵
     * @return
     */
    public List<Integer> spiralOrder(int[][] matrix) {
        int start = 0, count = 0;
        List<Integer> ans = new ArrayList<>();
        int row = matrix.length;
        int col = matrix[0].length;
        int left = 0, right = col - 1, top = row - 1, bottom = 0;
        while (left <= right && bottom <= top) {
            for (int i = left; i <= right; i++) {
                ans.add(matrix[bottom][i]);
            }
            for (int i = bottom + 1; i <= top - 1; i++) {
                ans.add(matrix[i][right]);
            }
            if (bottom < top) {
                for (int i = right; i >= left; i--) {
                    ans.add(matrix[top][i]);
                }
            }
            if (left < right) {
                for (int i = top - 1; i >= bottom; i--) {
                    ans.add(matrix[i][left]);
                }
            }
            left++;
            right--;
            top--;
            bottom++;
        }
        return ans;
    }

    /**
     * 合并区间
     * 排序+合并区间
     *
     * @param intervals
     * @return
     */
    public int[][] merge(int[][] intervals) {
        Arrays.sort(intervals, (o1, o2) -> {
            return o1[0] - o2[0];
        });
        int left = intervals[0][0];
        int right = intervals[0][1];
        List<int[]> ans = new ArrayList<>();
        for (int i = 1; i < intervals.length; i++) {
            int l = intervals[i][0];
            int r = intervals[i][1];
            if (right < l) {
                int[] path = new int[]{left, right};
                ans.add(path);
                left = l;
                right = r;
            } else {
                right = Math.max(r, right);
                left = Math.min(l, left);
            }
        }
        int[] path = new int[]{left, right};
        ans.add(path);
        return ans.toArray(new int[ans.size()][]);
    }

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
    }

    public class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    /**
     * LRUCache
     */
    private class LRUCache {
        private final int capacity;//容量
        Map<Integer, ListNode> map;//哈希表存储key-ListNode
        List<ListNode> contain = new LinkedList<>();//最近使用的在头部

        public LRUCache(int capacity) {
            this.capacity = capacity;
            map = new HashMap<>();
        }

        public int get(int key) {
            if (map.get(key) != null) {
                ListNode cur = map.get(key);
                contain.remove(cur);
                contain.addFirst(cur);
                return cur.val;
            } else {
                return -1;
            }
        }

        public void put(int key, int value) {
            if (map.containsKey(key)) {
                ListNode cur = map.get(key);
                cur.val = value;
                contain.remove(cur);
                contain.addFirst(cur);
            } else {
                ListNode cur = new ListNode(key, value);
                map.put(key, cur);
                if (contain.size() < capacity) {
                    contain.addFirst(cur);
                } else {
                    ListNode node = contain.getLast();
                    map.remove(node.key);
                    contain.removeLast();
                    contain.addFirst(cur);
                }
            }
        }

        private static class ListNode {
            int val;
            int key;

            public ListNode() {
            }

            public ListNode(int key, int val) {
                this.key = key;
                this.val = val;
            }
        }//节点存储 key-value
    }

    /**
     * 栈实现队列
     * 输出栈、输入栈实现队列
     */
    public class MyQueue {
        Stack<Integer> input = new Stack<>();
        Stack<Integer> output = new Stack<>();

        public MyQueue() {

        }

        public void push(int x) {
            while (!output.isEmpty()) {
                input.push(output.pop());
            }
            input.push(x);
        }

        public int pop() {
            while (!input.isEmpty()) {
                output.push(input.pop());
            }
            int x = output.pop();
            while (!output.isEmpty()) {
                input.push(output.pop());
            }
            return x;

        }

        public int peek() {
            while (!input.isEmpty()) {
                output.push(input.pop());
            }
            int x = output.peek();
            while (!output.isEmpty()) {
                input.push(output.pop());
            }
            return x;

        }

        public boolean empty() {
            return input.isEmpty() && output.isEmpty();

        }
    }

    /**
     * 字符串相加
     * 难点在于处理大数不能用BigInt
     * 超出long的范围的数要用字符串处理
     * @param num1
     * @param num2
     * @return
     */
    public String addString(String num1,String num2){
        char[]nums1 = num1.toCharArray();
        char[]nums2 = num2.toCharArray();
        int left1 = nums1.length-1;
        int left2 = nums2.length-1;
        String[]ans = new String[Math.max(left1,left2)+2];
        Arrays.fill(ans,".");
        int k = ans.length-1;
        while(left1>=0||left2>=0){
            int sum = 0;
            if(left1>=0){
                sum+=nums1[left1]-'0';
            }
            if(left2>=0){
                sum+=nums2[left2]-'0';
            }
            if(!ans[k].equals(".")){
                sum+=Integer.parseInt(ans[k]);
            }
            if(sum>=10){
                int high = sum/10;
                ans[k] = String.valueOf(sum%10);
                ans[k-1] = String.valueOf(high);
            }else{
                ans[k] = String.valueOf(sum);
            }
            k--;
            left1--;
            left2--;
        }
        StringBuilder res = new StringBuilder();
        for(String str:ans){
            if(!str.equals(".")){
                res.append(str);
            }
        }
        return res.toString();

    }

    /**
     * 搜索旋转排序数组
     * 二分法
     * @param nums 旋转数组
     * @param target 目标值
     * @return 索引
     */
    public int searchIndex(int[]nums,int target){
        int left = 0, right = nums.length-1;
        if(nums[nums.length-1]==target) return nums.length-1;
        boolean flag = target - nums[nums.length - 1] > 0;
        while(left<=right){
            int mid = (right - left)/2+left;
            int find = nums[mid];
            if(find == target){
                return mid;
            }else if(flag&&find>target){
                right = mid-1;
            }else if(flag){
                if(find>nums[0]){
                    left = mid+1;
                }else{
                    right = mid-1;
                }
            }else if(find>target){
                if(find>nums[nums.length-1]){
                    left = mid+1;
                }else{
                    right = mid -1;
                }
            }else{
                left = mid+1;
            }

        }
        return -1;
    }

    /**
     * 二叉树的前序遍历
     * mid left right
     * @param root 根节点
     * @return 返回遍历结果
     */
    List<Integer>treeRes = new ArrayList<>();
    public List<Integer> preorderTraversal(TreeNode root){
        travel(root);
        return treeRes;
    }
    private void travel(TreeNode root){
        if(root == null){
            return;
        }
        treeRes.add(root.val);
        travel(root.left);
        travel(root.right);
    }

    /**
     * 寻找最近的公共祖先节点
     */
    TreeNode ancestor = null;
    public TreeNode lowestCommonAncestor(TreeNode root,TreeNode p,TreeNode q){
        dfs(root,p,q);
        return ancestor;
    }
    private boolean dfs(TreeNode root,TreeNode p,TreeNode q){
        if(root == null) return false;
        boolean lSon = dfs(root.left,p,q);
        boolean rSon = dfs(root.right,p,q);
        boolean isParent = (lSon&&rSon)||(root.val == p.val||root.val==q.val)&&(lSon||rSon);
        if(isParent){
            ancestor = root;
        }
        return (lSon||rSon)||(root.val==p.val||root.val == q.val);
    }


}
