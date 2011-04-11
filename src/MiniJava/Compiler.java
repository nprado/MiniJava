
package MiniJava;
import MiniJava.parser.*;
import MiniJava.lexer.*;
import MiniJava.node.*;
import java.io.*;


/* Funcao Main , Baseada no tutorial oficial do Sablecc 
 * http://sablecc.sourceforge.net/thesis/thesis.html#PAGE28
 * Daniel Prado
 * Filipe Berti
 */
public class Compiler {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			System.out.println("Reading program...");
			
			//Creating a parser instance

            Lexer lexer = new Lexer(
            		new PushbackReader(
            				new BufferedReader(
            						new FileReader(args[0])), 1024));
            Parser p = new Parser(lexer);
			
			
			//Parse input
			
			Start tree = p.parse();
			
			
			//Translate
			
			tree.apply(new PrettyPrint());
			
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

}