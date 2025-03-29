package servlet.Director;
import dao.DirectorDAO;
import entity.Director;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet({"/Directors/directors", "/Directors/directorselectById", "/Directors/directorupdate", "/Directors/directorfilter", "/Directors/directoradd", "/Directors/directordelete"})
public class DirectorServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //System.out.println("GET");
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("POST");
        String requestURI = request.getRequestURI();
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        if (requestURI.endsWith("/Directors/directors")) {
            displayDirectorList(request, response);
        } else if (requestURI.endsWith("/Directors/directorselectById")) {
            int director_movie_id = Integer.parseInt(request.getParameter("director_movie_id"));
            DirectorDAO directorDAO = new DirectorDAO();
            Director director = directorDAO.selectById(director_movie_id);
            request.setAttribute("user_attribute", director);
            request.getRequestDispatcher("directorupdate.jsp").forward(request, response);
        } else if (requestURI.endsWith("/Directors/directorupdate")) {
            Integer director_movie_id = Integer.parseInt(request.getParameter("director_movie_id"));
            String movie_id = request.getParameter("movie_id");
            String name = request.getParameter("name");
            Director director = new Director();
            director.setDirector_movie_id(director_movie_id);
            director.setMovie_id(movie_id);
            director.setName(name);

            DirectorDAO directorDAO = new DirectorDAO();
            int count = directorDAO.update(director);

            String str = (count > 0) ? "修改信息成功" : "修改信息失败";

            PrintWriter out = response.getWriter();
            out.print("<script>");
            out.print("alert('" + str + "');");
            out.print("location.href='directors'");
            out.print("</script>");
            out.close();
        } else if (requestURI.endsWith("/Directors/directorfilter")) {
            String searchKey = request.getParameter("searchKey");
            String searchValue = request.getParameter("searchValue");

            DirectorDAO dao = new DirectorDAO();
            List<Director> filteredDirectors = dao.filterDirectors(searchKey, searchValue);

            request.setAttribute("FilteredDirectors", filteredDirectors);
            request.getRequestDispatcher("directorfilter.jsp").forward(request, response);
        } else if (requestURI.endsWith("/Directors/directordelete")) {
            int director_movie_id = Integer.parseInt(request.getParameter("director_movie_id"));
            DirectorDAO directorDAO = new DirectorDAO();

            int count = directorDAO.delete(director_movie_id);

            String str = (count > 0) ? "删除成功" : "删除失败";

            PrintWriter out = response.getWriter();
            out.print("<script>");
            out.print("alert('" + str + "');");
            out.print("location.href='directors'");
            out.print("</script>");
            out.close();
        } else {
            throw new IllegalArgumentException("URL不匹配！");
        }
    }

    private void displayDirectorList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DirectorDAO directorDAO = new DirectorDAO();
        List<Director> directorList = directorDAO.getAllDirectors();
        request.setAttribute("DirectorList", directorList);
        request.getRequestDispatcher("directors.jsp").forward(request, response);
    }

}