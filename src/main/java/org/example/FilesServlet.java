package org.example;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet("/files")
public class FilesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String parameter = req.getParameter("path");
        String openPath;

        if (parameter == null || parameter.isEmpty()) {
            openPath = System.getProperty("user.home");
        } else { openPath = parameter; }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        File file = new File(openPath);
        File[] files = file.listFiles();
        if (files == null) {
            resp.sendError(404, "File not found");
            return;
        }

        SimpleDateFormat fileFormat = new SimpleDateFormat("d/M/yy, K:mm:ss a");
        List<FileInformation> fileInformationList = new ArrayList<>();

        for (File tempFile : files) {
            fileInformationList.add(new FileInformation(tempFile.getName(),
                    openPath + System.getProperty("file.separator") + tempFile.getName(),
                    String.valueOf(tempFile.length()),
                    fileFormat.format(new Date(tempFile.lastModified())),
                    tempFile.isDirectory()));
        }

        req.setAttribute("date", LocalDateTime.now().format(formatter));
        req.setAttribute("back", file.getParent());
        req.setAttribute("name", openPath);
        req.setAttribute("files", fileInformationList);
        req.getRequestDispatcher("WEB-INF/main.jsp").forward(req, resp);
    }
}
