import org.mozilla.javascript.CompilerEnvirons;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Parser;
import org.mozilla.javascript.ast.AstRoot;

public class TranslationObjectFinder {
    private String source;
    private String uri;

    public TranslationObjectFinder(String source, String uri) {
        this.source = source;
        this.uri = uri;
    }

    public JSObjectHashMap find() {
        FindTranslationObjectVisitor visitor = new FindTranslationObjectVisitor();

        CompilerEnvirons compilerEnvirons = new CompilerEnvirons();
        compilerEnvirons.setLanguageVersion(Context.VERSION_ES6);

        AstRoot ast = new Parser(compilerEnvirons).parse(this.source, this.uri, 0);

        ast.visitAll(visitor);

        return new JSObjectHashMap(visitor.getTranslationObject()).build();
    }
}
