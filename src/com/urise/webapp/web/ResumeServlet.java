package com.urise.webapp.web;

import com.urise.webapp.Config;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.Storage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ResumeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        PrintWriter writer = response.getWriter();
        Storage storage = Config.get().getSqlStorage();

        writer.write("<body><table border='1'><tr><th>uuid</th><th>fullName</th></tr>");
        for (Resume resume : storage.getAllSorted()) {
            writer.write("<tr><td>" + resume.getUuid() + "</td><td>" + resume.getFullName() + "</td></tr>");
        }
        writer.write("</table></body>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
