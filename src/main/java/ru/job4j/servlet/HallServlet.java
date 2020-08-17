package ru.job4j.servlet;

import org.json.JSONObject;
import ru.job4j.model.Place;
import ru.job4j.store.PsqlStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Collectors;

public class HallServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("places", PsqlStore.instOf().findAllPlaces()
                .stream().map(Place::toString)
                .collect(Collectors.toList()));
        PrintWriter printWriter = resp.getWriter();
        printWriter.write(jsonObject.toString());
    }
}
