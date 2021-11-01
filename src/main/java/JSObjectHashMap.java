import org.mozilla.javascript.Token;
import org.mozilla.javascript.ast.*;

import java.util.HashMap;

public class JSObjectHashMap extends HashMap<String, Object> {
    private ObjectLiteral objectLiteral;

    public JSObjectHashMap (ObjectLiteral objectLiteral) {
        this.objectLiteral = objectLiteral;
    }

    private String astNodeToString(AstNode node) {
        switch (node.getType()) {
            case Token.NAME:
                return ((Name) node).getIdentifier();
            case Token.STRING:
                return ((StringLiteral) node).getValue();
            case Token.NUMBER:
                return ((NumberLiteral) node).getValue();
            default:
                return null;
        }
    }

    public JSObjectHashMap build() {
        for (ObjectProperty property : objectLiteral.getElements()) {
            AstNode keyNode = property.getLeft();
            AstNode valueNode = property.getRight();

            Object value = valueNode.getType() == Token.OBJECTLIT
                    ? new JSObjectHashMap((ObjectLiteral) valueNode).build()
                    : astNodeToString(valueNode);

            this.put(astNodeToString(keyNode), value);
        }

        return this;
    }
}
