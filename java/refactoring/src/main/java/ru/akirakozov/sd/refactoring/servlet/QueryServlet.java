package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.db.Db;
import ru.akirakozov.sd.refactoring.db.DbException;
import ru.akirakozov.sd.refactoring.model.Product;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @author akirakozov
 */
public class QueryServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");
        PrintWriter writer = response.getWriter();

        try {
            switch (command) {
                case "max":
                    Product mostExpensiveProduct = Db.selectLastProductInSorted("PRODUCT", "PRICE");

                    writer.print("<html><body>\n");
                    List.of("Product with max price: ").forEach(header -> writer.println("<h1>" + header + "</h1>"));
                    writer.println(mostExpensiveProduct.toString() + "</br>");
                    writer.print("</body></html>\n");
                    break;
                case "min":
                    Product cheapestProduct = Db.selectFirstProductInSorted("PRODUCT", "PRICE");

                    writer.print("<html><body>\n");
                    List.of("Product with min price: ").forEach(header -> writer.println("<h1>" + header + "</h1>"));
                    writer.println(cheapestProduct.toString() + "</br>");
                    writer.print("</body></html>\n");

                    break;
                case "sum":
                    int summaryPrice = Db.sum("PRODUCT", "PRICE");
                    writer.print("<html><body>\n");
                    writer.println("Summary price: \n" + summaryPrice);
                    writer.print("</body></html>\n");
                    break;
                case "count":
                    int numberOfProducts = Db.count("PRODUCT");
                    writer.print("<html><body>\n");
                    writer.println("Number of products: \n" + numberOfProducts);
                    writer.print("</body></html>\n");
                    break;
                default:
                    writer.println("Unknown command: " + command);
            }

            response.setContentType("text/html");
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (DbException e) {
            System.err.println(e.getMessage());
        }
    }
}
