package org.geogebra.common.kernel.arithmetic.vector;

import org.geogebra.common.kernel.StringTemplate;
import org.geogebra.common.kernel.printing.printable.vector.PrintableVector;
import org.geogebra.common.kernel.printing.printer.expression.ExpressionPrinter;
import org.geogebra.common.kernel.printing.printer.Printer;

class CasLatexPrinter implements Printer {

    private PrintableVector vector;

    CasLatexPrinter(PrintableVector vector) {
        this.vector = vector;
    }

    @Override
    public String print(StringTemplate tpl, ExpressionPrinter expressionPrinter) {
        return " \\binom{"
                + expressionPrinter.print(vector.getX(), tpl)
                + "}{"
                + expressionPrinter.print(vector.getY(), tpl)
                + "}";
    }
}
