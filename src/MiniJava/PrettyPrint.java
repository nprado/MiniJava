package MiniJava;

import java.util.*;
import java.io.PrintStream;
import MiniJava.node.*;
import MiniJava.analysis.*;
/*Pretty Print usando a ideia do Tutorial. Percorre a A AST em uma em busca em profundidade
 * Daniel Prado
 * Filipe Berti
 */
public class PrettyPrint extends DepthFirstAdapter
{
    PrintStream out;
    
    private int ident;
    private boolean printSpace;
	
    private void beginNest()
    {
	ident += 4;
    }
	
    private void endNest()
    {
	ident -= 4;
    }
	
    private void print(String s)
    {
	if ( printSpace )
	    for (int i = 0; i < ident; i++ )
		out.print(" ");
	out.print(s);
		
	printSpace = false;
    }
	
    private void println(String s)
    {
	if ( printSpace )
	    for (int i = 0; i < ident; i++ )
		out.print(" ");
	out.println(s);
		
	printSpace = true;
    }
	
    public PrettyPrint(PrintStream p)
    {
	super();
        out = p;
	ident = 0;
	printSpace = true;
    }
    
    public PrettyPrint()
    {
        this(System.out);
    }

    public void inAMainClass(AMainclass node)
    {
	print("class " + node.getFirst().getText());
	println(" {");
	beginNest();
		
	print( "public static void main(String[] " + node.getSecond().getText());
	println( ") {");
	beginNest();
    }

    public void outAMainClass(AMainclass node)
    {
	endNest();
	println("}");
	endNest();
	println("}");
    }

    public void caseAClassDecClassdecl(AClassDecClassdecl node)
    {
        inAClassDecClassdecl(node);
	print("class " + node.getId().getText());
	println( " {" );
	beginNest();
        {
            List<PVardecl> copy = new ArrayList<PVardecl>(node.getVardecl());
            for(PVardecl e : copy)
            {
                e.apply(this);
		println(";");
            }
        }
        {
            List<PMethoddecl> copy = new ArrayList<PMethoddecl>(node.getMethoddecl());
            for(PMethoddecl e : copy)
            {
                e.apply(this);
            }
        }
	endNest();
	println("}");
        outAClassDecClassdecl(node);
    }

    public void caseAExtendsClassDecl(AClassDecExtClassdecl node)
    {
        inAClassDecExtClassdecl(node);
	print("class " + node.getFirst().getText());
	print( " extends " + node.getSecond().getText());
	println(" {");
	beginNest();
        {
            List<PVardecl> copy = new ArrayList<PVardecl>(node.getVardecl());
            for(PVardecl e : copy)
            {
                e.apply(this);
		println(";");
            }
        }
        {
            List<PMethoddecl> copy = new ArrayList<PMethoddecl>(node.getMethoddecl());
            for(PMethoddecl e : copy)
            {
                e.apply(this);
            }
        }
	endNest();
	println("}");
        outAClassDecExtClassdecl(node);
    }

    public void outAVardecl(AVardecl node)
    {
	print(node.getId().getText());
    }

    public void caseAMethoddecl(AMethoddecl node)
    {
        inAMethoddecl(node);
	print("public ");
        if(node.getType() != null)
        {
            node.getType().apply(this);
        }
	print(node.getId().getText());
	print( "(");
	{
	    List<PFormal> copy = new ArrayList<PFormal>(node.getArgs());
	    if(copy.size()>0){
		for(int i = 0; i<copy.size()-1; i++)
		    {
			copy.get(i).apply(this);
			print(", ");
		    }
		copy.get(copy.size()-1).apply(this);
	    }
	}
	print(")");
	println(" {");
	beginNest();
        {
            List<PVardecl> copy = new ArrayList<PVardecl>(node.getVardecl());
            for(PVardecl e : copy)
            {
                e.apply(this);
		println(";");
            }
        }
        {
            List<PStatement> copy = new ArrayList<PStatement>(node.getStatement());
            for(PStatement e : copy)
            {
                e.apply(this);
            }
        }
	print("return ");
        if(node.getExp() != null)
        {
            node.getExp().apply(this);
        }
	println(";");
	endNest();
	println("}");
        outAMethoddecl(node);
    }

    public void inAIntArrayType(AIntArrayType node)
    {
        print("int[] ");
    }

    public void inABoolType(ABoolType node)
    {
        print("boolean ");
    }

    public void inAIntegerType(AIntegerType node)
    {
        print("int ");
    }

    public void inAIdType(AIdType node)
    {
        print(node.getId().getText() + " ");
    }

    public void inAStatementListStatement(AStatementListStatement node)
    {
	println("{");
	beginNest();
    }

    public void outAStatementListStatement(AStatementListStatement node)
    {
	endNest();
	println("}");
    }

    public void caseAIfStatement(AIfStatement node)
    {
        inAIfStatement(node);
	print( "if (" );
        if(node.getExp() != null)
        {
            node.getExp().apply(this);
        }
	print( ") " );
        if(node.getStm1() != null)
        {
            node.getStm1().apply(this);
        }
        if(node.getStm2() != null)
        {
	    print("else ");
            node.getStm1().apply(this);
        }
        outAIfStatement(node);
    }

    public void caseAWhileStatement(AWhileStatement node)
    {
        inAWhileStatement(node);
	print( "while(" );
        if(node.getExp() != null)
        {
            node.getExp().apply(this);
        }
	print( ") " );
        if(node.getStatement() != null)
        {
            node.getStatement().apply(this);
        }
        outAWhileStatement(node);
    }

    public void inAPrintStatement(APrintStatement node)
    {
	print( "System.out.println(" );
    }

    public void outAPrintStatement(APrintStatement node)
    {
	println( ");" );
    }

    public void caseAAssignStatement(AAssignStatement node)
    {
        inAAssignStatement(node);
	print(node.getId().getText() + " = " );
        if(node.getExp() != null)
        {
            node.getExp().apply(this);
        }
	println( ";" );
        outAAssignStatement(node);
    }


    public void caseAAndExp(AAndExp node)
    {
        inAAndExp(node);
        if(node.getLeft() != null)
        {
            node.getLeft().apply(this);
        }
	print( " && ");
        if(node.getRight() != null)
        {
            node.getRight().apply(this);
        }
        outAAndExp(node);
    }

    public void caseALessThanExp(ALessThanExp node)
    {
        inALessThanExp(node);
        if(node.getLeft() != null)
        {
            node.getLeft().apply(this);
        }
	print( " < ");
        if(node.getRight() != null)
        {
            node.getRight().apply(this);
        }
        outALessThanExp(node);
    }

    public void caseAPlusExp(APlusExp node)
    {
        inAPlusExp(node);
        if(node.getLeft() != null)
        {
            node.getLeft().apply(this);
        }
	print( " + ");
        if(node.getRight() != null)
        {
            node.getRight().apply(this);
        }
        outAPlusExp(node);
    }

    public void caseAMinusExp(AMinusExp node)
    {
        inAMinusExp(node);
        if(node.getLeft() != null)
        {
            node.getLeft().apply(this);
        }
	print( " - ");
        if(node.getRight() != null)
        {
            node.getRight().apply(this);
        }
        outAMinusExp(node);
    }

    @Override
    public void caseAMultExp(AMultExp node)
    {
        inAMultExp(node);
        if(node.getLeft() != null)
        {
            node.getLeft().apply(this);
        }
	print( " * ");
        if(node.getRight() != null)
        {
            node.getRight().apply(this);
        }
        outAMultExp(node);
    }


    public void caseAArrayLookupExp(AArrayLookupExp node)
    {
        inAArrayLookupExp(node);
        if(node.getExp() != null)
        {
            node.getExp().apply(this);
        }
	print( "[" );
        if(node.getIndex() != null)
        {
            node.getIndex().apply(this);
        }
	print( "]" );
        outAArrayLookupExp(node);
    }

    public void caseAArrayLengthExp(AArrayLengthExp node)
    {
        inAArrayLengthExp(node);
        if(node.getExp() != null)
        {
            node.getExp().apply(this);
        }
	print(".length");
        outAArrayLengthExp(node);
    }

    public void inANotExpExp(ANotExpExp node)
    {
	print( "!" );
    }

    public void inAIntegerLiteralExp(AIntegerLiteralExp node)
    {
	print(node.getInteger().getText());
    }

    public void inATrueExp(ATrueExp node)
    {
        print("true");
    }

    public void inAFalseExp(AFalseExp node)
    {
        print("false");
    }

    public void inAIdentifierdExp(AIdentifierExp node)
    {
        print(node.getId().getText());
    }

    public void inAThisExp(AThisExp node)
    {
	print("this");
    }

    public void inANewArrayExp(ANewArrayExp node)
    {
	print( "new int[" );
    }

    public void outANewArrayExp(ANewArrayExp node)
    {
	print( "]" );
    }

    public void inANewObjectExp(ANewObjectExp node)
    {
	print( "new " + node.getId().getText() + "()");
    }
}