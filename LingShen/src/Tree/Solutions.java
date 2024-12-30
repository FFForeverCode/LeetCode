package Tree;

import javax.swing.tree.TreeNode;

public class Solutions {
    private class ListNode{
        public int val;
        public ListNode next;
    }
    private class TreeNode{
        public int val;
        public TreeNode left;
        public TreeNode right;
    }

    /**
     * 二叉树中的链表
     * 本质上为枚举
     * 枚举每个树节点为根开始搜索匹配
     * @param head
     * @param root
     * @return
     */
    public boolean isSubPath(ListNode head, TreeNode root) {
        if(root == null){
            return false;
        }
        return dfs(root,head)||isSubPath(head,root.left)||isSubPath(head,root.right);
    }
    private boolean dfs(TreeNode root,ListNode head){
        if(head == null){
            return true;
        }
        if(root == null){
            return false;
        }
        if(root.val != head.val){
            return false;
        }
        return dfs(root.left,head.next)||dfs(root.right,head.next);
    }
}
