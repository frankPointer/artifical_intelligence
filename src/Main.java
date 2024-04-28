import com.decisiontree.ID3;
import com.decisiontree.TreeNode;

import java.io.IOException;

/**
 * @Date: 2024-03-29-11:57
 * @Description:
 */
public class Main {
    public static void main(String[] args) throws IOException {

        String filePath = "resources/decision_tree_origin_data.txt";
        ID3 id3 = new ID3(filePath);

        TreeNode decisionTree = TreeNode.createDecisionTree(id3);

        decisionTree.levelOrderTraversal();

        System.out.println("----------------------------");

        decisionTree.levelOrderTraversalAttributes();
    }
}
