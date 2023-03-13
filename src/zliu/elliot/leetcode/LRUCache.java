package zliu.elliot.leetcode;

import java.util.HashMap;
import java.util.Map;

public class LRUCache {

    class EntryNode {
        public int key;
        public int value;
        public EntryNode prev;
        public EntryNode next;
        public EntryNode(int key, int value, EntryNode prev, EntryNode next) {
            this.key = key;
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }

    private HashMap<Integer, EntryNode> cache;
    private int capacity;
    private EntryNode header;

    public LRUCache(int capacity) {
        this.cache = new HashMap<>();
        this.capacity = capacity;
        this.header = new EntryNode(-1, -1, null, null);
        this.header.next = this.header;
        this.header.prev = this.header;
    }

    /**
     * put时被调用
     */
    private EntryNode removeLeastRecentlyUsed(){
        if (this.cache.size() < 1) {
            return null;
        } else {
            return header.prev;
        }
    }

    /**
     * 更新双向链表
     * @param node
     */
    private void currentUse(EntryNode node) {
        if (header.next == node) {
            return;
        }
        node.prev.next = node.next;
        node.next.prev = node.prev;

        node.next = header.next;
        node.next.prev = node;
        header.next = node;
        node.prev = header;
    }

    public int get(int key) {
        EntryNode node = this.cache.getOrDefault(key, null);
        if (node != null) {
            currentUse(node);
            return node.value;
        } else {
            return -1;
        }
    }

    public void put(int key, int value) {
        if (this.cache.containsKey(key)) {
            EntryNode entryNode = this.cache.get(key);
            entryNode.value = value;
            currentUse(entryNode);
            return;
        }
        EntryNode oldReuse = null;
        if (this.cache.size() >= capacity) {
            oldReuse = removeLeastRecentlyUsed();
        }
        if (oldReuse != null) {
            this.cache.remove(oldReuse.key);
            oldReuse.key = key;
            oldReuse.value = value;
            this.cache.put(key, oldReuse);
            currentUse(oldReuse);
        } else {
            EntryNode entryNode = new EntryNode(key, value, this.header, this.header.next);
            this.cache.put(key, entryNode);
            this.header.next = entryNode;
            entryNode.next.prev = entryNode;
        }
    }

    public static void main(String[] args) {
        LRUCache lRUCache = new LRUCache(2);
        lRUCache.put(2,1);
        lRUCache.put(1,1);
        lRUCache.put(2,3);
        lRUCache.put(4,1);
        lRUCache.get(1);
        lRUCache.get(2);
    }

}
