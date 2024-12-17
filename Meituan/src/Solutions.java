import org.w3c.dom.Node;

import javax.swing.tree.TreeNode;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author ForeverCode
 * @date 2024/12/17
 */
public class Solutions {
    //2024-12-17

    /**
     * 手撕快速排序
     * 快速排序
     * 1.找到中位节点,左边<中位<右边
     * 2.继续拆分为左右两部分 直至low==high
     * 本质为使得中位左右有序,然后继续递归拆分
     * @param nums 未排序数组
     * @return 已排序数组
     */
    public int[] sortArray(int[]nums){
        QuickSorted(nums,0,nums.length-1);
        return nums;
    }
    private void QuickSorted(int[]nums,int low,int high){
        if(low<high){
            int index = getIndex(nums,low,high);
            QuickSorted(nums,low,index-1);
            QuickSorted(nums,index+1,high);
        }
    }
    private int getIndex(int[]nums,int low,int high){
        int i = low-1;//小于中位的数组的最后一个元素的索引
        int flag = nums[high];
        for(int k = low;k<high;k++){
            if(nums[k]<flag){
                i++;
                swap(nums,k,i);
            }
        }
        swap(nums,i+1,high);
        return i+1;
    }
    private void swap(int[]nums,int i,int j){
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    /**
     * 堆排序
     * 堆排序 首先要建堆,从最后的根节点开始下沉,依次下沉,直至到根节点
     * 此时根节点为最大的元素.
     * 然后从根节点开始依次将根节点置于尾节点处,然后下沉.
     * @param nums 待排序数组
     * @return 排序数组
     */
    public int[]HeapSorted(int[]nums){
        int Size = nums.length;
        buildMaxHeap(nums,Size);
        for(int i = nums.length-1;i>=0;i--){
            swap(nums,0,i);
            Size--;
            MaxHeap(nums,0,Size);
        }
        return nums;
    }
    private void buildMaxHeap(int[]nums,int Size){
        for(int i = Size/2-1;i>=0;i--){
            MaxHeap(nums,i,Size);
        }
    }
    private void MaxHeap(int[]nums,int i,int Size){
        int l = i*2+1,r = i*2+2,largest = i;
        if(l<Size&&nums[l]>nums[largest]){
            largest = l;
        }
        if(r<Size&&nums[r]>nums[largest]){
            largest = r;
        }
        if(i != largest){
            swap(nums,i,largest);
            MaxHeap(nums,largest,Size);
        }
    }
    public class ListNode{
        int val;
        ListNode next;
        ListNode(){}
        ListNode(int val){this.val = val;}
        ListNode(int val,ListNode next){this.val = val;this.next = next;}
    }

    /**
     * 重排链表
     * @param head 头节点
     */
    public void reorderList(ListNode head){
        Map<Integer,ListNode>map = new HashMap<>();
        int index = 0;
        ListNode dummyNode = new ListNode();
        dummyNode.next = head;
        ListNode cur = head;
        while(cur!=null){
            map.put(index,cur);
            ListNode later = cur.next;
            cur.next = null;
            cur = later;
            index++;
        }
        ListNode pre = dummyNode;
        int start = 0,end = index-1;
        while(start<end){
            ListNode node1 = map.get(start);
            ListNode node2 = map.get(end);
            pre.next = node1;
            node1.next = node2;
            pre = node2;
            start++;
            end--;
        }
        if(start == end){
            pre.next = map.get(start);
        }
        map.get(start).next = null;
    }

    public boolean hasCycle(ListNode head){
        Set<ListNode>contain = new HashSet<>();
        while(head!=null){
            if(contain.contains(head)){
                return true;
            }
            contain.add(head);
            head = head.next;
        }
        return false;
    }


}
