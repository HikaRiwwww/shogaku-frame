package com.throne.controller;

import com.throne.controller.admin.AdminPageController;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * 拦截大部分请求，并根据url对不同的请求作分发
 */
@WebServlet("/")
public class DispatcherServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        String servletPath = req.getServletPath();
        String method = req.getMethod();

        if ("/admin/addHeadLine".equals(servletPath) && "GET".equals(method)) {
            new AdminPageController().addHeadLine(req, resp);
        } else if ("/admin/removeHeadLine".equals(servletPath) && "GET".equals(method)) {
            new AdminPageController().removeHeadLine(req, resp);
        }
    }
}
