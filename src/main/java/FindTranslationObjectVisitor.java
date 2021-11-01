import org.mozilla.javascript.Token;
import org.mozilla.javascript.ast.*;

public class FindTranslationObjectVisitor implements  NodeVisitor {
    private ObjectLiteral translationObject;

    public ObjectLiteral getTranslationObject() {
        assert translationObject != null;
        return translationObject;
    }

    public boolean visit(AstNode node) {
        if (node.getType() != Token.VAR) {
            return true;
        }

        VariableDeclaration declaration = (VariableDeclaration) node;

        for (VariableInitializer var : declaration.getVariables()) {
            AstNode name = var.getTarget();

            if (name.getType() == Token.NAME && ((Name) name).getIdentifier().equals("TRANSLATIONS")) {
                translationObject = (ObjectLiteral) var.getInitializer();

                return false;
            }
        }

        return false;
    }
}
