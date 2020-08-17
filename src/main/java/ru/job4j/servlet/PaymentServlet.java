package ru.job4j.servlet;

import org.json.JSONObject;
import ru.job4j.store.PsqlStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Collectors;

public class PaymentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int price = PsqlStore.instOf().findPrice(req.getParameter("name"));
        PrintWriter printWriter = resp.getWriter();
        printWriter.write(new JSONObject().put("price", price).toString());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        JSONObject jsonObject = new JSONObject(req.getReader()
                .lines().collect(Collectors.joining()));
        PsqlStore.instOf().saveTicket(
                jsonObject.getString("username"),
                jsonObject.getString("phone"),
                jsonObject.getString("value")
        );
    }
}
